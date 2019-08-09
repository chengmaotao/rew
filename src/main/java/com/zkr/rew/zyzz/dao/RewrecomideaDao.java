/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.zyzz.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.zkr.rew.dict.entity.Rewproject;
import com.zkr.rew.zyzz.entity.Rewrecomidea;

/**
 * 小组推荐意见DAO接口
 * 
 * @author 成茂涛
 * @version 2016-08-15
 */
@MyBatisDao
public interface RewrecomideaDao extends CrudDao<Rewrecomidea> {

	public List<Rewrecomidea> findListByGroup(Rewrecomidea rewrecomidea);

	public List<Rewrecomidea> selectAllMessByGroup(User user);
	public List<Rewrecomidea> selectAllMessByGroup2(User user);
	
	public Rewrecomidea get(Rewproject p);
}