package com.stylefeng.guns.core.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * <p>
 * 数据库数据源配置
 * </p>
 * <p>
 * 说明:这个类中包含了许多默认配置,若这些配置符合您的情况,您可以不用管,若不符合,建议不要修改本类,建议直接在"application.yml"中配置即可
 * </p>
 * 
 * @author stylefeng
 * @date 2017-05-21 11:18
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidProperties {

	private String url = "jdbc:mysql://127.0.0.1:3306/guns?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";

	private String username = "root";

	private String password = "root";

	private String driverClassName = "com.mysql.jdbc.Driver";

	private Integer initialSize = 10;

	private Integer minIdle = 3;

	private Integer maxActive = 21;

	private Integer maxWait = 60001;

	private Integer timeBetweenEvictionRunsMillis = 60000;

	private Integer minEvictableIdleTimeMillis = 300000;

	private String validationQuery = "SELECT 'x'";

	private Boolean testWhileIdle = true;

	private Boolean testOnBorrow = false;

	private Boolean testOnReturn = false;

	private Boolean poolPreparedStatements = true;

	private Integer maxPoolPreparedStatementPerConnectionSize = 50;

	private String filters = "log4j,wall,mergeStat";//"stat";
	
	private Boolean removeAbandoned = true;
	
	private Integer removeAbandonedTimeout = 180;
	
	private Boolean logAbandoned = true;

	public void config(DruidDataSource dataSource) {

		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		dataSource.setDriverClassName(driverClassName); // 驱动类型
		dataSource.setInitialSize(initialSize); // 定义初始连接数
		dataSource.setMinIdle(minIdle); // 最小空闲
		dataSource.setMaxActive(maxActive); // 连接池中最大连接数
		dataSource.setMaxWait(maxWait); // 获取链接超时时间为1分钟，单位为毫秒。

		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

		// 是否自动回收超时连接--是否开启自动清理被租借的连接但是又没有还回线程池
		dataSource.setRemoveAbandoned(removeAbandoned);
		// 超过时间限制时间（单位秒），目前为5分钟，如果有业务处理时间超过5分钟，可以适当调整。
		dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);

		// 配置一个连接在池中最小生存的时间，单位是毫秒。
		// 连接池中连接，在时间段内一直空闲， 被逐出连接池的时间（默认为30分钟，可以适当做调整，需要和后端服务端的策略配置相关）
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		// #检测数据库链接是否有效，必须配置("SELECT 'x'")
		dataSource.setValidationQuery(validationQuery);
		
		// 此项配置为true即可，不影响性能，并且保证安全性(true)。
		// 意义为：申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
		dataSource.setTestWhileIdle(testWhileIdle);
		
		// 获取链接的时候，不校验是否可用，开启会有损性能(false)。
		dataSource.setTestOnBorrow(testOnBorrow);
		// 归还链接到连接池的时候校验链接是否可用(false)。
		dataSource.setTestOnReturn(testOnReturn);

		// 打开PSCache，并且指定每个连接上PSCache的大小
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		//是否在自动回收超时连接的时候打印连接的超时错误,关闭abanded连接时输出错误日志 2019-01-22
		dataSource.setLogAbandoned(logAbandoned);

		try {
			dataSource.setFilters(filters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}

	public Integer getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public Integer getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public Boolean getTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(Boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public Boolean getTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(Boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public Boolean getPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(Boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public Integer getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(Integer maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}
}
