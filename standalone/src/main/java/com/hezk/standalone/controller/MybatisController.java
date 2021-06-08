package com.hezk.standalone.controller;

import com.hezk.h2.domain.User;
import com.hezk.h2.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * mysql 压测，参考kafka-tool:2.6.0版本producer压测
 */
@RequestMapping("/mybatis")
@RestController
public class MybatisController {

    private volatile boolean running = false;

    private ExecutorService executorService = Executors.newFixedThreadPool(8);

    Timer timer;

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/benchmark")
    public String insert(@RequestParam(value = "size", defaultValue = "1") int size) {

        StatsBenchmarkProducer statsBenchmark = new StatsBenchmarkProducer();

        timer = new Timer("BenchmarkTimerThread", true);

        final LinkedList<Long[]> snapshotList = new LinkedList<Long[]>();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                snapshotList.addLast(statsBenchmark.createSnapshot());
                if (snapshotList.size() > 10) {
                    snapshotList.removeFirst();
                }
            }
        }, 1000, 1000);

        timer.scheduleAtFixedRate(new TimerTask() {
            private void printStats() {
                if (snapshotList.size() >= 10) {
                    Long[] begin = snapshotList.getFirst();
                    Long[] end = snapshotList.getLast();

                    final long sendTps = (long) (((end[1] - begin[1]) / (double) (end[0] - begin[0])) * 1000L);
                    final double averageRT = (end[3] - begin[3]) / (double) (end[1] - begin[1]);

                    System.out.printf("Insert TPS: %d Max RT: %d Average RT: %7.3f Insert Failed: %d%n",
                            sendTps, statsBenchmark.getInsertSuccessTimeMaxRT().get(), averageRT, end[2]);
                }
            }

            @Override
            public void run() {
                try {
                    this.printStats();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 10000, 10000);

        running = true;
        for (int i = 0; i < size; i++) {
            executorService.submit(() -> {
                int index = 0;
                while (running && !Thread.interrupted()) {
                    final long beginTimestamp = System.currentTimeMillis();

                    User user = new User();
                    user.setName(Thread.currentThread().getName() + ":" + index++);
                    user.setAge((index + 10) % 18);
                    user.setEmail("test" + index + "@gmail.com");
                    int ret = userMapper.insert(user);

                    final long currentRT = System.currentTimeMillis() - beginTimestamp;
                    statsBenchmark.getInsertSuccessTimeTotal().addAndGet(currentRT);
                    if (ret > 0) {
                        statsBenchmark.getInsertSuccessCount().incrementAndGet();
                    } else {
                        statsBenchmark.getInsertFailCount().incrementAndGet();
                    }
                    long prevMaxRT = statsBenchmark.getInsertSuccessTimeMaxRT().get();
                    while (currentRT > prevMaxRT) {
                        boolean updated = statsBenchmark.getInsertSuccessTimeMaxRT().compareAndSet(prevMaxRT, currentRT);
                        if (updated)
                            break;
                        prevMaxRT = statsBenchmark.getInsertSuccessTimeMaxRT().get();
                    }
                }
            });
        }
        return "success";
    }

    @GetMapping("/stop")
    public String stop() {
        running = false;
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }
        return "success";
    }
}

class StatsBenchmarkProducer {
    private final AtomicLong insertSuccessCount = new AtomicLong(0L);

    private final AtomicLong insertFailCount = new AtomicLong(0L);

    private final AtomicLong insertSuccessTimeTotal = new AtomicLong(0L);

    private final AtomicLong insertSuccessTimeMaxRT = new AtomicLong(0L);

    public Long[] createSnapshot() {
        Long[] snap = new Long[]{
                System.currentTimeMillis(),
                this.insertSuccessCount.get(),
                this.insertFailCount.get(),
                this.insertSuccessTimeTotal.get(),
                this.insertSuccessTimeMaxRT.get()
        };
        return snap;
    }

    public AtomicLong getInsertSuccessCount() {
        return insertSuccessCount;
    }

    public AtomicLong getInsertFailCount() {
        return insertFailCount;
    }

    public AtomicLong getInsertSuccessTimeTotal() {
        return insertSuccessTimeTotal;
    }

    public AtomicLong getInsertSuccessTimeMaxRT() {
        return insertSuccessTimeMaxRT;
    }
}
