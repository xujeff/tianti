# 天梯（tianti）
  ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/book.jpg)[配套书籍>>>](https://yuedu.baidu.com/ebook/7a5efa31fbd6195f312b3169a45177232f60e487)<br>
  [天梯](https://yuedu.baidu.com/ebook/7a5efa31fbd6195f312b3169a45177232f60e487)[tianti-tool](https://github.com/xujeff/tianti-tool)简介：<br>
  
    1、天梯是一款使用Java编写的免费的轻量级CMS系统，目前提供了从后台管理到前端展现的整体解决方案。
    2、用户可以不编写一句代码，就制作出一个默认风格的CMS站点。
    3、前端页面自适应，支持PC和H5端，采用前后端分离的机制实现。后端支持天梯蓝和天梯红换肤功能。
    4、项目技术分层明显，用户可以根据自己的业务模块进行相应地扩展，很方便二次开发。
   
  ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/tiantiframework.png)  <br>
  ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/gateway/help/help.png)  <br>

  技术架构：<br>
  
     1、技术选型：
        后端
         ·核心框架：Spring Framework 4.2.5.RELEASE
         ·安全框架：Apache Shiro 1.3.2
         ·视图框架：Spring MVC 4.2.5.RELEASE
         ·数据库连接池：Tomcat JDBC
         ·缓存框架：Ehcache 
         ·ORM框架：Spring Data JPA、hibernate 4.3.5.Final
         ·日志管理：SLF4J 1.7.21、Log4j
         ·编辑器：ueditor
         ·工具类：Apache Commons、Jackson 2.8.5、POI 3.15
         ·view层：JSP
         ·数据库：mysql、oracle等关系型数据库

        前端
         ·dom : Jquery
         ·分页 : jquery.pagination
         ·UI管理 : common
         ·UI集成 : uiExtend
         ·滚动条 : jquery.nicescroll.min.js
         ·图表 ： highcharts
         ·3D图表 ：highcharts-more
         ·轮播图 ： jquery-swipe
         ·表单提交 ：jquery.form
         ·文件上传 ：jquery.uploadify
         ·表单验证 ：jquery.validator
         ·展现树 ：jquery.ztree
         ·html模版引擎 ：template
     2、项目结构：
       2.1、tianti-common：系统基础服务抽象，包括entity、dao和service的基础抽象；
       2.2、tianti-org：用户权限模块服务实现；
       2.3、tianti-cms:资讯类模块服务实现；
       2.4、tianti-module-admin：天梯后台web项目实现；
       2.5、tianti-module-interface：天梯接口项目实现；
       2.6、tianti-module-gateway：天梯前端自适应项目实现（是一个静态项目，调用tianti-module-interface获取数据）；
        
  
  前端项目概览：<br>
     PC：<br>
     ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/gateway/index.png)  
     ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/gateway/columnlist.png)  
     ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/gateway/detail.png)  
     H5:<br>
     ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/gateway/h5/index.png)  
     ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/gateway/h5/columnlist.png)  
     ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/gateway/h5/detail.png)  
 <br>
  后台项目概览：<br>
     天梯登陆页面：
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/login.png)  
     天梯蓝风格（默认）：
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/userlist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/rolelist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/menulist.png)                                           
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/roleset.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/updatePwd.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/skin.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/lanmulist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/addlanmu.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/articlelist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/addarticle.png)  
   天梯红风格：
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/userlist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/rolelist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/menulist.png)                                       
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/roleSet.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/updatePwd.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/skin.png) 
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/lanmulist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/addlanmu.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/articlelist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/addarticle.png)  
    
    
