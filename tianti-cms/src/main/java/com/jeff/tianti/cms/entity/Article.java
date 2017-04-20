package com.jeff.tianti.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jeff.tianti.common.entity.BaseEntity;
/**
 * 文章信息实体
 * @author Jeff Xu
 */
@Entity
@Table(name = "cms_article")
public class Article extends BaseEntity{

	private static final long serialVersionUID = -2268477115365746554L;
	
	//普通文章（内容自己发布,可以选择封面图片）
	public static final Integer ARTICLE_TYPE_COMMON = 0;
	
	//外链文章（内容直接跳走）
    public static final Integer ARTICLE_TYPE_HREF = 1;
    
    //广告位文章
    public static final Integer ARTICLE_TYPE_AD =2;
		
	//所属栏目(叶子栏目)
	private ColumnInfo columnInfo;
	
	//所属栏目(根栏目)
	private ColumnInfo rootColumnInfo;
	
	//文章类型
	private Integer type;
	
	//标题
	private String title;
	
	//内容
	private String content;
	
	//摘要
	private String summary;
	
	//来源
	private String sourceFrom;
	
	//发布者
	private String publisher;
	
	//外链
	private String href;
	
	//封面
	private String coverImageUrl;
	
	//是否置顶
	private Boolean isTop;
	
	//是否审核
	private Boolean isAudit;
	
	//阅读数
	private Integer viewCount;
	
	//排序
	private Integer orderNo;

	@ManyToOne
	@JoinColumn(name = "column_info_id")
	public ColumnInfo getColumnInfo() {
		return columnInfo;
	}

	public void setColumnInfo(ColumnInfo columnInfo) {
		this.columnInfo = columnInfo;
	}

	@ManyToOne
	@JoinColumn(name = "root_column_info_id")
	public ColumnInfo getRootColumnInfo() {
		return rootColumnInfo;
	}

	public void setRootColumnInfo(ColumnInfo rootColumnInfo) {
		this.rootColumnInfo = rootColumnInfo;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(length = 256)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 512)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "source_from",length = 64)
	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	@Column(length = 64)
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Column(length = 128)
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Column(name = "cover_image_url",length = 128)
	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	@Column(name = "is_top")
	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	@Column(name = "is_audit")
	public Boolean getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Boolean isAudit) {
		this.isAudit = isAudit;
	}

	@Column(name = "view_count")
	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	@Column(name = "order_no")
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

}
