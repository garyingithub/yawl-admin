package org.yawlfoundation.admin;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.yawlfoundation.admin.data.configuration.RedisConfiguration;
import org.yawlfoundation.admin.view.Plain;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
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
        transactionManager.setNestedTransactionAllowed(true);

        return transactionManager;
    }




















}


