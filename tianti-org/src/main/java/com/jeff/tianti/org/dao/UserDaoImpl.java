package com.jeff.tianti.org.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jeff.tianti.common.dao.CustomBaseSqlDaoImpl;
import com.jeff.tianti.common.entity.PageModel;
import com.jeff.tianti.org.dto.UserQueryDTO;
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
	
	/**
	 * 根据用户信息查询分页信息
	 * @param userQueryDTO
	 * @return
	 */
	public PageModel<User> queryUserPage(UserQueryDTO userQueryDTO){
		Map<String,Object> params = new HashMap<String,Object>();
		StringBuilder hql = new StringBuilder();
		hql.append(" select u from User u where 1=1 ");
		if(StringUtils.isNotBlank(userQueryDTO.getUserName())){
			hql.append(" and u.username like :username ");
			params.put("username", "%"+userQueryDTO.getUserName()+"%");
		}
		hql.append(" order by u.createDate desc ");
		return this.queryForPageWithParams(hql.toString(), params, userQueryDTO.getCurrentPage(), userQueryDTO.getPageSize());
	}
	
	
}
