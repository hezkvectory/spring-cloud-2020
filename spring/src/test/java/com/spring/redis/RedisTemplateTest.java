package com.spring.redis;

import com.hezk.redis.RedisApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = RedisApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTemplateTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void test1() {
        stringRedisTemplate.opsForValue().set("h" + System.currentTimeMillis(), "" + System.currentTimeMillis());
        redisTemplate.opsForValue().set("hezk", "hezk");
    }
}
