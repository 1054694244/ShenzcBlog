package com.shenzc.config;

import com.shenzc.Entity.Article;
import com.shenzc.Entity.Category;
import com.shenzc.Entity.Reply;
import com.shenzc.Entity.User;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;
import java.time.Duration;

/**
 * @author shenzc
 * @create 2019-04-02-9:05
 */
@Configuration
public class RedisConfig {

    @Bean(value = "articleRedisTemplate")
    public RedisTemplate<Object, Article> articleRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Article> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //设置序列化器，默认的是jdk序列化器
        Jackson2JsonRedisSerializer<Article> jsonRedisSerializer = new Jackson2JsonRedisSerializer(Article.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }

    @Bean(value = "replyRedisTemplate")
    public RedisTemplate<Object, Reply> replyRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Reply> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //设置序列化器，默认的是jdk序列化器
        Jackson2JsonRedisSerializer<Reply> jsonRedisSerializer = new Jackson2JsonRedisSerializer(Reply.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }

    @Bean(value = "categoryRedisTemplate")
    public RedisTemplate<Object, Category> categoryRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Category> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //设置序列化器，默认的是jdk序列化器
        Jackson2JsonRedisSerializer<Category> jsonRedisSerializer = new Jackson2JsonRedisSerializer(Category.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }

    @Bean(value = "userRedisTemplate")
    public RedisTemplate<Object, User> userRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, User> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //设置序列化器，默认的是jdk序列化器
        Jackson2JsonRedisSerializer<User> jsonRedisSerializer = new Jackson2JsonRedisSerializer(User.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(24)); // 设置缓存有效期24小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }



}
