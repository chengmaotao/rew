/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.csfirst.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.csfirst.dao.RewcsfirstvoteDao;
import com.zkr.rew.csfirst.entity.Rewcsfirstvote;

/**
 * 初审第一轮投票Service
 * @author 成茂涛
 * @version 2016-08-15
 */
@Service
@Transactional(readOnly = true)
public class RewcsfirstvoteService extends CrudService<RewcsfirstvoteDao, Rewcsfirstvote> {

	
	@Autowired
	private RewcsfirstvoteDao csfirstvoteDao;
	
	public Rewcsfirstvote get(String id) {
		return super.get(id);
	}
	
	public List<Rewcsfirstvote> findList(Rewcsfirstvote rewcsfirstvote) {
		return super.findList(rewcsfirstvote);
	}
	
	public Page<Rewcsfirstvote> findPage(Page<Rewcsfirstvote> page, Rewcsfirstvote rewcsfirstvote) {
		return super.findPage(page, rewcsfirstvote);
	}
	
	@Transactional(readOnly = false)
	public void save(Rewcsfirstvote rewcsfirstvote) {
		super.save(rewcsfirstvote);
	}
	
	@Transactional(readOnly = false)
	public void delete(Rewcsfirstvote rewcsfirstvote) {
		super.delete(rewcsfirstvote);
	}

	// 保存
	@Transactional(readOnly = false)
	public void saveAllData(Rewcsfirstvote rewcsfirstvote) {
		
		// 获得提交的 list数据
		List<Rewcsfirstvote> csfirstvotes = rewcsfirstvote.getCsfirstvotes();
		
		if(!csfirstvotes.isEmpty()){
			Rewcsfirstvote csfirstvote = null;
			for(int i = 0; i < csfirstvotes.size(); i++){
				csfirstvote = csfirstvotes.get(i);
				
				if(csfirstvote.getRecomIdea() == null || "".equals(csfirstvote.getRecomIdea())){
					continue;
				}
				
				if (csfirstvote.getIsNewRecord()){
					
					if("1".equals(csfirstvote.getIsagree())){
						csfirstvote.preInsert();
						dao.insert(csfirstvote);
					}
				}else{
					
					if("0".equals(csfirstvote.getIsagree())){						
						dao.delete(csfirstvote);
					}
				}
			}
		}
	}
	
	// 获得该用户 已经投了多少票了
	public List<Rewcsfirstvote> selectVotedNum(){
		User user = UserUtils.getUser();
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		return csfirstvoteDao.selectVotedNum(user);
	}
	
}