/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.pszj.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 评审专家Entity
 * 
 * @author 成茂涛
 * @version 2016-08-12
 */
public class Rewprojectscope extends DataEntity<Rewprojectscope> {

	private static final long serialVersionUID = 1L;
	private String projectid; // projectid
	private String kpiweight1; // kpiweight1hidGroupId
	private String kpiweight2; // kpiweight2
	private String kpiweight3; // kpiweight3
	private String kpiweight4; // kpiweight4
	private String kpiweight5; // kpiweight5
	
	private String kpiweightAll;
	
	private String groupName;
	private String groupSonName;
	private String categoryName;

	public String getKpiweightAll() {
		return kpiweightAll;
	}

	public void setKpiweightAll(String kpiweightAll) {
		this.kpiweightAll = kpiweightAll;
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

	public Rewprojectscope() {
		super();
	}

	public Rewprojectscope(String id) {
		super(id);
	}

	@Length(min = 1, max = 15, message = "projectid长度必须介于 1 和 15 之间")
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	@Length(min = 0, max = 11, message = "kpiweight1长度必须介于 0 和 11 之间")
	public String getKpiweight1() {
		return kpiweight1;
	}

	public void setKpiweight1(String kpiweight1) {
		this.kpiweight1 = kpiweight1;
	}

	@Length(min = 0, max = 11, message = "kpiweight2长度必须介于 0 和 11 之间")
	public String getKpiweight2() {
		return kpiweight2;
	}

	public void setKpiweight2(String kpiweight2) {
		this.kpiweight2 = kpiweight2;
	}

	@Length(min = 0, max = 11, message = "kpiweight3长度必须介于 0 和 11 之间")
	public String getKpiweight3() {
		return kpiweight3;
	}

	public void setKpiweight3(String kpiweight3) {
		this.kpiweight3 = kpiweight3;
	}

	@Length(min = 0, max = 11, message = "kpiweight4长度必须介于 0 和 11 之间")
	public String getKpiweight4() {
		return kpiweight4;
	}

	public void setKpiweight4(String kpiweight4) {
		this.kpiweight4 = kpiweight4;
	}

	@Length(min = 0, max = 11, message = "kpiweight5长度必须介于 0 和 11 之间")
	public String getKpiweight5() {
		return kpiweight5;
	}

	public void setKpiweight5(String kpiweight5) {
		this.kpiweight5 = kpiweight5;
	}

}