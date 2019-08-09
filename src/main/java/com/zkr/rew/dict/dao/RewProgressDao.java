/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zkr.rew.dict.entity.RewProgress;

/**
 * 项目流程DAO接口
 * @author 成茂涛
 * @version 2017-11-06
 */
@MyBatisDao
public interface RewProgressDao extends CrudDao<RewProgress> {
	public String selectprogerss();
	
	public void saveprogerss(String progress);
}