package com.xzg;


public class RateLimiter {
    private RedisCounter redisCounter;
    public RateLimiter(RedisCounter redisCounter) {
        this.redisCounter = redisCounter;
    }
    public void test() {
        System.out.println("Hello World!");
    }
    //...
}