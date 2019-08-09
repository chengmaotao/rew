/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.cssecond.web;

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
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMath;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.cssecond.entity.Rewcssenondvote;
import com.zkr.rew.cssecond.service.RewcssenondvoteService;
import com.zkr.rew.dict.entity.Dmlb;
import com.zkr.rew.dict.entity.Rewgroup;

/**
 * 初审第二轮投票Controller
 * 
 * @author 成茂涛
 * @version 2016-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/cssecond/initrewcssenondvote")
public class RewcssenondvoteController extends BaseController {

	@Autowired
	private RewcssenondvoteService rewcssenondvoteService;

	@ModelAttribute
	public Rewcssenondvote get(@RequestParam(required = false) String id) {
		Rewcssenondvote entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = rewcssenondvoteService.get(id);
		}
		if (entity == null) {
			entity = new Rewcssenondvote();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(Rewcssenondvote rewcssenondvote, HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam(required=false)String kbn) {
		

		//【初审第二轮】菜单 
		Dmlb dmlb = new Dmlb("8893141efe4a4dbcac826912528a871f", null, null);
		
		List<Rewgroup> groupList2 = UtilsFns.getEnableGroupList();
		List<Rewgroup> groupList = new ArrayList<Rewgroup>(groupList2);
		Rewgroup r = new Rewgroup();
		r.setGroupname("所有");
		r.setId("");
		groupList.add(r);
		//检索区域
		model.addAttribute("projects", UtilsFns.getProjects("cstp"));
		model.addAttribute("groupList",groupList);
		model.addAttribute("hidGroupId",rewcssenondvote.getGroupId());
		
		model.addAttribute("isAgree",UtilsFns.isAgree());
		model.addAttribute("hidIsagree",rewcssenondvote.getIsAgreeKey());
		
		model.addAttribute("iscs", UtilsMessage.iscs());
		
		//初审第二轮  可用
		if(UtilsMessage.isGlyRole() || (UtilsFns.isMenuEnable(dmlb) && UtilsFns.getCsProgressResIsEnable())){
			
			if("1".equals(kbn)){
				model.addAttribute("message", "保存初审第二轮投票成功");
			}else if("11".equals(kbn)){
				model.addAttribute("message", "请不要重复提交");
			}else{
				model.addAttribute("message", "");
			}
			initCommonList(rewcssenondvote,request,response,model);
			model.addAttribute("isShow", UtilsMessage.isGlyRole());
			
			// 服务器端生成的 token
			String token = IdGen.uuid();
			request.getSession().setAttribute("token", token);
			model.addAttribute("token", token);

			return "modules/rew/cssecond/rewcssenondvoteList";
		}else{
		    //不可用
			initCommonList(rewcssenondvote,request,response,model);
			model.addAttribute("rewFoot", UtilsMessage.getValueByKey("disListMes"));
			return "modules/rew/cssecond/rewcssenondvoteDisList";
		}
		
	}
	
	
	private void initCommonList(Rewcssenondvote rewcssenondvote, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		// 当前用户 参加了初审评选
		if(UtilsMessage.iscs()){
			rewcssenondvote.setGlyRole(UtilsMessage.isGlyRole());
		    rewcssenondvote.setCurrentYear(UtilsFns.selectCurrentYear());
		    
			Page<Rewcssenondvote> page = rewcssenondvoteService.findPage(new Page<Rewcssenondvote>(request, response), rewcssenondvote);
			
			//  初审第一轮投票数目 低于总投票 1/2的 不能再初审第二轮列出来   开始			
			// 获得参与投票的总人数
			List<User> zjUsers = UserUtils.selectcsAllZJUsers();
			int allNum = 0;
			if (zjUsers != null && !zjUsers.isEmpty()) {
				allNum = zjUsers.size();
			}
			
			
			List<Rewcssenondvote> list = page.getList();
			//List<Rewcssenondvote> resList = new ArrayList<Rewcssenondvote>();
			int tpnum = 0;
			for (Rewcssenondvote t : list) {
				tpnum = Integer.parseInt(t.getCountNum());
				if(UtilsMath.isTrue(allNum,tpnum,2)){
					//resList.add(t);
					t.setSenondShow(true);
				}
            }
			//page.setList(resList);	
			//  初审第一轮投票数目 低于总投票 1/2的 不能再初审第二轮列出来   结束
			
			model.addAttribute("page", page);
			
			
			
			
			// 特等奖已经投过票的
			List<Rewcssenondvote> voted0s = rewcssenondvoteService.selectVotedNum0();
			model.addAttribute("voted0s", voted0s);
			
			//String voteNum0 = UtilsMessage.getValueByKey("csdeltpt");
			String voteNum0 = UtilsFns.selectRewSysvalByCurrentYear().getCsdeltpt();
			int allNum0 = Integer.valueOf(voteNum0);
			// 特等奖投票总数
			model.addAttribute("allNum0",allNum0);
			
			int num0 = allNum0;
			if(voted0s != null && !voted0s.isEmpty()){
				num0 = num0 - voted0s.size();
			}
			// 特等奖还可以提交的数目
			model.addAttribute("num0",num0);
			
			
			// 一等奖已经投过票的
			List<Rewcssenondvote> voted1s = rewcssenondvoteService.selectVotedNum1();
			model.addAttribute("voted1s", voted1s);
			
			//String voteNum1 = UtilsMessage.getValueByKey("csdeltpy");
			String voteNum1 = UtilsFns.selectRewSysvalByCurrentYear().getCsdeltpy();
			int allNum1 = Integer.valueOf(voteNum1);
			// 一等奖投票总数
			model.addAttribute("allNum1",allNum1);
			
			int num1 = allNum1;
			if(voted1s != null && !voted1s.isEmpty()){
				num1 = num1 - voted1s.size();
			}
			// 一等奖还可以提交的数目
			model.addAttribute("num1",num1);
			
			model.addAttribute("isGlyRole", UtilsMessage.isGlyRole());
		}else{
			model.addAttribute("message", UtilsMessage.getMessage("notcsmes",UserUtils.getUser().getName()));
		}
	}
	
	@RequestMapping(value = "saveAllData")
	public String saveAllData(Rewcssenondvote rewcssenondvote, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		
		Dmlb dmlb = new Dmlb("8893141efe4a4dbcac826912528a871f", null,null);
		//初审第二轮  不可用
		if(!UtilsFns.isMenuEnable(dmlb) || !UtilsFns.getCsProgressResIsEnable()){
			return "redirect:" + Global.getAdminPath() + "/cssecond/initrewcssenondvote/?repage";
		}
		
		boolean isRepeat = UtilsMessage.isRepeatSubmit(rewcssenondvote.getToken(), request);
		request.getSession().removeAttribute("token");
		if(isRepeat){
			return "redirect:" + Global.getAdminPath() + "/cssecond/initrewcssenondvote/?kbn=11&repage&groupId=" + rewcssenondvote.getGroupId() + "&isAgreeKey=" + rewcssenondvote.getIsAgreeKey();
		}else{
			rewcssenondvoteService.saveAllData(rewcssenondvote);
			return "redirect:" + Global.getAdminPath() + "/cssecond/initrewcssenondvote/?kbn=1&repage&groupId=" + rewcssenondvote.getGroupId() + "&isAgreeKey=" + rewcssenondvote.getIsAgreeKey();
		}

		
	}
}