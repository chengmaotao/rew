/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.dict.entity.RewRes;
import com.zkr.rew.dict.dao.RewResDao;

/**
 * 项目流程结果Service
 * @author 成茂涛
 * @version 2017-11-06
 */
@Service
@Transactional(readOnly = true)
public class RewResService extends CrudService<RewResDao, RewRes> {

	public RewRes get(String id) {
		return super.get(id);
	}
	
	public List<RewRes> findList(RewRes rewRes) {
		return super.findList(rewRes);
	}
	
	public Page<RewRes> findPage(Page<RewRes> page, RewRes rewRes) {
		return super.findPage(page, rewRes);
	}
	
	@Transactional(readOnly = false)
	public void save(RewRes rewRes) {
		super.save(rewRes);
	}
	
	@Transactional(readOnly = false)
	public void delete(RewRes rewRes) {
		super.delete(rewRes);
	}
	
}