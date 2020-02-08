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
+ task            当当网的esJob分布式定时任务组件
+ rabbitmq        rabbit消息队列组件

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

#### v1.2 20200218
1.添加RabbitMQ生产者封装组件以及三种发送方式，并且做了发送消息的持久化
+ 迅速消息            不需要保障消息的可靠性, 也不需要做confirm确认
+ 确认消息            不需要保障消息的可靠性，但是会做消息的confirm确认
+ 可靠性投递消息       保障数据库和所发的消息是原子性的

##### 使用方法
application.properties 配置参数如下
```
    ## 链接配置
    spring.rabbitmq.addresses=192.168.1.128:5672
    spring.rabbitmq.username=root
    spring.rabbitmq.password=123456
    spring.rabbitmq.virtual-host=/

    ##	使用启用消息确认模式
    spring.rabbitmq.publisher-confirms=true
    spring.rabbitmq.publisher-returns=true
    spring.rabbitmq.template.mandatory=true
    spring.rabbitmq.listener.simple.auto-startup=false
    spring.rabbitmq.connection-timeout=15000

    ## zk的配置
    elastic.job.zk.serverLists=47.104.250.122:2181
    elastic.job.zk.namespace=elastic-job
```

rabbit-producer-message.properties 配置参数如下
```
    rabbit.producer.druid.jdbc.driver-class-name=com.mysql.jdbc.Driver
    rabbit.producer.druid.jdbc.url=jdbc:mysql://192.168.1.128:3306/broker_message?characterEncoding=UTF-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useUnicode=true&serverTimezone=GMT
    rabbit.producer.druid.jdbc.username=root
    rabbit.producer.druid.jdbc.password=123456

```

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
