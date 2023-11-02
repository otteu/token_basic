package com.delivery.delivery_app.config.redis;

import com.delivery.delivery_app.api.controller.account.model.Account;
import io.lettuce.core.RedisURI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfiguration {

    private final RedisProperties redisProperties;

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;




    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // yml 에 설정한 경로고 가져오기

        // Lettuce 생성, 생성 하여 밑에 파마미터로 전달

//        LettuceConnectionFactory factory = new LettuceConnectionFactory("192.168.0.26", 3679);
        LettuceConnectionFactory factory = new LettuceConnectionFactory(host, port);
        factory.afterPropertiesSet();

        return factory;
    }

    // Redis 사용하고자 하는 정보
    @Bean
    public RedisTemplate<String, Account> accountRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Account> redisTemplate = new RedisTemplate<>();
        // 커넥션 정보 , 방법 2가지 이지만 Lettuce 사용 한다. 더 좋다
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 직렬화 정보
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Account>(Account.class));
        return redisTemplate;
    }

}
