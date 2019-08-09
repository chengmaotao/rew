/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.zszj.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 主审专家Entity
 * @author 成茂涛
 * @version 2016-08-10
 */
public class Rewidea extends DataEntity<Rewidea> {
	
	private static final long serialVersionUID = 1L;
	private String projectid;		// 项目编号
	private String levelid;		// 奖励等级
	private String revidea;		// 评审意见
	
	private String groupName;
	private String groupSonName;
	private String companyName;
	
	private List<String> projectids; //发货类别列表
	
	private String isZszj;  // 1时分配给了主审专家， 2时没有分配给主审专家
	
	public String getIsZszj() {
		return isZszj;
	}

	public void setIsZszj(String isZszj) {
		this.isZszj = isZszj;
	}

	public List<String> getProjectids() {
		return projectids;
	}

	public void setProjectids(List<String> projectids) {
		this.projectids = projectids;
	}

	private String projectName;
	private String userName;
	
	private String userid;
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Rewidea() {
		super();
	}

	public Rewidea(String id){
		super(id);
	}

	@Length(min=1, max=15, message="项目编号长度必须介于 1 和 15 之间")
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	@Length(min=1, max=11, message="奖励等级长度必须介于 1 和 11 之间")
	public String getLevelid() {
		return levelid;
	}

	public void setLevelid(String levelid) {
		this.levelid = levelid;
	}
	
	@Length(min=0, max=1000, message="评审意见长度必须介于 0 和 1000 之间")
	public String getRevidea() {
		return revidea;
	}

	public void setRevidea(String revidea) {
		this.revidea = revidea;
	}
	
}