/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.fs.service.fsres;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.csres.entity.RewcsResult;
import com.zkr.rew.fs.dao.fsres.RewfsresDao;
import com.zkr.rew.fs.entity.fsres.Rewfsres;

/**
 * 复审结果Service
 * 
 * @author 成茂涛
 * @version 2016-08-23
 */
@Service
@Transactional(readOnly = true)
public class RewfsresService extends CrudService<RewfsresDao, Rewfsres> {

	@Autowired
	private RewfsresDao fsresDao;

	public Rewfsres get(String id) {
		return super.get(id);
	}

	public List<Rewfsres> findList(Rewfsres rewfsres) {
		return super.findList(rewfsres);
	}

	public Page<Rewfsres> findPage(Page<Rewfsres> page, Rewfsres rewfsres) {
		return super.findPage(page, rewfsres);
	}

	@Transactional(readOnly = false)
	public void save(Rewfsres rewfsres) {
		super.save(rewfsres);
	}

	@Transactional(readOnly = false)
	public void delete(Rewfsres rewfsres) {
		super.delete(rewfsres);
	}

	@Transactional(readOnly = false)
	public void insertData(List<RewcsResult> result1) {
		fsresDao.deleteData(UtilsFns.selectCurrentYear());// 清空结果表
		if(result1 != null && !result1.isEmpty()){
			fsresDao.insertData(result1);
		}

	}
	@Transactional(readOnly = false)
	public void insertData2(List<RewcsResult> result1) {
		fsresDao.deleteData2(UtilsFns.selectCurrentYear());// 清空结果表
		if(result1 != null && !result1.isEmpty()){
			fsresDao.insertData2(result1);
		}
		
	}
	@Transactional(readOnly = false)
	public void insertData3(List<RewcsResult> result1) {
		fsresDao.deleteData3(UtilsFns.selectCurrentYear());// 清空结果表
		if(result1 != null && !result1.isEmpty()){
			fsresDao.insertData3(result1);
		}
		
	}

	public List<RewcsResult> selectFsres() {
		// TODO Auto-generated method stub
		return fsresDao.selectFsres(UtilsFns.selectCurrentYear());// 清空结果表
	}
	
	public List<RewcsResult> getFsDylTpResult() {
		// TODO Auto-generated method stub
		return fsresDao.getFsDylTpResult(UtilsFns.selectCurrentYear());// 清空结果表
	}
	
	public List<RewcsResult> getFsDelTpResult() {
		// TODO Auto-generated method stub
		return fsresDao.getFsDelTpResult(UtilsFns.selectCurrentYear());// 清空结果表
	}
	
	public List<RewcsResult> getFsDslTpResult() {
		// TODO Auto-generated method stub
		return fsresDao.getFsDslTpResult(UtilsFns.selectCurrentYear());// 清空结果表
	}

}