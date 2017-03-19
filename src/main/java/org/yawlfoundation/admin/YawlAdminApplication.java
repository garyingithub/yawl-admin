package org.yawlfoundation.admin;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
import org.yawlfoundation.admin.view.Plain;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//import org.springframework.orm.hibernate4.HibernateTransactionManager;

/**
 * Created by root on 17-2-7.
 */

@SpringBootApplication
@Import(Plain.class)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.yawlfoundation.admin.data.repository"})
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


