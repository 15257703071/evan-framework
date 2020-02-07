# evan-framework
 
### 模块说明

+ domain          核心传输对象
+ cache           cache存储组件
+ sysconfig       系统配置读取组件
+ datadict        数据字典读取组件
+ httpclient      http请求组件
+ mail            邮件发送组件
+ mq(丢弃）        消息队列组件
+ store           存储组件
+ persistence     持久化组件
+ syslog          系统日志组件
+ utils           工具集
+ web             web工程依赖包
+ task            当当网的esJob分布式定时任务

### 操作说明

##### 更新版本
```
mvn-updata-version.sh(.bat) $version
//参数version--要更新的目标版本
//例如更新到1.2-SNAPSHOT,执行：mvn-updata-version.sh(.bat) 1.2-SNAPSHOT
```
##### 发布到maven私服快照仓库
```
mvn-deploy-snapshot.sh(.bat)   
```
##### 发布到maven私服release仓库
```
mvn-deploy-release.sh(.bat) $version
//参数version--要发布的目标版本
```



### 版本说明

#### v1.1 20200217

1.添加当当网esJob分布式定时任务
```
zookeeper的相关配置
elastic.job.zk.namespace=elastic-job
elastic.job.zk.serverLists=192.168.1.111:2181,192.168.1.112:2181
```
文档http://elasticjob.io/index_zh.html

#### v1.0 20200216
1.创建
