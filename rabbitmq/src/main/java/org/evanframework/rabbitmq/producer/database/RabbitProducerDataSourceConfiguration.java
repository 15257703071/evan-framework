package org.evanframework.rabbitmq.producer.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@PropertySource({"classpath:rabbit-producer-message.properties"})
public class RabbitProducerDataSourceConfiguration {
	
	private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RabbitProducerDataSourceConfiguration.class);
	
	@Bean(name = "rabbitProducerDataSource")
	@Primary
	@ConfigurationProperties(prefix = "rabbit.producer.druid.jdbc")
	public DataSource rabbitProducerDataSource() throws SQLException {
		return new DruidDataSource();
	}

	@Deprecated
    public DataSourceProperties primaryDataSourceProperties(){
        return new DataSourceProperties();
    }

	@Deprecated
    public DataSource primaryDataSource(){
        return primaryDataSourceProperties().initializeDataSourceBuilder().build();
    }
	
}
