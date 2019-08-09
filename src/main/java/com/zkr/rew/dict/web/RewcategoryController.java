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
import com.zkr.rew.dict.entity.Rewcategory;
import com.zkr.rew.dict.service.RewcategoryService;

/**
 * 保存字典Controller
 * @author 成茂涛
 * @version 2016-08-04
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/rewcategory")
public class RewcategoryController extends BaseController {

	@Autowired
	private RewcategoryService rewcategoryService;
	
	@ModelAttribute
	public Rewcategory get(@RequestParam(required=false) String id) {
		Rewcategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rewcategoryService.get(id);
		}
		if (entity == null){
			entity = new Rewcategory();
		}
		return entity;
	}

	@RequestMapping(value = {"list", ""})
	public String list(Rewcategory rewcategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Rewcategory> page = rewcategoryService.findPage(new Page<Rewcategory>(request, response), rewcategory); 
		model.addAttribute("page", page);
		return "modules/rew/dict/rewcategoryList";
	}


	@RequestMapping(value = "form")
	public String form(Rewcategory rewcategory, Model model) {
		model.addAttribute("rewcategory", rewcategory);
		return "modules/rew/dict/rewcategoryForm";
	}


	@RequestMapping(value = "save")
	public String save(Rewcategory rewcategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rewcategory)){
			return form(rewcategory, model);
		}
		rewcategoryService.save(rewcategory);
		
        // 清空缓存
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE, UtilsFns.PROJECT_CACHE_ID + rewcategory.getId());
		addMessage(redirectAttributes, "保存申报类别成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewcategory/?repage";
	}
	

	@RequestMapping(value = "delete")
	public String delete(Rewcategory rewcategory, RedirectAttributes redirectAttributes) {
		rewcategoryService.delete(rewcategory);
		
        // 清空缓存
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.PROJECT_CACHE, UtilsFns.PROJECT_CACHE_ID + rewcategory.getId());
		addMessage(redirectAttributes, "删除申报类别成功");
		return "redirect:"+Global.getAdminPath()+"/dict/rewcategory/?repage";
	}
	
	
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData() {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Rewcategory> list = UtilsFns.getCagegoryList();
        for (int i = 0; i < list.size(); i++) {
        	Rewcategory e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("name", e.getCategoryname());
            mapList.add(map);
        }
        return mapList;
    }

}