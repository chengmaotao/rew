/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.csres.entity.RewcsResult;
import com.zkr.rew.dict.dao.RewdmlbDao;
import com.zkr.rew.dict.entity.Dmlb;
import com.zkr.rew.fs.service.fsdyl.RewfsdylService;

/**
 * 保存字典Service
 * 
 * @author 成茂涛
 * @version 2016-08-05
 */
@Service
@Transactional(readOnly = true)
public class RewdmlbService extends CrudService<RewdmlbDao, Dmlb> {

	@Autowired
	private RewdmlbDao dmlbDao;
	
	@Autowired
	private RewfsdylService rewfsService;

	public List<Dmlb> findList(Dmlb dmlb) {
		return super.findList(dmlb);
	}

	public List<Dmlb> selectMens(Dmlb dmlb) {
		return dmlbDao.selectMens(dmlb);
	}

	@Transactional(readOnly = false)
	public void mySave(Dmlb dmlb) {
		List<Dmlb> menus = dmlb.getMenus();
		String currentYear = UtilsFns.selectCurrentYear();
		if(!menus.isEmpty()){
			for(Dmlb e : menus){
				e.setCurrentYear(currentYear);
				dmlbDao.updateMenusById(e);
			}
		}
	}
	
	public String selectCurrentYear(){
		return dmlbDao.selectCurrentYear();
	}

	@Transactional(readOnly = false)
	public void savecurrentyear(String currentyear) {
		dmlbDao.savecurrentyear(currentyear);
		
	}

	public List<Dmlb> tpcontrolMenus() {
	    
	    return dmlbDao.tpcontrolMenus();
    }

	@Transactional(readOnly = false)
	public void tpmenuSave(Dmlb dmlb) {
		List<Dmlb> menus = dmlb.getMenus();
		if(!menus.isEmpty()){
			for(Dmlb e : menus){
				
				// 补投时 删除没有达到标准的票数  重新投票  begin
				if("1".equals(e.getId()) && "1".equals(e.getEnable())){
					// 复审 第一轮 补投
					
					// 已经选择出来的特等奖数
					List<RewcsResult> selectFsdyltpres = rewfsService.selectFsdyltpres();
					
					// 删除 没有评选上 特等奖的 项目投票数，重新开始投   如果不删除 结果保留住了我感觉更好
					rewfsService.deleteNotFsdyltpres(selectFsdyltpres); 
					
				}else if("2".equals(e.getId()) && "1".equals(e.getEnable())){
					//2 复审 第二轮 补投
					
					// 已经选择出来的一等奖数
					List<RewcsResult> selectFsdeltpres = rewfsService.selectFsdeltpres();
					
					// 删除 没有评选上 特等奖的 项目投票数，重新开始投    -----如果不删除 结果保留住了我感觉更好
					rewfsService.deleteNotFsdeltpres(selectFsdeltpres);
				}
				// 补投时 删除没有达到标准的票数  重新投票  end
				
				dmlbDao.updatetpMenusById(e);
			}
		}   
    }

	// 初审复审 显示
	public String cfcontrol() {
	    return UtilsFns.selectcfvalue();
    }

	@Transactional(readOnly = false)
	public void savecfcontrol(String currentval) {
		dmlbDao.savecfcontrol(currentval);
	    
    }
}