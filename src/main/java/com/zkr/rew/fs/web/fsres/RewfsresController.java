/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.fs.web.fsres;

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

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMath;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.csres.entity.RewcsResult;
import com.zkr.rew.fs.entity.fsdyl.Rewfsdyl;
import com.zkr.rew.fs.entity.fsres.Rewfsres;
import com.zkr.rew.fs.service.fsdyl.RewfsdylService;
import com.zkr.rew.fs.service.fsres.RewfsresService;

/**
 * 复审结果Controller
 * @author 成茂涛
 * @version 2016-08-23
 */
@Controller
@RequestMapping(value = "${adminPath}/fs/fsres/rewfsres")
public class RewfsresController extends BaseController {

	@Autowired
	private RewfsresService rewfsresService;
	
	@Autowired 
	private RewfsdylService rewfsdylService;
	
	@ModelAttribute
	public Rewfsres get(@RequestParam(required=false) String id) {
		Rewfsres entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rewfsresService.get(id);
		}
		if (entity == null){
			entity = new Rewfsres();
		}
		return entity;
	}
	
	// 复审第一轮投票结果
	@RequestMapping(value = {"initList", ""})
	public String list(Rewfsres rewfsres, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		model.addAttribute("isfs", UtilsMessage.isfs());
		// 参与复审评选
		if(!UtilsMessage.isfs()){
			model.addAttribute("message", UtilsMessage.getMessage("notfsmes",UserUtils.getUser().getName()));
			return "modules/rew/fs/fsres/rewfsresdylList";
		}
		
		// 获得参与投票的总人数
		List<User> zjUsers = UserUtils.selectfsAllZJUsers();
		int allNum = 0;
		if (zjUsers != null && !zjUsers.isEmpty()) {
			allNum = zjUsers.size();
		}
		
		model.addAttribute("allNum", allNum);
		
		// 复审可用
		if(UtilsFns.getFsProgressResIsEnable()){
			// 特等奖列表
			List<RewcsResult> result1 = new ArrayList<RewcsResult>();
			
			// 初审是特等奖  但是复审没有评选上的
			List<RewcsResult> notTdjResult = new ArrayList<RewcsResult>();
			
			List<RewcsResult> selectTdjList = rewfsdylService.selectTdjList();
			
			if (selectTdjList != null && !selectTdjList.isEmpty()) {
				for (RewcsResult e : selectTdjList) {
					// 大于2/3
					if (UtilsMath.isTrue(allNum, e.getNum(), 1)) {
						e.setAllNum(allNum);
						e.setLevelId("1");
						result1.add(e);  // 特等奖
						//paramets.add(e.getProjectid());
					}else{
						e.setAllNum(allNum);
						e.setLevelId("");
						e.setLevelName(UtilsMessage.getValueByKey("levelName1"));
						notTdjResult.add(e);
					}
				}
			}
			
			// 补投时 结果先不保存，补投结束在保存
			if(!UtilsFns.getmenucontroltp("1")){
				// 初审结果保存到数据库
				rewfsresService.insertData(result1);
			}

			result1.addAll(notTdjResult);
			model.addAttribute("results", result1);
		}else{
			model.addAttribute("results", rewfsresService.getFsDylTpResult());
		}

		return "modules/rew/fs/fsres/rewfsresdylList";
	}

	
	// 复审第二轮投票结果
	@RequestMapping(value = "initList2")
	public String list2(Rewfsres rewfsres, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		model.addAttribute("isfs", UtilsMessage.isfs());
		// 参与复审评选
		if(!UtilsMessage.isfs()){
			model.addAttribute("message", UtilsMessage.getMessage("notfsmes",UserUtils.getUser().getName()));
			return "modules/rew/fs/fsres/rewfsresdelList";
		}
		
		// 获得参与投票的总人数
		List<User> zjUsers = UserUtils.selectfsAllZJUsers();
		int allNum = 0;
		if (zjUsers != null && !zjUsers.isEmpty()) {
			allNum = zjUsers.size();
		}
		
		model.addAttribute("allNum", allNum);
		

		if(UtilsFns.getFsProgressResIsEnable()){
			// 一等奖列表
			List<RewcsResult> result = new ArrayList<RewcsResult>();
			List<RewcsResult> selectYdjList = rewfsdylService.selectYdjList();		
			List<RewcsResult> notYdjResult = new ArrayList<RewcsResult>();
			if(!selectYdjList.isEmpty()){
				for (RewcsResult e : selectYdjList) {
					if (UtilsMath.isTrue(allNum, e.getNum(), 2)) {
						e.setAllNum(allNum);
						e.setLevelId("2");
						result.add(e);  // 一等奖
					}else{
						e.setAllNum(allNum);
						e.setLevelId("");
						e.setLevelName(UtilsMessage.getValueByKey("levelName2"));
						notYdjResult.add(e);
					}
				}
			}
			
			if(!UtilsFns.getmenucontroltp("2")){
				// 初审结果保存到数据库
				rewfsresService.insertData2(result);
			}
			result.addAll(notYdjResult);
			model.addAttribute("results", result);
		}else{
			model.addAttribute("results", rewfsresService.getFsDelTpResult());
		}
		
		
		
		
		return "modules/rew/fs/fsres/rewfsresdelList";
	}
	
	
	
	
	// 复审第三轮投票结果
	@RequestMapping(value = "initList3")
	public String list3(Rewfsres rewfsres, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		model.addAttribute("isfs", UtilsMessage.isfs());
		// 参与复审评选
		if(!UtilsMessage.isfs()){
			model.addAttribute("message", UtilsMessage.getMessage("notfsmes",UserUtils.getUser().getName()));
			return "modules/rew/fs/fsres/rewfsresdslList";
		}
		
		// 获得参与投票的总人数
		List<User> zjUsers = UserUtils.selectfsAllZJUsers();
		int allNum = 0;
		if (zjUsers != null && !zjUsers.isEmpty()) {
			allNum = zjUsers.size();
		}
		
		model.addAttribute("allNum", allNum);

		if(UtilsFns.getFsProgressResIsEnable()){
			// 二等奖列表
			List<RewcsResult> result = new ArrayList<RewcsResult>();
			

			// 特等奖 和 一等奖 结果
			List<Rewfsdyl> parametes = rewfsdylService.selectTdjAndYdjOKList();
			
			List<RewcsResult> selectEdjList = rewfsdylService.selectEdjList(parametes);
			
			List<RewcsResult> notEdjResult = new ArrayList<RewcsResult>();
			if(!selectEdjList.isEmpty()){
				for (RewcsResult e : selectEdjList) {
					if (UtilsMath.isTrue(allNum, e.getNum(), 2)) {
						e.setAllNum(allNum);
						e.setLevelId("3");
						result.add(e);  // 二等奖
					}else{
						e.setAllNum(allNum);
						e.setLevelId("");
						e.setLevelName(UtilsMessage.getValueByKey("levelName3"));
						notEdjResult.add(e);
					}
				}
			}
			// 初审结果保存到数据库
			rewfsresService.insertData3(result);

			result.addAll(notEdjResult);
			model.addAttribute("results", result);
		}else{
			model.addAttribute("results", rewfsresService.getFsDslTpResult());
		}
		return "modules/rew/fs/fsres/rewfsresdslList";
	}
	
	
	// 复审结果
	@RequestMapping(value = "initList0")
	public String list0(Rewfsres rewfsres, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		model.addAttribute("isfs", UtilsMessage.isfs());
		// 参与复审评选
		if(!UtilsMessage.isfs()){
			model.addAttribute("message", UtilsMessage.getMessage("notfsmes",UserUtils.getUser().getName()));
			return "modules/rew/fs/fsres/rewfsresList";
		}
		
		// 获得参与投票的总人数
		List<User> zjUsers = UserUtils.selectfsAllZJUsers();
		int allNum = 0;
		if (zjUsers != null && !zjUsers.isEmpty()) {
			allNum = zjUsers.size();
		}
		
		model.addAttribute("allNum", allNum);
		
		// 复审结果
		List<RewcsResult> result = rewfsresService.selectFsres();
	
		model.addAttribute("results", result);
		
		model.addAttribute("isShow", UtilsMessage.isGlyRole());
		return "modules/rew/fs/fsres/rewfsresList";
	}
	
	@RequestMapping(value = "print")
	public String print(Rewfsres rewfsres, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		// 复审结果
		List<RewcsResult> result = rewfsresService.selectFsres();
		model.addAttribute("results", result);
		
		model.addAttribute("isShow", UtilsMessage.isGlyRole());
		return "modules/rew/fs/fsres/printfsresList";
	}

}