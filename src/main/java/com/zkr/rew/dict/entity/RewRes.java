/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 项目流程结果Entity
 * @author 成茂涛
 * @version 2017-11-06
 */
public class RewRes extends DataEntity<RewRes> {
	
	private static final long serialVersionUID = 1L;
	private String csres;		// csres
	private String fsres;		// fsres
	
	public RewRes() {
		super();
	}

	public RewRes(String id){
		super(id);
	}

	@Length(min=1, max=1, message="csres长度必须介于 1 和 1 之间")
	public String getCsres() {
		return csres;
	}

	public void setCsres(String csres) {
		this.csres = csres;
	}
	
	@Length(min=1, max=1, message="fsres长度必须介于 1 和 1 之间")
	public String getFsres() {
		return fsres;
	}

	public void setFsres(String fsres) {
		this.fsres = fsres;
	}
	
}