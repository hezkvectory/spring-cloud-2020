package com.hezk.redis.hyperloglog;

import com.hezk.redis.util.Constants;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

import static com.hezk.commons.utils.HLLUtils.*;

public class UVCounter {
    private Jedis client = new Jedis(Constants.REDIS_IP);

    private static final String PREFIX = "USER:LOGIN:";

    /**
     * 获取周UV
     *
     * @return UV数
     */
    public long getWeeklyUV() {
        List<String> suffixKeys = getLastDays(new Date(), 7);
        List<String> keys = addPrefix(suffixKeys, PREFIX);
        return client.pfcount(keys.toArray(new String[0]));
    }

    /**
     * 获取日UV
     *
     * @return UV数
     */
    public long getDailyUV() {
        List<String> suffixKeys = getLastHours(new Date(), 24);
        List<String> keys = addPrefix(suffixKeys, PREFIX);
        return client.pfcount(keys.toArray(new String[0]));
    }

    /**
     * 获取小时UV
     *
     * @return UV数
     */
    public long getHourlyUV() {
        List<String> suffixKeys = getLastHours(new Date(), 1);
        List<String> keys = addPrefix(suffixKeys, PREFIX);
        return client.pfcount(keys.toArray(new String[0]));
    }
}