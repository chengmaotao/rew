/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zkr.rew.dict.entity.Rewscopekpi;

/**
 * 保存字典DAO接口
 * @author 成茂涛
 * @version 2016-08-04
 */
@MyBatisDao
public interface RewscopekpiDao extends CrudDao<Rewscopekpi> {

	List<Rewscopekpi> findObjByCategoryIdAndSortNum(Rewscopekpi rewscopekpi);
	
	List<Rewscopekpi> findObjListByCategoryId(String id);
}