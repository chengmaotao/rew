package com.zkr.rew.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.dict.dao.RewFsdatasrcDao;
import com.zkr.rew.dict.dao.RewProgressDao;
import com.zkr.rew.dict.dao.RewResDao;
import com.zkr.rew.dict.dao.RewawardlevelDao;
import com.zkr.rew.dict.dao.RewcategoryDao;
import com.zkr.rew.dict.dao.RewdmlbDao;
import com.zkr.rew.dict.dao.RewgroupDao;
import com.zkr.rew.dict.dao.RewprojectDao;
import com.zkr.rew.dict.dao.RewscopekpiDao;
import com.zkr.rew.dict.dao.RewsysvalDao;
import com.zkr.rew.dict.entity.Dmlb;
import com.zkr.rew.dict.entity.RewFsdatasrc;
import com.zkr.rew.dict.entity.RewRes;
import com.zkr.rew.dict.entity.Rewawardlevel;
import com.zkr.rew.dict.entity.Rewcategory;
import com.zkr.rew.dict.entity.Rewgroup;
import com.zkr.rew.dict.entity.Rewproject;
import com.zkr.rew.dict.entity.Rewscopekpi;
import com.zkr.rew.dict.entity.Rewsysval;
import com.zkr.rew.fs.entity.fsdyl.Rewfsdyl;
import com.zkr.rew.zszj.dao.RewideaDao;
import com.zkr.rew.zszj.entity.Rewidea;

public class UtilsFns {

	public static final String CACHE_LISE = "LIST_";

	// 奖励级别
	private static RewawardlevelDao awardlevelDao = SpringContextHolder.getBean(RewawardlevelDao.class);
	public static final String AWARD_CACHE = "AWARD_CACHE";
	public static final String AWARD_CACHE_ID = "AWARD_";

	/**
	 * @Description:奖励级别List
	 * @param @return
	 * @return List<Rewawardlevel>
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:04:04
	 */
	public static List<Rewawardlevel> getAwardLevelList() {
		List<Rewawardlevel> awardLevelList = (List<Rewawardlevel>) CacheUtils.get(AWARD_CACHE, CACHE_LISE);
		if (awardLevelList == null || awardLevelList.size() == 0) {
			awardLevelList = awardlevelDao.findList(null);
			if (awardLevelList == null) {
				return null;
			}
		}
		CacheUtils.put(AWARD_CACHE, CACHE_LISE, awardLevelList);
		return awardLevelList;
	}

	/**
	 * @Description:根据Id获得奖励级别对象
	 * @param @param id
	 * @param @return
	 * @return Rewawardlevel
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:06:36
	 */
	public static Rewawardlevel getAwardLevelById(String id) {
		Rewawardlevel awardLevel = (Rewawardlevel) CacheUtils.get(AWARD_CACHE, AWARD_CACHE_ID + id);
		if (awardLevel == null) {
			awardLevel = awardlevelDao.get(id);
			if (awardLevel == null) {
				awardLevel = new Rewawardlevel();
				return awardLevel;
			}
		}
		CacheUtils.put(AWARD_CACHE, AWARD_CACHE_ID + id, awardLevel);
		return awardLevel;
	}

	// 类别
	private static RewcategoryDao categoryDao = SpringContextHolder.getBean(RewcategoryDao.class);
	public static final String CATEGORY_CACHE = "CATEGORY_CACHE";
	public static final String CATEGORY_CACHE_ID = "CATEGORY_";

	/**
	 * @Description:类别List
	 * @param @return
	 * @return List<Rewcategory>
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:04:04
	 */
	public static List<Rewcategory> getCagegoryList() {
		List<Rewcategory> categoryList = (List<Rewcategory>) CacheUtils.get(CATEGORY_CACHE, CACHE_LISE);
		if (categoryList == null || categoryList.size() == 0) {
			categoryList = categoryDao.findList(null);
			if (categoryList == null) {
				return null;
			}
		}
		CacheUtils.put(CATEGORY_CACHE, CACHE_LISE, categoryList);
		return categoryList;
	}

