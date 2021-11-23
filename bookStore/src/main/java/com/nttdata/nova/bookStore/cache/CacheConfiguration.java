package com.nttdata.nova.bookStore.cache;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;


@Configuration
@ConditionalOnProperty(value="resolved.cache.enabled", havingValue = "true", matchIfMissing = true)
public class CacheConfiguration {
    @Value("${resolved.cache.ttl:30}")
    private long ttlInMinutes;
    
    @Value("${ENV:env}")
    private String environment;
    
    private static final Logger LOG = LoggerFactory.getLogger(CacheConfiguration.class);

    @Bean(value = "cacheManager")
    public CacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
            LOG.info("[Cache] injecting TTL cache: enabled");
            RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                  .entryTtl(Duration.ofMinutes(ttlInMinutes));
            redisCacheConfiguration.usePrefix();

            return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
                  .cacheDefaults(redisCacheConfiguration).build();
    }
}
