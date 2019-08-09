/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.dict.entity.RewFsdatasrc;
import com.zkr.rew.dict.entity.Rewawardlevel;
import com.zkr.rew.dict.entity.Rewproject;
import com.zkr.rew.dict.service.RewFsdatasrcService;

/**
 * 复审数据来源Controller
 * @author 成茂涛
 * @version 2017-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/rewFsdatasrc")
public class RewFsdatasrcController extends BaseController {

	@Autowired
	private RewFsdatasrcService rewFsdatasrcService;
	
	@ModelAttribute
	public RewFsdatasrc get(@RequestParam(required=false) String id) {
		RewFsdatasrc entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rewFsdatasrcService.get(id);
		}
		if (entity == null){
			entity = new RewFsdatasrc();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(RewFsdatasrc rewFsdatasrc, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (StringUtils.isBlank(rewFsdatasrc.getCurrentYear())) {
			rewFsdatasrc.setCurrentYear(UtilsFns.selectCurrentYear());
		} else if ("yyyy".equals(rewFsdatasrc.getCurrentYear())) {
			rewFsdatasrc.setCurrentYear(null);
		}
		
		model.addAttribute("yearList", UtilsFns.selectYearList());
		
		// 奖项类别
		List<Rewawardlevel> awardLevelList2 = UtilsFns.getAwardLevelList();
		List<Rewawardlevel> awardLevelList = new ArrayList<Rewawardlevel>(awardLevelList2);
		Rewawardlevel r = new Rewawardlevel();
		r.setId("");
		r.setLevelname("所有");
		awardLevelList.add(r);

		model.addAttribute("awardLevelList", awardLevelList);

		
		Page<RewFsdatasrc> page = rewFsdatasrcService.findPage(new Page<RewFsdatasrc>(request, response), rewFsdatasrc); 
		model.addAttribute("page", page);
		return "modules/rew/dict/rewFsdatasrcList";
	}





	@RequestMapping(value = "save")
	public String save(RewFsdatasrc rewFsdatasrc, Model model, RedirectAttributes redirectAttributes) {

		rewFsdatasrcService.save(rewFsdatasrc);
		addMessage(redirectAttributes, "保存复审数据来源成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewFsdatasrc/?repage";
	}
	

	@RequestMapping(value = "delete")
	public String delete(RewFsdatasrc rewFsdatasrc, RedirectAttributes redirectAttributes) {
		rewFsdatasrcService.delete(rewFsdatasrc);
		addMessage(redirectAttributes, "删除复审数据来源成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewFsdatasrc/?repage";
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
			List<RewFsdatasrc> list = ei.getDataList(RewFsdatasrc.class);
			String projectId = "";
			String projectname = "";
			for (RewFsdatasrc t : list) {
				try {
					projectname = t.getProjectName().trim();
					projectId = getProjectIdByProjectName(projectname);
					if(StringUtils.equals("", projectId)){
						failureMsg.append("<br/>项目名称 " + projectname + " 不存在; ");
						failureNum++;
					}else{
						if(checkProjectId(projectId)){
							t.setProjectid(projectId);
							t.setCurrentYear(UtilsFns.selectCurrentYear());
							rewFsdatasrcService.save(t);
							successNum++;
						}else{
							failureMsg.append("<br/>项目名称 " + projectname + " 在复审项目中已经存在; ");
							failureNum++;
						}
					}
					
				}  catch (Exception ex) {
					failureMsg.append("<br/>项目名称 " + projectname + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条项目，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条项目" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入项目失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/dict/rewFsdatasrc/list?repage";
	}
	
	private String getProjectIdByProjectName(String projectName){
		Rewproject project = UtilsFns.getProjectByProjectName(projectName);
		if(project != null){
			return project.getProjectid();
		}else{
			return "";
		}
		
	}
	
	
	private boolean checkProjectId(String projectId) {

		RewFsdatasrc p = new RewFsdatasrc();
		p.setProjectid(projectId);
		p.setCurrentYear(UtilsFns.selectCurrentYear());
		
		RewFsdatasrc fsdatasrc = rewFsdatasrcService.getRewFsdatasrcByProjectId(p);
		
		if(fsdatasrc == null){
			return true;
		}
		return false;
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
			String fileName = "复审项目数据导入模板.xlsx";

			List<RewFsdatasrc> list = Lists.newArrayList();
			//
			//list.add(rewFsdatasrcService.findImportTempData());

			new ExportExcel("项目数据", RewFsdatasrc.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/dict/rewFsdatasrc/list?repage";
	}

}