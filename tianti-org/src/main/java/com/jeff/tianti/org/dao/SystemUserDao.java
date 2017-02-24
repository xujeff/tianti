package com.jeff.tianti.org.dao;


import java.util.List;
import java.util.Map;

import com.jeff.tianti.common.entity.PageModel;
import com.jeff.tianti.org.dto.UserQueryDTO;
import com.jeff.tianti.org.entity.User;

public interface SystemUserDao {
	
	public List<User> findUsers(Map<String, Object> params);
	
	/**
	 * 根据用户信息查询分页信息
	 * @param userQueryDTO
	 * @return
	 */
	PageModel<User> queryUserPage(UserQueryDTO userQueryDTO);
	
}
