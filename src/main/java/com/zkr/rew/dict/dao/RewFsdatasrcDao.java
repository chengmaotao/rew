/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zkr.rew.dict.entity.RewFsdatasrc;

/**
 * 复审数据来源DAO接口
 * @author 成茂涛
 * @version 2017-11-12
 */
@MyBatisDao
public interface RewFsdatasrcDao extends CrudDao<RewFsdatasrc> {

	RewFsdatasrc getRewFsdatasrcByProjectId(RewFsdatasrc p);

	List<RewFsdatasrc> findList2(Map<String, Object> parameters);

	List<RewFsdatasrc> findList3(Map<String, Object> parameters);

	
}