/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.dict.entity.Rewsysval;
import com.zkr.rew.dict.dao.RewsysvalDao;

/**
 * 历年投票数目控制Service
 * @author 成茂涛
 * @version 2017-10-16
 */
@Service
@Transactional(readOnly = true)
public class RewsysvalService extends CrudService<RewsysvalDao, Rewsysval> {

	public Rewsysval get(String id) {
		return super.get(id);
	}
	
	public List<Rewsysval> findList(Rewsysval rewsysval) {
		return super.findList(rewsysval);
	}
	
	public Page<Rewsysval> findPage(Page<Rewsysval> page, Rewsysval rewsysval) {
		return super.findPage(page, rewsysval);
	}
	
	@Transactional(readOnly = false)
	public void save(Rewsysval rewsysval) {
		super.save(rewsysval);
	}
	
	@Transactional(readOnly = false)
	public void delete(Rewsysval rewsysval) {
		super.delete(rewsysval);
	}
	
}