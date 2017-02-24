package com.jeff.tianti.org.dto;
import com.jeff.tianti.common.dto.CommonQueryDTO;
/**
 * 后台用户查询信息封装
 * @author Jeff Xu
 * @since 2016-01-20
 */
public class UserQueryDTO extends CommonQueryDTO{
	
	//账号
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
