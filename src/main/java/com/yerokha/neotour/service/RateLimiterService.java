package com.yerokha.neotour.service;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.yerokha.neotour.config.CacheConfig.MAX_REQUESTS_PER_SECOND;

@Component
public class RateLimiterService {

    @Autowired
    private Cache<String, Integer> requestCountCache;

    public boolean allowRequest(HttpServletRequest request) {
        String ipAddress = getClientIpAddress(request);
        Integer requestCount = requestCountCache.getIfPresent(ipAddress);
        if (requestCount != null && requestCount >= MAX_REQUESTS_PER_SECOND) {
            return false;
        }
        requestCountCache.put(ipAddress, requestCount == null ? 1 : requestCount + 1);
        return true;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader != null && !xForwardedForHeader.isEmpty()) {
            return xForwardedForHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
