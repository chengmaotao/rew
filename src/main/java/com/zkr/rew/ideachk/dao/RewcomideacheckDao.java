/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.ideachk.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.zkr.rew.dict.entity.Rewproject;
import com.zkr.rew.ideachk.entity.Rewcomideacheck;

/**
 * 推荐意见投票DAO接口
 * @author 成茂涛
 * @version 2016-08-18
 */
@MyBatisDao
public interface RewcomideacheckDao extends CrudDao<Rewcomideacheck> {
	
	public List<Rewcomideacheck> selectCountNumByProject(User user);

	public void deleteByProject(Rewproject p);
}