/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.pszj.web;

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
import com.zkr.rew.dict.entity.Rewproject;
import com.zkr.rew.dict.entity.Rewscopekpi;
import com.zkr.rew.pszj.entity.Rewprojectscope;
import com.zkr.rew.pszj.service.RewprojectscopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 评审专家Controller
 * 
 * @author 成茂涛
 * @version 2016-08-12
 */
@Controller
@RequestMapping(value = "${adminPath}/pszj/rewprojectscope")
public class RewprojectscopeController extends BaseController {

	@Autowired
	private RewprojectscopeService rewprojectscopeService;

	@ModelAttribute
	public Rewprojectscope get(@RequestParam(required = false) String id) {
		Rewprojectscope entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = rewprojectscopeService.get(id);
		}
		if (entity == null) {
			entity = new Rewprojectscope();
		}
		return entity;
	}

	// 专家评分
	@RequestMapping(value = { "list", "" })
	public String list(Rewprojectscope rewprojectscope, HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(required = false) String kbn) {

		User user = UserUtils.getUser();
		// e85140975ade477989127f9ae4a77fb7 菜单[专家评分]的id
		Dmlb dmlb = new Dmlb("e85140975ade477989127f9ae4a77fb7", user.getGroupId(), null);

		// 检索区域的 项目名称
		model.addAttribute("projects", UtilsFns.getProjects(""));

		model.addAttribute("iscs", UtilsMessage.iscs());
		
		model.addAttribute("isShow", UtilsMessage.isGlyRole());

		// 【专家评分 菜单】 可用  && 初审流程未结束
		if (UtilsMessage.isGlyRole() || (UtilsFns.isMenuEnable(dmlb) && UtilsFns.getCsProgressResIsEnable())) {

			// 检验没有通过 跳转过来的
			if ("2".equals(kbn)) {
				model.addAttribute("message", UtilsMessage.getValueByKey("xztjyj"));
			}

			// 专家评分 List
			initCommonList(rewprojectscope, request, response, model);

			// model.addAttribute("isShow", UtilsMessage.isGlyRole());

			return "modules/rew/pszj/rewprojectscopeList";
		} else {
			// 【专家评分 菜单】不可用

			// 专家评分 List
			initCommonList(rewprojectscope, request, response, model);

			model.addAttribute("rewFoot", UtilsMessage.getValueByKey("disListMes"));
			return "modules/rew/pszj/rewprojectscopeDisList";
		}
	}

	// 专家评分 List
	private void initCommonList(Rewprojectscope rewprojectscope, HttpServletRequest request, HttpServletResponse response, Model model) {

		// 当前用户 参加了初审评选
		if (UtilsMessage.iscs()) {
			rewprojectscope.setGlyRole(UtilsMessage.isGlyRole()); // 管理员
			// rewprojectscope.setZyzzRole(UtilsMessage.isZyzzRole()); // 专业组长
			rewprojectscope.setCurrentYear(UtilsFns.selectCurrentYear()); // 年度

			Page<Rewprojectscope> page = rewprojectscopeService.findPage(new Page<Rewprojectscope>(request, response), rewprojectscope);
			model.addAttribute("page", page);
		} else {
			model.addAttribute("message", UtilsMessage.getMessage("notcsmes", UserUtils.getUser().getName()));
		}

	}

	@RequestMapping(value = "form")
	public String form(Rewprojectscope rewprojectscope, Model model, String projectId, HttpServletRequest request) {

		// 服务器端生成的 token
		String token = IdGen.uuid();
		request.getSession().setAttribute("token", token);
		rewprojectscope.setToken(token);

		model.addAttribute("rewprojectscope", rewprojectscope);

		// 项目
		Rewproject project = UtilsFns.getProjectByProjectId(projectId);

		project.setGroupName(UtilsFns.getGroupById(project.getGroupId()).getGroupname());

		// 项目对象
		model.addAttribute("project", project);

		// 根据申报类别ID 获得评分指标对象
		List<Rewscopekpi> scopeKpiList = UtilsFns.getScopeKpiListByCategoryId(project.getCategoryid());

		for (Rewscopekpi e : scopeKpiList) {
			if ("1".equals(e.getSortNum())) {
				model.addAttribute("kpicategory1", e.getKpicategory());
				model.addAttribute("scopelevel1", e.getScopelevel());
				model.addAttribute("kpilist1", UtilsFns.getKPIList(e.getKpiweight()));
			} else if ("2".equals(e.getSortNum())) {

				model.addAttribute("kpicategory2", e.getKpicategory());
				model.addAttribute("scopelevel2", e.getScopelevel());
				model.addAttribute("kpilist2", UtilsFns.getKPIList(e.getKpiweight()));
			} else if ("3".equals(e.getSortNum())) {

				model.addAttribute("kpicategory3", e.getKpicategory());
				model.addAttribute("scopelevel3", e.getScopelevel());
				model.addAttribute("kpilist3", UtilsFns.getKPIList(e.getKpiweight()));
			} else if ("4".equals(e.getSortNum())) {

				model.addAttribute("kpicategory4", e.getKpicategory());
				model.addAttribute("scopelevel4", e.getScopelevel());
				model.addAttribute("kpilist4", UtilsFns.getKPIList(e.getKpiweight()));
			} else if ("5".equals(e.getSortNum())) {

				model.addAttribute("kpicategory5", e.getKpicategory());
				model.addAttribute("scopelevel5", e.getScopelevel());
				model.addAttribute("kpilist5", UtilsFns.getKPIList(e.getKpiweight()));
			}
		}
		return "modules/rew/pszj/rewprojectscopeForm";
	}

	@RequestMapping(value = "save")
	public String save(Rewprojectscope rewprojectscope, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		// 【专家评分 菜单】 不可用   或者 初审流程已经结束
		User user = UserUtils.getUser();
		Dmlb dmlb = new Dmlb("e85140975ade477989127f9ae4a77fb7", user.getGroupId(), null);
		if (!UtilsFns.isMenuEnable(dmlb) || !UtilsFns.getCsProgressResIsEnable()) {
			return "redirect:" + Global.getAdminPath() + "/pszj/rewprojectscope/?repage";
		}

		boolean isRepeat = UtilsMessage.isRepeatSubmit(rewprojectscope.getToken(), request);
		request.getSession().removeAttribute("token");
		if (isRepeat) {
			addMessage(redirectAttributes, "请不要重复提交");
		} else {
			rewprojectscopeService.save(rewprojectscope);
			addMessage(redirectAttributes, "专家评分成功");
		}

		return "redirect:" + Global.getAdminPath() + "/pszj/rewprojectscope/?repage";
	}
}