/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.zyzz.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 小组推荐意见Entity
 * @author 成茂涛
 * @version 2016-08-15
 */
public class Rewrecomidea extends DataEntity<Rewrecomidea> {
	
	private static final long serialVersionUID = 1L;
	private String projectid;		// projectid
	private String scope;		// scope
	private String recomidea;		// recomidea
	
	
	private String groupName;
	private String groupSonName;
	private String categoryName;
	
	private String groupId;
	private String groupSonId;
	
	
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

	private String allNum = "0";
	private String num = "0";
	private String result = "未通过";
	
	public String getAllNum() {
		return allNum;
	}

	public void setAllNum(String allNum) {
		this.allNum = allNum;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupSonName() {
		return groupSonName;
	}

	public void setGroupSonName(String groupSonName) {
		this.groupSonName = groupSonName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Rewrecomidea() {
		super();
	}

	public Rewrecomidea(String id){
		super(id);
	}

	@Length(min=1, max=15, message="projectid长度必须介于 1 和 15 之间")
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	@Length(min=0, max=11, message="scope长度必须介于 0 和 11 之间")
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Length(min=0, max=15, message="recomidea长度必须介于 0 和 15 之间")
	public String getRecomidea() {
		return recomidea;
	}

	public void setRecomidea(String recomidea) {
		this.recomidea = recomidea;
	}
	
}