/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.zszj.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.dict.entity.Dmlb;
import com.zkr.rew.dict.entity.Rewproject;
import com.zkr.rew.zszj.entity.Rewidea;
import com.zkr.rew.zszj.service.RewideaService;

/**
 * 主审专家Controller
 * 
 * @author 成茂涛
 * @version 2016-08-10
 */
@Controller
@RequestMapping(value = "${adminPath}/zszj/rewidea")
public class RewideaController extends BaseController {

	@Autowired
	private RewideaService rewideaService;

	@ModelAttribute
	public Rewidea get(@RequestParam(required = false) String id) {
		Rewidea entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = rewideaService.get(id);
			
/*			if(entity.getGroupSonName() != null && !"".equals(entity.getGroupSonName())){
				entity.setGroupName(entity.getGroupName() + "--" + entity.getGroupSonName());
			}*/
		}
		if (entity == null) {
			entity = new Rewidea();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(Rewidea rewidea, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		rewidea.setGlyRole(UtilsMessage.isGlyRole());
		// rewidea.setZyzzRole(UtilsMessage.isZyzzRole()); //专业组长
		rewidea.setUserid(UserUtils.getUser().getId());
		
		Page<Rewidea> page = rewideaService.findzxPage(new Page<Rewidea>(request, response), rewidea);
		model.addAttribute("page", page);
		
		User user = UserUtils.getUser();
		Dmlb dmlb = new Dmlb("ea54ef1bf418490e99807899ff1e96b4", user.getGroupId(), null);
		model.addAttribute("zyzz",UtilsMessage.isZyzzRole());
		
		model.addAttribute("projects", UtilsFns.getProjects(""));
		// 主审专家  可用
		if(UtilsFns.isMenuEnable(dmlb) && UtilsFns.getCsProgressResIsEnable()){
			model.addAttribute("isShow", UtilsMessage.isGlyRole());
			return "modules/rew/zszj/rewideaList";
		}else{
		  //不可用
			model.addAttribute("rewFoot", UtilsMessage.getValueByKey("disListMes"));
			return "modules/rew/zszj/rewideaDisList";
		}
	}
	
	@RequestMapping(value = "form")
	public String form(Rewidea rewidea, Model model,String projectId) {
		
		model.addAttribute("rewidea", rewidea);
		
		Rewproject project = UtilsFns.getProjectByProjectId(projectId);
		

		project.setGroupName(UtilsFns.getGroupById(project.getGroupId()).getGroupname());
	
		
		// 项目对象
		model.addAttribute("project", project);
		
		
		// 奖励等级
		model.addAttribute("levelList", UtilsFns.getAwardLevelList());
		return "modules/rew/zszj/rewideaForm";
	}

	@RequestMapping(value = "save")
	public String save(Rewidea rewidea, Model model, RedirectAttributes redirectAttributes) {
		
		User user = UserUtils.getUser();
		Dmlb dmlb = new Dmlb("ea54ef1bf418490e99807899ff1e96b4", user.getGroupId(), null);
		// 主审专家  不可用
		if(!UtilsFns.isMenuEnable(dmlb) || !UtilsFns.getCsProgressResIsEnable()){
			return "redirect:" + Global.getAdminPath() + "/zszj/rewidea/?repage";
		}
		
		if (!beanValidator(model, rewidea)) {
			return form(rewidea, model,"");
		}
		rewideaService.save(rewidea);
		addMessage(redirectAttributes, "保存评审意见成功");
		return "redirect:" + Global.getAdminPath() + "/zszj/rewidea/?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(Rewidea rewidea, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		Dmlb dmlb = new Dmlb("ea54ef1bf418490e99807899ff1e96b4", user.getGroupId(), null);
		// 主审专家  不可用
		if(!UtilsFns.isMenuEnable(dmlb) || !UtilsFns.getCsProgressResIsEnable()){
			return "redirect:" + Global.getAdminPath() + "/zszj/rewidea/?repage";
		}
		rewideaService.delete(rewidea);
		addMessage(redirectAttributes, "删除评审意见成功");
		return "redirect:" + Global.getAdminPath() + "/zszj/rewidea/?repage";
	}

	@RequestMapping(value = "projectMess")
	@ResponseBody
	public Map<String, String> getProjectMess(String projectId) {
		Map<String, String> res = new HashMap<String, String>();
		
		Rewproject project = UtilsFns.getProjectByProjectId(projectId);
		
		// 专业组
		StringBuffer group = new StringBuffer();
		group.append(UtilsFns.getGroupById(project.getGroupId()).getGroupname());
		
		// 专业组
		res.put("group", group.toString());
		// 申报单位
		res.put("company", UtilsFns.getOfficeById(project.getCompanyId()).getName());
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "checkProjectId")
	public String checkProjectId(String projectid,String oldProjectId) {
		
		if (oldProjectId != null && oldProjectId.equals(projectid)) {
			return "true";
		}
		Rewidea project = UtilsFns.getIdeaByProjectId(projectid);
		if (projectid != null && project == null) {
			return "true";
		}
		return "false";
	}

	
	
	//主审专家 对应项目列表
	@RequestMapping(value = "zxlist")
	public String zxlist(Rewidea rewidea, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		
		Page<Rewidea> page = rewideaService.findzxPage(new Page<Rewidea>(request, response), rewidea);
		model.addAttribute("page", page);
		

		return "modules/rew/dict/rewzxList";
	}
	
	
	@RequestMapping(value = "zxform")
	public String zxform(Rewidea rewidea, Model model,Rewproject rewproject) {
		
        // 主审专家
		 List<User> zszjList = UserUtils.selectZszjList();
		 model.addAttribute("zszjList", zszjList);

		// 项目名称
		 rewproject.setCurrentYear(UtilsFns.selectCurrentYear());
		 List<Rewproject> zszjProject = UtilsFns.selectZszjProjectList(rewproject);
		 model.addAttribute("zszjProject", zszjProject);
		
		return "modules/rew/dict/rewzxForm";
	}
	
	
	@RequestMapping(value = "zxsave")
	public String zxsave(Rewidea rewidea, Model model, RedirectAttributes redirectAttributes) {
		
		
		if (!beanValidator(model, rewidea)) {
			return form(rewidea, model,"");
		}
		rewideaService.zxsave(rewidea);
		addMessage(redirectAttributes, "保存主审专家对应的项目成功");
		return "redirect:" + Global.getAdminPath() + "/zszj/rewidea/zxlist?repage";
	}
	
	
	@RequestMapping(value = "zxdelete")
	public String zxdelete(Rewidea rewidea, RedirectAttributes redirectAttributes) {

		rewideaService.zxdelete(rewidea);
		addMessage(redirectAttributes, "删除主审专家对应的项目成功");
		return "redirect:" + Global.getAdminPath() + "/zszj/rewidea/zxlist?repage";
	}
	
	
/*	@ResponseBody
	@RequestMapping(value = "zszjtreeData")
	public List<Map<String, Object>> zszjtreeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();

		List<User> list = new ArrayList<User>();

		list = UserUtils.selectZszjList();

		for (int i = 0; i < list.size(); i++) {
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}*/
	
	@ResponseBody
	@RequestMapping(value = "xmmctreeData")
	public List<Rewproject> xmmctreeData(String id) {
		
		if(StringUtils.isNotBlank(id)){
			User user = UserUtils.get(id);
			
			Rewproject project = new Rewproject();
			project.setGroupId(user.getGroupId());
			//project.setGroupSonId(user.getGroupSonId());
			project.setCurrentYear(UtilsFns.selectCurrentYear());
			List<Rewproject> list = UtilsFns.selectZszjProjectList(project);
			
			return list;
		}else{
			Rewproject project = new Rewproject();
			project.setCurrentYear(UtilsFns.selectCurrentYear());
			List<Rewproject> list = UtilsFns.selectZszjProjectList(project);
			return list;
		}
		
	}
	
	@RequestMapping(value = { "pintIdea"})
	public String pintIdea(Rewidea rewidea, Model model,String projectId){
		model.addAttribute("rewidea", rewidea);
		
		Rewproject project = UtilsFns.getProjectByProjectId(projectId);
		
		project.setGroupName(UtilsFns.getGroupById(project.getGroupId()).getGroupname());
			
		// 项目对象
		model.addAttribute("project", project);
		
		// 奖励等级
		model.addAttribute("levelList", UtilsFns.getAwardLevelList());
		
		return "modules/rew/zszj/rewideaPrint";
		
	}
}