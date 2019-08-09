/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.zkr.rew.common.utils.UtilsFns;

/**
 * 用户工具类
 * 
 * @author ThinkGem
 * @version 2013-12-05
 */
public class UserUtils {

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_MENU_LIST0 = "menuList0";
	
	public static final String CACHE_MENU_LIST1 = "menuList1";
	public static final String CACHE_MENU_LISTAM1 = "menuListam1";
	public static final String CACHE_MENU_LISTPM1 = "menuListpm1";
	public static final String CACHE_MENU_LIST2 = "menuList2";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";


	public static final String CACHE_ALLUSER_LIST_CS = "allUserListcs";
	
	public static final String CACHE_ALLUSER_LIST_FS = "allUserListfs";
	

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id) {
		User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user == null) {
			user = userDao.get(id);
			if (user == null) {
				return null;
			}

			if (user.isAdmin()) {
				user.setRoleList(roleDao.findAllList(new Role()));
			} else {
				user.setRoleList(roleDao.findList(new Role(user)));
			}
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}

	/**
	 * 根据登录名获取用户
	 * 
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName) {
		User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null) {
			user = userDao.getByLoginName(new User(null, loginName));
			if (user == null) {
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}

	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(String id) {
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_MENU_LIST0);
		removeCache(CACHE_MENU_LIST1);
		removeCache(CACHE_MENU_LIST2);
		removeCache(CACHE_AREA_LIST);
		removeCache(CACHE_ALLUSER_LIST_CS + UtilsFns.selectCurrentYear());
		removeCache(CACHE_OFFICE_LIST);
		removeCache(CACHE_OFFICE_ALL_LIST);
		removeCache(CACHE_ROLE_LIST + id);
		UserUtils.clearCache(getUser());
	}

	/**
	 * 清除指定用户缓存
	 * 
	 * @param user
	 */
	public static void clearCache(User user) {
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
		if (user.getOffice() != null && user.getOffice().getId() != null) {
			CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
		}
	}

	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回 new User()
	 */
	public static User getUser() {
		Principal principal = getPrincipal();
		if (principal != null) {
			User user = get(principal.getId());
			if (user != null) {
				//user.setGroupSonName(UtilsFns.getGroupSonById(user.getGroupSonId()).getGroupsonname());
				user.setGroupName(UtilsFns.getGroupById(user.getGroupId()).getGroupname());
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}

	/**
	 * 获取当前用户角色列表 todo 获得所有角色列表
	 * 
	 * @return
	 */
	public static List<Role> getRoleList() {
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>) getCache(CACHE_ROLE_LIST);
		if (roleList == null) {
			User user = getUser();
			if (user.isAdmin()) {
				roleList = roleDao.findAllList(new Role());
			} else {
				Role role = new Role();
				/*
				 * role.getSqlMap().put("dsf",
				 * BaseService.dataScopeFilter(user.getCurrentUser(), "o",
				 * "u"));
				 */
				roleList = roleDao.findList(role);
			}
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}

	/**
	 * 根据用户ID 获得所有角色列表 chengmaotao
	 * 
	 * @return
	 */
	public static List<Role> getRoleListByUserId(String id) {
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>) getCache(CACHE_ROLE_LIST + id);
		if (roleList == null) {

			roleList = userDao.selectRoleListByUserId(id);

			putCache(CACHE_ROLE_LIST + id, roleList);
		}
		return roleList;
	}

	/**
	 * 获取当前用户授权菜单
	 * 
	 * @return
	 */
	public static List<Menu> getMenuList() {
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
		if (menuList == null) {
			User user = getUser();
			if (user.isAdmin()) {
				menuList = menuDao.findAllList(new Menu());
			} else {
				Menu m = new Menu();
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId(m);
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}
	
	/**
	 * 获取当前用户授权菜单 (出去 初审 复审的)
	 * 
	 * @return
	 */
	public static List<Menu> getMenuList0() {
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST0);
		if (menuList == null) {
			User user = getUser();
			if (user.isAdmin()) {
				menuList = menuDao.findAllList(new Menu());
			} else {
				Menu m = new Menu();
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId0(m);
			}
			putCache(CACHE_MENU_LIST0, menuList);
		}
		return menuList;
	}
	
	
	
	
	/**
	 * 获取当前用户授权复审菜单
	 * 
	 * @return
	 */
	public static List<Menu> getMenuList2() {
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST2);
		if (menuList == null) {
			User user = getUser();
		
			Menu m = new Menu();
			m.setUserId(user.getId());
			menuList = menuDao.findByUserId2(m);
	
			putCache(CACHE_MENU_LIST2, menuList);
		}
		return menuList;
	}
	

	/**
	 * 获取当前用户授权初审菜单
	 * 
	 * @return
	 */
	public static List<Menu> getMenuList1() {
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST1);
		if (menuList == null) {
			User user = getUser();
			Menu m = new Menu();
			m.setUserId(user.getId());
			menuList = menuDao.findByUserId1(m);		
			putCache(CACHE_MENU_LIST1, menuList);
		}
		return menuList;
	}
	
	
	/**
	 * 获取当前用户授权初审上午菜单
	 * 
	 * @return
	 */
	public static List<Menu> getMenuListAm1() {
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LISTAM1);
		if (menuList == null) {
			User user = getUser();
			Menu m = new Menu();
			m.setUserId(user.getId());
			menuList = menuDao.findByUserIdAM1(m);		
			putCache(CACHE_MENU_LISTAM1, menuList);
		}
		return menuList;
	}
	
	/**
	 * 获取当前用户授权初审下午菜单
	 * 
	 * @return
	 */
	public static List<Menu> getMenuListPm1() {
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LISTPM1);
		if (menuList == null) {
			User user = getUser();
			Menu m = new Menu();
			m.setUserId(user.getId());
			menuList = menuDao.findByUserIdPM1(m);		
			putCache(CACHE_MENU_LISTPM1, menuList);
		}
		return menuList;
	}
	
	
	
	

	/**
	 * 获取当前用户有权限访问的部门
	 * 
	 * @return
	 */
	public static List<Office> getOfficeList() {
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_LIST);
		if (officeList == null) {
			User user = getUser();
			if (user.isAdmin()) {
				officeList = officeDao.findAllList(new Office());
			} else {
				Office office = new Office();
				officeList = officeDao.findList(office);
			}
			putCache(CACHE_OFFICE_LIST, officeList);
		}
		return officeList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * 
	 * @return
	 */
	public static List<Office> getOfficeAllList() {
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_ALL_LIST);
		if (officeList == null) {
			officeList = officeDao.findAllList(new Office());
		}
		return officeList;
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal;
			}
			// subject.logout();
		} catch (UnavailableSecurityManagerException e) {

		} catch (InvalidSessionException e) {

		}
		return null;
	}

	public static Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
			// subject.logout();
		} catch (InvalidSessionException e) {

		}
		return null;
	}

	// ============== User Cache ==============

	public static Object getCache(String key) {
		return getCache(key, null);
	}

	public static Object getCache(String key, Object defaultValue) {
		// Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putCache(String key, Object value) {
		// getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		// getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}

	// public static Map<String, Object> getCacheMap(){
	// Principal principal = getPrincipal();
	// if(principal!=null){
	// return principal.getCacheMap();
	// }
	// return new HashMap<String, Object>();
	// }

    // 根据User 获得该User所在组下的用户 初审
	public static List<User> selectcsUsersByGroup(User user){
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		return userDao.selectcsUsersByGroup(user);
	}
	
    // 根据User 获得该User所在组下的用户 初审
	public static List<User> selectfsUsersByGroup(User user){
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		return userDao.selectfsUsersByGroup(user);
	}
	
    // 获得所有的投票专家  初审
	public static List<User> selectcsAllZJUsers(){
		
		List<User> alluses = (List<User>) getCache(CACHE_ALLUSER_LIST_CS + UtilsFns.selectCurrentYear());
		if (alluses == null) {
			User u = new User();
			u.setCurrentYear(UtilsFns.selectCurrentYear());
			alluses = userDao.selectcsAllZJUsers(u);
			putCache(CACHE_ALLUSER_LIST_CS + UtilsFns.selectCurrentYear(), alluses);
		}
		return alluses;
		
		//return userDao.selectAllZJUsers();
	}
	
	
    // 获得所有的投票专家  初审
	public static List<User> selectfsAllZJUsers(){
		
		List<User> alluses = (List<User>) getCache(CACHE_ALLUSER_LIST_FS + UtilsFns.selectCurrentYear());
		if (alluses == null) {
			User u = new User();
			u.setCurrentYear(UtilsFns.selectCurrentYear());
			alluses = userDao.selectfsAllZJUsers(u);
			putCache(CACHE_ALLUSER_LIST_FS + UtilsFns.selectCurrentYear(), alluses);
		}
		return alluses;
		
		//return userDao.selectAllZJUsers();
	}
	
    // 获得user表中 共有多少个组  初审
	public static List<User> selectcsGroups(User user){
		user.setCurrentYear(UtilsFns.selectCurrentYear());
		return userDao.selectcsGroups(user);
	}
	
	
	public static List<User> selectZszjList(){
		User u = new User();
		u.setCurrentYear(UtilsFns.selectCurrentYear());
		return userDao.selectZszjList(u);
	}
}
