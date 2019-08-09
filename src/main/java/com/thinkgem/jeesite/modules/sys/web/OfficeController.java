/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;

/**
 * 机构Controller
 * 
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@ModelAttribute("office")
	public Office get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return officeService.get(id);
		} else {
			return new Office();
		}
	}

	/**
	 * 默认机构列表
	 * @param office
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "" })
	public String index(Office office, Model model) {
		model.addAttribute("list", officeService.findList(office));
		return "modules/sys/officeList";
	}

	/**
	 * 机构列表
	 * @param office
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "list" })
	public String list(Office office, Model model) {
		model.addAttribute("list", officeService.findList(office));
		return "modules/sys/officeList";
	}

	
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}

	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes) {

		officeService.save(office);

		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");

		return "redirect:" + adminPath + "/sys/office/list";
	}

	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {

		officeService.delete(office);
		addMessage(redirectAttributes, "删除机构成功");

		return "redirect:" + adminPath + "/sys/office/list";
	}

	/**
	 * 验证机构名称是否已经存在
	 * 
	 * @param office
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "checkOfficeName")
	public @ResponseBody
	boolean checkOfficeName(String name) {

		return officeService.checkOfficeName(name);
	}

}
