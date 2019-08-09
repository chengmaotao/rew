/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.zszj.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zkr.rew.zszj.entity.Rewidea;

/**
 * 主审专家DAO接口
 * @author 成茂涛
 * @version 2016-08-10
 */
@MyBatisDao
public interface RewideaDao extends CrudDao<Rewidea> {
	
	public Rewidea getrewIdeaByProjectId(String projectId);

	public List<Rewidea> findzxList(Rewidea rewidea);
	public List<Rewidea> findzxList2(Rewidea rewidea);
	public List<Rewidea> findzxList3(Rewidea rewidea);
	

	public void zxdelete(Rewidea rewidea);

	public void zxsave(Rewidea rewidea);
	
	public Rewidea selectzxInfo(Rewidea rewidea);
}