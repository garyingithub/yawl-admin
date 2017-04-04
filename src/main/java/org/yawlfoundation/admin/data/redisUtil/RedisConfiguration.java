package org.yawlfoundation.admin.data.redisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.yawlfoundation.admin.data.CustomService;
import org.yawlfoundation.admin.data.Engine;
import org.yawlfoundation.admin.data.User;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gary on 20/03/2017.
 */
@Configuration
public class RedisConfiguration  {

    @Resource
    private Environment env;

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
        return new JedisConnectionFactory();
    }



    @Bean
    RedisSerializer<String> stringRedisSerializer(){

        return new StringRedisSerializer();

    }


    @Bean
    public RedisCacheManager sessionRedisManager(){
        RedisTemplate<String,String> template=new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        template.afterPropertiesSet();
        RedisCacheManager cacheManager=new RedisCacheManager(template);
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }












}
