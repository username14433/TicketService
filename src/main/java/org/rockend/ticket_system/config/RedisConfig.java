package org.rockend.ticket_system.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(
            @Value("${spring.data.redis.host}") String host,
            @Value("${spring.data.redis.port}") int port) {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        return om;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(ObjectMapper objectMapper) {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);

        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .entryTtl(Duration.ofMinutes(10)) // дефолт TTL
                .disableCachingNullValues();
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory,
                                          RedisCacheConfiguration defaultConfig) {
        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
        cacheConfigs.put("tickets", defaultConfig.entryTtl(Duration.ofMinutes(15))); // список тикетов
        cacheConfigs.put("ticket", defaultConfig.entryTtl(Duration.ofMinutes(30)));  // одиночный тикет
        cacheConfigs.put("comments", defaultConfig.entryTtl(Duration.ofMinutes(10))); // комменты по тикету
        cacheConfigs.put("users", defaultConfig.entryTtl(Duration.ofMinutes(5)));
        cacheConfigs.put("userStatistics", defaultConfig.entryTtl(Duration.ofMinutes(5)));
        cacheConfigs.put("userTickets", defaultConfig.entryTtl(Duration.ofMinutes(5)));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigs)
                .transactionAware()
                .build();
    }


////    @Value("${spring.data.redis.host}")
////    private String redisHost;
////
////    @Value("${spring.data.redis.port}")
////    private int redisPort;
////
////    @Bean
////    public LettuceConnectionFactory redisConnectionFactory() {
////        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
////        return new LettuceConnectionFactory(configuration);
////    }
//
//
////    @Bean
////    public RedisCacheConfiguration defaultCacheConfig(ObjectMapper redisObjectMapper) {
////        StringRedisSerializer keySerializer = new StringRedisSerializer();
////        GenericJackson2JsonRedisSerializer valueSerializer =
////                new GenericJackson2JsonRedisSerializer(redisObjectMapper);
////
////        return RedisCacheConfiguration.defaultCacheConfig()
////                .entryTtl(Duration.ofMinutes(2))
////                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
////                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer));
////    }
}

