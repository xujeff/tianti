package com.jeff.tianti.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeff.tianti.common.util.Md5Util;
import com.jeff.tianti.org.entity.Resource;
import com.jeff.tianti.util.WebHelper;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/do_login")
	public String doLogin(HttpServletRequest request, Model model){
		
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		
		boolean rememberMe = false;
		
		String md5Pwd = Md5Util.generatePassword(pwd);
		
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, md5Pwd, rememberMe);
			
			Subject subject = SecurityUtils.getSubject();
			
			subject.login(token);
			
			//跳转第一个菜单
			List<Resource> hasResource = (List<Resource>) request.getSession().getAttribute(WebHelper.SESSION_MENU_RESOURCE);
			if(hasResource != null && !hasResource.isEmpty()){
				for(Resource resource : hasResource){
					
					List<Resource> chResources = resource.getChildren();
					if(StringUtils.isNotBlank(resource.getUrl()) && (chResources == null || chResources.isEmpty())){
						return "redirect:" + resource.getUrl();
					}
					if(chResources != null && !chResources.isEmpty()){
						for(Resource chRes : chResources){
							if(StringUtils.isNotBlank(chRes.getUrl())){
								return "redirect:" + chRes.getUrl();
							}
						}
					}
				}
			}
			
			return "redirect:/user/list";
		} catch (LockedAccountException lae) {
//			lae.printStackTrace();
			model.addAttribute("msg", "账号已被禁用");
		} catch (AuthenticationException ae) {
//			ae.printStackTrace();
			model.addAttribute("msg", "账号或密码错误");
		} catch (Exception e) {
//			e.printStackTrace();
			model.addAttribute("msg", "登录异常");
		}
		
		return "login";
	}
	
	@RequestMapping("/login_out")
	public String loginOut(HttpServletRequest request){
		
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		
		return "redirect:/login";
	}

}
