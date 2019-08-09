/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 复审数据来源Entity
 * @author 成茂涛
 * @version 2017-11-12
 */
public class RewFsdatasrc extends DataEntity<RewFsdatasrc> {
	
	private static final long serialVersionUID = 1L;
	private String projectid;		// projectid
	
	private String sort="0";
	
	
	@ExcelField(title="排序", align=2, sort=20)
	public String getSort() {
    	return sort;
    }

	public void setSort(String sort) {
    	this.sort = sort;
    }

	@ExcelField(title="奖励等级", align=2, sort=15)
	private String recomidea;		// 推荐意见
	
	
	private String levelName;
	private String projectName;
	
	
	public String getLevelName() {
    	return levelName;
    }

	public void setLevelName(String levelName) {
    	this.levelName = levelName;
    }

	@ExcelField(title="项目名称", align=2, sort=10)
	public String getProjectName() {
    	return projectName;
    }

	public void setProjectName(String projectName) {
    	this.projectName = projectName;
    }

	public RewFsdatasrc() {
		super();
	}

	public RewFsdatasrc(String id){
		super(id);
	}

	@Length(min=1, max=15, message="projectid长度必须介于 1 和 15 之间")
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	@Length(min=0, max=15, message="推荐意见长度必须介于 0 和 15 之间")
	public String getRecomidea() {
		return recomidea;
	}

	public void setRecomidea(String recomidea) {
		this.recomidea = recomidea;
	}
	
}