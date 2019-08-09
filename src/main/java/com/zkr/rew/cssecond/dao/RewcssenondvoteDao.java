/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.cssecond.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.zkr.rew.cssecond.entity.Rewcssenondvote;

/**
 * 初审第二轮投票DAO接口
 * 
 * @author 成茂涛
 * @version 2016-08-16
 */
@MyBatisDao
public interface RewcssenondvoteDao extends CrudDao<Rewcssenondvote> {
	public List<Rewcssenondvote> selectVotedNum0(User user);

	public List<Rewcssenondvote> selectVotedNum1(User user);
}