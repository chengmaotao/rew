/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.dict.dao.RewprojectDao;
import com.zkr.rew.dict.entity.Rewcategory;
import com.zkr.rew.dict.entity.Rewgroup;
import com.zkr.rew.dict.entity.Rewproject;
import com.zkr.rew.zszj.dao.RewideaDao;
import com.zkr.rew.zszj.entity.Rewidea;

/**
 * 保存字典Service
 * @author 成茂涛
 * @version 2016-08-05
 */
@Service
@Transactional(readOnly = true)
public class RewprojectService extends CrudService<RewprojectDao, Rewproject> {

	@Autowired
	private RewprojectDao rewProjectDao;
	
	@Autowired
	private RewideaDao rewideaDao;
	
	public Rewproject get(String id) {
		return super.get(id);
	}
	
	public List<Rewproject> findList(Rewproject rewproject) {
		return super.findList(rewproject);
	}
	
	public Page<Rewproject> findPage(Page<Rewproject> page, Rewproject rewproject) {
		return super.findPage(page, rewproject);
	}
	
	/**
	 * 例子：工程物理组,小组1
	 * 先检查 工程物理组是否存在
	 * 在检查 工程物理组下面是否 存在小组1
	 * @param user
	 */
	private void checkGroupName(Rewproject rewproject){
		String groupName = rewproject.getGroupName().trim();
		
		// 专业组为空时
		if(StringUtils.isBlank(groupName)){
			throw new ServiceException(rewproject.getProjectid() + "没有设置专业组！");
		}
		
		String[] groupNames = StringUtils.split(groupName, ",");
		
		boolean bol = false;
		List<Rewgroup> groupList = UtilsFns.getEnableGroupList();
		for(Rewgroup e:groupList){
			if(groupNames[0].equals(e.getGroupname())){
				rewproject.setGroupId(e.getId());
				bol = true;
				break;
			}
		}
		if(!bol){
			throw new ServiceException(rewproject.getProjectid() + "设置的专业组["+groupNames[0]+"]不存在！");
		}
		
		
		
/*		List<Rewgroupson> groupSonList = UtilsFns.getGroupSonListByGroupId(rewproject.getGroupId());
		
		if(groupSonList != null && !groupSonList.isEmpty()){
			if(groupNames.length == 1){
				throw new ServiceException(rewproject.getProjectid() + "没有设置专业小组！");
			}else{
				boolean bol2 = false;
				for(Rewgroupson e : groupSonList){
					if(groupNames[1].equals(e.getGroupsonname())){
						rewproject.setGroupSonId(e.getId());
						bol2 = true;
						break;
					}
				}
				if(!bol2){
					throw new ServiceException(rewproject.getProjectid() + "设置的专业组["+groupNames[0]+"]下不存在 小组["+groupNames[1]+"]！");
				}
			}
			
		}else{
			rewproject.setGroupSonId("999999999");
		}*/
		
		
		// 申报类别
		String categoryName = rewproject.getCategoryName().trim();
		if(StringUtils.isBlank(categoryName)){
			throw new ServiceException(rewproject.getProjectid() + "没有设置申报类别！");
		}
		
		boolean bol3 = false;
		List<Rewcategory> cagegoryList = UtilsFns.getCagegoryList();
		for(Rewcategory e:cagegoryList){
			if(categoryName.equals(e.getCategoryname())){
				rewproject.setCategoryid(e.getId());
				bol3 = true;
				break;
			}
		}
		
		if(!bol3){
			throw new ServiceException(rewproject.getProjectid() + "设置的申报类别["+categoryName+"]不存在！");
		}
		
		//申报单位
		String companyName = rewproject.getCompanyName().trim();
		if(StringUtils.isBlank(companyName)){
			throw new ServiceException(rewproject.getProjectid() + "没有设置申报单位！");
		}
		
		boolean bol4 = false;
		List<Office> companyList = UtilsFns.findAllCompany();
		for(Office e:companyList){
			if(companyName.equals(e.getName())){
				rewproject.setCompanyId(e.getId());
				bol4 = true;
				break;
			}
		}
		
		if(!bol4){
			throw new ServiceException(rewproject.getProjectid() + "设置的申报单位["+companyName+"]不存在！");
		}
	}
	
	@Transactional(readOnly = false)
	public void save(Rewproject rewproject) {
		
		
		if (rewproject.getIsNewRecord()){
			if(!"1".equals(rewproject.getKbn())){
				checkGroupName(rewproject);
			}else{
				rewproject.setProjectid(rewproject.getProjectid() + UtilsFns.selectCurrentYear());
			}
			// rewproject.setProjectid(rewproject.getProjectid() + UtilsFns.selectCurrentYear());
			rewproject.setCurrentYear(UtilsFns.selectCurrentYear());
			rewproject.preInsert();
			dao.insert(rewproject);
		}else{
			
			rewproject.preUpdate();
			dao.update(rewproject);
			Rewidea rewidea = new Rewidea();
			rewidea.setProjectid(rewproject.getProjectid());
			rewideaDao.zxdelete(rewidea);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Rewproject rewproject) {
		super.delete(rewproject);
	}
	
	// 下载模板时   的数据
	public Rewproject findImportTempData(){
		 Rewproject importTempData = rewProjectDao.findImportTempData();
		 if(importTempData == null){
			 importTempData = new Rewproject();
		 }
		 //importTempData.setGroupSonName(UtilsFns.getGroupSonById(importTempData.getGroupSonId()).getGroupsonname());
		 importTempData.setGroupName(UtilsFns.getGroupById(importTempData.getGroupId()).getGroupname());
		 importTempData.setCategoryName(UtilsFns.getCagegoryById(importTempData.getCategoryid()).getCategoryname());
		 importTempData.setCompanyName(UtilsFns.getOfficeById(importTempData.getCompanyId()).getName());
		 return importTempData;
	}
	
	// 主审专家所在组的   项目列表
	public List<Rewproject> findProjectListByGroup(User user){
		return rewProjectDao.findProjectListByGroup(user);
	}

	public List<Rewproject> findProjectListNotInparametes() {
		
		return rewProjectDao.findProjectListNotInparametes(UtilsFns.selectCurrentYear());
	}

	public Rewproject getProjectInfoByProjectId(String projectId) {

		Rewproject p = new Rewproject();
		p.setProjectid(projectId);
		p.setCurrentYear(UtilsFns.selectCurrentYear());
		return rewProjectDao.getProjectInfoByProjectId(p);
	}
	
}