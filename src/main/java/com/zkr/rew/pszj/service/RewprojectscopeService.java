/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.pszj.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.pszj.dao.RewprojectscopeDao;
import com.zkr.rew.pszj.entity.Rewprojectscope;

/**
 * 评审专家Service
 * 
 * @author 成茂涛
 * @version 2016-08-12
 */
@Service
@Transactional(readOnly = true)
public class RewprojectscopeService extends CrudService<RewprojectscopeDao, Rewprojectscope> {

	public Rewprojectscope get(String id) {
		return super.get(id);
	}

	public List<Rewprojectscope> findList(Rewprojectscope rewprojectscope) {
		return super.findList(rewprojectscope);
	}

	public Page<Rewprojectscope> findPage(Page<Rewprojectscope> page, Rewprojectscope rewprojectscope) {
		return super.findPage(page, rewprojectscope);
	}

	@Transactional(readOnly = false)
	public void save(Rewprojectscope rewprojectscope) {
		super.save(rewprojectscope);
	}

	@Transactional(readOnly = false)
	public void delete(Rewprojectscope rewprojectscope) {
		super.delete(rewprojectscope);
	}

}