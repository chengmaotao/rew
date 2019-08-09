/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 历年投票数目控制Entity
 * @author 成茂涛
 * @version 2017-10-16
 */
public class Rewsysval extends DataEntity<Rewsysval> {
	
	private static final long serialVersionUID = 1L;
	private String csdyltp;		// 初审第一轮允许投票的总数
	private String csdeltpt;		// 初审第二轮特等奖允许投票的总数
	private String csdeltpy;		// 初审第二轮一等奖允许投票的总数
	private String fsdyltp;		// 复审第一轮允许投票数
	private String fsdeltp;		// 复审第二轮允许投票数
	private String fsdsltp;		// 复审第三轮允许投票数
	
	public Rewsysval() {
		super();
	}

	public Rewsysval(String id){
		super(id);
	}

	@Length(min=0, max=11, message="初审第一轮允许投票的总数长度必须介于 0 和 11 之间")
	public String getCsdyltp() {
		return csdyltp;
	}

	public void setCsdyltp(String csdyltp) {
		this.csdyltp = csdyltp;
	}
	
	@Length(min=0, max=11, message="初审第二轮特等奖允许投票的总数长度必须介于 0 和 11 之间")
	public String getCsdeltpt() {
		return csdeltpt;
	}

	public void setCsdeltpt(String csdeltpt) {
		this.csdeltpt = csdeltpt;
	}
	
	@Length(min=0, max=11, message="初审第二轮一等奖允许投票的总数长度必须介于 0 和 11 之间")
	public String getCsdeltpy() {
		return csdeltpy;
	}

	public void setCsdeltpy(String csdeltpy) {
		this.csdeltpy = csdeltpy;
	}
	
	@Length(min=0, max=11, message="复审第一轮允许投票数长度必须介于 0 和 11 之间")
	public String getFsdyltp() {
		return fsdyltp;
	}

	public void setFsdyltp(String fsdyltp) {
		this.fsdyltp = fsdyltp;
	}
	
	@Length(min=0, max=11, message="复审第二轮允许投票数长度必须介于 0 和 11 之间")
	public String getFsdeltp() {
		return fsdeltp;
	}

	public void setFsdeltp(String fsdeltp) {
		this.fsdeltp = fsdeltp;
	}
	
	@Length(min=0, max=11, message="复审第三轮允许投票数长度必须介于 0 和 11 之间")
	public String getFsdsltp() {
		return fsdsltp;
	}

	public void setFsdsltp(String fsdsltp) {
		this.fsdsltp = fsdsltp;
	}
	
}