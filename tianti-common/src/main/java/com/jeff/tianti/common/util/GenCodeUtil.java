package com.jeff.tianti.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Dao、Service层代码自动生成工具类
 * @author JeffXu 
 * @since 2015-12-09
 */
public class GenCodeUtil {
	
	//公共部分
		private static final String RT_1 = "\r\n";
		private static final String RT_2 = RT_1+RT_1;
		private static final String BLANK_1 =" ";
		
		//注释部分
		private static final String ANNOTATION_AUTHOR_PARAMTER = "@author ";
		private static final String ANNOTATION_DESC = "@desc ";
		private static final String ANNOTATION_DATE = "@date ";
		
		/**
		 * 创建查询信息封装DTO(用于后台的查询信息封装)
		 * @param c实体类
		 * @param commonPackage 基础包：如com.jeff.tianti.info
		 * @param author 作者
		 * @param desc 描述
		 * @throws IOException
		 */
		public static void createQueryDTO(Class c,String commonPackage,String author) throws IOException{
			String cName = c.getName();
			String dtoPath = "";
			if(author == null || author.equals("")){
				author = "Administrator";
			}
			if(commonPackage != null && !commonPackage.equals("")){
				dtoPath = commonPackage.replace(".", "/");
				String fileName = System.getProperty("user.dir") + "/src/main/java/" + dtoPath+"/dto"
						+ "/" + getLastChar(cName) + "QueryDTO.java";
				File f = new File(fileName);
				FileWriter fw = new FileWriter(f);
				fw.write("package "+commonPackage+".dto"+";"+RT_2+"import com.jeff.tianti.common.dto.CommonQueryDTO;"+RT_2
						+"/**"+RT_1+BLANK_1+"*"+BLANK_1+ANNOTATION_AUTHOR_PARAMTER+ author +RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DESC +getLastChar(cName)+"QueryDTO"+BLANK_1+RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DATE +getDate()+RT_1+BLANK_1+"*/"+RT_1
						+"public class " +getLastChar(cName) +"QueryDTO extends CommonQueryDTO{"+RT_2+"}");
				fw.flush();
				fw.close();
				showInfo(fileName);
			}else{
				System.out.println("创建"+getLastChar(cName)+"QueryDTO失败，原因是commonPackage为空！");
			}
		}
		
		/**
		 * 创建查询信息封装DTO(用于前台的查询信息封装)
		 * @param c实体类
		 * @param commonPackage 基础包：如com.jeff.tianti.info
		 * @param author 作者
		 * @param desc 描述
		 * @throws IOException
		 */
		public static void createFrontQueryDTO(Class c,String commonPackage,String author) throws IOException{
			String cName = c.getName();
			String dtoPath = "";
			if(author == null || author.equals("")){
				author = "Administrator";
			}
			if(commonPackage != null && !commonPackage.equals("")){
				dtoPath = commonPackage.replace(".", "/");
				String fileName = System.getProperty("user.dir") + "/src/main/java/" + dtoPath+"/dto"
						+ "/" + getLastChar(cName) + "FrontQueryDTO.java";
				File f = new File(fileName);
				FileWriter fw = new FileWriter(f);
				fw.write("package "+commonPackage+".dto"+";"+RT_2+"import com.jeff.tianti.common.dto.CommonQueryDTO;"+RT_2
						+"/**"+RT_1+BLANK_1+"*"+BLANK_1+ANNOTATION_AUTHOR_PARAMTER+ author +RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DESC +getLastChar(cName)+"QueryDTO"+BLANK_1+RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DATE +getDate()+RT_1+BLANK_1+"*/"+RT_1
						+"public class " +getLastChar(cName) +"FrontQueryDTO extends CommonQueryDTO{"+RT_2+"}");
				fw.flush();
				fw.close();
				showInfo(fileName);
			}else{
				System.out.println("创建"+getLastChar(cName)+"FrontQueryDTO失败，原因是commonPackage为空！");
			}
		}

