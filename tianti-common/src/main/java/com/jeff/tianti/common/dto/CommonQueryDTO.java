package com.jeff.tianti.common.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共信息查询封装DTO
 * 
 * @author Jeff Xu
 * @since 2015-12-28
 */
public class CommonQueryDTO {

	private Integer currentPage;

	private Integer pageSize;

	/**
	 * 查询条件
	 */
	private Map<String, String> condition = new HashMap<String, String>();

	public void addParam(String name, String value) {
		condition.put(name, value);
	}

	public void addParams(Map<String, String> map) {
		condition.putAll(map);
	}

	public void removeParam(String key) {
		condition.remove(key);
	}

	public Map<String, String> getCondition() {
		return condition;
	}

	public void setCondition(Map<String, String> condition) {
		this.condition = condition;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
