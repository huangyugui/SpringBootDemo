package com.example;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.Data;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class RedisTemplateTest {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Test
    public void redisAddTest() throws Exception{
//        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
//        opsForValue.set("test", "test333333333");
//        System.out.println(redisTemplate.opsForValue().get("test"));
//        System.out.println(opsForValue.getAndSet("test", "testtesttest"));
//        System.out.println(opsForValue.append("test", "11111111"));
//        System.out.println(opsForValue.multiGet(Arrays.asList(new String[]{"test", "test1"})));
//        System.out.println(opsForValue.setIfAbsent("test", "这个key有value了"));
//        System.out.println(opsForValue.size("test"));
//        opsForValue.set("timeout", "2s", 2, TimeUnit.SECONDS);
//        System.out.println(opsForValue.get("timeout"));
//        Thread.sleep(1000);
//        System.out.println(opsForValue.get("timeout") + "==========> 1 second is pass");
//        Thread.sleep(1100);
//        System.out.println(opsForValue.get("timeout") + "==========> 2.1 second is pass");
//        redisTemplate.delete("test");
        
        System.out.println("===========================================================");
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("aaa", "aaa");
        map.put("bbb", 22);
        map.put("ccc", true);
        map.put("ddd", new RedisObject());
        opsForHash.putAll("map", map);
        System.out.println(opsForHash.get("map", "ddd"));
        System.out.println(opsForHash.get("map", "bbb"));
        System.out.println(opsForHash.entries("map"));
        System.out.println(opsForHash.hasKey("map", "ttt"));
        System.out.println(opsForHash.get("map", "bbb"));
//        System.out.println(opsForHash.scan(key, options));
        Map<String, Integer> intMap = new HashMap<String, Integer>();
        intMap.put("aaa", 11);
        intMap.put("bbb", 222);
        opsForHash.putAll("intMap", map);
//        System.out.println(opsForHash.increment("intMap", "bbb", 33));
        
        
    }

}

@Data
class RedisObject implements Serializable{
    private int id;
    private String name;
    private boolean flag;
}
