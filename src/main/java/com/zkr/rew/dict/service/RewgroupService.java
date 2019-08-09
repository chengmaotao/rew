/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.dict.dao.RewgroupDao;
import com.zkr.rew.dict.entity.Rewgroup;

/**
 * 保存字典Service
 * @author 成茂涛
 * @version 2016-08-04
 */
@Service
@Transactional(readOnly = true)
public class RewgroupService extends CrudService<RewgroupDao, Rewgroup> {

	public Rewgroup get(String id) {
		return super.get(id);
	}
	
	public List<Rewgroup> findList(Rewgroup rewgroup) {
		return super.findList(rewgroup);
	}
	
	public Page<Rewgroup> findPage(Page<Rewgroup> page, Rewgroup rewgroup) {
		return super.findPage(page, rewgroup);
	}
	
	@Transactional(readOnly = false)
	public void save(Rewgroup rewgroup) {
		if (rewgroup.getIsNewRecord()){
			rewgroup.preInsert();
			
			rewgroup.setCurrentYear(UtilsFns.selectCurrentYear());
			
			dao.insert(rewgroup);
			
			dao.isertMenuEnable(rewgroup);
			
			//dao.updateMenuEnable(rewgroup);
			
		}else{
			rewgroup.preUpdate();
			dao.update(rewgroup);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Rewgroup rewgroup) {
		
		dao.deleteMenuEnableByGroup(rewgroup);
		
		//dao.deleteGroupSonByGroup(rewgroup);
		
		dao.delete(rewgroup);
		
		//super.delete(rewgroup);
	}
	
}