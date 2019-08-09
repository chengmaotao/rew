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
public class Rewawardlevel extends DataEntity<Rewawardlevel> {
	
	private static final long serialVersionUID = 1L;
	private String levelname;		// levelname
	
	public Rewawardlevel() {
		super();
	}

	public Rewawardlevel(String id){
		super(id);
	}

	@Length(min=0, max=20, message="levelname长度必须介于 0 和 20 之间")
	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	
}