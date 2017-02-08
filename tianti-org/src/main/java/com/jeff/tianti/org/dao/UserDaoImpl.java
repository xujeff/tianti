package com.jeff.tianti.org.dao;

import java.util.List;
import java.util.Map;

import com.jeff.tianti.common.dao.CustomBaseSqlDaoImpl;
import com.jeff.tianti.org.entity.User;

public class UserDaoImpl extends CustomBaseSqlDaoImpl implements SystemUserDao{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsers(Map<String, Object> params) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select u from User u where u.deleteFlag = 0 and u.type = 0 ");
		
		Object realName = params.get("realName");
		if(realName != null){
			sb.append(" and u.realName like :realName ");
		}
		
		Object username = params.get("username");
		if(username != null){
			sb.append(" and u.username = :username ");
		}
		
		sb.append("order by u.createDate desc");
		
		return this.queryByMapParams(sb.toString(), params);
	}
	
	
}
