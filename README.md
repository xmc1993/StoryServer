# 项目简介
本项目是由sping(4.1.0) structs2（2.3.24）mybatis(3.2.7) 组成

使用了redis缓存，七牛的云存储及直播，融云IM



# 准备

## 环境准备：

| 工具    | 版本或描述                |
| ----- | -------------------- |
| JDK   | 1.7或1.8                  |
| IDE   | eclipse 或者 IntelliJ IDEA |
| Maven | 3.x                  |
| redis |                   |


## 主机规划：

| 项目名称                        | 描述                     
| -------------------------------| ---------------------- | --------------- |
| sinture-api                    | 项目中所有跟数据库相关的实体的工程        
| service-api					 | 项目中service接口工程    
| business-server                | 运营管理端接口工程               
| user-server                    | app服务提供端接口工程                
| streamint-server               | 直播接口工程     
| rongcloud						 | 融云im   

## 工程主体结构java 源码：

	cn.edu.nju.software.action
	cn.edu.nju.software.aop					
	cn.edu.nju.software.dao					
	cn.edu.nju.software.exception			    
	cn.edu.nju.software.service
	cn.edu.nju.software.service.impl
	cn.edu.nju.software.task				
	cn.edu.nju.software.util				

## 工程主体结构配置文件：	

	datasource.properties   数据库连接配置文件
	log4j.properties		日志配置文件
	log4j2.xml				日志配置文件
	config/spring/			spring相关配置文件
	config/struts/			struts相关配置文件
	cn/edu/nju/software/dao mybaits相关文件

## 直播工程为：streaming-server

	cn.edu.nju.software.action.live.business  直播管理端action
	cn.edu.nju.software.action.live.user	  直播App接口端action
	cn.edu.nju.software.action.live.web	      直播web端action
