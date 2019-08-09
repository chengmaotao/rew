/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.csfirst.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.zkr.rew.csfirst.entity.Rewcsfirstvote;

/**
 * 初审第一轮投票DAO接口
 * @author 成茂涛
 * @version 2016-08-15
 */
@MyBatisDao
public interface RewcsfirstvoteDao extends CrudDao<Rewcsfirstvote> {
	
	public List<Rewcsfirstvote> selectVotedNum(User user);
}