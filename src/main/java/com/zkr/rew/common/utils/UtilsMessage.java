package com.zkr.rew.common.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zkr.rew.csres.entity.RewcsResult;
import com.zkr.rew.zyzz.service.RewrecomideaService;

public class UtilsMessage {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(UtilsMessage.class);

	/**
	 * @Description:获得消息
	 * @param @param key
	 * @param @param parameters
	 * @param @return
	 * @return String
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-1 上午10:48:24
	 */
	public static String getMessage(String key, Object... parameters) {
		Properties prop = getProperty();
		return MessageFormat.format(prop.getProperty(key), parameters);
	}

	/**
	 * @Description:根据 key 获得 value
	 * @param @param key
	 * @param @return
	 * @return String
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-1 上午10:51:28
	 */
	public static String getValueByKey(String key) {
		Properties prop = getProperty();
		return prop.getProperty(key);
	}

	/**
	 * @Description:读取配置文件
	 * @param @return
	 * @return Properties
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-8-1 上午10:43:13
	 */
	private static Properties getProperty() {
		Properties prop = new Properties();

		try {
			InputStream in = UtilsMessage.class.getClassLoader().getResourceAsStream("rew.properties");
			prop.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * @Description:判断当前角色是否是 管理员角色
	 * @param @return
	 * @return boolean
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-4-14 下午6:42:17
	 */
	public static boolean isGlyRole() {
		String roleName = UtilsFns.getRolesByUserId(UserUtils.getUser().getId());

		// 判断是否是 管理员角色
		if (roleName.contains(UtilsMessage.getValueByKey("glyRoleName"))) {
			return true; // 可以查看所有的数据
		}
		return false;
	}
	
	public static boolean isLingdaoRole() {
		String roleName = UtilsFns.getRolesByUserId(UserUtils.getUser().getId());

		// 判断是否是 管理员角色
		if (roleName.contains(UtilsMessage.getValueByKey("lingdaoRoleName"))) {
			return true; // 可以查看所有的数据
		}
		return false;
	}
	
	public static boolean isZyzMenuGlyRole() {
		String roleName = UtilsFns.getRolesByUserId(UserUtils.getUser().getId());

		// 判断是否是 管理员角色
		if (roleName.contains(UtilsMessage.getValueByKey("zyzcdglry"))) {
			return true; // 可以查看所有的数据
		}
		return false;
	}
	
	
	
	

	/**
	 * @Description:判断当前角色是否是 专业组组长角色
	 * @param @return
	 * @return boolean
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-4-14 下午6:42:17
	 */
	public static boolean isZyzzRole() {
		String roleName = UtilsFns.getRolesByUserId(UserUtils.getUser().getId());

		// 判断是否是 专业组组长角色
		if (roleName.contains(UtilsMessage.getValueByKey("zyzzRoleName"))) {
			return true; // 可以查看本组所有的数据
		}
		return false;
	}
	
	/**
	 * @Description:判断当前角色是否是 主审专家角色
	 * @param @return
	 * @return boolean
	 * @throws
	 * @autohr 成茂涛
	 * @date 2016-4-14 下午6:42:17
	 */
	public static boolean isZszjRole() {
		String roleName = UtilsFns.getRolesByUserId(UserUtils.getUser().getId());

		// 
		if (roleName.contains(UtilsMessage.getValueByKey("zszjRoleName"))) {
			return true; // 
		}
		return false;
	}
	

	// 获得 该User 的所有角色字符串
	public static String getRoleS(User user) {
		List<Role> roleList = user.getRoleList();
		if (roleList != null && !roleList.isEmpty()) {
			StringBuffer sbuffer = new StringBuffer();
			Role tempRole = null;
			for (int i = 0; i < roleList.size(); i++) {
				tempRole = roleList.get(i);
				sbuffer.append(tempRole.getName());
				sbuffer.append(",");
			}
			return sbuffer.toString();
		}
		return "";
	}

/*	// 既是管理员 又是 评审专家
	public static boolean isTwoRole() {
		String roles = UtilsMessage.getRoleS(UserUtils.getUser());

		String pszjName = UtilsMessage.getValueByKey("pszjRoleName");
		String glyName = UtilsMessage.getValueByKey("glyRoleName");

		// 即是管理员 又是 评审专家
		if (roles.contains(pszjName) && roles.contains(glyName)) {
			return true;
		} else {
			return false;
		}
	}*/

	/**
	 * @return 1:管理员 2:专业组长 3:主审专家 4:评审专家
	 */
	public static String getRole() {
		String roleName = UtilsFns.getRolesByUserId(UserUtils.getUser().getId());

		// 管理员角色
		if (roleName.contains(UtilsMessage.getValueByKey("glyRoleName"))) {
			return "1";
		} else if (roleName.contains(UtilsMessage.getValueByKey("zyzzRoleName"))) {
			// 专业组长
			return "2";
		} else if (roleName.contains(UtilsMessage.getValueByKey("zszjRoleName"))) {
			// 主审专家
			return "3";
		} else {
			// 评审专家
			return "4";
		}
	}

	public static String check(RewrecomideaService rewrecomideaService, String kbn) {
		String roleKbn = getRole();
		if ("1".equals(kbn)) {
			// 判断推荐意见
			if ("2".equals(roleKbn)) {
				// 专业组长
				if (!rewrecomideaService.selectAllMessByGroup(UserUtils.getUser(), "1")) {
					return "/zyzz/rewrecomidea/list";
				}
			} else if ("3".equals(roleKbn) || "4".equals(roleKbn)) {
				if (!rewrecomideaService.selectAllMessByGroup(UserUtils.getUser(), "1")) {
					return "/pszj/rewprojectscope/list";
				}
			}
		} else {
			// 判断推荐意见 是否通过
			if ("2".equals(roleKbn)) {
				// 专业组长
				if (!rewrecomideaService.selectAllMessByGroup2(UserUtils.getUser(), "2")) {
					return "/zyzz/rewrecomidea/result";
				}
			} else if ("3".equals(roleKbn) || "4".equals(roleKbn)) {
				if (!rewrecomideaService.selectAllMessByGroup2(UserUtils.getUser(), "2")) {
					return "/ideachk/rewcomideacheck/list";
				}
			}
		}
		return null;
	}

	
	// 复审 算结果时 用到了
	public static List<RewcsResult> getRes(List<RewcsResult>... list1) {
		List<RewcsResult> result = new ArrayList<RewcsResult>();
		
		for(int i = 0; i < list1.length; i++){
			result.addAll(list1[i]);
		}
		
		RewcsResult t = new RewcsResult();
		Map<String,RewcsResult> map = new HashMap<String, RewcsResult>();
		RewcsResult rewcsResult = null;
		
		int temp = 0;
		for(int i = 0; i < result.size(); i++){
			rewcsResult = null;
			t = result.get(i);
			 rewcsResult = map.get(t.getProjectid());
			if(rewcsResult == null){
				map.put(t.getProjectid(), t);
			}else{
				temp = rewcsResult.getNum() + t.getNum();
				rewcsResult.setNum(temp);
			}
		}
		
		
		List<RewcsResult> resultDis = new ArrayList<RewcsResult>();
		Set<Entry<String, RewcsResult>> entrySet = map.entrySet();
		
		Iterator<Entry<String, RewcsResult>> iterator = entrySet.iterator();
		
		Entry<String, RewcsResult> next;
		while (iterator.hasNext()) {
			 next = iterator.next();
			 resultDis.add(next.getValue());
		}
		Collections.sort(resultDis);
		return resultDis;
	}

	// 获得当前系统日期
	public static String getYear() {
		SimpleDateFormat spf = new SimpleDateFormat("yyyy");
		String year = spf.format(new Date());
		return year;
	}
	
	// 当前用户是否是 初审专家
	public static boolean iscs(){
		User user = UserUtils.getUser();
		if(isGlyRole() || "1".equals(user.getIscs())){
			return true;
		}
		return false;
	}
	
	// 当前用户是否是 复审专家
	public static boolean isfs(){
		User user = UserUtils.getUser();
		if(isGlyRole() || "1".equals(user.getIsfs())){
			return true;
		}
		return false;
	}

	public static boolean isglylogin(String name) {

		if("admin".equals(name) || "gly".equals(name)){
			return true;
		}else{
			return false;
		}
	}
	
	// 判断  页面token 和  服务器端token 是否一致
	public static boolean isRepeatSubmit(String client_token,HttpServletRequest request) {

        //1、如果用户提交的表单数据中没有token，则用户是重复提交了表单
        if(client_token==null){
        	request.getSession().removeAttribute("token");
            return true;
        }
        //取出存储在Session中的token
        String server_token = (String) request.getSession().getAttribute("token");
        //2、如果当前用户的Session中不存在Token(令牌)，则用户是重复提交了表单
        if(server_token==null){
        	request.getSession().removeAttribute("token");
            return true;
        }
        //3、存储在Session中的Token(令牌)与表单提交的Token(令牌)不同，则用户是重复提交了表单
        if(!client_token.equals(server_token)){
        	request.getSession().removeAttribute("token");
            return true;
        }
        request.getSession().removeAttribute("token");
        return false;
    }

	
}
