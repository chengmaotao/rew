/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.csres.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zkr.rew.csres.entity.RewcsResult;

/**
 * 初审结果DAO接口
 * 
 * @author 成茂涛
 * @version 2016-08-15
 */
@MyBatisDao
public interface RewcsresDao extends CrudDao<RewcsResult> {

	public List<RewcsResult> selectTdjList(String currentYear);

	public List<RewcsResult> selectYdjList(Map<String,Object> paramets);
	
	public List<RewcsResult> selectEdjList(Map<String,Object> paramets);
	
	public List<RewcsResult> selectBsjList(Map<String,Object> paramets);

	public void insertData(List<RewcsResult> result);
	
	public int deleteData(String currentYear);

	public List<RewcsResult> getListByLevelId(RewcsResult rewcsResult);
	
	public List<RewcsResult> getbsjList(RewcsResult rewcsResult);
	
	public List<RewcsResult> printList(RewcsResult rewcsResult);
	
	
}