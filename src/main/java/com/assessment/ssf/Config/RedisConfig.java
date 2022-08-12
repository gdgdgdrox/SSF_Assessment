package com.assessment.ssf.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private Integer redisPort;
    @Value("${spring.redis.username}")
    private String username;
    private String redisPassword = System.getenv("REDIS_PASSWORD");

    @Bean
    public RedisTemplate<String, Object> RedisTemplate() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        redisConfig.setUsername(username);
        redisConfig.setPassword(redisPassword);
        JedisClientConfiguration jedisConfig = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisFac = new JedisConnectionFactory(redisConfig, jedisConfig);
        jedisFac.afterPropertiesSet();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisFac);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
        redisTemplate.setValueSerializer(serializer);
        return redisTemplate;
    }
}