	/**
	 * @Description:根据Id获得类别对象
	 * @param @param id
	 * @param @return
	 * @return Rewcategory
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:06:36
	 */
	public static Rewcategory getCagegoryById(String id) {
		Rewcategory category = (Rewcategory) CacheUtils.get(CATEGORY_CACHE, CATEGORY_CACHE_ID + id);
		if (category == null) {
			category = categoryDao.get(id);
			if (category == null) {
				category = new Rewcategory();
				return category;
			}
		}
		CacheUtils.put(CATEGORY_CACHE, CATEGORY_CACHE_ID + id, category);
		return category;
	}

	// 项目
	private static RewprojectDao projectDao = SpringContextHolder.getBean(RewprojectDao.class);
	public static final String PROJECT_CACHE = "PROJECT_CACHE";
	public static final String PROJECT_CACHE_ID = "PROJECT_";
	public static final String PROJECT_CACHE_PROJECTID = "CACHE_PROJECT_";

	public static final String PROJECT_CACHE_GROUP = "PROJECT_CACHE_GROUP";

	/**
	 * @Description:项目List
	 * @param @return
	 * @return List<Rewproject>
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:04:04
	 */
	public static List<Rewproject> getProjectList() {

		Rewproject p = new Rewproject();
		p.setCurrentYear(UtilsFns.selectCurrentYear());
		List<Rewproject> projectList = projectDao.findList(p);
		if (projectList == null) {
			return null;
		}
		return projectList;
	}

	/**
	 * @Description:根据当前用户所在的专业组， 获得项目List
	 * @param @return
	 * @return List<Rewproject>
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:04:04
	 */
	public static List<Rewproject> getProjectListByGroup() {

		User user = UserUtils.getUser();
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		List<Rewproject> projectList = projectDao.findProjectListByGroup(user);
		return projectList;
	}

	/**
	 * @Description:根据Id获得项目对象
	 * @param @param id
	 * @param @return
	 * @return Rewcategory
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:06:36
	 */
	public static Rewproject getProjectById(String id) {
		Rewproject project = (Rewproject) CacheUtils.get(PROJECT_CACHE, PROJECT_CACHE_ID + id);
		if (project == null) {
			project = projectDao.get(id);
			if (project == null) {
				project = new Rewproject();
				return project;
			}
		}
		CacheUtils.put(PROJECT_CACHE, PROJECT_CACHE_ID + id, project);
		return project;
	}

	/**
	 * @Description:根据projectId获得项目对象
	 * @param @param projectId
	 * @param @return
	 * @return Rewcategory
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:06:36
	 */
	public static Rewproject getProjectByProjectId(String projectId) {

		Rewproject p = new Rewproject();
		p.setProjectid(projectId);
		p.setCurrentYear(UtilsFns.selectCurrentYear());

		Rewproject project = (Rewproject) CacheUtils.get(PROJECT_CACHE, PROJECT_CACHE_PROJECTID + projectId + p.getCurrentYear());
		if (project == null) {
			project = projectDao.getProjectByProjectId(p);
			if (project == null) {
				return null; // 不要改 其他地方用到了
			}
		}
		CacheUtils.put(PROJECT_CACHE, PROJECT_CACHE_PROJECTID + projectId + p.getCurrentYear(), project);
		return project;
	}

	// 专业组
	private static RewgroupDao groupDao = SpringContextHolder.getBean(RewgroupDao.class);
	public static final String GROUP_CACHE = "GROUP_CACHE";
	public static final String GROUP_CACHE_ID = "GROUP_";

