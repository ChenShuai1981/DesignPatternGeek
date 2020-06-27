package com.example.ratelimiter.app;

public class RateLimiterTest {

    public static void main(String[] args) throws Exception {
        String appId = "app-1"; // 调用方APP-ID
        String url = "v1/user";// 请求url
        RateLimiter ratelimiter = new RateLimiter();
        for (int i=0; i<3; i++) {
            boolean passed = ratelimiter.limit(appId, url);
            if (passed) {
                // 放行接口请求，继续后续的处理。
                System.out.println("放行接口请求，继续后续的处理");
            } else {
                // 接口请求被限流。
                System.out.println("接口请求被限流");
            }
        }
    }
}
