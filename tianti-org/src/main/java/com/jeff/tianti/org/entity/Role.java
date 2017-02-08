package com.jeff.tianti.org.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.jeff.tianti.common.entity.BaseEntity;

/**
 * 角色管理
 * @author Jianfang Xu
 *
 */
@Entity
@Table(name = "org_role")
public class Role extends BaseEntity{
	
private static final long serialVersionUID = 8160747791143322423L;
	
	private Integer no;
	
	private String name;
	
	private String description;
	
	private Integer status;

	private String code;						
	
	private Set<Resource> resources;			//拥有资源 
	
	private String roleName;					//shiro中name
	
	@Column(length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	@Column(length = 500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "org_role_resource_rel", 
		joinColumns = {@JoinColumn(name = "role_id")},
		inverseJoinColumns = {@JoinColumn(name = "resources_id")})
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@Column(name = "no")
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	@Column(name = "code", length = 128)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="role_name", length = 64)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
