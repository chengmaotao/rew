/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.dict.entity.RewProgress;
import com.zkr.rew.dict.service.RewProgressService;

/**
 * 项目流程Controller
 * @author 成茂涛
 * @version 2017-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/rewProgress")
public class RewProgressController extends BaseController {

	@Autowired
	private RewProgressService rewProgressService;
	
	@ModelAttribute
	public RewProgress get(@RequestParam(required=false) String id) {
		RewProgress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rewProgressService.get(id);
		}
		if (entity == null){
			entity = new RewProgress();
		}
		return entity;
	}
	
	// 项目流程
	@RequestMapping(value = "indexProgress")
	public String indexProgress(RewProgress rewProgress, Model model) {
		model.addAttribute("rewProgress", UtilsFns.getProgerss());
		
		return "modules/rew/dict/rewProgressForm";
	}
	
	
	// 项目流程保存
	@RequestMapping(value = "saveProgerss")
	public String savecurrentyear(Model model,String rewProgress) {
		rewProgressService.saveProgerss(rewProgress);
		CacheUtils.remove(UtilsFns.RPOGERSS_REW, UtilsFns.RPOGERSS_REW_VAVLE);
		model.addAttribute("rewProgress", UtilsFns.getProgerss());
		
	    model.addAttribute("message", "项目流程保存成功");
		return "modules/rew/dict/rewProgressForm";
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(RewProgress rewProgress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RewProgress> page = rewProgressService.findPage(new Page<RewProgress>(request, response), rewProgress); 
		model.addAttribute("page", page);
		return "rew/dict/rewProgressList";
	}

	@RequestMapping(value = "form")
	public String form(RewProgress rewProgress, Model model) {
		model.addAttribute("rewProgress", rewProgress);
		return "rew/dict/rewProgressForm";
	}

	@RequestMapping(value = "save")
	public String save(RewProgress rewProgress, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rewProgress)){
			return form(rewProgress, model);
		}
		rewProgressService.save(rewProgress);
		addMessage(redirectAttributes, "保存项目流程成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewProgress/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(RewProgress rewProgress, RedirectAttributes redirectAttributes) {
		rewProgressService.delete(rewProgress);
		addMessage(redirectAttributes, "删除项目流程成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewProgress/?repage";
	}

}