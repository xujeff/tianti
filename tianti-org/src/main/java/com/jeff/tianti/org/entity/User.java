package com.jeff.tianti.org.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.jeff.tianti.common.entity.BaseEntity;

/**
 * 系统用户
 * @author Jeff Xu
 *
 */
@Entity
@Table(name = "org_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity{

	private static final long serialVersionUID = -8821121831372299051L;
	
	public final static Integer STATUS_YES = 1; //可用
	
	public final static Integer STATUS_NO = 0; //不可用
	
	public final static Integer USER_TYPE_INS = 1; //机构用户
	
	private String username;					//用户名称
	
	private String password;					//密码
	
	private String realName;					//真实姓名
	
	private String email;						//邮箱
	
	private String mobile;						//电话
	
	private Integer status; 					//状态

	private Set<Role> roles;					//拥有角色
	
	private Integer type;						//0 为管理员
	
	private String currentSkin;                 //当前皮肤
	
	@Column(name = "username",length=50)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password",length=32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "real_name",length=50)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}	

	@Column(length=30)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length=20)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name="status", precision = 1)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "org_user_role_rel", 
			joinColumns = {@JoinColumn(name = "user_id")}, 
			inverseJoinColumns = {@JoinColumn(name = "role_id")})
	@Where(clause="delete_flag=0")
	@OrderBy("no")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Column(name = "type", precision = 1, columnDefinition = "int default 0", nullable = false)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "current_skin",length = 16)
	public String getCurrentSkin() {
		return currentSkin;
	}

	public void setCurrentSkin(String currentSkin) {
		this.currentSkin = currentSkin;
	}

}