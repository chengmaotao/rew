/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 项目流程Entity
 * @author 成茂涛
 * @version 2017-11-06
 */
public class RewProgress extends DataEntity<RewProgress> {
	
	private static final long serialVersionUID = 1L;
	private String progress;		// 0初审上午 1初审下午 其他都是复审
	
	public RewProgress() {
		super();
	}

	public RewProgress(String id){
		super(id);
	}

	@Length(min=1, max=1, message="0初审上午 1初审下午 其他都是复审长度必须介于 1 和 1 之间")
	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}
	
}