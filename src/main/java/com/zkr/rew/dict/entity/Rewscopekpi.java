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
public class Rewscopekpi extends DataEntity<Rewscopekpi> {
	
	private static final long serialVersionUID = 1L;
	private String scopekpiid;		// 评分指标ID
	private String categoryid;		// 分类ID
	private String kpicategory;		// 指标分类
	private String kpidetailed;		// 指标分类详细内容
	private String kpiweight;		// 权重
	private String scopelevel;		// 评分等级
	private String sortNum; 
	
	public String getSortNum() {
		return sortNum;
	}

	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}

	public Rewscopekpi() {
		super();
	}

	public Rewscopekpi(String id){
		super(id);
	}

	@Length(min=1, max=15, message="评分指标ID长度必须介于 1 和 15 之间")
	public String getScopekpiid() {
		return scopekpiid;
	}

	public void setScopekpiid(String scopekpiid) {
		this.scopekpiid = scopekpiid;
	}
	
	@Length(min=1, max=15, message="分类ID长度必须介于 1 和 15 之间")
	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	
	@Length(min=0, max=50, message="指标分类长度必须介于 0 和 50 之间")
	public String getKpicategory() {
		return kpicategory;
	}

	public void setKpicategory(String kpicategory) {
		this.kpicategory = kpicategory;
	}
	
	@Length(min=0, max=500, message="指标分类详细内容长度必须介于 0 和 500 之间")
	public String getKpidetailed() {
		return kpidetailed;
	}

	public void setKpidetailed(String kpidetailed) {
		this.kpidetailed = kpidetailed;
	}
	
	@Length(min=0, max=11, message="权重长度必须介于 0 和 11 之间")
	public String getKpiweight() {
		return kpiweight;
	}

	public void setKpiweight(String kpiweight) {
		this.kpiweight = kpiweight;
	}
	
	@Length(min=0, max=500, message="评分等级长度必须介于 0 和 500 之间")
	public String getScopelevel() {
		return scopelevel;
	}

	public void setScopelevel(String scopelevel) {
		this.scopelevel = scopelevel;
	}
	
}