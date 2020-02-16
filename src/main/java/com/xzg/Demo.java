package com.xzg;


import com.caselchen.designpattern.factory.ApplicationContext;
import com.caselchen.designpattern.factory.ClassPathXmlApplicationContext;

public class Demo {
    public static void main(String[] args) throws Exception {
        Class beanClass = Class.forName("com.xzg.RedisCounter");
        Class arg1Class = Class.forName("java.lang.String");
        Class arg2Class = Class.forName("java.lang.Integer");
        Class[] argClasses = {arg1Class, arg2Class};
        Object[] argValues = {"127.0.0.1", 1234};
        RedisCounter redisCounter = (RedisCounter) beanClass.getConstructor(argClasses).newInstance(argValues);
        System.out.println(redisCounter);

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "beans.xml");
        RateLimiter rateLimiter = (RateLimiter) applicationContext.getBean("rateLimiter");
        rateLimiter.test();
    }
}