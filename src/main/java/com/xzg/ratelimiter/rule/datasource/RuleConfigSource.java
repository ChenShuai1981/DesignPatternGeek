package com.xzg.ratelimiter.rule.datasource;

import com.xzg.ratelimiter.rule.RuleConfig;

public interface RuleConfigSource {
    RuleConfig load();
}
