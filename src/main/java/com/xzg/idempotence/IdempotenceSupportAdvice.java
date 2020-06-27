package com.xzg.idempotence;//package com.xzg.idempotence;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Aspect
//public class IdempotenceSupportAdvice {
//    @Autowired
//    private Idempotence idempotence;
//
//    @Pointcut("@annotation(com.xzg.idempotence.annotation.IdempotenceRequired)")
//    public void controllerPointcut() {
//    }
//
//    @Around(value = "controllerPointcut()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        // 从HTTP header中获取幂等号idempotenceId
//        String idempotenceId = parseId(joinPoint);
//
//        // 前置操作
//        boolean existed = idempotence.check(idempotenceId);
//
//        if (existed) {
//            // 两种处理方式：
//            // 1. 查询order，并且返回；
//            // 2. 返回duplication operation Exception
//        }
//        idempotence.record(idempotenceId);
//
//        Object result = joinPoint.proceed();
//        return result;
//    }
//}