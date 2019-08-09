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
public class Rewcategory extends DataEntity<Rewcategory> {
	
	private static final long serialVersionUID = 1L;
	private String categoryid;		// 分类ID
	private String categoryname;		// 分类Name
	
	public Rewcategory() {
		super();
	}

	public Rewcategory(String id){
		super(id);
	}

	@Length(min=1, max=15, message="分类ID长度必须介于 1 和 15 之间")
	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	
	@Length(min=0, max=50, message="分类Name长度必须介于 0 和 50 之间")
	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
}