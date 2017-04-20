package com.jeff.tianti.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jeff.tianti.common.entity.BaseEntity;
/**
 * 栏目信息实体
 * @author Jeff Xu
 */
@Entity
@Table(name = "cms_column_info")
public class ColumnInfo extends BaseEntity{

	private static final long serialVersionUID = 8427391463279205799L;
	
	//用于PC渠道
	public static final Integer CHANNEL_PC = 0;
	
	//用于H5渠道
	public static final Integer CHANNEL_H5 = 1;
	
	//顶级
	public static final Integer LEVEL_ROOT = 0;
	
	//叶子级
	public static final Integer LEVEL_LEAF = 1;
	
	//编码（一个机构里面必须是唯一的）
	private String code;
	
	//栏目icon
	private String icon;
	
	//名称
	private String name;
	
	//所属层级
	private Integer level;
	
	//绝对路径
	private String path;
	
	//父级栏目
	private ColumnInfo parent;
	
	//渠道
	private Integer channel;
	
	//排序
	private Integer orderNo;

	@Column(length = 20)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(length = 128)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(length = 32)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(length = 512)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public ColumnInfo getParent() {
		return parent;
	}

	public void setParent(ColumnInfo parent) {
		this.parent = parent;
	}

	@Column(name = "channel")
	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	@Column(name = "order_no")
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

}
