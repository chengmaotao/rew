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
import com.zkr.rew.dict.entity.RewRes;
import com.zkr.rew.dict.service.RewResService;

/**
 * 项目流程结果Controller
 * @author 成茂涛
 * @version 2017-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/rewRes")
public class RewResController extends BaseController {

	@Autowired
	private RewResService rewResService;
	
	@ModelAttribute
	public RewRes get(@RequestParam(required=false) String id) {
		RewRes entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rewResService.get(id);
		}
		if (entity == null){
			entity = new RewRes();
		}
		return entity;
	}
	

	@RequestMapping(value = {"list", ""})
	public String list(RewRes rewRes, HttpServletRequest request, HttpServletResponse response, Model model) {
		rewRes.setCurrentYear(UtilsFns.selectCurrentYear());
		Page<RewRes> page = rewResService.findPage(new Page<RewRes>(request, response), rewRes); 
		model.addAttribute("page", page);
		return "modules/rew/dict/rewResList";
	}

	

	@RequestMapping(value = "form")
	public String form(RewRes rewRes, Model model) {
		model.addAttribute("rewRes", rewRes);
		return "modules/rew/dict/rewResForm";
	}


	@RequestMapping(value = "save")
	public String save(RewRes rewRes, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rewRes)){
			return form(rewRes, model);
		}
		
		rewRes.setCurrentYear(UtilsFns.selectCurrentYear());
		
		// 复审结束  初审肯定也是结束
		if(!StringUtils.equals("0", rewRes.getFsres()) && StringUtils.equals("0", rewRes.getCsres())){
			rewRes.setCsres(rewRes.getFsres());
		}
		
		
		rewResService.save(rewRes);
		
		CacheUtils.remove(UtilsFns.RPOGERSS_REW, UtilsFns.RPOGERSS_REW_RESULT + UtilsFns.selectCurrentYear());
		
		addMessage(redirectAttributes, "保存项目流程结果成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewRes/?repage";
	}
	

	@RequestMapping(value = "delete")
	public String delete(RewRes rewRes, RedirectAttributes redirectAttributes) {
		rewResService.delete(rewRes);
		addMessage(redirectAttributes, "删除项目流程结果成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewRes/?repage";
	}

}