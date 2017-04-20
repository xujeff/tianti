package com.jeff.tianti.cms.dto;

import java.util.List;

import com.jeff.tianti.cms.entity.ColumnInfo;

/**
 * 栏目信息封装DTO
 * @author Jeff Xu
 *
 */
public class ColumnInfoDTO {
	
	private String id;
		
	private String name;
	
	private List<ColumnInfoDTO> childColumnInfoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ColumnInfoDTO> getChildColumnInfoList() {
		return childColumnInfoList;
	}

	public void setChildColumnInfoList(List<ColumnInfoDTO> childColumnInfoList) {
		this.childColumnInfoList = childColumnInfoList;
	}
 
}
