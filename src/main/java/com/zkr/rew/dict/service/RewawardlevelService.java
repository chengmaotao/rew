/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.dict.dao.RewawardlevelDao;
import com.zkr.rew.dict.entity.Rewawardlevel;

/**
 * 保存字典Service
 * @author 成茂涛
 * @version 2016-08-04
 */
@Service
@Transactional(readOnly = true)
public class RewawardlevelService extends CrudService<RewawardlevelDao, Rewawardlevel> {

	public Rewawardlevel get(String id) {
		return super.get(id);
	}
	
	public List<Rewawardlevel> findList(Rewawardlevel rewawardlevel) {
		return super.findList(rewawardlevel);
	}
	
	public Page<Rewawardlevel> findPage(Page<Rewawardlevel> page, Rewawardlevel rewawardlevel) {
		return super.findPage(page, rewawardlevel);
	}
	
	@Transactional(readOnly = false)
	public void save(Rewawardlevel rewawardlevel) {
		super.save(rewawardlevel);
	}
	
	@Transactional(readOnly = false)
	public void delete(Rewawardlevel rewawardlevel) {
		super.delete(rewawardlevel);
	}
	
}