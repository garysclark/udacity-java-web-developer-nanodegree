package com.byrneclark.garydata;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

//@Configuration
public class DataSourceConfig {

//	@Bean
//	@ConfigurationProperties("spring.datasource")
	public DataSource getDataSource(DataSourceProperties properties) {
		DataSourceBuilder<?> dsb = DataSourceBuilder.create();
		dsb.username("sa");
		dsb.password("sysadmin*1234M");
		dsb.url("jdbc.mysql://localhost:3306/plant");
		DataSource dataSource = dsb.build();
		return dataSource;
	}
}
