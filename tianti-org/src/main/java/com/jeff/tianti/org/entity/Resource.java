package com.jeff.tianti.org.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jeff.tianti.common.entity.BaseEntity;
/**
 * 资源
 * @author Jeff Xu
 *
 */
@Entity
@Table(name = "org_resource")
public class Resource extends BaseEntity{

	private static final long serialVersionUID = -8560521657225182602L;
	
	//模块资源
	public final static String RESOURCE_TYPE_MODULE = "module";
	
	//页面资源
	public final static String RESOURCE_TYPE_PAGE = "page";
	
	//按钮资源
	public final static String RESOURCE_TYPE_BUTTON = "button";
		
	private String name;
	
	private Resource parent;
	
	private String type;
	
	private String url;
	
	private String icon;				//菜单按钮
	
	private Integer orderNo;			//排序

	private List<Resource> children;	//子菜单
	
	@Column(length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	public Resource getParent() {
		return parent;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	@Column(length=10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(length=100)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "icon", length = 512)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "order_no")
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Transient
	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}
	
}
