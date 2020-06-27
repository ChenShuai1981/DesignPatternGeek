package com.xzg.ratelimiter.alg;

public abstract class RateLimitAlg {
    public abstract boolean tryAcquire();
}
