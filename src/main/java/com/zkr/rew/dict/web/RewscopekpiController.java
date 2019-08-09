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
import com.zkr.rew.dict.entity.Rewscopekpi;
import com.zkr.rew.dict.service.RewscopekpiService;

/**
 * 保存字典Controller
 * 
 * @author 成茂涛
 * @version 2016-08-04
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/rewscopekpi")
public class RewscopekpiController extends BaseController {

	@Autowired
	private RewscopekpiService rewscopekpiService;

	@ModelAttribute
	public Rewscopekpi get(@RequestParam(required = false) String id) {
		Rewscopekpi entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = rewscopekpiService.get(id);
		}
		if (entity == null) {
			entity = new Rewscopekpi();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(Rewscopekpi rewscopekpi, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Rewscopekpi> page = rewscopekpiService.findPage(new Page<Rewscopekpi>(request, response), rewscopekpi);
		model.addAttribute("page", page);
		
		model.addAttribute("categorys", UtilsFns.getCagegoryList());
		return "modules/rew/dict/rewscopekpiList";
	}

	@RequestMapping(value = "form")
	public String form(Rewscopekpi rewscopekpi, Model model) {
		model.addAttribute("rewscopekpi", rewscopekpi);
		
		model.addAttribute("categorys", UtilsFns.getCagegoryList());
		return "modules/rew/dict/rewscopekpiForm";
	}

	@RequestMapping(value = "save")
	public String save(Rewscopekpi rewscopekpi, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rewscopekpi)) {
			return form(rewscopekpi, model);
		}
		rewscopekpiService.save(rewscopekpi);
		addMessage(redirectAttributes, "保存评价指标成功");
		
        // 清空缓存
		UtilsFns.clearCacheById(UtilsFns.SCOPEKPI_CACHE, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.SCOPEKPI_CACHE, UtilsFns.CACHE_LISE_CATEGORY + rewscopekpi.getId());
		UtilsFns.clearCacheById(UtilsFns.SCOPEKPI_CACHE, UtilsFns.SCOPEKPI_CACHE_ID + rewscopekpi.getId());
		
		return "redirect:" + Global.getAdminPath() + "/dict/rewscopekpi/?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(Rewscopekpi rewscopekpi, RedirectAttributes redirectAttributes) {
		rewscopekpiService.delete(rewscopekpi);
		addMessage(redirectAttributes, "删除评价指标成功");
        // 清空缓存
		UtilsFns.clearCacheById(UtilsFns.SCOPEKPI_CACHE, UtilsFns.CACHE_LISE);
		UtilsFns.clearCacheById(UtilsFns.SCOPEKPI_CACHE, UtilsFns.CACHE_LISE_CATEGORY + rewscopekpi.getId());
		UtilsFns.clearCacheById(UtilsFns.SCOPEKPI_CACHE, UtilsFns.SCOPEKPI_CACHE_ID + rewscopekpi.getId());
		return "redirect:" + Global.getAdminPath() + "/dict/rewscopekpi/?repage";
	}
	
	
   /**
    * 根据申报类别 和  sortNum 
    * 该申报类别下已经有了 该sortNum 返回true
    * 否则 返回false
    * @param rewscopekpi
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "checkSortNum")
    public boolean checkSortNum(Rewscopekpi rewscopekpi) {
    	return rewscopekpiService.findObjByCategoryIdAndSortNum(rewscopekpi);  
    }
	
	// 申报类别
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