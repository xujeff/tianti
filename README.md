# tianti
    项目描述：天梯是一个用java相关技术搭建的后台服务端模板，目前仅仅包括基础模块和权限模块，用户可以结合自身业务进行相应扩展。
    
    技术选型：Spring Data JPA、Hibernate、Shiro、 Spring MVC、Layer、Mysql等。
    
    项目结构：
     tianti-common：系统基础服务抽象，包括entity、dao和service的基础抽象；
     tianti-org：用户权限模块服务实现；
     tianti-module-admin：后台web项目实现；
  
    使用说明：
     1、数据库使用mysql，初始化脚本位于tianti-module-admin中的src/main/webapp/scripts/tianti_stage.sql。
     2、后台的登陆路径为http://ip:端口/login,用户名为admin，初始密码为123456。
      
    项目概览(节选)：
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/login.png)                                                
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/menulist.png)
    ![image](https://raw.githubusercontent.com/xujeff/tianti/master/screenshots/roleset.png)

    联系方式：xuzhexu@139.com