		/**
		 * 创建Dao接口
		 * @param c实体类
		 * @param commonPackage 基础包：如com.jeff.tianti.info
		 * @param author 作者
		 * @param desc 描述
		 * @throws IOException
		 */
		public static void createDaoInterface(Class c,String commonPackage,String author) throws IOException{
			String cName = c.getName();
			String daoPath = "";
			if(author == null || author.equals("")){
				author = "Administrator";
			}
			if(commonPackage != null && !commonPackage.equals("")){
				daoPath = commonPackage.replace(".", "/");
				String fileName = System.getProperty("user.dir") + "/src/main/java/" + daoPath+"/dao"
						+ "/" + getLastChar(cName) + "Dao.java";
				File f = new File(fileName);
				FileWriter fw = new FileWriter(f);
				fw.write("package "+commonPackage+".dao"+";"+RT_2+"import "+commonPackage+".entity"+"."+getLastChar(cName)+";"+RT_1+"import com.jeff.tianti.common.dao.CommonDao;"+RT_2
						+"/**"+RT_1+BLANK_1+"*"+BLANK_1+ANNOTATION_AUTHOR_PARAMTER+ author +RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DESC +getLastChar(cName)+"Dao接口"+BLANK_1+RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DATE +getDate()+RT_1+BLANK_1+"*/"+RT_1
						+"public interface " +getLastChar(cName) +"Dao extends "+getLastChar(cName)+"DaoCustom,CommonDao<"+getLastChar(cName)+",String>{"+RT_2+"}");
				fw.flush();
				fw.close();
				showInfo(fileName);
			}else{
				System.out.println("创建Dao接口失败，原因是commonPackage为空！");
			}
		}
		
		/**
		 * 创建DaoCustom接口
		 * @param c实体类
		 * @param commonPackage 基础包：如com.jeff.tianti.info
		 * @throws IOException
		 */
		public static void createDaoCustomInterface(Class c,String commonPackage,String author) throws IOException{
			String cName = c.getName();
			String daoPath = "";
			if(author == null || author.equals("")){
				author = "Administrator";
			}
			if(commonPackage != null && !commonPackage.equals("")){
				daoPath = commonPackage.replace(".", "/");
				String fileName = System.getProperty("user.dir") + "/src/main/java/" + daoPath+"/dao"
						+ "/" + getLastChar(cName) + "DaoCustom.java";
				File f = new File(fileName);
				FileWriter fw = new FileWriter(f);
				fw.write("package "+commonPackage+".dao"+";"+RT_2
						+"import com.jeff.tianti.common.entity.PageModel;"+RT_1
						+"import java.util.List;"+RT_1
						+"import "+commonPackage+".entity"+"."+getLastChar(cName)+";"+RT_1
						+"import "+commonPackage+".dto"+"."+getLastChar(cName)+"QueryDTO;"+RT_1
						+"/**"+RT_1+BLANK_1+"*"+BLANK_1+ANNOTATION_AUTHOR_PARAMTER+ author +RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DESC +getLastChar(cName)+"DaoCustom接口"+BLANK_1+RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DATE +getDate()+RT_1+BLANK_1+"*/"+RT_1
						+"public interface " +getLastChar(cName) +"DaoCustom {"+RT_2
						+"      PageModel<"+getLastChar(cName)+"> query"+getLastChar(cName)+"Page("+getLastChar(cName)+"QueryDTO "+getFirstLowercase(cName)+"QueryDTO);"+RT_2
						+"      List<"+getLastChar(cName)+"> query"+getLastChar(cName)+"List("+getLastChar(cName)+"QueryDTO "+getFirstLowercase(cName)+"QueryDTO);"+RT_2
						+RT_2+"}");
				fw.flush();
				fw.close();
				showInfo(fileName);
			}else{
				System.out.println("创建DaoCustom接口失败，原因是commonPackage为空！");
			}
			
		}
		
