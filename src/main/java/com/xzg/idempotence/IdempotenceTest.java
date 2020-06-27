package com.xzg.idempotence;//package com.xzg.idempotence;
//
//import com.controller.Order;
//import com.controller.OrderController;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//
//public class IdempotenceTest {
//
//    public static void main(String[] args) {
//
/////////// 使用方式一: 在业务代码中处理幂等 ////////////
//// 接口调用方
//        GenericObjectPoolConfig jedisPoolConfig = new GenericObjectPoolConfig();
//        jedisPoolConfig.setMaxTotal(10);
//        jedisPoolConfig.setMaxIdle(5);
//        jedisPoolConfig.setMinIdle(2);
//        jedisPoolConfig.setMaxWaitMillis(5000);
//
//        IdempotenceStorage storage = new RedisClusterIdempotenceStorage("localhost:637", jedisPoolConfig);
//        Idempotence idempotence = new Idempotence(storage);
//        IdempotenceIdGenerator generator = new IdempotenceIdGenerator();
//        String idempotenceId = generator.generateId();
//        Order order = createOrderWithIdempotence("id", "name",idempotenceId);
//
//    }
//}
