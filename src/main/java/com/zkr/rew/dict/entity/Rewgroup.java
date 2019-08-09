/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 保存字典Entity
 * @author 成茂涛
 * @version 2016-08-04
 */
public class Rewgroup extends DataEntity<Rewgroup> {
	
	private static final long serialVersionUID = 1L;
	private String groupid;		// 专业组ID
	private String groupname;		// 专业组名字
	private String reviewscope;		// 评审范围
	
	private String isenable;
	
	public String getIsenable() {
		return isenable;
	}

	public void setIsenable(String isenable) {
		this.isenable = isenable;
	}

	public Rewgroup() {
		super();
	}

	public Rewgroup(String id){
		super(id);
	}

	@Length(min=1, max=64, message="专业组ID长度必须介于 1 和 15 之间")
	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
	@Length(min=0, max=50, message="专业组名字长度必须介于 0 和 50 之间")
	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	@Length(min=0, max=1000, message="评审范围长度必须介于 0 和 1000 之间")
	public String getReviewscope() {
		return reviewscope;
	}

	public void setReviewscope(String reviewscope) {
		this.reviewscope = reviewscope;
	}
	
}