	/**
	 * @Description:专业组List
	 * @param @return
	 * @return List<Rewgroup>
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:04:04
	 */
	public static List<Rewgroup> getEnableGroupList() {
		String currentYear = UtilsFns.selectCurrentYear();
		List<Rewgroup> groupList = (List<Rewgroup>) CacheUtils.get(GROUP_CACHE + currentYear, CACHE_LISE);
		if (groupList == null || groupList.size() == 0) {
			Rewgroup rewgroup = new Rewgroup();
			rewgroup.setCurrentYear(currentYear);
			groupList = groupDao.findAllList(rewgroup);
			if (groupList == null) {
				return null;
			}
		}
		CacheUtils.put(GROUP_CACHE + currentYear, CACHE_LISE, groupList);
		return groupList;
	}

	public static List<SelectEntity> isAgree() {
		List<SelectEntity> list = new ArrayList<SelectEntity>();
		SelectEntity entity1 = new SelectEntity(1, "同意");
		SelectEntity entity2 = new SelectEntity(0, "不同意");
		list.add(entity1);
		list.add(entity2);
		return list;
	}

	/**
	 * @Description:根据Id获得专业组对象
	 * @param @param id
	 * @param @return
	 * @return Rewgroup
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:06:36
	 */
	public static Rewgroup getGroupById(String id) {
		Rewgroup group = (Rewgroup) CacheUtils.get(GROUP_CACHE, GROUP_CACHE_ID + id);
		if (group == null) {
			group = groupDao.get(id);
			if (group == null) {
				group = new Rewgroup();
				return group;
			}
		}
		CacheUtils.put(GROUP_CACHE, GROUP_CACHE_ID + id, group);
		return group;
	}

	/**
	 * @description: 清空缓存
	 * @param cacheName
	 * @param key
	 * @author：chengMaotao
	 * @date : 2016年8月12日 下午1:13:28
	 */
	public static void clearCacheById(String cacheName, String key) {
		Object object = CacheUtils.get(cacheName, key);

		if (object != null) {
			CacheUtils.remove(cacheName, key);
		}

	}

	// 评价指标
	private static RewscopekpiDao scopekpiDao = SpringContextHolder.getBean(RewscopekpiDao.class);
	public static final String SCOPEKPI_CACHE = "SCOPEKPI_CACHE";
	public static final String SCOPEKPI_CACHE_ID = "SCOPEKPI_";
	public static final String CACHE_LISE_CATEGORY = "CACHE_LISE_CATEGORY";

	/**
	 * @Description:评价指标List
	 * @param @return
	 * @return List<Rewscopekpi>
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:04:04
	 */
	public static List<Rewscopekpi> getScopeKpiList() {
		List<Rewscopekpi> scopeKpiList = (List<Rewscopekpi>) CacheUtils.get(SCOPEKPI_CACHE, CACHE_LISE);
		if (scopeKpiList == null || scopeKpiList.size() == 0) {
			scopeKpiList = scopekpiDao.findList(null);
			if (scopeKpiList == null) {
				return null;
			}
		}
		CacheUtils.put(SCOPEKPI_CACHE, CACHE_LISE, scopeKpiList);
		return scopeKpiList;
	}

	/**
	 * @Description:评价指标List
	 * @param @return
	 * @return List<Rewscopekpi>
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:04:04
	 */
	public static List<Rewscopekpi> getScopeKpiListByCategoryId(String id) {
		List<Rewscopekpi> scopeKpiList = (List<Rewscopekpi>) CacheUtils.get(SCOPEKPI_CACHE, CACHE_LISE_CATEGORY + id);
		if (scopeKpiList == null || scopeKpiList.size() == 0) {
			scopeKpiList = scopekpiDao.findObjListByCategoryId(id);
			if (scopeKpiList == null) {
				return null;
			}
		}
		CacheUtils.put(SCOPEKPI_CACHE, CACHE_LISE_CATEGORY + id, scopeKpiList);
		return scopeKpiList;
	}

