package com.xzg.ratelimiter;

import com.xzg.ratelimiter.alg.FixedTimeWinRateLimitAlg;
import com.xzg.ratelimiter.alg.RateLimitAlg;
import com.xzg.ratelimiter.exception.InternalErrorException;
import com.xzg.ratelimiter.exception.InvalidUrlException;
import com.xzg.ratelimiter.rule.ApiLimit;
import com.xzg.ratelimiter.rule.RateLimitRule;
import com.xzg.ratelimiter.rule.RuleConfig;
import com.xzg.ratelimiter.rule.TrieRateLimitRule;
import com.xzg.ratelimiter.rule.datasource.FileRuleConfigSource;
import com.xzg.ratelimiter.rule.datasource.RuleConfigSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;


public class RateLimiter {
    private static final Logger log = LoggerFactory.getLogger(RateLimiter.class);
    // 为每个api在内存中存储限流计数器
    private ConcurrentHashMap<String, RateLimitAlg> counters = new ConcurrentHashMap<>();
    private RateLimitRule rule;

    public RateLimiter() {
        //改动主要在这里：调用RuleConfigSource类来实现配置加载
        RuleConfigSource configSource = new FileRuleConfigSource();
        RuleConfig ruleConfig = configSource.load();
        this.rule = new TrieRateLimitRule(ruleConfig);
    }

    public boolean limit(String appId, String url) throws InternalErrorException, InvalidUrlException {
        ApiLimit apiLimit = rule.getLimit(appId, url);
        if (apiLimit == null) {
            return true;
        }

        // 获取api对应在内存中的限流计数器（rateLimitCounter）
        String counterKey = appId + ":" + apiLimit.getApi();
        RateLimitAlg rateLimitCounter = counters.get(counterKey);
        if (rateLimitCounter == null) {
            RateLimitAlg newRateLimitCounter = new FixedTimeWinRateLimitAlg(apiLimit.getLimit());
            rateLimitCounter = counters.putIfAbsent(counterKey, newRateLimitCounter);
            if (rateLimitCounter == null) {
                rateLimitCounter = newRateLimitCounter;
            }
        }

        // 判断是否限流
        return rateLimitCounter.tryAcquire();
    }
}