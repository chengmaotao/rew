/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.csfirst.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 初审第一轮投票Entity
 * @author 成茂涛
 * @version 2016-08-15
 */
public class Rewcsfirstvote extends DataEntity<Rewcsfirstvote> {
	
	private static final long serialVersionUID = 1L;
	private String projectid;		// projectid
	private String scope;		// 评分（平均分）
	private String isagree = "0";		// 1：同意；0：不同意
	
	private String categoryName;
	private String recomIdea;
	
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
	
	private List<Rewcsfirstvote>  csfirstvotes = new ArrayList<Rewcsfirstvote>();
	
	public List<Rewcsfirstvote> getCsfirstvotes() {
		return csfirstvotes;
	}

	public void setCsfirstvotes(List<Rewcsfirstvote> csfirstvotes) {
		this.csfirstvotes = csfirstvotes;
	}

	public String getRecomIdea() {
		return recomIdea;
	}

	public void setRecomIdea(String recomIdea) {
		this.recomIdea = recomIdea;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Rewcsfirstvote() {
		super();
	}

	public Rewcsfirstvote(String id){
		super(id);
	}

	@Length(min=1, max=15, message="projectid长度必须介于 1 和 15 之间")
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Length(min=1, max=1, message="1：同意；0：不同意长度必须介于 1 和 1 之间")
	public String getIsagree() {
		return isagree;
	}

	public void setIsagree(String isagree) {
		this.isagree = isagree;
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
	
}