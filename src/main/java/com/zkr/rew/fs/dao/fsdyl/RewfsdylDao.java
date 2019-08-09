/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.fs.dao.fsdyl;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.zkr.rew.csres.entity.RewcsResult;
import com.zkr.rew.fs.entity.fsdyl.Rewfsdyl;

/**
 * 复审第一轮投票DAO接口
 * 
 * @author 成茂涛
 * @version 2016-08-22
 */
@MyBatisDao
public interface RewfsdylDao extends CrudDao<Rewfsdyl> {
	public List<Rewfsdyl> selectFsdyltpList(Rewfsdyl rewfsdyl);  // 数据来源小组推荐意见

	
	
	public List<Rewfsdyl> selectFsdyltpNum(User user);

	public List<RewcsResult> selectTdjList(String currentYear);  // 特等奖结果列表  数据来源小组推荐意见

	

	public List<Rewfsdyl> selectFsdeltpNum(User user);

	public List<Rewfsdyl> selectFsdeltpList(Map<String,Object> parametes); // 复审第二轮  数据来源小组推荐意见

	

	public void insert2(Rewfsdyl e);

	public void update2(Rewfsdyl e);

	public List<Rewfsdyl> selectFsdsltpNum(User user);

	public List<Rewfsdyl> selectFsdsltpList(Map<String,Object> parametes); 
	
	

	public void insert3(Rewfsdyl e);

	public void update3(Rewfsdyl e);

	

	public List<Rewfsdyl> selectTdjOKList();

	public List<RewcsResult> selectYdjList(String currentYear);   // 一等奖结果列表  数据来源小组推荐意见
	

	public List<RewcsResult> selectEdjList(Map<String,Object> parametes);

	public List<RewcsResult> selectNotTdjList(Map<String,Object> parametes);

	public List<Rewfsdyl> selectTdjAndYdjOKList();

	public List<Rewfsdyl> selectFsdsltpList(List<Rewfsdyl> fsdyls);

	public List<RewcsResult> selectNotTdjAndYdjList(Map<String,Object> parametes);
	
	public List<RewcsResult> selectNotYdjList(Map<String,Object> parametes);

	public List<Rewfsdyl> selectFsdyltpNumtp(Map<String, Object> map);

	public List<Rewfsdyl> selectFsdyltpListbt(Map<String,Object> map);   // 数据来源 小组推荐意见
	

	public List<Rewfsdyl> selectFsdeltpNumtp(Map<String, Object> map);

	public void deleteNotFsdyltpres(Map<String,Object> parametes);

	public void deleteNotFsdeltpres(Map<String,Object> parametes);



	public void delete2(Rewfsdyl e);



	public void delete3(Rewfsdyl e);
	
	
	
	
	
	
}