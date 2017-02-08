package com.jeff.tianti.org.dao;

import java.util.List;
import java.util.Map;

import com.jeff.tianti.common.dao.CustomBaseSqlDaoImpl;
import com.jeff.tianti.org.entity.Role;

public class RoleDaoImpl extends CustomBaseSqlDaoImpl implements RoleCustomDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findRoles(Map<String, Object> params) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select r from Role r ");
		
		if(params != null){
			StringBuilder sbWhere = new StringBuilder();
			Object name = params.get("name");
			if(name != null){
				sbWhere.append(" and r.name like :name ");
			}
			
			Object deleteFlag = params.get("deleteFlag");
			if(deleteFlag != null){
				sbWhere.append(" and r.deleteFlag = :deleteFlag ");
			}
			
			sb.append(sbWhere.toString().replaceFirst("and", "where"));
		}
		
		sb.append("order by r.no");
		
		return this.queryByMapParams(sb.toString(), params);
	}

	
	
}
