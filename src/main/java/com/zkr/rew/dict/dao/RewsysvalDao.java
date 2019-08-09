/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zkr.rew.dict.entity.Rewsysval;

/**
 * 历年投票数目控制DAO接口
 * @author 成茂涛
 * @version 2017-10-16
 */
@MyBatisDao
public interface RewsysvalDao extends CrudDao<Rewsysval> {
	
}