package com.xzg.ratelimiter.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieRateLimitRule implements RateLimitRule {
    private Map<String /*appId*/, Map<String /*api*/, ApiLimit>> map = new HashMap<>();

    public TrieRateLimitRule(RuleConfig ruleConfig) {
        List<RuleConfig.AppRuleConfig> configs = ruleConfig.getConfigs();
        for (RuleConfig.AppRuleConfig config : configs) {
            String appId = config.getAppId();
            map.putIfAbsent(appId, new HashMap<>());
            List<ApiLimit> limits = config.getLimits();
            for (ApiLimit limit : limits) {
                String api = limit.getApi();
                map.get(appId).putIfAbsent(api, limit);
            }
        }
    }

    @Override
    public ApiLimit getLimit(String appId, String url) {
        ApiLimit limit = null;
        if (map.containsKey(appId)) {
            limit = map.get(appId).getOrDefault(url, null);
        }
        return limit;
    }
}
