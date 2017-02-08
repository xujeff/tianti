package com.jeff.tianti.org.dao;

import java.util.List;
import java.util.Map;

import com.jeff.tianti.org.entity.Resource;


public interface ResourceDaoCustom {

	public List<Resource> findMenuResource(Map<String, Object> params);
	
}
