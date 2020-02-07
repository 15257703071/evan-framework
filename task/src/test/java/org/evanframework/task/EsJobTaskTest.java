package org.evanframework.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.evanframework.task.annotation.ElasticJobConfig;
import org.evanframework.task.annotation.EnableElasticJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoJob.class})
@EnableElasticJob
public class EsJobTaskTest {

    @Test
    public void contextLoads() {
        while (true) {

        }
    }
}

@Component
@ElasticJobConfig(
        name = "org.evanframework.task.DemoJob",
        cron = "0/10 * * * * ?",
        description = "样例定时任务",
        overwrite = false,
        shardingTotalCount = 2
)
class DemoJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        System.err.println("执行Demo job.");
    }
}
