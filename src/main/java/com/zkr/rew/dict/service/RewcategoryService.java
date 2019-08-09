/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.dict.dao.RewcategoryDao;
import com.zkr.rew.dict.entity.Rewcategory;

/**
 * 保存字典Service
 * @author 成茂涛
 * @version 2016-08-04
 */
@Service
@Transactional(readOnly = true)
public class RewcategoryService extends CrudService<RewcategoryDao, Rewcategory> {

	public Rewcategory get(String id) {
		return super.get(id);
	}
	
	public List<Rewcategory> findList(Rewcategory rewcategory) {
		return super.findList(rewcategory);
	}
	
	public Page<Rewcategory> findPage(Page<Rewcategory> page, Rewcategory rewcategory) {
		return super.findPage(page, rewcategory);
	}
	
	@Transactional(readOnly = false)
	public void save(Rewcategory rewcategory) {
		super.save(rewcategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(Rewcategory rewcategory) {
		super.delete(rewcategory);
	}
	
}