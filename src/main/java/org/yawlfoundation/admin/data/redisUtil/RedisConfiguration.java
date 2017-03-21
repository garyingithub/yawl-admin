package org.yawlfoundation.admin.data.redisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
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
        config.setMaxTotal(Integer.parseInt(env.getRequiredProperty("spring.redis.pool.maxActive")));
        config.setMaxIdle(Integer.parseInt(env.getRequiredProperty("spring.redis.pool.maxIdle")));

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
    private RedisSerializer<String> stringRedisSerializer(){

        return new StringRedisSerializer();

    }

    class BaseRedisTemplate<String,T> extends RedisTemplate<String,T> {

        public BaseRedisTemplate(){
            super();
            this.setKeySerializer(stringRedisSerializer());
            this.setConnectionFactory(jedisConnectionFactory());

        }

    }


    @Bean
    public RedisCacheManager caseEngineRedisManager(){
        RedisTemplate<String,Engine> template=new BaseRedisTemplate<>();
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Engine.class));
        return new RedisCacheManager(template);
    }

    @Bean
    public RedisCacheManager caseDefaultWorklistRedisManager(){
        RedisTemplate<String,CustomService> template=new BaseRedisTemplate<>();
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(CustomService.class));
        return new RedisCacheManager(template);
    }

    @Bean
    public RedisCacheManager sessionRedisManager(){
        RedisTemplate<String,User> template=new BaseRedisTemplate<>();
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
        return new RedisCacheManager(template);
    }








}
