package com.xzg.ratelimiter.rule;

public interface RateLimitRule {
    ApiLimit getLimit(String appId, String url);
}
