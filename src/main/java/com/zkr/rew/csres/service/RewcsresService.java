/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.csres.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.csres.dao.RewcsresDao;
import com.zkr.rew.csres.entity.RewcsResult;

/**
 * 初审第一轮投票Service
 * 
 * @author 成茂涛
 * @version 2016-08-15
 */
@Service
@Transactional(readOnly = true)
public class RewcsresService extends CrudService<RewcsresDao, RewcsResult> {

	@Autowired
	private RewcsresDao csresDao;
	
	public List<RewcsResult> selectTdjList(String currentyear){
		return csresDao.selectTdjList(currentyear);
	}

	public List<RewcsResult> selectYdjList(List<String> parametersList) {
		
		Map<String,Object> parameters = new HashMap<String, Object>();
		
		parameters.put("list", parametersList);
		
        parameters.put("currentYear", UtilsFns.selectCurrentYear());
		
		return csresDao.selectYdjList(parameters);
		
	}
	
	public List<RewcsResult> selectEdjList(List<String> parametersList){
		
		Map<String,Object> parameters = new HashMap<String, Object>();
		
		parameters.put("list", parametersList);
		
       parameters.put("currentYear", UtilsFns.selectCurrentYear());
		
		return csresDao.selectEdjList(parameters);
	}
	
	public List<RewcsResult> selectBsjList(List<String> parametersList){
		
		Map<String,Object> parameters = new HashMap<String, Object>();
		
		parameters.put("list", parametersList);
		
       parameters.put("currentYear", UtilsFns.selectCurrentYear());
		
		return csresDao.selectBsjList(parameters);
	}	
	
	

	@Transactional(readOnly = false)
	public void insertData(List<RewcsResult> result) {
		csresDao.deleteData(UtilsFns.selectCurrentYear());// 清空初审结果表
		if(result != null && !result.isEmpty()){
			
			csresDao.insertData(result); 
		}
	}

	public List<RewcsResult> getListByLevelId(RewcsResult rewcsResult) {
		rewcsResult.setCurrentYear(UtilsFns.selectCurrentYear());
		
/*		// 不授奖
		if("4".equals(rewcsResult.getLevelId())){
			List<RewcsResult> res1 = csresDao.getListByLevelId(rewcsResult);
			List<RewcsResult> res2 = csresDao.getbsjList(rewcsResult);
			res1.addAll(res2);
			return res1;
		}else{
			// 特等奖 一等奖 二等奖
			return csresDao.getListByLevelId(rewcsResult);
		}*/
		
		
		return csresDao.getListByLevelId(rewcsResult);
	}
	
	public List<RewcsResult> printList(RewcsResult rewcsResult){
		rewcsResult.setCurrentYear(UtilsFns.selectCurrentYear());
		return csresDao.printList(rewcsResult);
	}
	
	public List<RewcsResult> getbsjList(RewcsResult rewcsResult) {
		rewcsResult.setCurrentYear(UtilsFns.selectCurrentYear());
		return csresDao.getbsjList(rewcsResult);
	}
	
}