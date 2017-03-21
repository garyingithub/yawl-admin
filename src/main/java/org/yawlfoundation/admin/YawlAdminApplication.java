package org.yawlfoundation.admin;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.export.MetricExportProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.yawlfoundation.admin.data.Engine;
import org.yawlfoundation.admin.data.redisUtil.RedisConfiguration;
import org.yawlfoundation.admin.view.Plain;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.beans.PropertyVetoException;
import java.util.*;

//import org.springframework.orm.hibernate4.HibernateTransactionManager;

/**
 * Created by root on 17-2-7.
 */

@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.yawlfoundation.admin.data.repository"})
@Import(RedisConfiguration.class)
public class YawlAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YawlAdminApplication.class, args);
    }


    @Resource
    private Environment env;




    @Bean
    BeanNameViewResolver beanNameViewResolver(){
        BeanNameViewResolver resolver=new BeanNameViewResolver();
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    View plain(){
        return new Plain();
    }

    @Bean
    public ComboPooledDataSource dataSource(){

        ComboPooledDataSource dataSource=new ComboPooledDataSource();

        try {
            dataSource.setDriverClass(env.getRequiredProperty(Constant.DATABASE_DRIVER));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        dataSource.setJdbcUrl(env.getRequiredProperty(Constant.DATABASE_URL));
        dataSource.setUser(env.getRequiredProperty(Constant.DATABASE_USER));
        dataSource.setPassword(env.getRequiredProperty(Constant.DATABASE_PASSWORD));
        return dataSource;

    }

    @Bean
    JpaVendorAdapter jpaVendorAdapter(){

        HibernateJpaVendorAdapter jpaVendorAdapter=new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(false);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        return jpaVendorAdapter;

    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,JpaVendorAdapter jpaVendorAdapter,Properties jpaProperties
    ){

        LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(jpaVendorAdapter);
        factory.setPackagesToScan("org.yawlfoundation.admin.data");


        factory.setJpaProperties(jpaProperties);

        return factory;

    }


    @Bean
    Properties jpaProperties(){
        Properties jpaProperties = new Properties();

        //Specifies the action that is invoked to the database when the Hibernate
        //SessionFactory is created or closed.

        jpaProperties.put("hibernate.hbm2ddl.auto",
                env.getRequiredProperty("spring.jpa.hibernate.ddl-auto")
        );

        jpaProperties.put("hibernate.id.new_generator_mappings",
                env.getRequiredProperty("spring.jpa.hibernate.use-new-id-generator-mappings")
        );

        jpaProperties.put("hibernate.ejb.naming_strategy",
                env.getRequiredProperty("spring.jpa.hibernate.naming.strategy")
        );

        return jpaProperties;
    }


    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory,
                                                         Properties jpaProperties){

        JpaTransactionManager transactionManager=new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setJpaProperties(jpaProperties);
        return transactionManager;
    }




















}


