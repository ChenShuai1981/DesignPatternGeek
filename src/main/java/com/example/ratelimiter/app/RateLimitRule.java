package com.example.ratelimiter.app;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateLimitRule {
    Map<String /*appId*/, Map<String /*api*/, ApiLimit>> map = new HashMap<>();

    public RateLimitRule(RuleConfig ruleConfig) {
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

    public ApiLimit getLimit(String appId, String api) {
        ApiLimit limit = null;
        if (map.containsKey(appId)) {
            limit = map.get(appId).getOrDefault(api, null);
        }
        return limit;
    }
}