		/**
		 * 创建Dao类
		 * @param c实体类
		 * @param commonPackage 基础包：如com.jeff.tianti.info
		 * @throws IOException
		 */
		public static void createDaoClass(Class c,String commonPackage,String author) throws IOException{
			String cName = c.getName();
			String daoPath = "";
			if(author == null || author.equals("")){
				author = "Administrator";
			}
			if(commonPackage != null && !commonPackage.equals("")){
				daoPath = commonPackage.replace(".", "/");
				String fileName = System.getProperty("user.dir") + "/src/main/java/" + daoPath+"/dao"
						+ "/" + getLastChar(cName) + "DaoImpl.java";
				File f = new File(fileName);
				FileWriter fw = new FileWriter(f);
				fw.write("package "+commonPackage+".dao"+";"+RT_2+"import com.jeff.tianti.common.dao.CustomBaseSqlDaoImpl;"+RT_1
						+"import com.jeff.tianti.common.entity.PageModel;"+RT_1
						+"import java.util.HashMap;"+RT_1
						+"import java.util.List;"+RT_1
						+"import java.util.Map;"+RT_1
						+"import "+commonPackage+".entity"+"."+getLastChar(cName)+";"+RT_1
						+"import "+commonPackage+".dto"+"."+getLastChar(cName)+"QueryDTO;"+RT_1
						+"/**"+RT_1+BLANK_1+"*"+BLANK_1+ANNOTATION_AUTHOR_PARAMTER+ author +RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DESC +getLastChar(cName)+"DaoImpl类"+BLANK_1+RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DATE +getDate()+RT_1+BLANK_1+"*/"+RT_2
						+"public class " +getLastChar(cName) +"DaoImpl extends CustomBaseSqlDaoImpl implements " +getLastChar(cName) +"DaoCustom  {"+RT_2
						+"    public PageModel<"+getLastChar(cName)+"> query"+getLastChar(cName)+"Page("+getLastChar(cName)+"QueryDTO "+getFirstLowercase(cName)+"QueryDTO){"+RT_1
						+"         Map<String,Object> map = new HashMap<String,Object>();"+RT_1
						+"         StringBuilder hql = new StringBuilder();"+RT_1
						+"         hql.append(\"select t from "+getLastChar(cName)+" t \");"+RT_1
						+"         return this.queryForPageWithParams(hql.toString(),map,"+getFirstLowercase(cName)+"QueryDTO.getCurrentPage(),"+getFirstLowercase(cName)+"QueryDTO.getPageSize());"+RT_1
						+"    }"+RT_2
						+"    public List<"+getLastChar(cName)+"> query"+getLastChar(cName)+"List("+getLastChar(cName)+"QueryDTO "+getFirstLowercase(cName)+"QueryDTO){"+RT_1
						+"         Map<String,Object> map = new HashMap<String,Object>();"+RT_1
						+"         StringBuilder hql = new StringBuilder();"+RT_1
						+"         hql.append(\"select t from "+getLastChar(cName)+" t \");"+RT_1
						+"         return this.queryByMapParams(hql.toString(),map);"+RT_1
						+"    }"+RT_1
						+RT_2+"}");
				fw.flush();
				fw.close();
				showInfo(fileName);
			}else{
				System.out.println("创建DaoImpl接口失败，原因是commonPackage为空！");
			}
		}
		
