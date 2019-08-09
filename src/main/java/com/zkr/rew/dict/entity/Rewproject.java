/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.dict.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 项目管理Entity
 * @author 成茂涛
 * @version 2016-08-09
 */
public class Rewproject extends DataEntity<Rewproject> {
	
	private static final long serialVersionUID = 1L;
	private String projectid;		// projectid
	private String projectname;		// projectname
	private String categoryid;		// categoryid
	private String groupId;		// groupid
	private String groupSonId;		// groupsonid
	
	private String awardlevel;
	
	@ExcelField(title="申报等级", align=2, sort=30)
	public String getAwardlevel() {
		return awardlevel;
	}

	public void setAwardlevel(String awardlevel) {
		this.awardlevel = awardlevel;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	@ExcelField(title="申报单位", align=2, sort=25)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	private String companyId;
	private String companyName;
	
	
	
	private String groupSonName;
	public String getGroupSonName() {
		return groupSonName;
	}

	public void setGroupSonName(String groupSonName) {
		this.groupSonName = groupSonName;
	}
	private String groupName;
	
	@ExcelField(title="专业组", align=2, sort=15)
	public String getGroupName() {
		if(groupSonName != null && !"".equals(groupSonName)){
			return groupName + "," + groupSonName;
		}
		return groupName;
	}
	
	private String categoryName;
	
	@ExcelField(title="申报类别", align=2, sort=20)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	private String kbn; // 值为1的时候  是添加，非1时 excel导入
	
	public String getKbn() {
		return kbn;
	}

	public void setKbn(String kbn) {
		this.kbn = kbn;
	}
	
	public Rewproject() {
		super();
	}

	public Rewproject(String id){
		super(id);
	}

	@Length(min=1, max=15, message="项目编号长度必须介于 1 和 15 之间")
	@ExcelField(title="项目编号", align=2, sort=5)
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	@Length(min=0, max=100, message="项目名称长度必须介于 0 和 100 之间")
	@ExcelField(title="项目名称", align=2, sort=10)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@Length(min=1, max=15, message="申报类别长度必须介于 1 和 15 之间")
	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupSonId() {
		return groupSonId;
	}

	public void setGroupSonId(String groupSonId) {
		this.groupSonId = groupSonId;
	}
	
}