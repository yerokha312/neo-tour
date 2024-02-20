package com.yerokha.neotour.config;

import com.github.benmanes.caffeine.cache.CaffeineSpec;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                "discoverToursCache",
                "recommendedToursCache",
                "tourDetailsCache");

        cacheManager.setCaffeineSpec(CaffeineSpec.parse("maximumSize=100, expireAfterWrite=30m"));
        return cacheManager;
    }
}