		/**
		 * 创建service类
		 * @param c实体类
		 * @param commonPackage 基础包：如com.jeff.tianti.info
		 * @throws IOException
		 */
		public static void createServiceClass(Class c,String commonPackage,String author) throws IOException{
			String cName = c.getName();
			String servicePath = "";
			if(author == null || author.equals("")){
				author = "Administrator";
			}
			if(commonPackage != null && !commonPackage.equals("")){
				servicePath = commonPackage.replace(".", "/");
				String fileName = System.getProperty("user.dir") + "/src/main/java/" + servicePath+"/service"
						+ "/" + getLastChar(cName) + "Service.java";
				File f = new File(fileName);
				FileWriter fw = new FileWriter(f);
				fw.write("package "+commonPackage+".service"+";"+RT_2+"import "+commonPackage+".entity"+"."+getLastChar(cName)+";"+RT_1
						+"import "+commonPackage+".dao"+"."+getLastChar(cName)+"Dao;"+RT_1
						+"import "+commonPackage+".dto"+"."+getLastChar(cName)+"QueryDTO;"+RT_1
						+"import com.jeff.tianti.common.service.CommonService;"+RT_1
						+"import com.jeff.tianti.common.entity.PageModel;"+RT_1
						+"import org.springframework.stereotype.Service;"+RT_1
						+"import java.util.List;"+RT_1
						+"import org.springframework.beans.factory.annotation.Autowired;"+RT_1
						+"/**"+RT_1+BLANK_1+"*"+BLANK_1+ANNOTATION_AUTHOR_PARAMTER+ author +RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DESC +getLastChar(cName)+"Service类"+BLANK_1+RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DATE +getDate()+RT_1+BLANK_1+"*/"+RT_1
						+"@Service"+RT_1+"public class " +getLastChar(cName) +"Service extends CommonService< " +getLastChar(cName) +",String >  {"+
						RT_2+
						"    @Autowired"+RT_1+"    private "+getLastChar(cName)+"Dao "+getFirstLowercase(cName)+"Dao;"+RT_2+
						"    @Autowired"+RT_1+"    public void set"+getLastChar(cName)+"Dao("+getLastChar(cName)+"Dao "+getFirstLowercase(cName)+"Dao){"+RT_1+
						"      super.setCommonDao("+getFirstLowercase(cName)+"Dao);"+RT_1+"    }"+RT_2+
						"    public PageModel<"+getLastChar(cName)+"> query"+getLastChar(cName)+"Page("+getLastChar(cName)+"QueryDTO "+getFirstLowercase(cName)+"QueryDTO){"+RT_1+
						"           return this."+getFirstLowercase(cName)+"Dao.query"+getLastChar(cName)+"Page("+getFirstLowercase(cName)+"QueryDTO);"+RT_1+
						"    }"+RT_2+
						"    public List<"+getLastChar(cName)+"> query"+getLastChar(cName)+"List("+getLastChar(cName)+"QueryDTO "+getFirstLowercase(cName)+"QueryDTO){"+RT_1+
						"           return this."+getFirstLowercase(cName)+"Dao.query"+getLastChar(cName)+"List("+getFirstLowercase(cName)+"QueryDTO);"+RT_1+
						"    }"+RT_1+
						RT_2+"}");
				fw.flush();
				fw.close();
				showInfo(fileName);
			}else{
				System.out.println("创建Service接口失败，原因是commonPackage为空！");
			}
		}
		
