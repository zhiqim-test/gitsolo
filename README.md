### gitsolo的简介和优势


 **简介** ：gitsolo是一个极简的git仓库管理器，支持HTT(S)协议，包含部门角色权限等管理。

 **优势** ：

1. 全程自主研发，无外部依赖包，限制少。

2. 下载容易，安装便捷，启动简单。

3. 可以集成其他知启蒙组件，如[ZhiqimIssue](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimIssue.htm),[ZhiqimFeature](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimFeature.htm)等，进行二次开发，可持续开发性强。
  

### gitsolo的项目依赖

 **依赖一**： 

ZhiqimDK：包括[ZhiqimKernel](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimKernel.htm), [ZhiqimORM](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimORM.htm), [ZhiqimML](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimML.htm), [ZhiqimHttpd](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimHttpd.htm)四部分。
- [ZhiqimKernel](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimKernel.htm):是Zhiqim Development Kit的核心，负责工程的生命周期管理：包括工程开发和发布的目录结构管理、统一的配置规约、单多例服务接口定义、服务启动运行更新和销毁管理。并提供基础开发工具：包括工具类、日志类、线程池、JSON/XML编解析、HTTP客户端、时钟任务定时器等。
- [ZhiqimORM](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimORM.htm):是Zhiqim Development Kit面向数据库开发的多例服务，包括ZSQL规范和三大映射关系（表映射、字段映射和指令映射）。
- [ZhiqimML](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimML.htm):是实现ZML规范的引擎系统。支持两种ZML加载方式（按文件目录和按类路径）、能够在ZML文件改动后立即发现并在触发时重新加载、 并提供详细的“表达式合并运算优先级”、“变量多维作用域”和“无障碍访问Java代码”，以及设置上下文作用域的配置模板、对模板进行缓存等特性。
- [ZhiqimHttpd](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimHttpd.htm):是Zhiqim Development Kit面向WEB开发的多例服务，提供更简洁配置、积木式组件模块和天然的模型模板设计。

 **依赖二**：

[ZhiqimUI](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimUI.htm):是一套集成Javascript库、Css库、Font库、常用ico图标和Flash控件等，并在其上开发的大量UI组件组成的前端开发套件。


 **依赖三** ：
- [ZhiqimManager](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimManager.htm):是知启蒙团队自主研发的后台管理台，可独立使用，也可作为组件与其他组件配合使用，使用范围非常广。
- [ZhiqimProject](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimProject.htm):即知启蒙项目组件，包含了项目信息管理，项目成员管理，项目动态以及项目计划总结等汇报设置。


 **依赖四**：

[sqlite](https://github.com/zhiqim-com/download/raw/master/ZhiqimTools/V1.4.0.R2018010101/zhiqim_sqlite3.8.6_native.jar)：文件数据库。

 
### Gitsolo的特点
1. 组件式的项目，可以在此基础上进行二次开发，也可作为另一个大项目下的组件使用，使用实例可参考[知启蒙网站](https://www.zhiqim.com/index.htm)。
2. 依赖于[ZhiqimManager](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimManager.htm),自带了管理和权限功能。
3. 集成了[sqlite](https://github.com/zhiqim-com/download/raw/master/ZhiqimTools/V1.4.0.R2018010101/zhiqim_sqlite3.8.6_native.jar)文件数据库，安装使用方便。
4. 全程自主研发，无外部包依赖，开发环境纯净。
5. 安装简单，启动容易，意见是的启动方式。
6. 可以集成[ZhiqimIssue](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimIssue.htm),[ZhiqimFeature](https://www.zhiqim.com/gitcan/zhiqim/ZhiqimFeature.htm)等组件，形成一个新项目，可持续性开发非常强。



### gitsolo安装指南
1.  **JDK下载安装** ：其详细安装教程请前往[【JDK安装教程】](https://www.zhiqim.com/document/bestcase/jdk.htm)。

2.  **执行程序下载** ：源码下载克隆请点击[【Gitsolo】](https://www.zhiqim.com/gitcan/zhiqim/Gitsolo.htm)。

3.  **安装配置** ：
gitsolo配置时，有几个需要主要的点，详见下图图解说明：

配置zhiqim.xml:
![输入图片说明](https://images.gitee.com/uploads/images/2018/0705/163637_1edfc186_1793820.jpeg "zhiqim配置.jpg")

配置httpd.xml：
![输入图片说明](https://images.gitee.com/uploads/images/2018/0705/164043_61f9b77f_1793820.jpeg "httpd配置.jpg")

4. **启动**：

![输入图片说明](https://images.gitee.com/uploads/images/2018/0705/165548_8e285386_1793820.jpeg "启动.jpg")


5.  **gitsolo功能展示** 

-  **自带的管理和权限功能** 

系统基础设置,如系统菜单，系统头像，系统配置，系统参数首页主题等。
部门角色管理，如部门管理，部门成员，部门权限，角色管理，角色成员，角色权限
还有操作员管理，操作日志查询

![输入图片说明](https://images.gitee.com/uploads/images/2018/0705/170555_bb7bc3db_1793820.jpeg "管理和权限.jpg")

-  **项目信息管理功能** 

项目的增删改查和转让等功能。

![输入图片说明](https://images.gitee.com/uploads/images/2018/0705/170636_11bcc6b0_1793820.jpeg "项目信息管理功能.jpg")

-  **项目成员管理功能** 
项目成员的添加和删除功能。

![输入图片说明](https://images.gitee.com/uploads/images/2018/0705/170708_13dfa779_1793820.jpeg "项目成员管理功能.jpg")

-  **项目代码仓库功能** 

增加，修改，删除，重命名和迁移仓库功能。

![输入图片说明](https://images.gitee.com/uploads/images/2018/0705/170739_5253fac8_1793820.jpeg "项目代码仓库功能.jpg")

-  **设置独立密钥功能** 

开启仓库独立密钥，加强保密性。

![输入图片说明](https://images.gitee.com/uploads/images/2018/0705/170802_69c6ae1b_1793820.jpeg "设置独立密钥功能.jpg")

-  **我的汇报设置功能** 

可以增加我汇报工作的对象，以及向我汇报工作的对象。

![输入图片说明](https://images.gitee.com/uploads/images/2018/0705/170905_67098e9c_1793820.jpeg "我的汇报设置功能.jpg")

-  **我的项目动态功能** 

可以查看我创建的项目动态以及向我汇报的项目的动态。

![输入图片说明](https://images.gitee.com/uploads/images/2018/0705/170942_2a770e8f_1793820.jpeg "我的项目动态功能.jpg")



### 知启蒙基本框架

![输入图片说明](https://images.gitee.com/uploads/images/2018/0705/171238_eb0618c8_1793820.jpeg "gitsolo.jpg")



### 知启蒙许可证

知启蒙的所有开源项目都必须遵循知启蒙许可证。

![知启蒙许可证举例](https://images.gitee.com/uploads/images/2018/0706/113855_3a4dd47e_1793820.jpeg "许可证.jpg")

欲知知启蒙许可证详细内容，[【请戳这里戳这里戳这里】](http://opensource.zhiqim.com/licenses/zhiqim_register_publish_license.htm)





### 知启蒙技术交流

 QQ群：加入交流群，请点击[【458171582】](https://jq.qq.com/?_wv=1027&k=5DWlB3b) 

 欲知晓更多详情，[请戳这里>>](https://www.zhiqim.com/index.htm)



 







