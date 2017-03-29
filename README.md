# 天梯（tianti）

    项目描述：天梯（tianti）是一款使用Java编写的轻量级权限系统，目前可以实现后端登陆、用户管理、角色管理、资源目录管理、角色授权等基本权限管理。<br>
    同时也是一款服务端后台模板，使用layer和自身样式实现了固定模块的增删查改功能。项目技术分层明显，用户可以根据自己的业务模块进行相应地扩展。<br>
    同时提供了针对dao、service等的代码生成工具。<br>
    同时用户可以选择后台样式，目前拥有天梯蓝和天梯红两种皮肤。皮肤偏清新风格。<br>
    联系方式：xuzhexu@139.com qq交流群：422039518<br>
    天梯的版权属于开源作者，任何人不得用于商业用途。<br>
    
    后续计划：开发一个免费开源的JAVA的CMS系统，预计四月份发布，敬请期待。
    
    技术选型：Spring Data JPA、Hibernate、Shiro、 Spring MVC、Layer、Mysql等。
    
    项目结构：
     tianti-common：系统基础服务抽象，包括entity、dao和service的基础抽象；
     tianti-org：用户权限模块服务实现；
     tianti-module-admin：后台web项目实现；
  
    使用说明：
     1、数据库使用mysql，初始化脚本位于tianti-module-admin中的src/main/webapp/scripts/tianti_stage.sql。
     2、后台的登陆路径为http://ip:端口/login,用户名为admin，初始密码为123456。
     3、后台中所用到的图标是从font文件夹里面选择copy进去的，font文件夹在聊天群里面拥有。
  
   项目概览：
     天梯登陆页面：
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/login.png)  
     天梯蓝风格（默认）：
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/userlist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/rolelist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/menulist.png)                                           
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/roleset.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/updatePwd.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/skin.png)
   天梯红风格：
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/userlist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/rolelist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/menulist.png)                                       
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/roleSet.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/updatePwd.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/red/skin.png)
    