package com.jeff.tianti.org.dao;

import java.util.List;
import java.util.Map;

import com.jeff.tianti.org.entity.Role;

public interface RoleCustomDao {
	
	public List<Role> findRoles(Map<String, Object> params);
	
}
