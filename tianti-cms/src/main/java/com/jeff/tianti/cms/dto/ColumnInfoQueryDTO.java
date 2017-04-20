package com.jeff.tianti.cms.dto;

import com.jeff.tianti.common.dto.CommonQueryDTO;

/**
 * @author xujianfang
 * @desc ColumnInfoQueryDTO 
 * @date 2017-03-16
 */
public class ColumnInfoQueryDTO extends CommonQueryDTO{
	
	//是否根据根节点模糊查询
	private Boolean isRootColumnLike;
	
	//父级栏目ID
	private String rootColumnId;
	
	//所属层级
	private Integer level;
	
	//栏目编码
	private String code;
	
	//code是否模糊搜索
	private Boolean isCodeLike;
	
	//栏目名称
	private String name;
	
	public Boolean getIsRootColumnLike() {
		return isRootColumnLike;
	}

	public void setIsRootColumnLike(Boolean isRootColumnLike) {
		this.isRootColumnLike = isRootColumnLike;
	}

	public String getRootColumnId() {
		return rootColumnId;
	}

	public void setRootColumnId(String rootColumnId) {
		this.rootColumnId = rootColumnId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsCodeLike() {
		return isCodeLike;
	}

	public void setIsCodeLike(Boolean isCodeLike) {
		this.isCodeLike = isCodeLike;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}