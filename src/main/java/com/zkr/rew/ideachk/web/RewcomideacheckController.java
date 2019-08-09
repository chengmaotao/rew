/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.ideachk.web;

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
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.dict.entity.Dmlb;
import com.zkr.rew.ideachk.entity.Rewcomideacheck;
import com.zkr.rew.ideachk.service.RewcomideacheckService;
import com.zkr.rew.zyzz.service.RewrecomideaService;

/**
 * 推荐意见投票Controller
 * @author 成茂涛
 * @version 2016-08-18
 */
@Controller
@RequestMapping(value = "${adminPath}/ideachk/rewcomideacheck")
public class RewcomideacheckController extends BaseController {

	@Autowired
	private RewcomideacheckService rewcomideacheckService;
	
	@Autowired
	private RewrecomideaService rewrecomideaService;
	
	@ModelAttribute
	public Rewcomideacheck get(@RequestParam(required=false) String id) {
		Rewcomideacheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rewcomideacheckService.get(id);
		}
		if (entity == null){
			entity = new Rewcomideacheck();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Rewcomideacheck rewcomideacheck, HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam(required=false)String kbn) {
		
		User user = UserUtils.getUser();
		// 【推荐意见投票】 菜单
		Dmlb dmlb = new Dmlb("09d0e9d318a244b58d789d64cfb47268", user.getGroupId(), null);
		
		// 检索区域  项目名称
		model.addAttribute("projects", UtilsFns.getProjects(""));
		
		model.addAttribute("iscs", UtilsMessage.iscs());
		
		model.addAttribute("isShow", UtilsMessage.isGlyRole());
		
		// 推荐意见投票  可用
		if(UtilsMessage.isGlyRole() || (UtilsFns.isMenuEnable(dmlb) && UtilsFns.getCsProgressResIsEnable())){
			
			// 判断推荐意见  是否全部填写
			String check = UtilsMessage.check(rewrecomideaService,"1");
			if(check != null){	
				return "redirect:"+Global.getAdminPath() + check+"?kbn=2";
			}
			
			if("1".equals(kbn)){
				model.addAttribute("message", "保存投票意见成功");
			}else if("2".equals(kbn)){
				model.addAttribute("message", UtilsMessage.getValueByKey("xztjyjtg"));
			}else if("11".equals(kbn)){
				model.addAttribute("message", "请不要重复提交");
			}
			// 推荐意见列表
			initCommonList(rewcomideacheck,request,response,model);
			/*model.addAttribute("isShow", UtilsMessage.isGlyRole());*/
			
			// 服务器端生成的 token
			String token = IdGen.uuid();
			request.getSession().setAttribute("token", token);
			rewcomideacheck.setToken(token);
			model.addAttribute("token", token);
			
			return "modules/rew/ideachk/rewcomideacheckList";
		}else{
		    //不可用
			
			// 推荐意见列表
			initCommonList(rewcomideacheck,request,response,model);
			
			model.addAttribute("rewFoot", UtilsMessage.getValueByKey("disListMes"));
			return "modules/rew/ideachk/rewcomideacheckDisList";
		}	
	}
	
	private void initCommonList(Rewcomideacheck rewcomideacheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 当前用户 参加了初审评选
		if(UtilsMessage.iscs()){
			rewcomideacheck.setGlyRole(UtilsMessage.isGlyRole());
			model.addAttribute("isGlyRole", UtilsMessage.isGlyRole());
			
			rewcomideacheck.setCurrentYear(UtilsFns.selectCurrentYear());
			
			Page<Rewcomideacheck> page = rewcomideacheckService.findPage(new Page<Rewcomideacheck>(request, response), rewcomideacheck); 
			model.addAttribute("page", page);
		}else{
			model.addAttribute("message", UtilsMessage.getMessage("notcsmes",UserUtils.getUser().getName()));
		}
	}
	
	

	@RequestMapping(value = "form")
	public String form(Rewcomideacheck rewcomideacheck, Model model) {
		model.addAttribute("rewcomideacheck", rewcomideacheck);
		return "modules/rew/ideachk/rewcomideacheckForm_del";
	}

	@RequestMapping(value = "saveAllData")
	public String saveAllData(Rewcomideacheck rewcomideacheck, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		User user = UserUtils.getUser();
		Dmlb dmlb = new Dmlb("09d0e9d318a244b58d789d64cfb47268", user.getGroupId(), null);
		// 推荐意见投票  不可用
		if(!UtilsFns.isMenuEnable(dmlb) || !UtilsFns.getCsProgressResIsEnable()){
			return "redirect:"+Global.getAdminPath()+"/ideachk/rewcomideacheck/?repage";
		}
		
		if (!beanValidator(model, rewcomideacheck)){
			return form(rewcomideacheck, model);
		}
		boolean isRepeat = UtilsMessage.isRepeatSubmit(rewcomideacheck.getToken(), request);
		request.getSession().removeAttribute("token");
		if(isRepeat){
			return "redirect:"+Global.getAdminPath()+"/ideachk/rewcomideacheck/?kbn=11&repage";
		}else{
			rewcomideacheckService.saveAllData(rewcomideacheck);
			return "redirect:"+Global.getAdminPath()+"/ideachk/rewcomideacheck/?kbn=1&repage";
		}
		
	}
}