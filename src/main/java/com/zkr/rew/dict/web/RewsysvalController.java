/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.dict.entity.Rewsysval;
import com.zkr.rew.dict.service.RewsysvalService;

/**
 * 历年投票数目控制Controller
 * @author 成茂涛
 * @version 2017-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/rewsysval")
public class RewsysvalController extends BaseController {

	@Autowired
	private RewsysvalService rewsysvalService;
	
	@ModelAttribute
	public Rewsysval get(@RequestParam(required=false) String id) {
		Rewsysval entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rewsysvalService.get(id);
		}
		if (entity == null){
			entity = new Rewsysval();
		}
		return entity;
	}
	

	@RequestMapping(value = {"list", ""})
	public String list(Rewsysval rewsysval, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		rewsysval.setCurrentYear(UtilsFns.selectCurrentYear());
		Page<Rewsysval> page = rewsysvalService.findPage(new Page<Rewsysval>(request, response), rewsysval); 
		model.addAttribute("page", page);
		return "modules/rew/dict/rewsysvalList";
	}


	@RequestMapping(value = "form")
	public String form(Rewsysval rewsysval, Model model) {
		model.addAttribute("rewsysval", rewsysval);
		return "modules/rew/dict/rewsysvalForm";
	}

	@RequestMapping(value = "save")
	public String save(Rewsysval rewsysval, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rewsysval)){
			return form(rewsysval, model);
		}
		rewsysval.setCurrentYear(UtilsFns.selectCurrentYear());
		rewsysvalService.save(rewsysval);
		CacheUtils.remove(UtilsFns.SYS_VAL, UtilsFns.SYS_VAL_YEAR + UtilsFns.selectCurrentYear());
		addMessage(redirectAttributes, "保存投票数目成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewsysval/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Rewsysval rewsysval, RedirectAttributes redirectAttributes) {
		rewsysvalService.delete(rewsysval);
		addMessage(redirectAttributes, "删除投票数目成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewsysval/?repage";
	}

}