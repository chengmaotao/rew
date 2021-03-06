/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zkr.rew.dict.entity.RewRes;

/**
 * 项目流程结果DAO接口
 * @author 成茂涛
 * @version 2017-11-06
 */
@MyBatisDao
public interface RewResDao extends CrudDao<RewRes> {

	RewRes selectprogerssRes(String selectCurrentYear);
	
}