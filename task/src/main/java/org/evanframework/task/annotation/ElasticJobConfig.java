package org.evanframework.task.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主要是采用当当网的elasticJob分布式定时任务
 * @see http://elasticjob.io/docs/elastic-job-lite/02-guide/config-manual/
 *
 * 使用前提：必须在项目中使用@EnableElasticJob注解,才能被Spring扫描到
 * 用途：以下参数配置将自动以bean的形式注入到Spring当中，并且注册到zookeeper上进行统一管理
 * application.properties以下参数必填才有效:
 * 				elastic.job.zk.namespace : elastic-job(zk的命名空间)
 *				elastic.job.zk.serverLists : 192.168.11.111:2181(zk的路由加端口,集群的配置如:192.168.11.111:2181,192.168.11.112:2181)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticJobConfig {

	/**
	 * elasticjob的名称
	 */
	String name();	//must

	/**
	 * cron表达式，用于控制作业触发时间
	 */
	String cron() default "";//must

	/**
	 * 作业分片总数
	 */
	int shardingTotalCount() default 1;

	/**
	 * 分片序列号和参数用等号分隔，多个键值对用逗号分隔
	 * 分片序列号从0开始，不可大于或等于作业分片总数
	 * 如：
	 * 0=a,1=b,2=c
	 */
	String shardingItemParameters() default "";

	/**
	 * 作业自定义参数
	 * 作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业
	 * 例：每次获取的数据量、作业实例从数据库读取的主键等
	 */
	String jobParameter() default "";

	/**
	 * 是否开启任务执行失效转移，开启表示如果作业在一次任务执行中途宕机，允许将该次未完成的任务在另一作业节点上补偿执行
	 */
	boolean failover() default false;

	/**
	 * 是否开启错过任务重新执行
	 */
	boolean misfire() default true;

	/**
	 * 作业描述信息
	 */
	String description() default "";

	/**
	 * 本地配置是否可覆盖注册中心配置
	 * 如果可覆盖，每次启动作业都以本地配置为准
	 */
	boolean overwrite() default false;

	/**
	 * 是否流式处理数据
	 * 如果流式处理数据, 则fetchData不返回空结果将持续执行作业
	 * 如果非流式处理数据, 则处理数据完成后作业结束
	 */
	boolean streamingProcess() default false;

	/**
	 * 脚本型作业执行命令行
	 */
	String scriptCommandLine() default "";

	/**
	 * 监控作业运行时状态
	 * 每次作业执行时间和间隔时间均非常短的情况，建议不监控作业运行时状态以提升效率。因为是瞬时状态，所以无必要监控。请用户自行增加数据堆积监控。并且不能保证数据重复选取，应在作业中实现幂等性。
	 * 每次作业执行时间和间隔时间均较长的情况，建议监控作业运行时状态，可保证数据不会重复选取。
	 */
	boolean monitorExecution() default false;

	/**
	 * 作业监控端口
	 * 建议配置作业监控端口, 方便开发者dump作业信息。
	 * 使用方法: echo “dump” | nc 127.0.0.1 9888
	 */
	public int monitorPort() default -1;	//must

	/**
	 * 最大允许的本机与注册中心的时间误差秒数
	 * 如果时间误差超过配置秒数则作业启动时将抛异常
	 * 配置为-1表示不校验时间误差
	 */
	public int maxTimeDiffSeconds() default -1;	//must

	/**
	 * 作业分片策略实现类全路径
	 * 默认使用平均分配策略
	 * 详情参见：作业分片策略
	 * @see http://elasticjob.io/docs/elastic-job-lite/02-guide/job-sharding-strategy/
	 */
	public String jobShardingStrategyClass() default "";	//must

	/**
	 * 修复作业服务器不一致状态服务调度间隔时间，配置为小于1的任意值表示不执行修复
	 * 单位：分钟
	 */
	public int reconcileIntervalMinutes() default 10;	//must

	/**
	 * 作业事件追踪的数据源Bean引用
	 */
	public String eventTraceRdbDataSource() default "";	//must

	/**
	 * 前置后置任务监听实现类，需实现ElasticJobListener接口
	 */
	public String listener() default "";	//must

	/**
	 * 作业是否禁止启动
	 * 可用于部署作业时，先禁止启动，部署结束后统一启动
	 */
	public boolean disabled() default false;	//must

	public String distributedListener() default "";

	public long startedTimeoutMilliseconds() default Long.MAX_VALUE;	//must

	public long completedTimeoutMilliseconds() default Long.MAX_VALUE;		//must

	public String jobExceptionHandler() default "com.dangdang.ddframe.job.executor.handler.impl.DefaultJobExceptionHandler";

	public String executorServiceHandler() default "com.dangdang.ddframe.job.executor.handler.impl.DefaultExecutorServiceHandler";
	
}
