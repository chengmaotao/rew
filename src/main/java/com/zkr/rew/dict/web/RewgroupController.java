/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.dict.entity.Rewgroup;
import com.zkr.rew.dict.service.RewgroupService;

/**
 * 保存字典Controller
 * @author 成茂涛
 * @version 2016-08-04
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/rewgroup")
public class RewgroupController extends BaseController {

	@Autowired
	private RewgroupService rewgroupService;
	
	@ModelAttribute
	public Rewgroup get(@RequestParam(required=false) String id) {
		Rewgroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rewgroupService.get(id);
		}
		if (entity == null){
			entity = new Rewgroup();
		}
		return entity;
	}
	

	@RequestMapping(value = {"list", ""})
	public String list(Rewgroup rewgroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		rewgroup.setCurrentYear(UtilsFns.selectCurrentYear());
		Page<Rewgroup> page = rewgroupService.findPage(new Page<Rewgroup>(request, response), rewgroup); 
		model.addAttribute("page", page);
		return "modules/rew/dict/rewgroupList";
	}


	@RequestMapping(value = "form")
	public String form(Rewgroup rewgroup, Model model) {
		model.addAttribute("rewgroup", rewgroup);
		return "modules/rew/dict/rewgroupForm";
	}


	@RequestMapping(value = "save")
	public String save(Rewgroup rewgroup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rewgroup)){
			return form(rewgroup, model);
		}
		rewgroupService.save(rewgroup);
        // 清空缓存
		UtilsFns.clearCacheById(UtilsFns.GROUP_CACHE, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.GROUP_CACHE, UtilsFns.GROUP_CACHE_ID + rewgroup.getId());
		addMessage(redirectAttributes, "保存专业组成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewgroup/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Rewgroup rewgroup, RedirectAttributes redirectAttributes) {
		rewgroupService.delete(rewgroup);
        // 清空缓存
		UtilsFns.clearCacheById(UtilsFns.GROUP_CACHE, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.GROUP_CACHE, UtilsFns.GROUP_CACHE_ID + rewgroup.getId());
		addMessage(redirectAttributes, "删除专业组成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewgroup/?repage";
	}
	
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData() {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Rewgroup> list = UtilsFns.getEnableGroupList();
        for (int i = 0; i < list.size(); i++) {
        	Rewgroup e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("name", e.getGroupname());
            mapList.add(map);
        }
        return mapList;
    }

}