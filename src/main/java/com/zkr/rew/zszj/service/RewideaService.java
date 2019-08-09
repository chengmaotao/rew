/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.zszj.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.zszj.dao.RewideaDao;
import com.zkr.rew.zszj.entity.Rewidea;

/**
 * 主审专家Service
 * @author 成茂涛
 * @version 2016-08-10
 */
@Service
@Transactional(readOnly = true)
public class RewideaService extends CrudService<RewideaDao, Rewidea> {

	
	public Rewidea get(String id) {
		return super.get(id);
	}
	
	public List<Rewidea> findList(Rewidea rewidea) {
		return super.findList(rewidea);
	}
	
	public Page<Rewidea> findPage(Page<Rewidea> page, Rewidea rewidea) {
		return super.findPage(page, rewidea);
	}
	
	public List<Rewidea> pintIdea(Rewidea rewidea){
		// 本年度
		rewidea.setCurrentYear(UtilsFns.selectCurrentYear());
		return dao.findzxList3(rewidea);
	}
	
	public Page<Rewidea> findzxPage(Page<Rewidea> page, Rewidea rewidea) {
		
		// 本年度
		rewidea.setCurrentYear(UtilsFns.selectCurrentYear());
		// 管理员
		if(UtilsMessage.isGlyRole()){
			rewidea.setPage(page);
			page.setList(dao.findzxList3(rewidea));
		}else if(UtilsMessage.isZyzzRole() && UtilsMessage.isZszjRole()){
			// 即是专业组长 又是 主审专家
			rewidea.setPage(page);
			//List<Rewidea> list1 = dao.findzxList(rewidea);
			List<Rewidea> list2 = dao.findzxList2(rewidea);
			//list1.addAll(list2);
			page.setList(list2);
		}else if(UtilsMessage.isZszjRole()){
			// 仅仅主审专家
			rewidea.setPage(page);
			page.setList(dao.findzxList(rewidea));
		}else{
			// 仅仅专业组长
			rewidea.setPage(page);
			page.setList(dao.findzxList3(rewidea));
		}
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(Rewidea rewidea) {
		super.save(rewidea);
	}
	
	@Transactional(readOnly = false)
	public void delete(Rewidea rewidea) {
		super.delete(rewidea);
	}

	@Transactional(readOnly = false)
	public void zxdelete(Rewidea rewidea) {
		dao.zxdelete(rewidea);
	}

	@Transactional(readOnly = false)
	public void zxsave(Rewidea rewidea) {
		
		List<String> projectids = rewidea.getProjectids();
		if(projectids != null && !projectids.isEmpty()){
			for(int i = 0; i < projectids.size();i++){
				rewidea.setProjectid(projectids.get(i));
				Rewidea res = dao.selectzxInfo(rewidea);
				if(res == null){
					dao.zxsave(rewidea);
				}
			}
		}
	}
}