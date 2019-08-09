/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.ideachk.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 推荐意见投票Entity
 * @author 成茂涛
 * @version 2016-08-18
 */
public class Rewcomideacheck extends DataEntity<Rewcomideacheck> {
	
	private static final long serialVersionUID = 1L;
	private String projectid;		// projectid
	private String isagree = "0";		// isagree
	
	
	private String groupName;
	private String groupSonName;
	private String categoryName;
	private String recomidea;
	
	private String scope;
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	private int num;
	

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	private List<Rewcomideacheck> comideaChecks = new ArrayList<Rewcomideacheck>();
	
	public List<Rewcomideacheck> getComideaChecks() {
		return comideaChecks;
	}

	public void setComideaChecks(List<Rewcomideacheck> comideaChecks) {
		this.comideaChecks = comideaChecks;
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

	public String getRecomidea() {
		return recomidea;
	}

	public void setRecomidea(String recomidea) {
		this.recomidea = recomidea;
	}

	public Rewcomideacheck() {
		super();
	}

	public Rewcomideacheck(String id){
		super(id);
	}

	@Length(min=1, max=15, message="projectid长度必须介于 1 和 15 之间")
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	@Length(min=1, max=1, message="isagree长度必须介于 1 和 1 之间")
	public String getIsagree() {
		return isagree;
	}

	public void setIsagree(String isagree) {
		this.isagree = isagree;
	}
	
}