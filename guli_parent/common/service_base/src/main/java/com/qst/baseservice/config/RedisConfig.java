package com.qst.baseservice.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/21
 * @description     redis配置类
 *
 *     spring包中提供了许多关于缓存的注解，这些注解Redis、SpringCache等缓存都可以用
 *     几个常用的：
 *       @Cacheable : 在方法上使用,根据方法对其返回结果进行缓存，下次请求时，如果缓存存在，则直接读取缓存数据返回；
 *                      如果缓存不存在，则执行方法，并把返回的结果存入缓存中。一般用在查询方法上。
 *       @CachePut : 在方法上使用，使用该注解标志的方法，>>注意：每次都会执行<<，并将结果存入指定的缓存中。
 *                  其他方法可以直接从相应的缓存中读取缓存数据，而不需要再去查询数据库。一般用在新增方法上。
 *          @Cacheable和@CachePut的参数：
 *              value： 缓存名，必填，它指定了你的缓存存放在哪块命名空间(当value和key属性同时存在时，
 *                       会在缓存中会创建一个名为"value值"的文件夹，文件夹中村的是key为 value值::key)
 *                       例如@Cacheable(value = "banner",key = "'selectIndexList'")
 *                       在缓存中就是db0下有一个banner文件夹,文件夹中是key-value结构的数据，key就为banner::selectIndexList
 *              cacheNames: 与 value 差不多，二选一即可
 *              key : 可选属性，可以使用 SpEL 标签自定义缓存的key
 *       @CacheEvict : 在方法上使用,使用该注解标志的方法，会清空指定的缓存。一般用在更新或者删除方法上
 *          @CacheEvict参数：
 *              value
 *              cacheNames:
 *              key
 *              allEntries : 是否清空所有缓存，默认为 false。如果指定为 true，则方法调用后将立即清空所有的缓存
 *              beforeInvocation : 是否在方法执行前就清空，默认为 false。如果指定为 true，则在方法执行前就会清空缓存
 *
 *
 */
@EnableCaching  //开启缓存
@Configuration
@SuppressWarnings("all")
public class RedisConfig extends CachingConfigurerSupport {

    /**
     *  redis模板
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setConnectionFactory(factory);
        //key序列化方式
        template.setKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //value hashmap序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    /**
     *  缓存管理插件  可以在里面设置缓存中的数据的过去时间等
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置序列化（解决乱码的问题）,设置缓存的数据的过期时间为600秒
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(600))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }
}

