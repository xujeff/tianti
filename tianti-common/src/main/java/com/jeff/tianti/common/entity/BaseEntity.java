package com.jeff.tianti.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.lang3.StringUtils;

/**
 * 定义实体的基础公共属性，所有实体都会继承.
 * @author Jeff Xu
 * @since 2015-12-09
 */
@MappedSuperclass
public class BaseEntity extends MysqlSequenceIdEntity implements Serializable{

	private static final long serialVersionUID = -4498233384948128317L;

	//逻辑删除标识位—已删除状态
	public static final String DELETE_FLAG_DELETED = "1";
	
	//逻辑删除标识位—未删除状态
	public static final String DELETE_FLAG_NORMAL = "0";
	
	//审核状态标识位—未审核
	public static final String AUDIT_FLAG_NOT = "0";
	
	//审核状态标识位—审核通过
	public static final String AUDIT_FLAG_PASS = "1";
	
	//审核状态标识位—审核通不过
	public static final String AUDIT_FLAG_FAIL = "2";
	
	/**
	 * 创建日期
	 */
	protected Date createDate;
	/**
	 * 修改日期
	 */
	protected Date updateDate;
	
	/**
	 * 删除标志(0-正常，1-删除)
	 * @return
	 */
	protected String deleteFlag;
	
	/**
	 * 审核状态(0-未审核，1-审核通过，2-审核不通过)
	 * @return
	 */
	protected String auditFlag;
	
	@Column(name = "create_date", updatable = false)
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Column(name = "delete_flag",length=1)
	public String getDeleteFlag() {
		return deleteFlag;
	}
	
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "audit_flag",length=2)
	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) object;
			if (this.getId() == null || baseEntity.getId() == null) {
				return false;
			} else {
				return (this.getId().equals(baseEntity.getId()));
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id == null ? System.identityHashCode(this) : (this.getClass().getName() + this.getId()).hashCode();
	}
	
	@PrePersist
    public void prePersist() {
		if(this.createDate == null){
			this.setCreateDate(new Date());
		}
		this.setUpdateDate(new Date());
		if(StringUtils.isBlank(this.getDeleteFlag())){
			this.setDeleteFlag(BaseEntity.DELETE_FLAG_NORMAL);	
		}
    }
 
    @PreUpdate
    public void preUpdate() {
    	this.setUpdateDate(new Date());
    }
	
}
