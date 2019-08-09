/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.dict.entity.RewFsdatasrc;
import com.zkr.rew.dict.dao.RewFsdatasrcDao;

/**
 * 复审数据来源Service
 * @author 成茂涛
 * @version 2017-11-12
 */
@Service
@Transactional(readOnly = true)
public class RewFsdatasrcService extends CrudService<RewFsdatasrcDao, RewFsdatasrc> {

	public RewFsdatasrc get(String id) {
		return super.get(id);
	}
	
	public List<RewFsdatasrc> findList(RewFsdatasrc rewFsdatasrc) {
		return super.findList(rewFsdatasrc);
	}
	
	public Page<RewFsdatasrc> findPage(Page<RewFsdatasrc> page, RewFsdatasrc rewFsdatasrc) {
		return super.findPage(page, rewFsdatasrc);
	}
	
	@Transactional(readOnly = false)
	public void save(RewFsdatasrc rewFsdatasrc) {
		super.save(rewFsdatasrc);
	}
	
	@Transactional(readOnly = false)
	public void delete(RewFsdatasrc rewFsdatasrc) {
		super.delete(rewFsdatasrc);
	}

	public RewFsdatasrc getRewFsdatasrcByProjectId(RewFsdatasrc p) {
	    return dao.getRewFsdatasrcByProjectId(p);
    }
	
}