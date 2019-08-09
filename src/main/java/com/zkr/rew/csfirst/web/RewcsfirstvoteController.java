/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.csfirst.web;

import java.util.ArrayList;
import java.util.List;

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
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.csfirst.entity.Rewcsfirstvote;
import com.zkr.rew.csfirst.service.RewcsfirstvoteService;
import com.zkr.rew.dict.entity.Dmlb;
import com.zkr.rew.dict.entity.Rewgroup;
import com.zkr.rew.zyzz.service.RewrecomideaService;

/**
 * 初审第一轮投票Controller
 * @author 成茂涛
 * @version 2016-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/csfirst/initrewcsfirstvote")
public class RewcsfirstvoteController extends BaseController {

	@Autowired
	private RewcsfirstvoteService rewcsfirstvoteService;
	
	@Autowired
	private RewrecomideaService rewrecomideaService;
	
	@ModelAttribute
	public Rewcsfirstvote get(@RequestParam(required=false) String id) {
		Rewcsfirstvote entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rewcsfirstvoteService.get(id);
		}
		if (entity == null){
			entity = new Rewcsfirstvote();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Rewcsfirstvote rewcsfirstvote, HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam(required=false)String kbn) {
		
		//User user = UserUtils.getUser();
		// 初审第一轮投票 菜单
		Dmlb dmlb = new Dmlb("9f8ecf109f614618b3b48dd7071ee1ae", null, null);
		
		List<Rewgroup> groupList2 = UtilsFns.getEnableGroupList();
		
		List<Rewgroup> groupList = new ArrayList<Rewgroup>(groupList2);
		
		Rewgroup r = new Rewgroup();
		r.setGroupname("所有");
		r.setId("");
		groupList.add(r);

		//检索区域
		model.addAttribute("groupList",groupList);
		model.addAttribute("projects", UtilsFns.getProjects("cstp"));
		
		model.addAttribute("isAgree",UtilsFns.isAgree());
		
		model.addAttribute("hidGroupId",rewcsfirstvote.getGroupId());
		model.addAttribute("hidIsagree",rewcsfirstvote.getIsAgreeKey());
		
		model.addAttribute("iscs", UtilsMessage.iscs());
		
		//初审第一轮  可用
		if(UtilsMessage.isGlyRole() || (UtilsFns.isMenuEnable(dmlb) && UtilsFns.getCsProgressResIsEnable())){
			String check = UtilsMessage.check(rewrecomideaService,"2");
			if(check != null){	
				return "redirect:"+Global.getAdminPath() + check + "?kbn=2";
			}
			
			if("1".equals(kbn)){
				model.addAttribute("message", "保存初审第一轮投票成功");
			}else if("11".equals(kbn)){
				model.addAttribute("message", "请不要重复提交");
			}
			initCommonList(rewcsfirstvote,request,response,model);
			
			model.addAttribute("isShow", UtilsMessage.isGlyRole());
			
			// 服务器端生成的 token
			String token = IdGen.uuid();
			request.getSession().setAttribute("token", token);
			model.addAttribute("token", token);

			return "modules/rew/csfirst/rewcsfirstvoteList";
		}else{
		    //不可用
			initCommonList(rewcsfirstvote,request,response,model);
			model.addAttribute("rewFoot", UtilsMessage.getValueByKey("disListMes"));
			return "modules/rew/csfirst/rewcsfirstvoteDisList";
		}
	}
		
	private void initCommonList(Rewcsfirstvote rewcsfirstvote, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		// 当前用户 参加了初审评选
		if(UtilsMessage.iscs()){
			rewcsfirstvote.setGlyRole(UtilsMessage.isGlyRole());
			//rewcsfirstvote.setZyzzRole(UtilsMessage.isZyzzRole()); //专业组长
			rewcsfirstvote.setCurrentYear(UtilsFns.selectCurrentYear());
			
			
			Page<Rewcsfirstvote> page = rewcsfirstvoteService.findPage(new Page<Rewcsfirstvote>(request, response), rewcsfirstvote); 
			model.addAttribute("page", page);
			
			
			// 已经投过票的
			List<Rewcsfirstvote> voteds = rewcsfirstvoteService.selectVotedNum();
			model.addAttribute("voteds", voteds);
			
			//String voteNum = UtilsMessage.getValueByKey("csdyltp");
			String voteNum = UtilsFns.selectRewSysvalByCurrentYear().getCsdyltp();
			int allNum = Integer.valueOf(voteNum);
			// 投票总数
			model.addAttribute("allNum",allNum);
			
			int num = allNum;
			if(voteds != null && !voteds.isEmpty()){
				num = num - voteds.size();
			}
			// 还可以提交的数目
			model.addAttribute("num",num);
			
			model.addAttribute("isGlyRole", UtilsMessage.isGlyRole());
		}else{
			model.addAttribute("message", UtilsMessage.getMessage("notcsmes",UserUtils.getUser().getName()));
		}
	}
	
	
	@RequestMapping(value = "saveAllData")
	public String saveAllData(Rewcsfirstvote rewcsfirstvote, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		
		Dmlb dmlb = new Dmlb("9f8ecf109f614618b3b48dd7071ee1ae", null, null);
		//初审第一轮  不可用
		if(!UtilsFns.isMenuEnable(dmlb) || !UtilsFns.getCsProgressResIsEnable()){
			return "redirect:"+Global.getAdminPath()+"/csfirst/initrewcsfirstvote/?repage";
		}
		
		boolean isRepeat = UtilsMessage.isRepeatSubmit(rewcsfirstvote.getToken(), request);
		request.getSession().removeAttribute("token");
		if(isRepeat){
			return "redirect:"+Global.getAdminPath()+"/csfirst/initrewcsfirstvote/?kbn=11&repage&groupId=" + rewcsfirstvote.getGroupId() + "&isAgreeKey=" + rewcsfirstvote.getIsAgreeKey();

		}else{
			rewcsfirstvoteService.saveAllData(rewcsfirstvote);
			return "redirect:"+Global.getAdminPath()+"/csfirst/initrewcsfirstvote/?kbn=1&repage&groupId=" + rewcsfirstvote.getGroupId() + "&isAgreeKey=" + rewcsfirstvote.getIsAgreeKey();

		}

	}
}