/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.fs.service.fsdyl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.common.utils.UtilsFns;
import com.zkr.rew.common.utils.UtilsMessage;
import com.zkr.rew.csres.entity.RewcsResult;
import com.zkr.rew.fs.dao.fsdyl.RewfsdylDao;
import com.zkr.rew.fs.dao.fsres.RewfsresDao;
import com.zkr.rew.fs.entity.fsdyl.Rewfsdyl;

/**
 * 复审第一轮投票Service
 * 
 * @author 成茂涛
 * @version 2016-08-22
 */
@Service
@Transactional(readOnly = true)
public class RewfsdylService extends CrudService<RewfsdylDao, Rewfsdyl> {

	@Autowired
	private RewfsdylDao fsdylDao;

	@Autowired
	private RewfsresDao rewfsresDao;

	// 复审第一轮投票列表
	public List<Rewfsdyl> findfsdylPage(Rewfsdyl rewfsdyl) {

		rewfsdyl.setCurrentYear(UtilsFns.selectCurrentYear());

		return fsdylDao.selectFsdyltpList(rewfsdyl);
	}

	// 复审第一轮投票列表 补投时
	public List<Rewfsdyl> findfsdylPagetp(Rewfsdyl rewfsdyl, List<RewcsResult> selectFsdyltpres) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentYear", UtilsFns.selectCurrentYear());
		map.put("userId", UserUtils.getUser().getId());
		map.put("list", selectFsdyltpres);
		
		map.put("projectid", rewfsdyl.getProjectid());
		
		map.put("isGlyRole", rewfsdyl.isGlyRole());
		
		// rewfsdyl.setCurrentYear(UtilsFns.selectCurrentYear());

