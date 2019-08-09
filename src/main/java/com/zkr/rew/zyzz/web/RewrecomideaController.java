/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.zyzz.web;

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
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.dict.entity.Dmlb;
import com.zkr.rew.dict.entity.Rewawardlevel;
import com.zkr.rew.dict.entity.Rewproject;
import com.zkr.rew.ideachk.service.RewcomideacheckService;
import com.zkr.rew.zyzz.entity.Rewrecomidea;
import com.zkr.rew.zyzz.service.RewrecomideaService;

/**
 * 小组推荐意见Controller
 * @author 成茂涛
 * @version 2016-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/zyzz/rewrecomidea")
public class RewrecomideaController extends BaseController {

	@Autowired
	private RewrecomideaService rewrecomideaService;
	
	@Autowired
	private RewcomideacheckService rewcomideacheckService;
	
	@ModelAttribute
	public Rewrecomidea get(@RequestParam(required=false) String projectId) {
		Rewrecomidea entity = null;
		if (StringUtils.isNotBlank(projectId)){
			entity = rewrecomideaService.get(projectId);
		}
		if (entity == null){
			entity = new Rewrecomidea();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Rewrecomidea rewrecomidea, HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam(required=false)String kbn) {
		
		User user = UserUtils.getUser();
		// 【小组推荐意见】 菜单
		Dmlb dmlb = new Dmlb("2b35f814dd784f5bb6eb2a87a004b52d", user.getGroupId(), null);
		
		// 检索区域  项目名称
		model.addAttribute("projects", UtilsFns.getProjects(""));
		
		model.addAttribute("iscs", UtilsMessage.iscs());
		
		model.addAttribute("isShow", UtilsMessage.isGlyRole());
		
		// 【小组推荐意见】 菜单 可用
		if(UtilsMessage.isGlyRole() || (UtilsFns.isMenuEnable(dmlb) && UtilsFns.getCsProgressResIsEnable())){
			// 检验没有通过   跳转过来的
			if("2".equals(kbn)){
				model.addAttribute("message", UtilsMessage.getValueByKey("xztjyj"));
			}
			
			// 小组推荐意见列表
			initCommonList(rewrecomidea,request,response,model);
			
			model.addAttribute("isShow", UtilsMessage.isGlyRole());
			return "modules/rew/zyzz/rewrecomideaList";
		}else{
			// 【小组推荐意见】 菜单 不可用
			
			// 小组推荐意见列表
			initCommonList(rewrecomidea,request,response,model);
			model.addAttribute("rewFoot", UtilsMessage.getValueByKey("disListMes"));
			return "modules/rew/zyzz/rewrecomideaDisList";
		}
		
		
	}
	
	// 小组推荐意见列表
	private void initCommonList(Rewrecomidea rewrecomidea, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		// 当前用户 参加了初审评选
		if(UtilsMessage.iscs()){
			rewrecomidea.setGlyRole(UtilsMessage.isGlyRole()); //管理员
			rewrecomidea.setCurrentYear(UtilsFns.selectCurrentYear()); // 当前年度
			Page<Rewrecomidea> page = rewrecomideaService.findPage(new Page<Rewrecomidea>(request, response), rewrecomidea); 
			model.addAttribute("page", page);
		}else{
			model.addAttribute("message", UtilsMessage.getMessage("notcsmes",UserUtils.getUser().getName()));
		}
	}
	

	@RequestMapping(value = "form")
	public String form(Rewrecomidea rewrecomidea, Model model,String projectId,HttpServletRequest request) {
		
		// 服务器端生成的 token
		String token = IdGen.uuid();
		request.getSession().setAttribute("token", token);
		rewrecomidea.setToken(token);

		model.addAttribute("rewrecomidea", rewrecomidea);
		
		// 项目
		Rewproject project = UtilsFns.getProjectByProjectId(projectId);

		project.setGroupName(UtilsFns.getGroupById(project.getGroupId()).getGroupname());
		

		// 项目对象
		model.addAttribute("project", project);
		
		List<Rewawardlevel> LevelList = UtilsFns.getAwardLevelList();
		model.addAttribute("LevelList",LevelList);
		
		return "modules/rew/zyzz/rewrecomideaForm";
	}

	@RequestMapping(value = "save")
	public String save(Rewrecomidea rewrecomidea, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		
		User user = UserUtils.getUser();
		Dmlb dmlb = new Dmlb("2b35f814dd784f5bb6eb2a87a004b52d", user.getGroupId(), null);
		// 【小组推荐意见】 菜单 不可用
		if(!UtilsFns.isMenuEnable(dmlb) || !UtilsFns.getCsProgressResIsEnable()){
			return "redirect:"+Global.getAdminPath()+"/zyzz/rewrecomidea/?repage";
		}
		boolean isRepeat = UtilsMessage.isRepeatSubmit(rewrecomidea.getToken(), request);
		request.getSession().removeAttribute("token");
		if(isRepeat){
			addMessage(redirectAttributes, "请不要重复提交");
		}else{
			rewrecomideaService.save(rewrecomidea);
			addMessage(redirectAttributes, "保存小组推荐意见成功");
		}

		
		return "redirect:"+Global.getAdminPath()+"/zyzz/rewrecomidea/?repage";
	}
	
	@RequestMapping(value = "result")
	public String viewResult(Rewrecomidea rewrecomidea, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response,@RequestParam(required=false)String kbn) {

		User user = UserUtils.getUser();
		
		// e85140975ade477989127f9ae4a77fb7  菜单[推荐意见投票结果]的id
		Dmlb dmlb = new Dmlb("f63023288d5a477a82e3292450493997", user.getGroupId(), null);
		
		//model.addAttribute("projects", UtilsFns.getProjects(""));
		
		model.addAttribute("iscs", UtilsMessage.iscs());
		// 推荐意见投票结果  可用
		if(UtilsMessage.isGlyRole() || (UtilsFns.isMenuEnable(dmlb) && UtilsFns.getCsProgressResIsEnable())){
			
			String check = UtilsMessage.check(rewrecomideaService,"1");
			if(check != null){	
				return "redirect:"+Global.getAdminPath() + check + "?kbn=2";
			}
			
			if("2".equals(kbn)){
				model.addAttribute("message", UtilsMessage.getValueByKey("xztjyjtg"));
			}else if("1".equals(kbn)){
				model.addAttribute("message", "修改小组推荐意见成功");
			}else if("11".equals(kbn)){
				model.addAttribute("message", "请不要重复提交");
			}
			
			// 推荐意见投票结果列表
			initCommonResult(rewrecomidea,request,response,model);
			model.addAttribute("isShow", (UtilsMessage.isGlyRole() && UtilsMessage.iscs()));
			return "modules/rew/ideachk/checkResultList";
		}else{
		   //不可用
			
			// 推荐意见投票结果列表
			initCommonResult(rewrecomidea,request,response,model);
			model.addAttribute("rewFoot", UtilsMessage.getValueByKey("disListMes"));
			return "modules/rew/ideachk/checkResultDisList";
		}
		
	}
	
	
	// 推荐意见投票结果列表
	private void initCommonResult(Rewrecomidea rewrecomidea, HttpServletRequest request, HttpServletResponse response, Model model) {
		// ①获得该组下面 有多少个 项目;②获得该组下面有多少个人,即总投票数;③获得投票数;④计算  得到投票结果(通过/不通过)
        if(UtilsMessage.iscs()){
			rewrecomidea.setGlyRole(UtilsMessage.isGlyRole());
			
			//rewrecomidea.setZyzzRole(UtilsMessage.isZyzzRole());
			rewrecomidea.setCurrentYear(UtilsFns.selectCurrentYear());
			
			// ①获得该组下面 有多少个 项目
			Page<Rewrecomidea> page = rewrecomideaService.findPageByGroup(new Page<Rewrecomidea>(request, response), rewrecomidea); 
			
			List<Rewrecomidea> rewrecomideaList = page.getList();
	
			//②③④
			List<Rewrecomidea> checkResult = rewcomideacheckService.getCheckResult(rewrecomideaList,UserUtils.getUser());
			
			page.setList(checkResult);
			
			model.addAttribute("page", page);
        }else{
        	model.addAttribute("message", UtilsMessage.getMessage("notcsmes",UserUtils.getUser().getName()));
        }
	}
	
	
	@RequestMapping(value = "myForm")
	public String myForm(Rewrecomidea rewrecomidea, Model model,String projectId,HttpServletRequest request) {
		
		// 服务器端生成的 token
		String token = IdGen.uuid();
		request.getSession().setAttribute("token", token);
		rewrecomidea.setToken(token);

		model.addAttribute("rewrecomidea", rewrecomidea);
		
		// 项目
		Rewproject project = UtilsFns.getProjectByProjectId(projectId);

		project.setGroupName(UtilsFns.getGroupById(project.getGroupId()).getGroupname());
		

		// 项目对象
		model.addAttribute("project", project);
		
		List<Rewawardlevel> LevelList = UtilsFns.getAwardLevelList();
		model.addAttribute("LevelList",LevelList);
		
		return "modules/rew/zyzz/rewrecomideaMyForm";
	}
	
	@RequestMapping(value = "mySave")
	public String mySave(Rewrecomidea rewrecomidea, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes,@RequestParam(required=false)String hidRecomidea) {
		
		User user = UserUtils.getUser();
		Dmlb dmlb = new Dmlb("f63023288d5a477a82e3292450493997", user.getGroupId(), null);
		// 推荐意见投票结果  不可用
		if(!UtilsFns.isMenuEnable(dmlb) || !UtilsFns.getCsProgressResIsEnable()){
			return "redirect:"+Global.getAdminPath()+"/zyzz/rewrecomidea/result?repage";
		}
		
		boolean isRepeat = UtilsMessage.isRepeatSubmit(rewrecomidea.getToken(), request);
		request.getSession().removeAttribute("token");
		if(isRepeat){
			return "redirect:"+Global.getAdminPath()+"/zyzz/rewrecomidea/result?kbn=11&repage";
		}else{
			if (!hidRecomidea.equals(rewrecomidea.getRecomidea())){
				rewrecomideaService.mySave(rewrecomidea);
			}
			return "redirect:"+Global.getAdminPath()+"/zyzz/rewrecomidea/result?kbn=1&repage";
		}
		
	}

}