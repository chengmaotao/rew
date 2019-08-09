/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.cssecond.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 初审第二轮投票Entity
 * @author 成茂涛
 * @version 2016-08-16
 */
public class Rewcssenondvote extends DataEntity<Rewcssenondvote> {
	
	private static final long serialVersionUID = 1L;
	private String projectid;		// projectid
	private String isagree0 = "0";		// 特等奖
	private String isagree1 = "0";		// 一等奖
	private String groupName;
	private String groupSonName;
	private String groupId;
	
	private String isAgreeKey;
	public String getIsAgreeKey() {
		return isAgreeKey;
	}

	public void setIsAgreeKey(String isAgreeKey) {
		this.isAgreeKey = isAgreeKey;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	private String recomIdea;
	
	public String getRecomIdea() {
		return recomIdea;
	}

	public void setRecomIdea(String recomIdea) {
		this.recomIdea = recomIdea;
	}

	private String categoryName;
	private String countNum;
	private List<Rewcssenondvote> cssenondvotes;
	
	public List<Rewcssenondvote> getCssenondvotes() {
		return cssenondvotes;
	}

	public void setCssenondvotes(List<Rewcssenondvote> cssenondvotes) {
		this.cssenondvotes = cssenondvotes;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCountNum() {
		return countNum;
	}

	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}

	public Rewcssenondvote() {
		super();
	}

	public Rewcssenondvote(String id){
		super(id);
	}

	@Length(min=1, max=15, message="projectid长度必须介于 1 和 15 之间")
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	@Length(min=1, max=1, message="特等奖长度必须介于 1 和 1 之间")
	public String getIsagree0() {
		return isagree0;
	}

	public void setIsagree0(String isagree0) {
		this.isagree0 = isagree0;
	}
	
	@Length(min=1, max=1, message="一等奖长度必须介于 1 和 1 之间")
	public String getIsagree1() {
		return isagree1;
	}

	public void setIsagree1(String isagree1) {
		this.isagree1 = isagree1;
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
	
	public boolean senondShow=false;
	public boolean isSenondShow() {
    	return senondShow;
    }

	public void setSenondShow(boolean senondShow) {
    	this.senondShow = senondShow;
    }
	

	
	
}