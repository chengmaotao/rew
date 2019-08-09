/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.zkr.rew.dict.entity.Rewproject;

/**
 * 保存字典DAO接口
 * @author 成茂涛
 * @version 2016-08-05
 */
@MyBatisDao
public interface RewprojectDao extends CrudDao<Rewproject> {
	
	public Rewproject getProjectByProjectId(Rewproject p);
	
	public Rewproject findImportTempData();
	
	public List<Rewproject> findProjectListByGroup(User user);
	
	public List<Rewproject> findProjectListNotInparametes(String currentYear);

	public Rewproject getProjectInfoByProjectId(Rewproject p);

	public List<Rewproject> selectZszjProjectList(Rewproject rewproject);

	public Rewproject getProjectByProjectName(Rewproject p);
	
	
}