		/**
		 * 创建Controller层的代码
		 * @param c实体类
		 * @param commonPackage  基础包：如com.jeff.tianti.info
		 * @param controllerPackage Controller包如com.jeff.tianti.kaoyan.web
		 * @param author 作者
		 * @param isBackground 是否后台
		 * @throws IOException
		 */
		public static void createControllerClass(Class c,String commonPackage,String controllerPackage,String author,Boolean isBackground) throws IOException{
			String cName = c.getName();
			String controllerPath = "";
			if(author == null || author.equals("")){
				author = "Administrator";
			}
			if(commonPackage != null && !commonPackage.equals("")){
				//引入包字符串
				String importStr = "";
				//查询字符串
				String queryInfoStr = "";
				//UrlMapping定义
				String requestMappingStr = "";
				if(isBackground){
					importStr = "import "+commonPackage+".dto"+"."+getLastChar(cName)+"QueryDTO;"+RT_1;
					queryInfoStr = "       "+getLastChar(cName)+"QueryDTO "+getFirstLowercase(cName)+"QueryDTO = new "+getLastChar(cName)+"QueryDTO(); "+RT_1
							+"       "+getFirstLowercase(cName)+"QueryDTO.setCurrentPage(currentPage);"+RT_1
							+"       "+getFirstLowercase(cName)+"QueryDTO.setPageSize(pageSize);"+RT_1
							+"       PageModel<"+getLastChar(cName)+"> page = this."+getFirstLowercase(cName)+"Service.query"+getLastChar(cName)+"Page("+getFirstLowercase(cName)+"QueryDTO);"+RT_1;
					requestMappingStr = "@RequestMapping(\"/boss/"+getFirstLowercase(cName)+"\")"+RT_1;
				}else{
					importStr = "import "+commonPackage+".dto"+"."+getLastChar(cName)+"FrontQueryDTO;"+RT_1;
					queryInfoStr = "       "+getLastChar(cName)+"FrontQueryDTO "+getFirstLowercase(cName)+"FrontQueryDTO = new "+getLastChar(cName)+"FrontQueryDTO(); "+RT_1
							+"       "+getFirstLowercase(cName)+"FrontQueryDTO.setCurrentPage(currentPage);"+RT_1
							+"       "+getFirstLowercase(cName)+"FrontQueryDTO.setPageSize(pageSize);"+RT_1
							+"       PageModel<"+getLastChar(cName)+"> page = this."+getFirstLowercase(cName)+"Service.query"+getLastChar(cName)+"FrontPage("+getFirstLowercase(cName)+"FrontQueryDTO);"+RT_1;
					requestMappingStr = "@RequestMapping(\"/"+getFirstLowercase(cName)+"\")"+RT_1;
				}
				controllerPath = controllerPackage.replace(".", "/");
				String fileName = System.getProperty("user.dir") + "/src/main/java/" + controllerPath+""
						+ "/" + getLastChar(cName) + "Controller.java";
				File f = new File(fileName);
				FileWriter fw = new FileWriter(f);
				
				fw.write("package "+controllerPackage+";"+RT_2
						+"import "+commonPackage+".entity"+"."+getLastChar(cName)+";"+RT_1
						+"import "+commonPackage+".service"+"."+getLastChar(cName)+"Service;"+RT_1
						+"import javax.servlet.http.HttpServletRequest; "+RT_1
						+"import org.apache.commons.lang3.StringUtils; "+RT_1
						+"import java.util.Date; "+RT_1
						+"import org.springframework.beans.factory.annotation.Autowired; "+RT_1
						+"import org.springframework.stereotype.Controller; "+RT_1
						+"import org.springframework.ui.Model; "+RT_1
						+"import org.springframework.web.bind.annotation.RequestMapping; "+RT_1
						+"import com.jeff.tianti.common.entity.PageModel; "+RT_1
						+importStr
						+"/**"+RT_1+BLANK_1+"*"+BLANK_1+ANNOTATION_AUTHOR_PARAMTER+ author +RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DESC +getLastChar(cName)+"Controller"+BLANK_1+RT_1
						+BLANK_1+"*"+BLANK_1+ANNOTATION_DATE +getDate()+RT_1+BLANK_1+"*/"+RT_1
						+"@Controller"+RT_1
						+requestMappingStr
						+"public class " +getLastChar(cName) +"Controller{"+RT_2
						+"    @Autowired"+RT_1
						+"    private "+getLastChar(cName)+"Service "+getFirstLowercase(cName)+"Service;"+RT_2
						+"    @RequestMapping(\"/list\")"+RT_1
						+"    public String list(HttpServletRequest request,Model model){"+RT_1
						+"       String currentPageStr = request.getParameter(\"currentPage\");"+RT_1
						+"       String pageSizeStr = request.getParameter(\"pageSize\");"+RT_1
						+"       int currentPage = 1;"+RT_1
						+"       int pageSize = 10;"+RT_1
						+"       if(StringUtils.isNotBlank(currentPageStr)){"+RT_1
						+"             currentPage = Integer.parseInt(currentPageStr);"+RT_1
						+"       }"+RT_1
						+"       if(StringUtils.isNotBlank(pageSizeStr)){"+RT_1
						+"             pageSize = Integer.parseInt(pageSizeStr);"+RT_1
						+"       }"+RT_1
						+queryInfoStr
						+"       model.addAttribute(\"page\", page);"+RT_2
						+"       return \"/"+getFirstLowercase(cName)+"/list\";"+RT_1
						+"    }"+RT_2
						+"    @RequestMapping(\"/add\")"+RT_1
						+"    public String add(HttpServletRequest request,Model model){"+RT_1
						+"        String id = request.getParameter(\"id\");"+RT_1
						+"        "+getLastChar(cName)+" "+getFirstLowercase(cName)+" = null;"+RT_1
						+"        if(StringUtils.isNotBlank(id)){"+RT_1
						+"             "+getFirstLowercase(cName)+" = this."+getFirstLowercase(cName)+"Service.find(id);"+RT_1
						+"        }"+RT_1
						+"        model.addAttribute(\""+getFirstLowercase(cName)+"\", "+getFirstLowercase(cName)+");"+RT_1
						+"        model.addAttribute(\"id\", id);"+RT_1
						+"        return \"/"+getFirstLowercase(cName)+"/add\";"+RT_1
						+"    }"+RT_2
						+"    @RequestMapping(\"/saveorupdate\")"+RT_1
						+"    public String doSaveOrUpdate(HttpServletRequest request){"+RT_1
						+"        String id = request.getParameter(\"id\");"+RT_1
						+"        "+getLastChar(cName)+" "+getFirstLowercase(cName)+" = null;"+RT_1
						+"        if(StringUtils.isNotBlank(id)){"+RT_1
						+"             "+getFirstLowercase(cName)+" = this."+getFirstLowercase(cName)+"Service.find(id);"+RT_1
						+"             "+getFirstLowercase(cName)+".setUpdateDate(new Date());"+RT_1
						+"             this."+getFirstLowercase(cName)+"Service.update("+getFirstLowercase(cName)+");"+RT_1
						+"        }else{"+RT_1
						+"             "+getFirstLowercase(cName)+" = new "+getLastChar(cName)+"();"+RT_1
						+"             "+getFirstLowercase(cName)+".setCreateDate(new Date());"+RT_1
						+"             this."+getFirstLowercase(cName)+"Service.save("+getFirstLowercase(cName)+");"+RT_1
						+"        }"+RT_1
						+"        return \"redirect:/"+getFirstLowercase(cName)+"/list\";"+RT_1
						+"    }"+RT_2
						+"    @RequestMapping(\"/doDelete\")"+RT_1
						+"    public String doDelete(HttpServletRequest request){"+RT_1
						+"        String id = request.getParameter(\"id\");"+RT_1
						+"        "+getLastChar(cName)+" "+getFirstLowercase(cName)+" = this."+getFirstLowercase(cName)+"Service.find(id);"+RT_1
						+"        if("+getFirstLowercase(cName)+" != null){"+RT_1
						+"           "+getFirstLowercase(cName)+".setDeleteFlag(\"1\");"+RT_1
						+"           this."+getFirstLowercase(cName)+"Service.update("+getFirstLowercase(cName)+");"+RT_1
						+"        }"+RT_1
						+"        return \"redirect:/"+getFirstLowercase(cName)+"/list\";"+RT_1
						+"    }"+RT_2
						+RT_2+"}");
				fw.flush();
				fw.close();
				showInfo(fileName);
			}else{
				System.out.println("创建Controller失败，原因是commonPackage为空！");
			}
		}
		
