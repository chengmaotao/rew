/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.fs.dao.fsres;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zkr.rew.csres.entity.RewcsResult;
import com.zkr.rew.fs.entity.fsres.Rewfsres;

/**
 * 复审结果DAO接口
 * @author 成茂涛
 * @version 2016-08-23
 */
@MyBatisDao
public interface RewfsresDao extends CrudDao<Rewfsres> {

	public void deleteData(String currentYear);

	public void insertData(List<RewcsResult> result1);

	public void deleteData2(String currentYear);

	public void insertData2(List<RewcsResult> result1);
	
	public void deleteData3(String currentYear);

	public void insertData3(List<RewcsResult> result1);

	public List<RewcsResult> selectFsres(String currentYear);
	public List<RewcsResult> getFsDylTpResult(String currentYear);
	public List<RewcsResult> getFsDelTpResult(String currentYear);
	public List<RewcsResult> getFsDslTpResult(String currentYear);
	
	public List<RewcsResult> selectFsdyltpres(String currentYear);
	
	public List<RewcsResult> selectFsdeltpres(String currentYear);
	
}