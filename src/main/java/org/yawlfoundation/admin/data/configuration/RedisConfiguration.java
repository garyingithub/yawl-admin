package org.yawlfoundation.admin.data.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import javax.annotation.Resource;

/**
 * Created by gary on 20/03/2017.
 */
@Configuration
public class RedisConfiguration  {

    @Resource
    private Environment env;


    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return jedisConnectionFactory();
    }

    @Bean
    JedisPoolConfig jedisPoolConfig(){

        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(env.getRequiredProperty("spring.redis.pool.max-active")));
        config.setMaxIdle(Integer.parseInt(env.getRequiredProperty("spring.redis.pool.max-idle")));

        return config;

    }




    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory factory=new JedisConnectionFactory();
        factory.setPoolConfig(jedisPoolConfig());
        factory.setUsePool(true);
        factory.setShardInfo(new JedisShardInfo(env.getRequiredProperty("spring.redis.host")));

        //System.out.println(factory.getShardInfo().getHost());
        return factory;
    }



    @Bean
    RedisSerializer<String> stringRedisSerializer(){

        return new StringRedisSerializer();

    }


    @Bean
    public StringRedisTemplate stringRedisTemplate(){
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        template.afterPropertiesSet();
        //System.out.println(template.keys("*"));
        return template;
    }


}
