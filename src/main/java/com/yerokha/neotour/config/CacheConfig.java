package com.yerokha.neotour.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final int MAX_REQUESTS_PER_SECOND = 5;
    private static final int EXPIRATION_TIME_IN_SECONDS = 1;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                "tourListsCache",
                "tourDetailsCache");

        cacheManager.setCaffeineSpec(CaffeineSpec.parse("maximumSize=100, expireAfterWrite=30m"));
        return cacheManager;
    }

    @Bean
    public Cache<String, Integer> requestCountCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(EXPIRATION_TIME_IN_SECONDS, TimeUnit.SECONDS)
                .maximumSize(MAX_REQUESTS_PER_SECOND)
                .build();
    }
}
