/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.ideachk.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMath;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.ideachk.dao.RewcomideacheckDao;
import com.zkr.rew.ideachk.entity.Rewcomideacheck;
import com.zkr.rew.zyzz.dao.RewrecomideaDao;
import com.zkr.rew.zyzz.entity.Rewrecomidea;

/**
 * 推荐意见投票Service
 * @author 成茂涛
 * @version 2016-08-18
 */
@Service
@Transactional(readOnly = true)
public class RewcomideacheckService extends CrudService<RewcomideacheckDao, Rewcomideacheck> {

	@Autowired
	private RewcomideacheckDao comideacheckDao;
	
	
	@Autowired
	private RewrecomideaDao recomideaDao;
	
	
	public Rewcomideacheck get(String id) {
		return super.get(id);
	}
	
	public List<Rewcomideacheck> findList(Rewcomideacheck rewcomideacheck) {
		return super.findList(rewcomideacheck);
	}
	
	public Page<Rewcomideacheck> findPage(Page<Rewcomideacheck> page, Rewcomideacheck rewcomideacheck) {
		return super.findPage(page, rewcomideacheck);
	}
	
	@Transactional(readOnly = false)
	public void save(Rewcomideacheck rewcomideacheck) {
		super.save(rewcomideacheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(Rewcomideacheck rewcomideacheck) {
		super.delete(rewcomideacheck);
	}

	@Transactional(readOnly = false)
	public void saveAllData(Rewcomideacheck rewcomideacheck) {
		// 获得提交的 list数据
				 List<Rewcomideacheck> comideaChecks = rewcomideacheck.getComideaChecks();
				
				if(!comideaChecks.isEmpty()){
					Rewcomideacheck comideaCheck = null;
					for(int i = 0; i < comideaChecks.size(); i++){
						comideaCheck = comideaChecks.get(i);

						//comideaCheck.setCurrentYear(UtilsFns.selectCurrentYear());
						
						if((!"1".equals(comideaCheck.getIsagree())) && comideaCheck.getIsNewRecord()){
							continue;
						}
						
						if (comideaCheck.getIsNewRecord()){
							comideaCheck.preInsert();
							dao.insert(comideaCheck);
						}else if(!"1".equals(comideaCheck.getIsagree())){
							//comideaCheck.preUpdate();
							//dao.update(comideaCheck);
							dao.delete(comideaCheck);
						}else {
							comideaCheck.preUpdate();
							dao.update(comideaCheck);
						}
					}
				}
	}
	
	@Transactional(readOnly = false)
	public List<Rewrecomidea> getCheckResult(List<Rewrecomidea> comideas,User user){
		
		user.setGlyRole(UtilsMessage.isGlyRole());
		if(UtilsMessage.isGlyRole()){
			// 
			List<User> groups = UserUtils.selectcsGroups(user);
			for(User e:groups){
				user.setGroupId(e.getGroupId());
				//user.setGroupSonId(e.getGroupSonId());
				getRes(comideas,user);
			}
			
		}else{
		    getRes(comideas,user);
		}
		return comideas;
		
	}
	
	
	private void getRes(List<Rewrecomidea> comideas,User user){
		 // 根据User 获得该组下面 有多少个 用户 ,即总投票数;
		List<User> usersByGroup = UserUtils.selectcsUsersByGroup(user);
		int userByGroupCount = 0;
		if (usersByGroup != null && !usersByGroup.isEmpty()) {
			userByGroupCount = usersByGroup.size();
		}
		// 按照项目分组， 查看每个项目的 投票数
		// 循环项目列表， 判断 投票数目 超过 1/2 通过，否则 不通过
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		List<Rewcomideacheck> projects = comideacheckDao.selectCountNumByProject(user);
	
		for (Rewrecomidea e : comideas) {
			if(e.getGroupId().equals(user.getGroupId())){
				e.setAllNum(String.valueOf(userByGroupCount));
			}
			nai: for (Rewcomideacheck t : projects) {
					if (t.getProjectid().equals(e.getProjectid())) {
		
						if (UtilsMath.isTrue(userByGroupCount, t.getNum(), 2)) {
							// 通过了
							e.setResult("通过");
							e.setNum(String.valueOf(t.getNum()));
						} else {
							// 没通过
							e.setNum(String.valueOf(t.getNum()));
						}
/*						if(e.getIsNewRecord()){
							e.setUpdateBy(UserUtils.getUser());
							e.setCreateBy(UserUtils.getUser());
							e.setUpdateDate(new Date());
							e.setCreateDate(e.getUpdateDate());
							recomideaDao.insert(e);
						}else{
							e.setUpdateBy(UserUtils.getUser());
							e.setUpdateDate(new Date());
							recomideaDao.update(e);
						}*/
						
						recomideaDao.update(e);
						break nai;
					}
			}
			
		}
		
	}
	
}