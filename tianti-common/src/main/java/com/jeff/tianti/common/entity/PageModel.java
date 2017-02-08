package com.jeff.tianti.common.entity;

import java.io.Serializable;
import java.util.List;
/**
 * 分页模型
 * @author Jeff Xu
 * @since 2015-12-09
 */
public class PageModel<E> implements Serializable {
	
	private static final long serialVersionUID = 3265524976080127173L;

	private int totalCount; //总记录数
	
	private int pageSize = 10; //每页显示的数量
	
	private int totalPage; //总页数
	
	private int currentPage = 1; //当前页数
	
	private List<E> list; //分页集合列表
	
	private String url; //分页跳转的URL
	
	public PageModel() {
	}
	
	public PageModel(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
