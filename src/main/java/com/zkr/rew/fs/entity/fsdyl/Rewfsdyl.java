/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zkr.rew.fs.entity.fsdyl;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 复审第一轮投票Entity
 * 
 * @author 成茂涛
 * @version 2016-08-22
 */
public class Rewfsdyl extends DataEntity<Rewfsdyl> {

	private static final long serialVersionUID = 1L;
	private String projectid; // projectid

	private String isAgree = "0";

	private String projectName;

	private String groupId;
	
	
	private List<Rewfsdyl> fsdyls = new ArrayList<Rewfsdyl>();
	
	private List<Rewfsdyl> qtFsdyls = new ArrayList<Rewfsdyl>();
	

	private List<Rewfsdyl> qtOldFsdyls = new ArrayList<Rewfsdyl>();
	
	public List<Rewfsdyl> getQtOldFsdyls() {
		return qtOldFsdyls;
	}

	public void setQtOldFsdyls(List<Rewfsdyl> qtOldFsdyls) {
		this.qtOldFsdyls = qtOldFsdyls;
	}

	public List<Rewfsdyl> getQtFsdyls() {
		return qtFsdyls;
	}

	public void setQtFsdyls(List<Rewfsdyl> qtFsdyls) {
		this.qtFsdyls = qtFsdyls;
	}

	public List<Rewfsdyl> getFsdyls() {
		return fsdyls;
	}

	public void setFsdyls(List<Rewfsdyl> fsdyls) {
		this.fsdyls = fsdyls;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSblevel() {
		return sblevel;
	}

	public void setSblevel(String sblevel) {
		this.sblevel = sblevel;
	}

	private String groupSonId;

	private String companyId;

	private String categoryId;

	private String sblevel;

	public Rewfsdyl() {
		super();
	}

	public Rewfsdyl(String id) {
		super(id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projectid == null) ? 0 : projectid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Rewfsdyl other = (Rewfsdyl) obj;
		if (projectid == null) {
			if (other.projectid != null)
				return false;
		} else if (!projectid.equals(other.projectid))
			return false;
		return true;
	}
	
	
	private String csresLevelId;
	private String csresAllNum;
	private String csresNum;

	public String getCsresLevelId() {
    	return csresLevelId;
    }

	public void setCsresLevelId(String csresLevelId) {
    	this.csresLevelId = csresLevelId;
    }

	public String getCsresAllNum() {
    	return csresAllNum;
    }

	public void setCsresAllNum(String csresAllNum) {
    	this.csresAllNum = csresAllNum;
    }

	public String getCsresNum() {
    	return csresNum;
    }

	public void setCsresNum(String csresNum) {
    	this.csresNum = csresNum;
    }


}