		/**
		 *生成标准的Add Jsp文件
		 * @param jspPath jsp路径(从views开始，不包括jsp文件名)
		 * @throws IOException 
		 */
		public static void createStandardAddJsp(String jspPath) throws IOException{
			String fileName = System.getProperty("user.dir") + "/WebRoot/WEB_INF/" + jspPath+""
					+ "/add.jsp";
			File f = new File(fileName);
			FileWriter fw = new FileWriter(f);
			fw.write("<%@ page contentType=\"text/html; charset=UTF-8\"%>"+RT_1
					+"<%@ taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\"%>"+RT_2
					+"<!DOCTYPE html>"+RT_1
					+"<html>"+RT_1
					+"<head>"+RT_1
					+"<%@ include file=\"../common/common.jsp\"%>"+RT_1
					+"<%@ include file=\"../common/common_html_validator.jsp\"%>"+RT_1
					+"<title>添加</title>"+RT_1
					+"</head>"+RT_2
					+"<body>"+RT_1
					+"<form action=\"\" method=\"POST\" id=\"form\">"+RT_1
					);
			fw.flush();
			fw.close();
			showInfo(fileName);
		}

		/**
		 * 显示信息
		 * 
		 * @param info
		 */
		public static void showInfo(String info) {
			System.out.println("创建文件：" + info + "成功！");
		}

		/**
		 * 获取系统时间
		 * 
		 * @return
		 */
		public static String getDate() {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return simpleDateFormat.format(new Date());
		}
		

		/**
		 * 获取路径的最后面字符串<br>
		 * 如：<br>
		 * <code>str = "com.gen.entity.User"</code><br>
		 * <code> return "User";<code>
		 * 
		 * @param str
		 * @return
		 */
		public static String getLastChar(String str) {
			if ((str != null) && (str.length() > 0)) {
				int dot = str.lastIndexOf('.');
				if ((dot > -1) && (dot < (str.length() - 1))) {
					return str.substring(dot + 1);
				}
			}
			return str;
		}
		
		/**
		 * 返回第一个字母小写
		 * 如：传入User，返回user
		 * @param str
		 * @return
		 */
		public static String getFirstLowercase(String str){
			String s = "";
			if(str != null && !"".equals(str)){
				str = getLastChar(str);
				s = str.substring(0, 1).toLowerCase();
				if(str.length() > 1){
					s+=str.substring(1, str.length());
				}
			}
			return s;
		}
		

}
