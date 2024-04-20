package com.scaler.EcomProductService.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/*To define the connection settings between the application client and the Redis server instance, we need to use a Redis client.

        There is a number of Redis client implementations available for Java.
        Here, we are using Jedis — a simple and powerful Redis client implementation.*/
@Configuration
public class  RedisConfiguration {
/*    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }
*/
    //JedisConnectionFactory implements RedisConnectionFactory
    //Some springboot versions doesn't support JedisConnectionFactory, so we are directly using RedisConnectionFactory
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
    //if we need to configure the connection details, we can always modify the jedisConnectionFactory configuration as shown below:
    //The same can be done by setting properties in application.properties as well

/*    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory();
        jedisConFactory.setHostName("localhost");
        jedisConFactory.setPort(6379);
        return jedisConFactory;
    }
*/
}