	/**
	 * @Description:根据Id获得评价指标对象
	 * @param @param id
	 * @param @return
	 * @return Rewscopekpi
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-13 上午11:06:36
	 */
	public static Rewscopekpi getScopeKpiById(String id) {
		Rewscopekpi scopeKpi = (Rewscopekpi) CacheUtils.get(SCOPEKPI_CACHE, SCOPEKPI_CACHE_ID + id);
		if (scopeKpi == null) {
			scopeKpi = scopekpiDao.get(id);
			if (scopeKpi == null) {
				scopeKpi = new Rewscopekpi();
				return scopeKpi;
			}
		}
		CacheUtils.put(SCOPEKPI_CACHE, SCOPEKPI_CACHE_ID + id, scopeKpi);
		return scopeKpi;
	}

	/**
	 * 根据userID 获得角色列表
	 * 
	 * @param id
	 * @return
	 */
	public static String getRolesByUserId(String id) {
		List<Role> roleList = UserUtils.getRoleListByUserId(id);
		if (roleList == null || roleList.isEmpty()) {
			return "";
		}
		StringBuffer sbuffer = new StringBuffer();
		for (Role r : roleList) {
			sbuffer.append(r.getName() + ",");
		}
		// 返回 去掉 最后一个 逗号的
		return sbuffer.deleteCharAt(sbuffer.toString().length() - 1).toString();
	}

