/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.dict.entity.Rewproject;
import com.zkr.rew.dict.service.RewprojectService;

/**
 * 保存字典Controller
 * 
 * @author 成茂涛
 * @version 2016-08-05
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/rewproject")
public class RewprojectController extends BaseController {

	@Autowired
	private RewprojectService rewprojectService;

	@ModelAttribute
	public Rewproject get(@RequestParam(required = false) String id) {
		Rewproject entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = rewprojectService.get(id);
		}
		if (entity == null) {
			entity = new Rewproject();
			entity.setCurrentYear(UtilsFns.selectCurrentYear());
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(Rewproject rewproject, HttpServletRequest request, HttpServletResponse response, Model model) {

		if ("".equals(rewproject.getCurrentYear())) {
			rewproject.setCurrentYear(UtilsFns.selectCurrentYear());
		} else if ("yyyy".equals(rewproject.getCurrentYear())) {
			rewproject.setCurrentYear(null);
		}

		Page<Rewproject> page = rewprojectService.findPage(new Page<Rewproject>(request, response), rewproject);
		model.addAttribute("page", page);

		model.addAttribute("groups", UtilsFns.getEnableGroupList());

		model.addAttribute("categorys", UtilsFns.getCagegoryList());

		model.addAttribute("companys", UtilsFns.findAllCompany());

		model.addAttribute("yearList", UtilsFns.selectYearList());

		return "modules/rew/dict/rewprojectList";
	}

	@RequestMapping(value = "form")
	public String form(Rewproject rewproject, Model model) {

		model.addAttribute("rewproject", rewproject);

		// 专业组List
		model.addAttribute("groupList", UtilsFns.getEnableGroupList());

		model.addAttribute("categorys", UtilsFns.getCagegoryList());

		model.addAttribute("companys", UtilsFns.findAllCompany());

		return "modules/rew/dict/rewprojectForm";
	}

	@RequestMapping(value = "save")
	public String save(Rewproject rewproject, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rewproject)) {
			return form(rewproject, model);
		}
		rewproject.setKbn("1");
		
		rewprojectService.save(rewproject);
		// 清空缓存
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE_GROUP, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE, UtilsFns.PROJECT_CACHE_ID + rewproject.getId());
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE, UtilsFns.PROJECT_CACHE_PROJECTID + rewproject.getProjectid());

		addMessage(redirectAttributes, "保存项目成功");
		return "redirect:" + Global.getAdminPath() + "/dict/rewproject/?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(Rewproject rewproject, RedirectAttributes redirectAttributes) {
		rewprojectService.delete(rewproject);

		// 清空缓存
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE_GROUP, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE, UtilsFns.PROJECT_CACHE_ID + rewproject.getId());
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE, UtilsFns.PROJECT_CACHE_PROJECTID + rewproject.getProjectid());
		addMessage(redirectAttributes, "删除项目成功");
		return "redirect:" + Global.getAdminPath() + "/dict/rewproject/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "checkProjectId")
	public String checkProjectId(String projectid, String oldProjectId) {

		if (oldProjectId != null && oldProjectId.equals(projectid)) {
			return "true";
		}
		Rewproject project = UtilsFns.getProjectByProjectId(projectid);
		if (projectid != null && project == null) {
			return "true";
		}
		return "false";
	}

	
	/**
	 * 导入项目数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Rewproject> list = ei.getDataList(Rewproject.class);
			for (Rewproject rewProject : list) {
				try {
					rewProject.setProjectid(rewProject.getProjectid() + UtilsFns.selectCurrentYear());
					if ("true".equals(checkProjectId(rewProject.getProjectid(), ""))) {
						BeanValidators.validateWithException(validator, rewProject);

						rewprojectService.save(rewProject);
						successNum++;
					} else {
						failureMsg.append("<br/>项目编号 " + rewProject.getProjectid() + " 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>项目编号 " + rewProject.getProjectid() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>项目编号 " + rewProject.getProjectid() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条项目，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条项目" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入项目失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/dict/rewproject/list?repage";
	}

	/**
	 * 下载导入项目数据模板
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "项目数据导入模板.xlsx";

			List<Rewproject> list = Lists.newArrayList();
			//
			list.add(rewprojectService.findImportTempData());

			new ExportExcel("项目数据", Rewproject.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/dict/rewproject/list?repage";
	}

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = UtilsFns.findAllCompany();
		for (int i = 0; i < list.size(); i++) {
			Office e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}

	@ResponseBody
	@RequestMapping(value = "treeDataByGroup")
	public List<Map<String, Object>> treeDataByGrou(@RequestParam(required = false) String kbn) {
		List<Map<String, Object>> mapList = Lists.newArrayList();

		List<Rewproject> list = new ArrayList<Rewproject>();
		// 是管理员 查看所有的项目， 不是管理员 只能查看本组的项目
		if (UtilsMessage.isGlyRole() || "csdeltp".equals(kbn)) {
			list = UtilsFns.getProjectList();
		} else if ("fsdeltp".equals(kbn)) {
			list = rewprojectService.findProjectListNotInparametes();
		} else {
			list = UtilsFns.getProjectListByGroup();
		}
		for (int i = 0; i < list.size(); i++) {
			Rewproject e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getProjectid());
			map.put("name", e.getProjectname());
			mapList.add(map);
		}
		return mapList;
	}

	// 根据项目ID获得项目信息
	@ResponseBody
	@RequestMapping(value = "getProjectInfoByProjectId")
	public Rewproject getProjectInfoByProjectId(String projectId) {
		return rewprojectService.getProjectInfoByProjectId(projectId);
	}

}