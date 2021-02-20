package com.hezk.redis.test;

import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class StringApplicationRunner implements ApplicationRunner {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        RBucket<Integer> al = redissonClient.getBucket("str");
//        al.set(1234, 10, TimeUnit.SECONDS);
//        System.out.println(al.getIdleTime());

        RMapCache<String, String> cache = redissonClient.getMapCache("cache");
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");

        System.out.println(cache.get("k1"));
        System.out.println(cache.get("k4"));


//        threadPoolTaskExecutor.execute(() -> {
//            RLock rwl = redissonClient.getLock("dd");
//            rwl.lock();
//            System.out.println("StringApplicationRunner.run start thread");
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("StringApplicationRunner.run end thread");
//            rwl.unlock();
//        });
//
//        TimeUnit.SECONDS.sleep(1);
//        RLock rwl = redissonClient.getLock("dd");
//        rwl.lock();
//        System.out.println("------1");
//        TimeUnit.SECONDS.sleep(10);
//        System.out.println("StringApplicationRunner.run main");
//        rwl.unlock();

//        RDeque deque = redissonClient.getDeque("deque");
//        deque.addFirst("firset");
//        deque.addFirst("second");
//        deque.addLast("dd");
//        System.out.println(deque.size());
//        deque.clear();
//
//        RList rlist = redissonClient.getList("list");
//        rlist.add("firset");
//        rlist.add("second");
//        rlist.add("dd");
//        System.out.println(rlist.size());
//        rlist.clear();
//
//        RSet<String> rset1 = redissonClient.getSet("set1");
//        rset1.clear();
//        rset1.add("s1");
//        rset1.add("s2");
//        System.out.println(rset1.size());
//
//
//        RSet<String> rset2 = redissonClient.getSet("set2");
//        rset2.clear();
//        rset2.add("s23");
//        rset2.add("s2");
//        System.out.println(rset2.size());
//
//        RSet<String> rset3 = redissonClient.getSet("set3");
//        rset3.clear();
//        rset3.union("set1", "set2");
//        for (Iterator<String> iterator = rset3.iterator(); iterator.hasNext(); ) {
//            System.out.println(iterator.next());
//        }
//
//        RScoredSortedSet<String> rss = redissonClient.getScoredSortedSet("sss");
//        rss.clear();
//        rss.add(1, "1");
//        rss.add(2, "2");
//
//        RMap<String, Integer> map = redissonClient.getMap("map");
//        map.clear();
//        map.put("k1", 1);
//        map.put("k2", 2);

//        RPriorityQueue<String> pq = redissonClient.getPriorityQueue("pq");
//        pq.add("3");
//        pq.add("1");
//        pq.add("2");

//        RListMultimap rlist = redissonClient.getListMultimap("lm");
//        rlist.clear();
//        rlist.put("h1", "va1");
//        rlist.put("h1", "va11");
//        rlist.put("h1", "va122");
//        rlist.put("h2", "va2");
//        rlist.put("h3", "va3");

//        RBitSet bs = redissonClient.getBitSet("bs");
//        bs.set(System.currentTimeMillis()/100000);
    }
}
