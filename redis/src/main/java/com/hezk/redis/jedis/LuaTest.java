package com.hezk.redis.jedis;

import redis.clients.jedis.Jedis;

public class LuaTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("10.33.1.11", 6379);
//        Object obj = jedis.eval("return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}", Arrays.asList("key1", "key2"), Arrays.asList("first", "second"));
//        System.out.println(obj);

        Object obj1 = jedis.eval("return redis.call('get', KEYS[1])", 1, "str1");
        System.out.println(obj1);
    }

}
