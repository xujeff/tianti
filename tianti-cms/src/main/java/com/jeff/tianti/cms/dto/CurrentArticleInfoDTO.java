package com.jeff.tianti.cms.dto;

import java.util.Date;

/**
 * 当前文章信息封装
 * @author Jeff Xu
 * @since 2017-04-10
 */
public class CurrentArticleInfoDTO {
	
	//文章ID
	private String articleId;
	
	//文章所属栏目ID
	private String columnId;
	
	//时间
	private Date articleDate;
	
	//当前文章的排序
	private Integer orderNo;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public Date getArticleDate() {
		return articleDate;
	}

	public void setArticleDate(Date articleDate) {
		this.articleDate = articleDate;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

}
