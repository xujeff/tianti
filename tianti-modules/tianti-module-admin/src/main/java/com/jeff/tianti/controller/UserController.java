package com.jeff.tianti.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeff.tianti.common.dto.AjaxResult;
import com.jeff.tianti.common.entity.PageModel;
import com.jeff.tianti.common.util.ExcelUtils;
import com.jeff.tianti.common.util.Md5Util;
import com.jeff.tianti.org.dto.RoleQueryDTO;
import com.jeff.tianti.org.dto.UserQueryDTO;
import com.jeff.tianti.org.entity.Resource;
import com.jeff.tianti.org.entity.Role;
import com.jeff.tianti.org.entity.User;
import com.jeff.tianti.org.service.ResourceService;
import com.jeff.tianti.org.service.RoleService;
import com.jeff.tianti.org.service.UserService;
import com.jeff.tianti.util.Constants;
import com.jeff.tianti.util.WebHelper;
/**
 * 用户管理Controller
 * @author Jeff Xu
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取用户列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model){
		String userName = request.getParameter("userName");
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		
		int currentPage = 1;
		int pageSize = 10;
		if(StringUtils.isNotBlank(currentPageStr)){
			currentPage = Integer.parseInt(currentPageStr);
		}
		if(StringUtils.isNotBlank(pageSizeStr)){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		UserQueryDTO userQueryDTO = new UserQueryDTO();
		userQueryDTO.setUserName(userName);
		userQueryDTO.setCurrentPage(currentPage);
		userQueryDTO.setPageSize(pageSize);
		
		PageModel<User> page = userService.queryUserPage(userQueryDTO);
		model.addAttribute("page", page);
		model.addAttribute("userQueryDTO", userQueryDTO);
		model.addAttribute(Constants.MENU_NAME, Constants.MENU_USER_LIST);
		
		return "user/user_list";
	}
	
	/**
	 * 导出用户数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/export")
	public String exportOrder(HttpServletRequest request, HttpServletResponse response){	
		String userName = request.getParameter("userName");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userName)){
			params.put("username", userName);
		}

		List<User> userList = this.userService.findUsers(params);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, String> headNameMap = new LinkedHashMap<String, String>();
		headNameMap.put("roleName", "角色");
		headNameMap.put("userName", "账号");
		headNameMap.put("realName", "姓名");
		headNameMap.put("mobile", "电话号码");
		headNameMap.put("createDate", "创建时间");
		headNameMap.put("status", "状态");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(userList != null && userList.size() > 0){
			for(User user : userList){
				String statusName = "正常";
				String createDate = "";
				String roleName = "";
				if(StringUtils.isNotBlank(user.getDeleteFlag()) && user.getDeleteFlag().equals(User.DELETE_FLAG_DELETED)){
					statusName = "删除";
				}
				
				if(user.getCreateDate() != null){
					createDate = sdf.format(user.getCreateDate());
				}
				
				Set<Role> roleSet = user.getRoles();
				if(roleSet != null && roleSet.size() > 0){
					for(Role r:roleSet){
						roleName +=r.getName()+" ";
					}
				}
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("roleName", roleName);
				map.put("userName", user.getUsername());
				map.put("realName", user.getRealName());
				map.put("mobile", user.getMobile());
				map.put("createDate", createDate);
				map.put("status", statusName);
				list.add(map);
			}
		}
		
		ExcelUtils.exportXlsx(response, "用户数据", headNameMap, list);
		return null;
	}
	
	/**
	 * 跳转到用户编辑页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/dialog/edit")
	public String dialogEdit(HttpServletRequest request, Model model){
		
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			User user = userService.find(id);
			model.addAttribute("user", user);
			
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", "0");
		List<Role> roles = roleService.findRoles(params);
		model.addAttribute("roles", roles);
		
		return "user/dialog/user_edit";
	}
	
	/**
	 * 用户保存操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajax/save")
	@ResponseBody
	public AjaxResult ajaxSave(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			
			String id = request.getParameter("id");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String realName = request.getParameter("realName");
			String mobile = request.getParameter("mobile");
			String[] roleIds = request.getParameterValues("roleId");
			
			
			User user = null;
			if(StringUtils.isNotBlank(id)){
				user = userService.find(id);
			}else{
				user = new User();
				user.setUsername(StringUtils.trim(username));
				user.setStatus(User.STATUS_YES);
			}
			
			if(StringUtils.isNotBlank(password)){
				user.setPassword(Md5Util.generatePassword(password));
			}
			
			user.setRealName(StringUtils.trim(realName));
			user.setMobile(StringUtils.trim(mobile));
			
			Set<Role> set = new HashSet<Role>();
			if(roleIds != null){
				for(String roleId : roleIds){
					Role role = roleService.find(roleId);
					if(role != null){
						set.add(role);
					}
				}
			}
			user.setRoles(set);
			
			if(StringUtils.isNotBlank(id)){
				userService.update(user);
			}else{
				user.setType(0);
				userService.save(user);
			}
			ajaxResult.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ajaxResult;
	}
	
	
	/**
	 * 修改用户状态
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajax/upd/status")
	@ResponseBody
	public AjaxResult ajaxUpdStatus(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			String[] ids = request.getParameterValues("ids");
			String status = request.getParameter("status");
			
			userService.updateStatus(ids, Integer.parseInt(status));
			
			ajaxResult.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ajaxResult;
	}
	
	/**
	 * 皮肤列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/skin/list")
	public String skinList(HttpServletRequest request,Model model){
		model.addAttribute(Constants.MENU_NAME, Constants.MENU_UPDATE_SKIN);
		return "/user/skin_list";
	}
	
	/**
	 * 修改皮肤
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajax/upd/skin")
	@ResponseBody
	public AjaxResult ajaxUpdSkin(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			String currentSkin = request.getParameter("skin");
			User user = (User)request.getSession().getAttribute(WebHelper.SESSION_LOGIN_USER);
			if(user != null){
				user.setCurrentSkin(currentSkin);
				this.userService.update(user);
				request.getSession().setAttribute(WebHelper.SESSION_LOGIN_USER, user);
				ajaxResult.setSuccess(true);
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	
	/**
	 * 校验用户是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajax/validator/username")
	@ResponseBody
	public Map<String, Object> ajaxValidatorUsername(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		
		String username = request.getParameter("username");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(username)){
			params.put("username", StringUtils.trim(username));
		}
		
		List<User> users = userService.findUsers(params);
		if(users != null && !users.isEmpty()){
			map.put("error", "账号已经存在");
		}else{
			map.put("ok", "");
		}
		
		return map;
	} 
	
	/**
	 * 角色列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/role_list")
	public String roleList(HttpServletRequest request, Model model){		
		
		String name = request.getParameter("name");
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		
		int currentPage = 1;
		int pageSize = 10;
		if(StringUtils.isNotBlank(currentPageStr)){
			currentPage = Integer.parseInt(currentPageStr);
		}
		if(StringUtils.isNotBlank(pageSizeStr)){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		RoleQueryDTO roleQueryDTO = new RoleQueryDTO();
		roleQueryDTO.setName(name);
		roleQueryDTO.setCurrentPage(currentPage);
		roleQueryDTO.setPageSize(pageSize);
		
		PageModel<Role> page = roleService.queryRolePage(roleQueryDTO);
		model.addAttribute("page", page);
		model.addAttribute("roleQueryDTO", roleQueryDTO);
		model.addAttribute(Constants.MENU_NAME, Constants.MENU_ROLE_LIST);
		
		return "user/role_list";
	}
	
	/**
	 * 跳转到角色编辑页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/dialog/role_edit")
	public String dialogRoleEdit(HttpServletRequest request, Model model){
		
		List<Map<String, Object>> resources = resourceService.getMap();
		
		String roleId = request.getParameter("id");
		if(StringUtils.isNotBlank(roleId)){
			Role role = roleService.find(roleId);
			model.addAttribute("role", role);
			
			Set<Resource> set = role.getResources();
			if(set != null && !set.isEmpty()){
				for(int i=0,size=resources.size();i<size;i++){
					Map<String, Object> map = resources.get(i);
					String id = map.get("id").toString();
					for(Resource resource : set){
						if(id.equals(resource.getId())){
							map.put("checked", true);
							map.put("open", true);
							break;
						}
					}
				}
			}
			
		}
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String resourceJson = objectMapper.writeValueAsString(resources);
			model.addAttribute("resourceJson", resourceJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "user/dialog/role_edit";
	}
	
	/**
	 * 保存角色
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajax/save_role")
	@ResponseBody
	public AjaxResult ajaxSaveRole(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			String[] rescoureIds = request.getParameterValues("rescoureIds");
			
			
			Role role = null;
			if(StringUtils.isNotBlank(id)){
				role = roleService.get(id);
			}else{
				role = new Role();
			}
			
			role.setName(StringUtils.trim(name));
			role.setDescription(StringUtils.trim(description));
			
			Set<Resource> resources = new HashSet<Resource>();
			if(rescoureIds != null){
				for(String rId : rescoureIds){
					Resource resource = resourceService.find(rId);
					if(resource != null){
						resources.add(resource);
					}
				}
			}
			role.setResources(resources);
			
			if(StringUtils.isNotBlank(role.getId())){
				roleService.update(role);
			}else{
				roleService.save(role);
			}
			
			ajaxResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ajaxResult;
	}
		

	/**
	 * 角色删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajax/upd_role/delete_flag")
	@ResponseBody
	public AjaxResult ajaxUpdRoleDeleteFlag(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			String[] ids = request.getParameterValues("ids");
			String deleteFlag = request.getParameter("deleteFlag");
			
			roleService.updateDeleteFlag(ids, deleteFlag);
			
			ajaxResult.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ajaxResult;
	}
	
	/**
	 * 跳转到菜单列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/menu_list")
	public String menuList(HttpServletRequest request, Model model){
		String name = request.getParameter("name");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(name)){
			params.put("name", "%" + StringUtils.trim(name) + "%");
		}
		model.addAttribute("name", name);
		
		List<Resource> resources = resourceService.findMenuResource(params);
		model.addAttribute("resources", resources);
		model.addAttribute(Constants.MENU_NAME, Constants.MENU_NAME_LIST);
		
		return "user/menu_list";
	}
	
	/**
	 * 逻辑操作菜单状态
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajax/upd_menu/delete_flag")
	@ResponseBody
	public AjaxResult ajaxUpdMenuDeleteFlag(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			String[] ids = request.getParameterValues("ids");
			String deleteFlag = request.getParameter("deleteFlag");
			
			resourceService.updateDeleteFlag(ids, deleteFlag);
			
			ajaxResult.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ajaxResult;
	}
	
	/**
	 * 跳转到菜单编辑页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/dialog/menu_edit")
	public String dialogMenuEdit(HttpServletRequest request, Model model){
		
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Resource resource = resourceService.find(id);
			model.addAttribute("resource", resource);
		}
		
		List<Resource> modelResources = resourceService.getRootResourceList();
		model.addAttribute("modelResources", modelResources);
		
		return "user/dialog/menu_edit";
	}
	
	/**
	 * 保存菜单
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajax/save_menu")
	@ResponseBody
	public AjaxResult ajaxSaveMenu(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			String parentId = request.getParameter("parentId");
			String url = request.getParameter("url");
			String icon = request.getParameter("icon");
			String orderNoStr = request.getParameter("orderNo");
			Integer orderNo = null;
			if(StringUtils.isNotBlank(orderNoStr)){
				orderNo = Integer.parseInt(orderNoStr);
			}
			
			Resource resource = null;
			if(StringUtils.isNotBlank(id)){
				resource = resourceService.find(id);
			}else{
				resource = new Resource();
			}
			resource.setName(StringUtils.trim(name));
			resource.setType(type);
			Resource parentResource = null;
			if(StringUtils.isNotBlank(parentId) && "page".equals(type)){
				parentResource = resourceService.find(parentId);
			}
			resource.setParent(parentResource);
			resource.setUrl(StringUtils.trim(url));
			resource.setIcon(StringUtils.trim(icon));
			resource.setOrderNo(orderNo);
			
			resourceService.saveResource(resource);
			
			ajaxResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ajaxResult;
	}
	
	/**
	 * 跳转到修改密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/update_pwd")
	public String updatePwd(HttpServletRequest request, Model model){
		
		model.addAttribute(Constants.MENU_NAME, Constants.MENU_UPDATE_PWD);
		
		return "user/update_pwd";
	}
	
	/**
	 * 保存密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajax/save_pwd")
	@ResponseBody
	public AjaxResult ajaxSavePwd(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			
			User user = WebHelper.getUser(request);
			
			String oldPwd = request.getParameter("oldPwd");
			String pwd = request.getParameter("pwd");
			
			user = userService.find(user.getId());
			if(Md5Util.generatePassword(oldPwd).equals(user.getPassword())){
				
				user.setPassword(Md5Util.generatePassword(pwd));
				userService.update(user);
				
				ajaxResult.setSuccess(true);
			}else{
				ajaxResult.setMsg("原始密码输入不正确");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setMsg("修改失败");
		}
		
		return ajaxResult;
	}
	
}
