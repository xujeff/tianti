package com.jeff.tianti.org.dao;


import java.util.List;
import java.util.Map;

import com.jeff.tianti.org.entity.User;

public interface SystemUserDao {
	
	public List<User> findUsers(Map<String, Object> params);
	
}
