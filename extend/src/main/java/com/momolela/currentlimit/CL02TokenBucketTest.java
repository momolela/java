package com.momolela.currentlimit;

import com.google.common.util.concurrent.RateLimiter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class CL02TokenBucketTest {
    public static void main(String[] args) throws Exception {

        // // 因为令牌桶的加令牌速率是 100/s 个，所以限流也是每秒 100 个
        // // TokenBucketSimple tokenBucket = TokenBucketSimple.newBuilder().maxFlowRate(100).period(10).build();
        // TokenBucketInGuava tokenBucket = new TokenBucketInGuava(100, 10);
        // BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/sunzj/text.txt")));
        // for (int i = 1; i <= 1000; i++) {
        //     TimeUnit.MILLISECONDS.sleep(10);
        //     boolean token = tokenBucket.getToken();
        //     if (token) {
        //         bufferedWriter.write("token pass --- index:" + i);
        //         System.out.println("token pass --- index:" + i);
        //     } else {
        //         bufferedWriter.write("token reject --- index：" + i);
        //         System.out.println("token reject --- index：" + i);
        //     }
        //     bufferedWriter.newLine();
        //     bufferedWriter.flush();
        // }
        // bufferedWriter.close();

        RateLimiter rateLimiter = RateLimiter.create(100);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/sunzj/text.txt")));
        for (int i = 1; i <= 1000; i++) {
            TimeUnit.MILLISECONDS.sleep(5);
            boolean b = rateLimiter.tryAcquire();
            if (b) {
                bufferedWriter.write("token pass --- index:" + i);
                System.out.println("token pass --- index:" + i);
            } else {
                bufferedWriter.write("token reject --- index：" + i);
                System.out.println("token reject --- index：" + i);
            }
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

    }


    static class TokenBucketSimple {

        private final int DEFAULT_MAX_FLOW_RATE = 200;

        private int MAX_FLOW_RATE;

        private long PERIOD;

        private final byte TOKEN_CHAR = 'A';

        private final ReentrantLock lock = new ReentrantLock(true);

        private ArrayBlockingQueue<Byte> tokenQueue = new ArrayBlockingQueue<Byte>(DEFAULT_MAX_FLOW_RATE);

        public TokenBucketSimple() {
        }

        public TokenBucketSimple(int maxFlowRate, long period) {
            this.MAX_FLOW_RATE = maxFlowRate;
            this.PERIOD = period;
        }

        public static TokenBucketSimple newBuilder() {
            return new TokenBucketSimple();
        }

        public void addToken() {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (MAX_FLOW_RATE > tokenQueue.size()) {
                        tokenQueue.offer(TOKEN_CHAR);
                    }
                }
            }, 0, PERIOD);
        }

        public Boolean getToken() {
            Byte poll = tokenQueue.poll();
            if (poll == null) {
                return false;
            } else {
                return true;
            }
        }

        public TokenBucketSimple maxFlowRate(int maxFlowRate) {
            this.MAX_FLOW_RATE = maxFlowRate;
            return this;
        }

        public TokenBucketSimple period(long period) {
            this.PERIOD = period;
            return this;
        }

        public TokenBucketSimple build() {
            this.start();
            return this;
        }

        private void start() {
            if (MAX_FLOW_RATE != 0) {
                tokenQueue = new ArrayBlockingQueue<Byte>(MAX_FLOW_RATE);
            }
            // 默认加 MAX_FLOW_RATE 个 token进去
            for (int i = 0; i < MAX_FLOW_RATE; i++) {
                tokenQueue.offer(TOKEN_CHAR);
            }
            // 开启定时任务加入
            addToken();
        }
    }


    static class TokenBucketInGuava {
        // 上一次获取令牌的时间
        private AtomicLong beforeAtomic;
        // 生成令牌的时间间隔
        private int interval;
        // 最大令牌数量
        private int max;
        // 用于控制令牌数量修改的可重入锁
        private ReentrantLock lock;

        public TokenBucketInGuava(int max, int interval) {
            this.beforeAtomic = new AtomicLong(System.currentTimeMillis() - interval * max);
            this.interval = interval;
            this.max = max;
            this.lock = new ReentrantLock(true);
        }

        public boolean getToken() {
            boolean flag = false;
            // 当前线程自旋尝试获取令牌
            while (!flag) {
                // 当前获取令牌时间
                long now = System.currentTimeMillis();
                // 上一次获取令牌时间
                long before = this.beforeAtomic.get();
                // 计算时间间隔内生成的令牌数
                long tokenSum = ((now - before) / interval);
                // 有剩余令牌，则尝试获取令牌
                if (tokenSum > 0) {
                    boolean locked = false;
                    // 如果令牌数量超过设置的上限，则将令牌数量归于上限值
                    if (tokenSum > max) {
                        // 可重入锁对令牌数修改操作进并发访问控制
                        locked = lock.tryLock();
                        if (locked) {
                            // 成功拿到锁，则进行修改，将令牌数量归于上限值
                            this.beforeAtomic.set(now - interval * max + interval);
                            flag = true;
                            lock.unlock();
                        }
                    }
                    // 若尝试获得锁失败，说明另一个线程正在修改最后一次获得令牌的时间
                    if (!locked) {
                        // CAS尝试修改最后一次获取令牌的时间，成功则完成这次获取令牌操作，否则继续自旋尝试获得令牌
                        flag = this.beforeAtomic.compareAndSet(before, before + interval);
                    }
                } else {
                    // 无剩余令牌，尝试获取令牌失败
                    return false;
                }
            }
            return true;
        }
    }

}
