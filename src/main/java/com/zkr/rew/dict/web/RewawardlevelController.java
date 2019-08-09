/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.dict.entity.Rewawardlevel;
import com.zkr.rew.dict.service.RewawardlevelService;

/**
 * 保存字典Controller
 * @author 成茂涛
 * @version 2016-08-04
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/rewawardlevel")
public class RewawardlevelController extends BaseController {

	@Autowired
	private RewawardlevelService rewawardlevelService;
	
	@ModelAttribute
	public Rewawardlevel get(@RequestParam(required=false) String id) {
		Rewawardlevel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rewawardlevelService.get(id);
		}
		if (entity == null){
			entity = new Rewawardlevel();
		}
		return entity;
	}
	

	@RequestMapping(value = {"list", ""})
	public String list(Rewawardlevel rewawardlevel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Rewawardlevel> page = rewawardlevelService.findPage(new Page<Rewawardlevel>(request, response), rewawardlevel); 
		model.addAttribute("page", page);
		return "modules/rew/dict/rewawardlevelList";
	}


	@RequestMapping(value = "form")
	public String form(Rewawardlevel rewawardlevel, Model model) {
		model.addAttribute("rewawardlevel", rewawardlevel);
		return "modules/rew/dict/rewawardlevelForm";
	}


	@RequestMapping(value = "save")
	public String save(Rewawardlevel rewawardlevel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rewawardlevel)){
			return form(rewawardlevel, model);
		}
		rewawardlevelService.save(rewawardlevel);
		
        // 清空缓存
		UtilsFns.clearCacheById(UtilsFns.AWARD_CACHE, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.AWARD_CACHE, UtilsFns.AWARD_CACHE_ID + rewawardlevel.getId());
		
		addMessage(redirectAttributes, "保存奖励等级成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewawardlevel/?repage";
	}
	

	@RequestMapping(value = "delete")
	public String delete(Rewawardlevel rewawardlevel, RedirectAttributes redirectAttributes) {
		rewawardlevelService.delete(rewawardlevel);
		
        // 清空缓存
		UtilsFns.clearCacheById(UtilsFns.AWARD_CACHE, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.AWARD_CACHE, UtilsFns.AWARD_CACHE_ID + rewawardlevel.getId());
		
		addMessage(redirectAttributes, "删除奖励等级成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewawardlevel/?repage";
	}

}