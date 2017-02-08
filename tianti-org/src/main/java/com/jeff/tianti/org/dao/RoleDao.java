package com.jeff.tianti.org.dao;


import org.springframework.data.jpa.repository.Query;

import com.jeff.tianti.common.dao.CommonDao;
import com.jeff.tianti.org.entity.Role;

public interface RoleDao extends RoleCustomDao,CommonDao<Role,String>{

	@Query("from Role r where r.roleName = ?1 ")
	public Role findByRoleName(String roleName);
	
}
