/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.dict.entity.RewProgress;
import com.zkr.rew.dict.dao.RewProgressDao;

/**
 * 项目流程Service
 * @author 成茂涛
 * @version 2017-11-06
 */
@Service
@Transactional(readOnly = true)
public class RewProgressService extends CrudService<RewProgressDao, RewProgress> {

	public RewProgress get(String id) {
		return super.get(id);
	}
	
	public List<RewProgress> findList(RewProgress rewProgress) {
		return super.findList(rewProgress);
	}
	
	public Page<RewProgress> findPage(Page<RewProgress> page, RewProgress rewProgress) {
		return super.findPage(page, rewProgress);
	}
	
	@Transactional(readOnly = false)
	public void save(RewProgress rewProgress) {
		super.save(rewProgress);
	}
	
	@Transactional(readOnly = false)
	public void delete(RewProgress rewProgress) {
		super.delete(rewProgress);
	}

	@Transactional(readOnly = false)
	public void saveProgerss(String rewProgress) {
	    dao.saveprogerss(rewProgress);
	    
    }
	
}