package com.hezk.redis;

import com.hezk.redis.hyperloglog.UVCounter;

public class Application {

    public static void main(String[] args) {
        UVCounter uvCounter = new UVCounter();

        uvCounter.getDailyUV();
    }
}
