/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.cssecond.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.cssecond.dao.RewcssenondvoteDao;
import com.zkr.rew.cssecond.entity.Rewcssenondvote;

/**
 * 初审第二轮投票Service
 * 
 * @author 成茂涛
 * @version 2016-08-16
 */
@Service
@Transactional(readOnly = true)
public class RewcssenondvoteService extends CrudService<RewcssenondvoteDao, Rewcssenondvote> {

	@Autowired
	private RewcssenondvoteDao cssenondvoteDao;

	public Rewcssenondvote get(String id) {
		return super.get(id);
	}

	public List<Rewcssenondvote> findList(Rewcssenondvote rewcssenondvote) {
		return super.findList(rewcssenondvote);
	}

	public Page<Rewcssenondvote> findPage(Page<Rewcssenondvote> page, Rewcssenondvote rewcssenondvote) {
		return super.findPage(page, rewcssenondvote);
	}

	@Transactional(readOnly = false)
	public void save(Rewcssenondvote rewcssenondvote) {
		super.save(rewcssenondvote);
	}

	@Transactional(readOnly = false)
	public void delete(Rewcssenondvote rewcssenondvote) {
		super.delete(rewcssenondvote);
	}

	public List<Rewcssenondvote> selectVotedNum0() {
		
		User user = UserUtils.getUser();
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		
		return cssenondvoteDao.selectVotedNum0(user);
	}

	public List<Rewcssenondvote> selectVotedNum1() {
		User user = UserUtils.getUser();
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		return cssenondvoteDao.selectVotedNum1(user);
	}

	@Transactional(readOnly = false)
	public void saveAllData(Rewcssenondvote rewcssenondvote) {
		// 获得提交的 list数据
		List<Rewcssenondvote> cssenondvotes = rewcssenondvote.getCssenondvotes();
		
		if(!cssenondvotes.isEmpty()){
			Rewcssenondvote cssenondvote = null;
			for(int i = 0; i < cssenondvotes.size(); i++){
				cssenondvote = cssenondvotes.get(i);
				if (cssenondvote.getIsNewRecord()){
					if("0".equals(cssenondvote.getIsagree0()) && "0".equals(cssenondvote.getIsagree1())){
						continue;
					}
					cssenondvote.preInsert();
					dao.insert(cssenondvote);
				}else{
					if("0".equals(cssenondvote.getIsagree0()) && "0".equals(cssenondvote.getIsagree1())){
						dao.delete(cssenondvote);
					}else{
						cssenondvote.preUpdate();
						dao.update(cssenondvote);
					}
				}
			}
		}
		
	}

}