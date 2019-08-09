/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.fs.web.fsdyl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.csres.entity.RewcsResult;
import com.zkr.rew.dict.entity.Dmlb;
import com.zkr.rew.fs.entity.fsdyl.Rewfsdyl;
import com.zkr.rew.fs.service.fsdyl.RewfsdylService;

/**
 * 复审第一轮投票Controller
 * @author 成茂涛
 * @version 2016-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/fs/fsdyl/initrewfsdyl")
public class RewfsdylController extends BaseController {

	@Autowired
	private RewfsdylService rewfsService;
	
	// 复审第一轮投票
	@RequestMapping(value = {"list", ""})
	public String list(Rewfsdyl rewfsdyl, HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam(required=false)String kbn) {
		
		model.addAttribute("isfs", UtilsMessage.isfs());
		
		model.addAttribute("rewfsdyl", rewfsdyl);
		// 参与复审评选
		if(!UtilsMessage.isfs()){
			model.addAttribute("message", UtilsMessage.getMessage("notfsmes",UserUtils.getUser().getName()));
			return "modules/rew/fs/fsdyl/rewfsdylList";
		}
		
		rewfsdyl.setGlyRole(UtilsMessage.isGlyRole());
		if("1".equals(kbn)){
			model.addAttribute("message", "保存复审第一轮投票成功");
		}else if("11".equals(kbn)){
			model.addAttribute("message", "请不要重复提交");
		}else{
			model.addAttribute("message", "");
		}
		
		model.addAttribute("projects", UtilsFns.getFsdylProjects());
		
		// 参数 是1：  复审第一轮补投
		if(UtilsFns.getmenucontroltp("1")){
			
			// 已经选择出来的特等奖数
			List<RewcsResult> selectFsdyltpres = rewfsService.selectFsdyltpres();
			
			// 已经投过票的
			List<Rewfsdyl> fsdyltpNums = rewfsService.selectFsdyltpNumtp(selectFsdyltpres);
			model.addAttribute("fsdyltpNums", fsdyltpNums);
			
			 // 总可以投票数   UtilsMessage.getValueByKey("fsdyltp")
			int allNum = Integer.valueOf(UtilsFns.selectRewSysvalByCurrentYear().getFsdyltp()) - selectFsdyltpres.size();
			model.addAttribute("allNum", allNum);
			
			// 还可以投票数
			int num = allNum;
			if(fsdyltpNums != null && !fsdyltpNums.isEmpty()){
				num = allNum - fsdyltpNums.size();
			}
			model.addAttribute("num", num);
			
			List<Rewfsdyl> fsdylList = rewfsService.findfsdylPagetp(rewfsdyl,selectFsdyltpres); 			
			model.addAttribute("fsdylList", fsdylList);
		}else{

			// 已经投过票的
			List<Rewfsdyl> fsdyltpNums = rewfsService.selectFsdyltpNum();
			model.addAttribute("fsdyltpNums", fsdyltpNums);
			
		    // 总可以投票数  UtilsMessage.getValueByKey("fsdyltp")
			int allNum = Integer.valueOf(UtilsFns.selectRewSysvalByCurrentYear().getFsdyltp());
			model.addAttribute("allNum", allNum);
			
			// 还可以投票数
			int num = allNum;
			if(fsdyltpNums != null && !fsdyltpNums.isEmpty()){
				num = allNum - fsdyltpNums.size();
			}
			model.addAttribute("num", num);
			
			List<Rewfsdyl> fsdylList = rewfsService.findfsdylPage(rewfsdyl); 			
			model.addAttribute("fsdylList", fsdylList);
						
		}
		
		Dmlb dmlb = new Dmlb("1bf8d6ed096d405e890ac6e7e45f210b", null, null);
		//复审第一轮  可用
		if(UtilsMessage.isGlyRole() || (UtilsFns.isMenuEnable(dmlb) && UtilsFns.getFsProgressResIsEnable())){
			model.addAttribute("isShow", UtilsMessage.isGlyRole());
			// 服务器端生成的 token
			String token = IdGen.uuid();
			request.getSession().setAttribute("token", token);
			model.addAttribute("token", token);

			
			return "modules/rew/fs/fsdyl/rewfsdylList";
		}else{
		  //不可用
			model.addAttribute("rewFoot", UtilsMessage.getValueByKey("disListMes"));
			return "modules/rew/fs/fsdyl/rewfsdylDisList";
		}
	}

	
	@RequestMapping(value = "saveAllData")
	public String saveAllData(Rewfsdyl rewcsres, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {

		boolean isRepeat = UtilsMessage.isRepeatSubmit(rewcsres.getToken(), request);
		request.getSession().removeAttribute("token");
		if(isRepeat){
			return "redirect:"+Global.getAdminPath()+"/fs/fsdyl/initrewfsdyl/?kbn=11&repage";
		}else{
			rewfsService.saveAllData(rewcsres);
			return "redirect:"+Global.getAdminPath()+"/fs/fsdyl/initrewfsdyl/?kbn=1&repage";
		}


	}
	
	
	
	
	//复审第二轮投票     
	@RequestMapping(value = "list2")
	public String list2(Rewfsdyl rewfsdyl, HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam(required=false)String kbn) {
		
		model.addAttribute("isfs", UtilsMessage.isfs());
		model.addAttribute("rewfsdyl", rewfsdyl);
		// 参与复审评选
		if(!UtilsMessage.isfs()){
			model.addAttribute("message", UtilsMessage.getMessage("notfsmes",UserUtils.getUser().getName()));
			return "modules/rew/fs/fsdel/rewfsdelList";
		}
		
		if("1".equals(kbn)){
			model.addAttribute("message", "保存复审第二轮投票成功");
		}else if("11".equals(kbn)){
			model.addAttribute("message", "请不要重复提交");
		}else{
			model.addAttribute("message", "");
		}
		
		//model.addAttribute("isGlyRole", UtilsMessage.isGlyRole());
		//model.addAttribute("projects", UtilsFns.getFsdelProjects());
		// 参数 是2：  复审第二轮补投
		if(UtilsFns.getmenucontroltp("2")){
			
			// 已经选择出来的一等奖数
			List<RewcsResult> selectFsdeltpres = rewfsService.selectFsdeltpres(); 
			
			// 已经投过票的
			List<Rewfsdyl> fsdeltpNums = rewfsService.selectFsdeltpNumtp(selectFsdeltpres);
			model.addAttribute("fsdyltpNums", fsdeltpNums);
			
		    // 总可以投票数  UtilsMessage.getValueByKey("fsdeltp")
			int allNum = Integer.valueOf(UtilsFns.selectRewSysvalByCurrentYear().getFsdeltp()) - selectFsdeltpres.size();
			model.addAttribute("allNum", allNum);
			
			
			// 还可以投票数
			int num = allNum;
			if(fsdeltpNums != null && !fsdeltpNums.isEmpty()){
				num = allNum - fsdeltpNums.size();
			}
			model.addAttribute("num", num);
			
			
			// 特等奖列表 里评选上特等奖的
			List<Rewfsdyl> parametes = rewfsService.selectTdjOKList();
			rewfsdyl.setFsdyls(parametes);
			model.addAttribute("projects", UtilsFns.getFsdelProjects(parametes));
			
			List<Rewfsdyl> rewdsdelList = rewfsService.findfsdelPagetp(rewfsdyl,selectFsdeltpres);
			model.addAttribute("rewdsdelList", rewdsdelList);
			
		}else{
			// 已经投过票的
			List<Rewfsdyl> fsdeltpNums = rewfsService.selectFsdeltpNum();
			model.addAttribute("fsdyltpNums", fsdeltpNums);
			
		    // 总可以投票数 UtilsMessage.getValueByKey("fsdeltp")
			int allNum = Integer.valueOf(UtilsFns.selectRewSysvalByCurrentYear().getFsdeltp());
			model.addAttribute("allNum", allNum);
			
			
			// 还可以投票数
			int num = allNum;
			if(fsdeltpNums != null && !fsdeltpNums.isEmpty()){
				num = allNum - fsdeltpNums.size();
			}
			model.addAttribute("num", num);
			
			
			// 特等奖列表 里评选上特等奖的
			List<Rewfsdyl> parametes = rewfsService.selectTdjOKList();
			rewfsdyl.setFsdyls(parametes);
			model.addAttribute("projects", UtilsFns.getFsdelProjects(parametes));
			List<Rewfsdyl> rewdsdelList = rewfsService.findfsdelPage(rewfsdyl); 
			model.addAttribute("rewdsdelList", rewdsdelList);
		}
		
		Dmlb dmlb = new Dmlb("962230b6328a4d2ba373a61c4812fc79", null, null);
		//复审第二轮  可用
		if(UtilsMessage.isGlyRole() || (UtilsFns.isMenuEnable(dmlb) && UtilsFns.getFsProgressResIsEnable())){
			model.addAttribute("isShow", UtilsMessage.isGlyRole());
			
			// 服务器端生成的 token
			String token = IdGen.uuid();
			request.getSession().setAttribute("token", token);
			model.addAttribute("token", token);

			return "modules/rew/fs/fsdel/rewfsdelList";
		}else{
		  //不可用
			model.addAttribute("rewFoot", UtilsMessage.getValueByKey("disListMes"));
			return "modules/rew/fs/fsdel/rewfsdelDisList";
		}
	}
	
	@RequestMapping(value = "saveAllData2")
	public String saveAllData2(Rewfsdyl rewcsres, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {

		boolean isRepeat = UtilsMessage.isRepeatSubmit(rewcsres.getToken(), request);
		request.getSession().removeAttribute("token");
		if(isRepeat){
			return "redirect:"+Global.getAdminPath()+"/fs/fsdyl/initrewfsdyl/list2?kbn=11&repage";

		}else{
			rewfsService.saveAllData2(rewcsres);
			return "redirect:"+Global.getAdminPath()+"/fs/fsdyl/initrewfsdyl/list2?kbn=1&repage";

		}

	}
	
	
	//复审第三轮投票
	@RequestMapping(value = "list3")
	public String list3(Rewfsdyl rewfsdyl, HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam(required=false)String kbn) {
		
		model.addAttribute("isfs", UtilsMessage.isfs());
		model.addAttribute("rewfsdyl", rewfsdyl);
		// 参与复审评选
		if(!UtilsMessage.isfs()){
			model.addAttribute("message", UtilsMessage.getMessage("notfsmes",UserUtils.getUser().getName()));
			return "modules/rew/fs/fsdsl/rewfsdslList";
		}
		
		if("1".equals(kbn)){
			model.addAttribute("message", "保存复审第三轮投票成功");
		}else if("11".equals(kbn)){
			model.addAttribute("message", "请不要重复提交");
		}else{
			model.addAttribute("message", "");
		}
		
		//model.addAttribute("isGlyRole", UtilsMessage.isGlyRole());
		//model.addAttribute("projects", UtilsFns.getFsdslProjects());
		// 已经投过票的
		List<Rewfsdyl> fsdsltpNums = rewfsService.selectFsdsltpNum();
		model.addAttribute("fsdyltpNums", fsdsltpNums);
		
	    // 总可以投票数  UtilsMessage.getValueByKey("fsdsltp")
		int allNum = Integer.valueOf(UtilsFns.selectRewSysvalByCurrentYear().getFsdsltp());
		model.addAttribute("allNum", allNum);
		
		// 还可以投票数
		int num = allNum;
		if(fsdsltpNums != null && !fsdsltpNums.isEmpty()){
			num = allNum - fsdsltpNums.size();
		}
		model.addAttribute("num", num);
		
		
		// 特等奖 和 一等奖 结果
		List<Rewfsdyl> parametes = rewfsService.selectTdjAndYdjOKList();
		rewfsdyl.setFsdyls(parametes);
		
		model.addAttribute("projects", UtilsFns.getFsdslProjects(parametes));
		// 复审第三轮数据 来源  都是  从初审结果里取。
		List<Rewfsdyl> rewfsdslList = rewfsService.findfsdslPage(rewfsdyl); 
		model.addAttribute("rewfsdslList", rewfsdslList);
		
		Dmlb dmlb = new Dmlb("2d99f5e1d58f46ae94f192e226e8b2f0", null, null);
		
		//复审第三轮  可用
		if(UtilsMessage.isGlyRole() || (UtilsFns.isMenuEnable(dmlb) && UtilsFns.getFsProgressResIsEnable())){
			model.addAttribute("isShow", UtilsMessage.isGlyRole());
			// 服务器端生成的 token
			String token = IdGen.uuid();
			request.getSession().setAttribute("token", token);
			model.addAttribute("token", token);

			return "modules/rew/fs/fsdsl/rewfsdslList";
		}else{
		  //不可用
			model.addAttribute("rewFoot", UtilsMessage.getValueByKey("disListMes"));
			return "modules/rew/fs/fsdsl/rewfsdslDisList";
		}
		
		
	}
	
	@RequestMapping(value = "saveAllData3")
	public String saveAllData3(Rewfsdyl rewcsres, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {

		boolean isRepeat = UtilsMessage.isRepeatSubmit(rewcsres.getToken(), request);
		request.getSession().removeAttribute("token");
		if(isRepeat){
			return "redirect:"+Global.getAdminPath()+"/fs/fsdyl/initrewfsdyl/list3?kbn=11&repage";
		}else{
			rewfsService.saveAllDatas(rewcsres);
			return "redirect:"+Global.getAdminPath()+"/fs/fsdyl/initrewfsdyl/list3?kbn=1&repage";
		}
	}
	

}