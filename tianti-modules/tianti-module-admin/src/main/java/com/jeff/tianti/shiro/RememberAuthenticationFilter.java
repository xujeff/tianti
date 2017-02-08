package com.jeff.tianti.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class RememberAuthenticationFilter extends FormAuthenticationFilter{
	
	/**
	 * 判断是否让用户登陆
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		
		Subject subject = getSubject(request, response);
		
		return subject.isAuthenticated() || subject.isRemembered();
	}

}