	public static final String CACH_COMPANY = "CACH_COMPANY";
	public static final String CACH_COMPANY_ID = "COMPANY_";
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);

	// 获得所有的公司列表
	public static List<Office> findAllCompany() {
		List<Office> officeList = (List<Office>) CacheUtils.get(CACH_COMPANY, CACHE_LISE);
		if (officeList == null) {
			officeList = officeDao.findAllList(new Office());
			if (officeList == null) {
				return null;
			}

		}
		CacheUtils.put(CACH_COMPANY, CACHE_LISE, officeList);
		return officeList;
	}

	public static Office getOfficeById(String id) {
		Office office = (Office) CacheUtils.get(CACH_COMPANY, CACH_COMPANY_ID + id);
		if (office == null) {
			office = officeDao.get(id);
			if (office == null) {
				return new Office();
			}
		}
		CacheUtils.put(CACH_COMPANY, CACH_COMPANY_ID + id, office);
		return office;
	}

	// 获得评审意见
	public static String getRewIdea(String rewIdea) {
		int ideaLen = Integer.valueOf(UtilsMessage.getValueByKey("rewIdeaLen"));

		if (rewIdea == null || ideaLen < 1 || rewIdea.length() <= ideaLen) {
			return rewIdea;
		} else {
			return rewIdea.substring(0, ideaLen) + "…";
		}
	}

	/**
	 * @description: 替换掉 回车符
	 * @param bz
	 * @return
	 * @author：chengMaotao
	 * @date : 2016年1月6日 上午11:19:38
	 */
	public static String replaceRn(String bz) {
		return bz.replace("\r\n", " ").replace("\r", " ").replace("\n", " ");
	}

	// 评审意见
	private static RewideaDao ideaDao = SpringContextHolder.getBean(RewideaDao.class);
	private static final String IDEA_CACHE = "IDEA_CACHE";
	public static final String IDEA_CACHE_PROJECTID = "IDEA__PROJECT";

	/**
	 * 根据projectid 获得评审意见
	 * 
	 * @param id
	 * @return
	 */
	public static Rewidea getIdeaByProjectId(String id) {
		Rewidea idea = (Rewidea) CacheUtils.get(IDEA_CACHE, IDEA_CACHE_PROJECTID + id);
		if (idea == null) {
			idea = ideaDao.getrewIdeaByProjectId(id);
			if (idea == null) {
				return null;
			}
		}
		CacheUtils.put(IDEA_CACHE, IDEA_CACHE_PROJECTID + id, idea);
		return idea;
	}

	public static String getKpiScope(String scope) {
		if (scope == null || "".equals(scope)) {
			return "";
		}
		return new StringBuffer(scope).append("分").toString();
	}

	private static RewdmlbDao dmlbDao = SpringContextHolder.getBean(RewdmlbDao.class);

	// 菜单可用 返回 true
	public static boolean isMenuEnable(Dmlb dmlb) {

		dmlb.setCurrentYear(UtilsFns.selectCurrentYear());

		// 管理员
		if (UtilsMessage.isGlyRole()) {
			return true;
		}
		Dmlb dmlbRes = dmlbDao.selectMenuById(dmlb);
		if (dmlbRes != null && "1".equals(dmlbRes.getEnable())) {
			return true;
		}
		return false;
	}

	public static List<Rewproject> selectZszjProjectList(Rewproject rewproject) {
		return projectDao.selectZszjProjectList(rewproject);
	}

	public static List<Rewproject> getProjects(String kbn) {
		List<Rewproject> list = new ArrayList<Rewproject>();
		// 是管理员 查看所有的项目 || 初审的时候也是查询所有的项目， 不是管理员 只能查看本组的项目
		if (UtilsMessage.isGlyRole() || "cstp".equals(kbn)) {
			list = UtilsFns.getProjectList();
		} else if ("fsdeltp".equals(kbn)) {
			list = projectDao.findProjectListNotInparametes(UtilsFns.selectCurrentYear());
		} else {
			list = UtilsFns.getProjectListByGroup();
		}
		return list;
	}
	private static RewFsdatasrcDao fsdatasrcDao = SpringContextHolder.getBean(RewFsdatasrcDao.class);
	public static final String FS_CACHE = "fs_cache";
	public static final String FS_CACHE_ID = "fs_cache_id";
	
	// 复审第一轮下拉框
	public static List<RewFsdatasrc> getFsdylProjects() {
		
		List<RewFsdatasrc> list = (List<RewFsdatasrc>) CacheUtils.get(FS_CACHE, FS_CACHE_ID + "1");
		if(list == null){
			RewFsdatasrc entity = new RewFsdatasrc();
			entity.setCurrentYear(UtilsFns.selectCurrentYear());
			entity.setRecomidea("1");
			list = fsdatasrcDao.findList(entity);
			
			CacheUtils.put(FS_CACHE, FS_CACHE_ID + "1");
		}
		return list;
	}

	// 复审第二轮下拉框  (*********)
	public static List<RewFsdatasrc> getFsdelProjects(List<Rewfsdyl> tdjs) {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("list", tdjs);

		parameters.put("currentYear", UtilsFns.selectCurrentYear());

		List<RewFsdatasrc> list = fsdatasrcDao.findList2(parameters);

		return list;
	}
	
	// 复审第三轮下拉框
	public static List<RewFsdatasrc> getFsdslProjects(List<Rewfsdyl> tdjs) {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("list", tdjs);

		parameters.put("currentYear", UtilsFns.selectCurrentYear());

		List<RewFsdatasrc> list = fsdatasrcDao.findList3(parameters);

		return list;
	}
	
	
	public static List<SelectEntity> getKPIList(String KPIWeight) {

		List<SelectEntity> list = new ArrayList<SelectEntity>();

		int scope = Integer.parseInt(KPIWeight);

		SelectEntity entity = null;
		for (int i = 1; i <= scope; i++) {

			entity = new SelectEntity(i, i + "分");
			list.add(entity);
		}
		return list;
	}

	public static final String SYS_VAL = "SYS_VAL";
	public static final String SYS_VAL_YEAR = "SYS_VAL_YEAR";
	public static final String SYS_FS_DATASRC = "SYS_FS_DATASRC";

	// 当前年
	public static String selectCurrentYear() {

		String res = (String) CacheUtils.get(SYS_VAL, SYS_VAL_YEAR + "currentyear");
		if (res == null) {
			res = dmlbDao.selectCurrentYear();
		}
		CacheUtils.put(SYS_VAL, SYS_VAL_YEAR + "currentyear",res);

		return res;

	}

	private static RewsysvalDao rewsysvalDao = SpringContextHolder.getBean(RewsysvalDao.class);

	// 当前年的 特等 一等 总共 复审 投票数
	public static Rewsysval selectRewSysvalByCurrentYear() {

		List<Rewsysval> res = (List<Rewsysval>) CacheUtils.get(SYS_VAL, SYS_VAL_YEAR + selectCurrentYear());
		if (res == null) {
			Rewsysval r = new Rewsysval();
			r.setCurrentYear(selectCurrentYear());
			res = rewsysvalDao.findList(r);
		}
		if (res != null && !res.isEmpty()) {
			CacheUtils.put(SYS_VAL, SYS_VAL_YEAR + selectCurrentYear());
			return res.get(0);
		}

		return new Rewsysval();

	}

	// 初审 复审结果 0初审显示 1 复审显示 其他都显示
	public static String selectcfvalue() {
		return dmlbDao.selectcfvalue();
	}

	public static List<SelectEntity> selectYearList() {
		return dmlbDao.selectYearList();
	}

	// 1 复审第一轮 2复审第二轮 (enable 1补投可用 0不可用)
	public static boolean getmenucontroltp(String id) {
		Dmlb getmenucontroltpById = dmlbDao.getmenucontroltpById(id);
		if ("1".equals(getmenucontroltpById.getEnable())) {
			return true;
		}
		return false;
	}

	private static RewProgressDao rewProgressDao = SpringContextHolder.getBean(RewProgressDao.class);
	public static final String RPOGERSS_REW = "RPOGERSS_REW";
	public static final String RPOGERSS_REW_VAVLE = "RPOGERSS_REW_VAVLE";
	public static final String RPOGERSS_REW_RESULT = "RPOGERSS_REW_RESULT";

	
	// 项目流程
	public static String getProgerss(){
		String res = (String) CacheUtils.get(RPOGERSS_REW, RPOGERSS_REW_VAVLE);
		if (res == null) {
			res = rewProgressDao.selectprogerss();
		}
		
		CacheUtils.put(RPOGERSS_REW, RPOGERSS_REW_VAVLE,res);

		return res;
	}
	
	
	
	
	
	private static RewResDao rewResDao = SpringContextHolder.getBean(RewResDao.class);
	// 项目流程 结果
	public static RewRes getProgerssRes(){
		String currentYear = selectCurrentYear();
		RewRes res = (RewRes) CacheUtils.get(RPOGERSS_REW, RPOGERSS_REW_RESULT + currentYear);
		if (res == null) {
			res = rewResDao.selectprogerssRes(currentYear);
		}
		
		CacheUtils.put(RPOGERSS_REW, RPOGERSS_REW_RESULT + currentYear,res);

		return res;
	}
	
	// 初审流程结果
	// ture  初审可用   false 初审不可用
	public static boolean getCsProgressResIsEnable(){
		RewRes rewRes = getProgerssRes();
		
		if(rewRes == null){
			return false;
		}
		
		if(StringUtils.equals("0", rewRes.getCsres())){
			return true;
		}else{
			return false;
		}
	}
	
	// 复审流程结果
	// ture  复审可用   false 复审不可用
	public static boolean getFsProgressResIsEnable(){
		RewRes rewRes = getProgerssRes();
		if(StringUtils.equals("0", rewRes.getFsres())){
			return true;
		}else{
			return false;
		}
	}

	public static Rewproject getProjectByProjectName(String projectName) {
		Rewproject p = new Rewproject();
		p.setProjectname(projectName);
		p.setCurrentYear(UtilsFns.selectCurrentYear());


		return projectDao.getProjectByProjectName(p);
			

    }

}
