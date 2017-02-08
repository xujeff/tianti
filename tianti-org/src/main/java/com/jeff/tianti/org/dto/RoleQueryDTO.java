package com.jeff.tianti.org.dto;

import com.jeff.tianti.common.dto.CommonQueryDTO;

/**
 * 后台角色查询信息封装
 * @author Jeff Xu
 * @since 2016-01-21
 */
public class RoleQueryDTO extends CommonQueryDTO{
	
	//名称
	private String name;
	
	//状
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
