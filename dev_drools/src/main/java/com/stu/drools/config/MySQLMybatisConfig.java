package com.stu.drools.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * mybatis 配置数据源类
 */
@Slf4j
@Configuration
@MapperScan(basePackages = "com.stu.drools.mapper", sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
@EnableTransactionManagement
public class MySQLMybatisConfig  {

  ///////////////////// druid start 参数///////////////////////////////////////////////////
  @Value(value = "${spring.datasource.filters}")
  private String filters;
  @Value(value = "${spring.datasource.maxActive}")
  private String maxActive;
  @Value(value = "${spring.datasource.initialSize}")
  private String initialSize;
  @Value(value = "${spring.datasource.maxWait}")
  private String maxWait;
  @Value(value = "${spring.datasource.minIdle}")
  private String minIdle;
  @Value(value = "${spring.datasource.timeBetweenEvictionRunsMillis}")
  private String timeBetweenEvictionRunsMillis;
  @Value(value = "${spring.datasource.minEvictableIdleTimeMillis}")
  private String minEvictableIdleTimeMillis;
  @Value(value = "${spring.datasource.maxEvictableIdleTimeMillis}")
  private String maxEvictableIdleTimeMillis;
  @Value(value = "${spring.datasource.keepAlive}")
  private boolean keepAlive;
  @Value(value = "${spring.datasource.testWhileIdle}")
  private String testWhileIdle;
  @Value(value = "${spring.datasource.testOnBorrow}")
  private String testOnBorrow;
  @Value(value = "${spring.datasource.testOnReturn}")
  private String testOnReturn;
  @Value(value = "${spring.datasource.poolPreparedStatements}")
  private String poolPreparedStatements;
  @Value(value = "${spring.datasource.maxOpenPreparedStatements}")
  private String maxOpenPreparedStatements;
  @Value(value = "${spring.datasource.mysqlValidationQuery}")
  private String mysqlValidationQuery;
  ///////////////////// druid start 参数///////////////////////////////////////////////////
  /** mysql 驱动名称，默认值为：com.mysql.jdbc.Driver  **/
  @Value(value = "${spring.datasource.mysql.driver-class-name:com.mysql.jdbc.Driver}")
  private String driveClassName;
  /** mysql 连接地址  **/
  @Value(value = "${spring.datasource.mysql.url}")
  private String url;
  /** mysql 用户名称  **/
  @Value(value = "${spring.datasource.mysql.username}")
  private String userName;
  /** mysql 用户密码 （加密模式） **/
  @Value(value = "${spring.datasource.mysql.password}")
  private String password;
  /** mysql 扫描xml目录  **/
  @Value(value = "${mybatis.mysql.xmlLocation}")
  private String xmlLocation;

  protected void initDataSource(DruidDataSource druidDataSource) {
    log.info(" ===========================数据源参数信息 start=====================================");
    log.info("DataSourceConfig Param filters is :{}",filters);
    log.info("DataSourceConfig Param maxActive is :{}",maxActive);
    log.info("DataSourceConfig Param initialSize is :{} ",initialSize);
    log.info("DataSourceConfig Param maxWait is :{} ",maxWait);
    log.info("DataSourceConfig Param minIdle is :{}",minIdle);
    log.info("DataSourceConfig Param timeBetweenEvictionRunsMillis is :{}",timeBetweenEvictionRunsMillis);
    log.info("DataSourceConfig Param minEvictableIdleTimeMillis is :{}",minEvictableIdleTimeMillis);
    log.info("DataSourceConfig Param maxEvictableIdleTimeMillis is :{}",maxEvictableIdleTimeMillis);
    log.info("DataSourceConfig Param keepAlive is :{}",keepAlive);
    log.info("DataSourceConfig Param testWhileIdle is :{}",testWhileIdle);
    log.info("DataSourceConfig Param testOnBorrow is :{}",testOnBorrow);
    log.info("DataSourceConfig Param testOnReturn is :{}",testOnReturn);
    log.info("DataSourceConfig Param poolPreparedStatements is :{}",poolPreparedStatements);
    log.info("DataSourceConfig Param maxOpenPreparedStatements is :{}",maxOpenPreparedStatements);
    log.info("DataSourceConfig Param mysqlValidationQuery is :{}",mysqlValidationQuery);
    log.info(" ===========================数据源参数信息 end =====================================");

    druidDataSource.setMaxActive(StringUtils.isNotBlank(maxActive) ? Integer.parseInt(maxActive) : 10);
    druidDataSource.setInitialSize(StringUtils.isNotBlank(initialSize) ? Integer.parseInt(initialSize) : 1);
    druidDataSource.setMaxWait(StringUtils.isNotBlank(maxWait) ? Integer.parseInt(maxWait) : 60000);
    druidDataSource.setMinIdle(StringUtils.isNotBlank(minIdle) ? Integer.parseInt(minIdle) : 3);
    druidDataSource.setTimeBetweenEvictionRunsMillis(StringUtils.isNotBlank(timeBetweenEvictionRunsMillis) ? Integer.parseInt(timeBetweenEvictionRunsMillis):60000);
    druidDataSource.setMinEvictableIdleTimeMillis(StringUtils.isNotBlank(minEvictableIdleTimeMillis) ? Integer.parseInt(minEvictableIdleTimeMillis):300000);
    druidDataSource.setMaxEvictableIdleTimeMillis(StringUtils.isNotBlank(maxEvictableIdleTimeMillis) ? Integer.parseInt(maxEvictableIdleTimeMillis):900000);
    druidDataSource.setKeepAlive(keepAlive ? keepAlive : true);
    druidDataSource.setTestWhileIdle(StringUtils.isNotBlank(testWhileIdle) ? Boolean.parseBoolean(testWhileIdle) : true);
    druidDataSource.setTestOnBorrow(StringUtils.isNotBlank(testOnBorrow) ? Boolean.parseBoolean(testOnBorrow) : false);
    druidDataSource.setTestOnReturn(StringUtils.isNotBlank(testOnReturn) ? Boolean.parseBoolean(testOnReturn) : false);
    druidDataSource.setPoolPreparedStatements(StringUtils.isNotBlank(poolPreparedStatements) ? Boolean.parseBoolean(poolPreparedStatements) : true);
    druidDataSource.setMaxOpenPreparedStatements(StringUtils.isNotBlank(maxOpenPreparedStatements) ? Integer.parseInt(maxOpenPreparedStatements) : 20);
    druidDataSource.setValidationQuery(StringUtils.isNotBlank(mysqlValidationQuery) ?  mysqlValidationQuery : "select 'x'");
    try {
      druidDataSource.setFilters(StringUtils.isNotBlank(filters) ? filters : "stat, wall");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  @Bean
  public DataSource mysqlDataSource() {
    log.info(" ===========================mysql数据库参数信息 start=====================================");
    log.info("MySQLMybatisConfig Param driveClassName is :{}",driveClassName);
    log.info("MySQLMybatisConfig Param url is :{}",url);
    log.info("MySQLMybatisConfig Param userName is :{}",userName);
    log.info("MySQLMybatisConfig Param password is hide .");
    log.info("MySQLMybatisConfig Param xmlLocation is :{}",xmlLocation);
    log.info(" ===========================mysql数据库参数信息 end=====================================");
    log.info(" ******************************************************************************************");


    /** 2.参数设置 ***/
    DruidDataSource druidDataSource = new DruidDataSource();
    druidDataSource.setUrl(url);
    druidDataSource.setUsername(userName);
    druidDataSource.setPassword(password);
    druidDataSource.setDriverClassName(driveClassName);
    // 初始化mysql数据源信息
    this.initDataSource(druidDataSource);

    return druidDataSource;
  }

  @Bean(name = "mysqlSqlSessionFactory")
  public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
		/* 模糊匹配扫描包
		if (StringUtils.isNotBlank(typeAliasesPackage)) {
			bean.setTypeAliasesPackage(typeAliasesPackage);
		}
		*/
    // 添加XML目录
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    try {
      bean.setMapperLocations(resolver.getResources(xmlLocation));
      try {
//开启驼峰命名转换
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }

      return bean.getObject();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Bean
  public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  @Bean(name = "mysqlTransactionManager")
  public DataSourceTransactionManager transactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }



}