		return fsdylDao.selectFsdyltpListbt(map);

	}

	// 复审第一轮投票 已经投票数
	public List<Rewfsdyl> selectFsdyltpNum() {

		User user = UserUtils.getUser();
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		return fsdylDao.selectFsdyltpNum(user);
	}

	// 已经选择出来的特等奖数
	public List<RewcsResult> selectFsdyltpres() {
		return rewfsresDao.selectFsdyltpres(UtilsFns.selectCurrentYear());
	}

	// 复审第一轮投票 已经投票数(补投时)
	public List<Rewfsdyl> selectFsdyltpNumtp(List<RewcsResult> fsdyltpNumtp) {

		User user = UserUtils.getUser();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", user.getId());
		map.put("currentYear", UtilsFns.selectCurrentYear());
		map.put("list", fsdyltpNumtp);

		return fsdylDao.selectFsdyltpNumtp(map);
	}

	@Transactional(readOnly = false)
	public void saveAllData(Rewfsdyl rewcsres) {

		// List<Rewfsdyl> qtFsdyls = rewcsres.getQtFsdyls();
		List<Rewfsdyl> fsdyls = rewcsres.getFsdyls();

		// 保存
		if (fsdyls != null && !fsdyls.isEmpty()) {
			for (Rewfsdyl e : fsdyls) {
				if (e.getIsNewRecord()) {
					if ("1".equals(e.getIsAgree())) {
						e.preInsert();
						dao.insert(e);
					}
				} else {
					if(!"1".equals(e.getIsAgree())){
						dao.delete(e);
					}else{
						e.preUpdate();
						dao.update(e);
					}
				}
			}
		}

	}

	// 获得 特等奖列表
	public List<RewcsResult> selectTdjList() {

		return dao.selectTdjList(UtilsFns.selectCurrentYear());
	}

	// 复审第二轮投票***************************************************
	public List<Rewfsdyl> selectFsdeltpNum() {
		User user = UserUtils.getUser();
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		return dao.selectFsdeltpNum(user);
	}

	// 已经选择出来的一等奖数 补投 一等奖时
	public List<RewcsResult> selectFsdeltpres() {
		return rewfsresDao.selectFsdeltpres(UtilsFns.selectCurrentYear());
	}

	public List<Rewfsdyl> selectFsdeltpNumtp(List<RewcsResult> fsdeltpNumtp) {

		User user = UserUtils.getUser();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", user.getId());
		map.put("currentYear", UtilsFns.selectCurrentYear());
		map.put("list", fsdeltpNumtp);
		return dao.selectFsdeltpNumtp(map);
	}

	public List<Rewfsdyl> findfsdelPage(Rewfsdyl rewfsdyl) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("currentUserId", UserUtils.getUser().getId());// 当前用户

		parameters.put("list", rewfsdyl.getFsdyls());

		parameters.put("isGlyRole", UtilsMessage.isGlyRole());

		parameters.put("currentYear", UtilsFns.selectCurrentYear());
		
		parameters.put("projectid", rewfsdyl.getProjectid());

		return fsdylDao.selectFsdeltpList(parameters);

	}

	// 补投时 复审第二轮
	public List<Rewfsdyl> findfsdelPagetp(Rewfsdyl rewfsdyl, List<RewcsResult> selectFsdeltpres) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("currentUserId", UserUtils.getUser().getId());// 当前用户

		// 已经评选上特等奖 和 一等奖的 排除掉
		List<Rewfsdyl> fsdyls = rewfsdyl.getFsdyls();
		Rewfsdyl tempRewfsdyl = null;
		for (RewcsResult rewcsResult : selectFsdeltpres) {
			if (null == fsdyls) {
				fsdyls = new ArrayList<Rewfsdyl>();
			}
			tempRewfsdyl = new Rewfsdyl();
			tempRewfsdyl.setProjectid(rewcsResult.getProjectid());
			fsdyls.add(tempRewfsdyl);
		}

		parameters.put("list", fsdyls);

		parameters.put("isGlyRole", UtilsMessage.isGlyRole());
		parameters.put("currentYear", UtilsFns.selectCurrentYear());
		
		parameters.put("projectid", rewfsdyl.getProjectid());

		return fsdylDao.selectFsdeltpList(parameters);

	}

	@Transactional(readOnly = false)
	public void saveAllData2(Rewfsdyl rewcsres) {
		List<Rewfsdyl> fsdeltp = rewcsres.getFsdyls();
		// 保存
		if (fsdeltp != null && !fsdeltp.isEmpty()) {
			for (Rewfsdyl e : fsdeltp) {
				if (e.getIsNewRecord()) {
					if ("1".equals(e.getIsAgree())) {
						e.preInsert();
						fsdylDao.insert2(e);
					}
				} else {
					if(!"1".equals(e.getIsAgree())){
						fsdylDao.delete2(e);
					}else{
						e.preUpdate();;
						fsdylDao.update2(e);
					}
				}
			}
		}
	}

	// 复审第三轮投票***************************************************
	public List<Rewfsdyl> selectFsdsltpNum() {
		User user = UserUtils.getUser();
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		return dao.selectFsdsltpNum(user);
	}

	public List<Rewfsdyl> findfsdslPage(Rewfsdyl rewfsdyl) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("currentUserId", UserUtils.getUser().getId());// 当前用户

		parameters.put("list", rewfsdyl.getFsdyls());

		parameters.put("isGlyRole", UtilsMessage.isGlyRole());

		parameters.put("currentYear", UtilsFns.selectCurrentYear());
		
		parameters.put("projectid", rewfsdyl.getProjectid());

		return fsdylDao.selectFsdsltpList(parameters);

	}

	@Transactional(readOnly = false)
	public void saveAllDatas(Rewfsdyl rewcsres) {
		List<Rewfsdyl> fsdsltp = rewcsres.getFsdyls();
		// 保存
		if (fsdsltp != null && !fsdsltp.isEmpty()) {
			for (Rewfsdyl e : fsdsltp) {
				if (e.getIsNewRecord()) {
					if ("1".equals(e.getIsAgree())) {
						e.preInsert();
						fsdylDao.insert3(e);
					}
				} else {
					if(!"1".equals(e.getIsAgree())){
						fsdylDao.delete3(e);
					}else {
						e.preUpdate();
						fsdylDao.update3(e);
					}
				}
			}
		}

	}

	/*
	 * //没有评选上特等奖 自动进入到一等奖参与评选 （*没有考虑 第一轮的 其他项目） public List<RewcsResult>
	 * selectNotTdjList(List<String> paramets) {
	 * 
	 * return fsdylDao.selectNotTdjList(paramets); }
	 */

	public List<Rewfsdyl> selectTdjOKList() {
		return fsdylDao.selectTdjOKList();
	}

	public List<RewcsResult> selectYdjList() {

		return fsdylDao.selectYdjList(UtilsFns.selectCurrentYear());

	}

	public List<RewcsResult> selectEdjList(List<Rewfsdyl> parametes) {
		// TODO Auto-generated method stub

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentYear", UtilsFns.selectCurrentYear());
		map.put("list", parametes);

		return fsdylDao.selectEdjList(map);
	}

	public List<RewcsResult> selectNotTdjList(Rewfsdyl rewfsdyl) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("list", rewfsdyl.getFsdyls());

		parameters.put("currentYear", UtilsFns.selectCurrentYear());

		return fsdylDao.selectNotTdjList(parameters);

	}

	public List<Rewfsdyl> selectTdjAndYdjOKList() {
		return fsdylDao.selectTdjAndYdjOKList();
	}

	public List<RewcsResult> selectNotTdjAndYdjList(Rewfsdyl rewfsdyl) {
		// TODO Auto-generated method stub

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("list", rewfsdyl.getFsdyls());

		parameters.put("currentYear", UtilsFns.selectCurrentYear());

		return fsdylDao.selectNotTdjAndYdjList(parameters);
	}

	public List<RewcsResult> selectNotYdjList(Rewfsdyl rewfsdyl) {
		// TODO Auto-generated method stub

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("list", rewfsdyl.getFsdyls());

		parameters.put("currentYear", UtilsFns.selectCurrentYear());

		return fsdylDao.selectNotYdjList(parameters);
	}

	@Transactional(readOnly = false)
	public void deleteNotFsdyltpres(List<RewcsResult> selectFsdyltpres) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentYear", UtilsFns.selectCurrentYear());
		map.put("list", selectFsdyltpres);
		fsdylDao.deleteNotFsdyltpres(map);
	}

	@Transactional(readOnly = false)
	public void deleteNotFsdeltpres(List<RewcsResult> selectFsdeltpres) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentYear", UtilsFns.selectCurrentYear());
		map.put("list", selectFsdeltpres);

		fsdylDao.deleteNotFsdeltpres(map);
	}

}