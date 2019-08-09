/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.csres.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMath;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.csres.entity.RewcsResult;
import com.zkr.rew.csres.service.RewcsresService;
import com.zkr.rew.dict.entity.Rewawardlevel;

/**
 * 初审结果Controller
 * 
 * @author 成茂涛
 * @version 2016-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/csres/res")
public class RewcsresController extends BaseController {

	@Autowired
	private RewcsresService csresService;

	@RequestMapping(value = { "initList", "" })
	public String list(RewcsResult rewcsResult, HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(required = false) String kbn) {

		model.addAttribute("iscs", UtilsMessage.iscs());
		if (!UtilsMessage.iscs()) {
			model.addAttribute("message", UtilsMessage.getMessage("notcsmes", UserUtils.getUser().getName()));
			return "modules/rew/csres/csresList";
		}

		model.addAttribute("rewcsResult", rewcsResult);
		// 项目名字，申报类别，专业组，总人数，投票数

		// 获得参与投票的总人数
		List<User> zjUsers = UserUtils.selectcsAllZJUsers();
		int allNum = 0;
		if (zjUsers != null && !zjUsers.isEmpty()) {
			allNum = zjUsers.size();
		}

		model.addAttribute("allNum", allNum);
		model.addAttribute("isShow", UtilsMessage.isGlyRole());
		
		// 初审未结束
		if(UtilsFns.getCsProgressResIsEnable()){
			// 最终结果
			List<RewcsResult> result = new ArrayList<RewcsResult>();

			// 特等奖列表
			List<RewcsResult> result1 = new ArrayList<RewcsResult>();
			List<String> paramets = new ArrayList<String>();
			List<RewcsResult> selectTdjList = csresService.selectTdjList(UtilsFns.selectCurrentYear());
			if (selectTdjList != null && !selectTdjList.isEmpty()) {
				for (RewcsResult e : selectTdjList) {
					// 大于2/3
					if (UtilsMath.isTrue(allNum, e.getNum(), 1)) {
						e.setAllNum(allNum);
						e.setLevelId("1");
						result1.add(e); // 特等奖
						paramets.add(e.getProjectid());
					}
				}
			}

			// 二等奖列表
			List<RewcsResult> result3 = new ArrayList<RewcsResult>();
			List<String> paramets3 = new ArrayList<String>();
			
			// 一等奖列表
			List<RewcsResult> result2 = new ArrayList<RewcsResult>();
			List<String> paramets2 = new ArrayList<String>();
			
			List<RewcsResult> selectYdjList = csresService.selectYdjList(paramets);
			if (selectYdjList != null && !selectYdjList.isEmpty()) {
				for (RewcsResult e : selectYdjList) {
					if (UtilsMath.isTrue(allNum, e.getNum(), 1)) {
						e.setAllNum(allNum);
						e.setLevelId("2");
						result2.add(e); // 一等奖
						paramets2.add(e.getProjectid());
					} else if (UtilsMath.isTrue(allNum, e.getNum(), 2)) {
						// 大于1/2
						e.setAllNum(allNum);
						e.setLevelId("3");
						e.setLevelName("二等奖");
						result3.add(e);
						paramets3.add(e.getProjectid());
					}
				}
			}

			paramets2.addAll(paramets); // 排除特等奖和一等奖的
			List<RewcsResult> selectEdjList = csresService.selectEdjList(paramets2);
			List<RewcsResult> result4 = new ArrayList<RewcsResult>(); // 第一轮就超过1/2的
			List<String> paramets4 = new ArrayList<String>();
			
			if (selectEdjList != null && !selectEdjList.isEmpty()) {
				for (RewcsResult e : selectEdjList) {
					if (UtilsMath.isTrue(allNum, e.getNum(), 2)) {
						e.setAllNum(allNum);
						e.setLevelId("3");
						result4.add(e);
						paramets4.add(e.getProjectid());
					}
				}
			}

			paramets2.addAll(paramets3); // 特等奖 和 一等奖 和 第二轮已经获选二等奖的 和 第一轮就超过1/2的
			paramets2.addAll(paramets4);
			// 不授奖列表
			List<RewcsResult> result5 = new ArrayList<RewcsResult>();
			List<RewcsResult> selectBsjList = csresService.selectBsjList(paramets2);
			if(selectBsjList != null && !selectBsjList.isEmpty()){
				for (RewcsResult e : selectBsjList) {
					e.setAllNum(allNum);
					e.setLevelId("4");
					result5.add(e);
	            }
			}
			
			
			

			// 去除重复的二等奖    票数哪个高 保留哪个
			result4.addAll(result3);
			Collections.sort(result4);
			List<RewcsResult> resultEdj = UtilsMath.getResult(result4);

			result.addAll(result1); // 特等奖
			result.addAll(result2); // 一等奖
			result.addAll(resultEdj); // 二等奖
			// result.addAll(result4); //初审第一轮二等奖

			result.addAll(result5); // 不授奖
			
			List<RewcsResult> res2 = csresService.getbsjList(rewcsResult);
			result.addAll(res2);

			// 初审结果保存到数据库
			csresService.insertData(result);

			model.addAttribute("results", result);
		}else{
			
			List<RewcsResult> result = csresService.printList(rewcsResult);

			model.addAttribute("results", result);
		}
		
		
		
		
		

		// 奖项类别
		List<Rewawardlevel> awardLevelList2 = UtilsFns.getAwardLevelList();
		List<Rewawardlevel> awardLevelList = new ArrayList<Rewawardlevel>(awardLevelList2);
		Rewawardlevel r = new Rewawardlevel();
		r.setId("");
		r.setLevelname("所有");
		awardLevelList.add(r);

		model.addAttribute("awardLevelList", awardLevelList);

		return "modules/rew/csres/csresList";

	}

	@RequestMapping(value = "levelList")
	public String list(RewcsResult rewcsResult, Model model) {

		model.addAttribute("iscs", UtilsMessage.iscs());
		if (!UtilsMessage.iscs()) {
			model.addAttribute("message", UtilsMessage.getMessage("notcsmes", UserUtils.getUser().getName()));
			return "modules/rew/csres/csresList";
		}

		if (StringUtils.isNotBlank(rewcsResult.getLevelId())) {
			model.addAttribute("rewcsResult", rewcsResult);
			// 奖项类别
			List<Rewawardlevel> awardLevelList2 = UtilsFns.getAwardLevelList();
			List<Rewawardlevel> awardLevelList = new ArrayList<Rewawardlevel>(awardLevelList2);
			Rewawardlevel r = new Rewawardlevel();
			r.setId("");
			r.setLevelname("所有");
			awardLevelList.add(r);
			model.addAttribute("awardLevelList", awardLevelList);
			
			List<RewcsResult> results = csresService.getListByLevelId(rewcsResult);
			model.addAttribute("results", results);
			
			

			// 获得参与投票的总人数
			List<User> zjUsers = UserUtils.selectcsAllZJUsers();
			int allNum = 0;
			if (zjUsers != null && !zjUsers.isEmpty()) {
				allNum = zjUsers.size();
			}

			model.addAttribute("isShow", UtilsMessage.isGlyRole());
			model.addAttribute("allNum", allNum);
			
			

			return "modules/rew/csres/csresList";
		} else {
			return "redirect:" + Global.getAdminPath() + "/csres/res/";
		}

	}

	@RequestMapping(value = "print")
	public String print(RewcsResult rewcsResult, Model model) {

		List<RewcsResult> printList = csresService.printList(rewcsResult);

		model.addAttribute("printList", printList);

		return "modules/rew/csres/printList";
	}

}