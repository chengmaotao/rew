package com.zkr.rew.dict.entity;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class Dmlb extends DataEntity<Dmlb> {

	private String name;
	
	private String groupId;
	private String groupSonId;
	
	
	public Dmlb(){
		super();
	}
	
	public Dmlb(String id,String groupId,String groupSonId){
		this.id = id;
		this.groupId = groupId;
		this.groupSonId = groupSonId;
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

	private List<Dmlb> menus = new ArrayList<Dmlb>();

	public List<Dmlb> getMenus() {
		return menus;
	}

	public void setMenus(List<Dmlb> menus) {
		this.menus = menus;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	private String enable = "0";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
