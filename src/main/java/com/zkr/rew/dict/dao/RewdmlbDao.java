/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zkr.rew.common.utils.SelectEntity;
import com.zkr.rew.dict.entity.Dmlb;

/**
 * 保存字典DAO接口
 * 
 * @author 成茂涛
 * @version 2016-08-04
 */
@MyBatisDao
public interface RewdmlbDao extends CrudDao<Dmlb> {
	public List<Dmlb> selectMens(Dmlb dmlb);

	public void updateMenusById(Dmlb dmlb);

	public Dmlb selectMenuById(Dmlb dmlb);

	public String selectCurrentYear();

	public void savecurrentyear(String currentyear);

	public List<SelectEntity> selectYearList();

	public List<Dmlb> tpcontrolMenus();

	public void updatetpMenusById(Dmlb e);

	public Dmlb getmenucontroltpById(String id);

	public String selectcfvalue();

	public void savecfcontrol(String currentval);
}