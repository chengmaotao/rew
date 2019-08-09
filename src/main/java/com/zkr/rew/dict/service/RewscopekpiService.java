/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.dict.dao.RewscopekpiDao;
import com.zkr.rew.dict.entity.Rewscopekpi;

/**
 * 保存字典Service
 * @author 成茂涛
 * @version 2016-08-04
 */
@Service
@Transactional(readOnly = true)
public class RewscopekpiService extends CrudService<RewscopekpiDao, Rewscopekpi> {

	@Autowired
	private RewscopekpiDao scopekpiDao;
	
	public Rewscopekpi get(String id) {
		return super.get(id);
	}
	
	public List<Rewscopekpi> findList(Rewscopekpi rewscopekpi) {
		return super.findList(rewscopekpi);
	}
	
	public Page<Rewscopekpi> findPage(Page<Rewscopekpi> page, Rewscopekpi rewscopekpi) {
		return super.findPage(page, rewscopekpi);
	}
	
	@Transactional(readOnly = false)
	public void save(Rewscopekpi rewscopekpi) {
		super.save(rewscopekpi);
	}
	
	@Transactional(readOnly = false)
	public void delete(Rewscopekpi rewscopekpi) {
		super.delete(rewscopekpi);
	}

	public boolean findObjByCategoryIdAndSortNum(Rewscopekpi rewscopekpi) {
		List<Rewscopekpi> retList = scopekpiDao.findObjByCategoryIdAndSortNum(rewscopekpi);
		if(retList != null && !retList.isEmpty()){
			return true;
		}
		return false;
		
	}
	
	public List<Rewscopekpi> findObjListByCategoryId(String id){
		return scopekpiDao.findObjListByCategoryId(id);
	}
	
}