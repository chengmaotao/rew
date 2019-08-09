/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.csres.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 初审第一轮投票Entity
 * 
 * @author 成茂涛
 * @version 2016-08-15
 */
public class RewcsResult extends DataEntity<RewcsResult> implements Comparable<RewcsResult>{

	private static final long serialVersionUID = 1L;

	private String projectid;

	private String projectname;
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	private int num;
	private int allNum;
	public int getAllNum() {
		return allNum;
	}

	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}

	private String companyId;
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	private String categoryId;
	private String groupId;
	private String groupSonId;
	
	private String levelName;
	private String levelId;

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupSonId() {
		return groupSonId;
	}

	public void setGroupSonId(String groupSonId) {
		this.groupSonId = groupSonId;
	}

	@Override
	public int compareTo(RewcsResult o) {
		
		return o.getNum() - this.getNum();
	}
}