/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.dict.entity.Dmlb;
import com.zkr.rew.dict.service.RewdmlbService;

/**
 * 保存字典Controller
 * 
 * @author 成茂涛
 * @version 2016-08-04
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/dmlb")
public class SysController extends BaseController {

	@Autowired
	private RewdmlbService rewdmlbService;

	@RequestMapping(value = "list")
	@ResponseBody
	public List<Dmlb> dmlbList() {

		return rewdmlbService.findList(null);

	}

	@RequestMapping(value = {"initDmlb", ""})
	public String initDmlb(Model model) {
		return "modules/rew/dict/dmlbList";
	}
	
	
	@RequestMapping(value = "initSyscontrol")
	public String initSysControl(HttpServletRequest request, HttpServletResponse response,Model model,@RequestParam(required=false) String kbn) {
		
		if("1".equals(kbn)){
			model.addAttribute("message", "菜单可用控制保存成功");
		}else{
			model.addAttribute("message", "");
		}
		Dmlb dmlb = new Dmlb();
		dmlb.setGlyRole(UtilsMessage.isGlyRole());
		dmlb.setCurrentYear(UtilsFns.selectCurrentYear());
		
		Page<Dmlb> page = new Page<Dmlb>(request, response);
		dmlb.setPage(page);
		List<Dmlb> firstMenus = rewdmlbService.selectMens(dmlb);
		page.setList(firstMenus);
		model.addAttribute("page", page);
		
		return "modules/rew/dict/controlMenus";
	}
	
	
	@RequestMapping(value = "menuSave")
	public String menuSave(Model model,Dmlb dmlb) {
		
		rewdmlbService.mySave(dmlb);
		
		return "redirect:"+Global.getAdminPath()+"/dict/dmlb/initSyscontrol?kbn=1&repage";
	}
	
	@RequestMapping(value = "currentyear")
	public String currentYear(Model model,Dmlb dmlb) {
		
		String currentyear = UtilsFns.selectCurrentYear();
		
		model.addAttribute("currentyear", currentyear);
		
		return "modules/rew/dict/rewcurrentYearForm";
	}
	
	
	
	@RequestMapping(value = "savecurrentyear")
	public String savecurrentyear(Model model,String currentyear) {
		
		CacheUtils.remove(UtilsFns.PROJECT_CACHE, UtilsFns.CACHE_LISE);
		
		rewdmlbService.savecurrentyear(currentyear);
		CacheUtils.remove(UtilsFns.SYS_VAL, UtilsFns.SYS_VAL_YEAR + "currentyear");
		model.addAttribute("currentyear", currentyear);
		
	    model.addAttribute("message", "评审年度保存成功");
		return "modules/rew/dict/rewcurrentYearForm";
	}
	
	
	@RequestMapping(value = "inittpcontrol")
	public String inittpcontrol(HttpServletRequest request, HttpServletResponse response,Model model,@RequestParam(required=false) String kbn) {
		
		if("1".equals(kbn)){
			model.addAttribute("message", "投票(补投)可用控制保存成功");
		}else{
			model.addAttribute("message", "");
		}

		List<Dmlb> tpcontrols = rewdmlbService.tpcontrolMenus();

		model.addAttribute("tpcontrols", tpcontrols);
		return "modules/rew/dict/tpcontrolMenus";
	}
	
	@RequestMapping(value = "tpmenuSave")
	public String tpmenuSave(Model model,Dmlb dmlb) {
		
		rewdmlbService.tpmenuSave(dmlb);
		
		return "redirect:"+Global.getAdminPath()+"/dict/dmlb/inittpcontrol?kbn=1&repage";
	}
	
	
	// 初审 复审 菜单显示控制 开始
	/**
	 * 初审 复审 菜单显示
	 * @param request
	 * @param response
	 * @param model
	 * @param kbn
	 * @return
	 */
	@RequestMapping(value = "cfcontrol")
	public String cfcontrol(Model model) {
		

        // 0 初审显示   1复审显示  其他都显示
		String currentval = rewdmlbService.cfcontrol();

		model.addAttribute("currentval", currentval);
		return "modules/rew/dict/cfcontrolForm";
	}
	
	@RequestMapping(value = "savecfcontrol")
	public String savecfcontrol(Model model,String currentval) {
		

		rewdmlbService.savecfcontrol(currentval);
		
		model.addAttribute("currentval", currentval);
		
	    model.addAttribute("message", "保存成功");
		return "modules/rew/dict/cfcontrolForm";
	}
	
	
	// 初审 复审 菜单显示控制 结束
}