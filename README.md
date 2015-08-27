NutzWk 2.0 纯净版
======

基于Nutz的开源企业级开发框架


NutzWk 2.0 新特性：
*   集成Shrio权限框架
*   集成Ehcache缓存
*   支持语言国际化
*   支持注解式事务
*   支持动作链
*   支持注解式日志系统
*   支持插件式加载[Coding..]
*   集成Email服务
*   集成Quartz定时任务
*   集成Lucene搜索引擎[Coding..]
*   采用Bootsrtap皮肤(Sublime貌似收费?请不要吝啬那点钱买正版授权)


使用说明：
*   创建空的数据库
*   修改数据库连接 /resources/config/custom/db.properties
*   项目使用Maven构建，IDEA/Eclipse直接打开，等待包下载完毕
*   nutz-1.b.53.jar 暂未正式发布，请将/lib下此包手动加到编译环境*
*   cn.wizzer.modules.MainSetup 第45行（启动时自动建表）
*   用户名：superadmin  密码：1



在线演示地址
============================================

http://121.43.107.94/private/login                 NutzWk 2.0

http://121.43.107.94/wzflow/private/login          NutzWk 1.0 (含Activiti工作流)

=======Park 调试=============================
1、数据库连接修改，设置数据库
2、POM.XML文件修改
	2.1 增加Tomcat Plugin
	2.2 Build Src Resource 路径不正确
	2.3 commons-logging 依赖 Sevlet 2.3包有冲突
	2.4 JavaSE 1.7 Builder

http://localhost:8080/nutzfw/private/sys/menu



