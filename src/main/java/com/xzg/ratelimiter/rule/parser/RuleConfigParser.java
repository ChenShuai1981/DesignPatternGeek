package com.xzg.ratelimiter.rule.parser;

import com.xzg.ratelimiter.rule.RuleConfig;

import java.io.InputStream;

public interface RuleConfigParser {
    RuleConfig parse(String configText);
    RuleConfig parse(InputStream in);
}
