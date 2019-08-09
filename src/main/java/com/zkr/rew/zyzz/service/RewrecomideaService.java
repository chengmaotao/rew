/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.zyzz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.dict.entity.Rewproject;
import com.zkr.rew.ideachk.dao.RewcomideacheckDao;
import com.zkr.rew.zyzz.dao.RewrecomideaDao;
import com.zkr.rew.zyzz.entity.Rewrecomidea;

/**
 * 小组推荐意见Service
 * @author 成茂涛
 * @version 2016-08-15
 */
@Service
@Transactional(readOnly = true)
public class RewrecomideaService extends CrudService<RewrecomideaDao, Rewrecomidea> {

	@Autowired
	private RewrecomideaDao recomideaDao;
	
	@Autowired
	private RewcomideacheckDao rewcomideacheckDao;
	
	public Rewrecomidea get(String id) {
		Rewproject p = new Rewproject();
		p.setProjectid(id);
		p.setCurrentYear(UtilsFns.selectCurrentYear());
		return recomideaDao.get(p);

	}
	
	public List<Rewrecomidea> findList(Rewrecomidea rewrecomidea) {
		return super.findList(rewrecomidea);
	}
	
	public Page<Rewrecomidea> findPage(Page<Rewrecomidea> page, Rewrecomidea rewrecomidea) {
		return super.findPage(page, rewrecomidea);
	}
	
	@Transactional(readOnly = false)
	public void save(Rewrecomidea rewrecomidea) {
		if (rewrecomidea.getIsNewRecord()){
			rewrecomidea.preInsert();
			dao.insert(rewrecomidea);
		}else{
			rewrecomidea.preUpdate();
			dao.update(rewrecomidea);
		}
		
		Rewproject p = new Rewproject();
		p.setProjectid(rewrecomidea.getProjectid());
		//p.setCurrentYear(UtilsFns.selectCurrentYear());
		rewcomideacheckDao.deleteByProject(p);
	}
	
	@Transactional(readOnly = false)
	public void delete(Rewrecomidea rewrecomidea) {
		super.delete(rewrecomidea);
	}
	
	
/*	public List<Rewrecomidea> findListByGroup(User user){
		return recomideaDao.findListByGroup(user);
	}*/

	public Page<Rewrecomidea> findPageByGroup(Page<Rewrecomidea> page, Rewrecomidea rewrecomidea) {
		
		rewrecomidea.setPage(page);
		page.setList(recomideaDao.findListByGroup(rewrecomidea));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void mySave(Rewrecomidea rewrecomidea) {
		if(rewrecomidea.getIsNewRecord()){
			rewrecomidea.preInsert();
			recomideaDao.insert(rewrecomidea);
		}else{
			rewrecomidea.preUpdate();
			recomideaDao.update(rewrecomidea);
			
			Rewproject p = new Rewproject();
			p.setProjectid(rewrecomidea.getProjectid());
			p.setCurrentYear(UtilsFns.selectCurrentYear());
			rewcomideacheckDao.deleteByProject(p);
		}
		
/*		Rewproject p = new Rewproject();
		p.setProjectid(rewrecomidea.getProjectid());
		p.setCurrentYear(UtilsFns.selectCurrentYear());
		
		rewcomideacheckDao.deleteByProject(p);*/
		
	}
	
	// 判断 小组推荐意见投票 是否全部 投了
	// kbn 1 判断评审意见    2 判断是否通过
	public boolean selectAllMessByGroup(User user,String kbn){
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		 List<Rewrecomidea> allMessByGroup = recomideaDao.selectAllMessByGroup(user);
		 if(allMessByGroup == null && allMessByGroup.isEmpty()){
			 return false;
		 }
		 if("1".equals(kbn)){
			 for(Rewrecomidea e:allMessByGroup){
				 if(e.getRecomidea() == null || "".equals(e.getRecomidea())){
					 return false;
				 }
			 }
		 }else{
			 for(Rewrecomidea e:allMessByGroup){
				 if(e.getResult().equals("") || "未通过".equals(e.getResult())){
					 return false;
				 }
			 }
		 }

		 return true;
	}
	
	public boolean selectAllMessByGroup2(User user,String kbn){
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		 List<Rewrecomidea> allMessByGroup = recomideaDao.selectAllMessByGroup2(user);
		 if(allMessByGroup == null && allMessByGroup.isEmpty()){
			 return false;
		 }
		 if("1".equals(kbn)){
			 for(Rewrecomidea e:allMessByGroup){
				 if(e.getRecomidea() == null || "".equals(e.getRecomidea())){
					 return false;
				 }
			 }
		 }else{
			 for(Rewrecomidea e:allMessByGroup){
				 if(e.getResult().equals("") || "未通过".equals(e.getResult())){
					 return false;
				 }
			 }
		 }

		 return true;
	}
	
}