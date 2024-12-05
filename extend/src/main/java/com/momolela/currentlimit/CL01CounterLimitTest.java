package com.momolela.currentlimit;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数器限流
 */
public class CL01CounterLimitTest {
    public static void main(String[] args) {
        // FixedCounterLimit slideCounterLimit = new FixedCounterLimit(60L, ChronoUnit.SECONDS, 100);
        SlideCounterLimit slideCounterLimit = new SlideCounterLimit(60L, ChronoUnit.SECONDS, 100, 10);

        // for (int i = 0; i < 10; i++) {
        //     new Thread(() -> {
        //         for (int i1 = 0; i1 < 11; i1++) {
        //             if (slideCounterLimit.tryLimit()) {
        //                 System.out.println("限流了" + slideCounterLimit.getCount());
        //             } else {
        //                 System.out.println("没限流" + slideCounterLimit.getCount());
        //             }
        //         }
        //     }).start();
        // }

        for (; ; ) {
            // 模拟一秒
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Random random = new Random();
            int i = random.nextInt(3);
            // 模拟 1 秒内请求 1 次
            if (i == 1) {
                if (slideCounterLimit.tryLimit()) {
                    System.out.println("限流了" + slideCounterLimit.getCount());
                } else {
                    System.out.println("没限流" + slideCounterLimit.getCount());
                }
            } else if (i == 2) { // 模拟 1 秒内请求 2 次
                for (int j = 0; j < 2; j++) {
                    if (slideCounterLimit.tryLimit()) {
                        System.out.println("限流了" + slideCounterLimit.getCount());
                    } else {
                        System.out.println("没限流" + slideCounterLimit.getCount());
                    }
                }
            } else { // 模拟 1 秒内请求 10 次
                for (int j = 0; j < 10; j++) {
                    if (slideCounterLimit.tryLimit()) {
                        System.out.println("限流了" + slideCounterLimit.getCount());
                    } else {
                        System.out.println("没限流" + slideCounterLimit.getCount());
                    }
                }
            }
        }
    }
}

/**
 * 固定窗口
 */
class FixedCounterLimit {

    // 结束时间
    private LocalDateTime endDate;
    // 时长
    private final Long time;
    // 时长单位
    private final TemporalUnit unit;
    // 阈值
    private final Integer countLine;
    // 动态计数器
    private final AtomicInteger count;

    public FixedCounterLimit(Long time, TemporalUnit unit, Integer countLine) {
        this.time = time;
        this.unit = unit;
        this.countLine = countLine;
        this.count = new AtomicInteger(0);
    }

    public boolean tryLimit() {
        if (this.endDate == null) {
            // 首次使用的情况，需要在调用的时候进行初始化结束时间
            this.endDate = LocalDateTime.now().plus(this.time, this.unit);
        }
        if (LocalDateTime.now().isAfter(this.endDate)) {
            // 大于时间范围，计数器清零，结束时间为当前时间
            this.count.set(0);
            this.endDate = LocalDateTime.now().plus(this.time, this.unit);
        } else {
            // 没到时间，计数器加 1
            this.count.addAndGet(1);
        }
        return this.count.get() > this.countLine;
    }

    public int getCount() {
        return this.count.get();
    }
}

/**
 * 滑动窗口
 */
class SlideCounterLimit {

    /**
     * 结束时间，上次统计时间
     */
    private long lastDateMilliTime = 0L;
    /**
     * 每个窗口的时长，6s
     */
    private final long milliTimeOffset;
    /**
     * 阈值，100 个
     */
    private final Integer countLine;
    /**
     * 当前的窗口索引
     */
    private int currentWindowIndex;
    /**
     * 窗口个数，10 个
     */
    private final int windowSize;
    /**
     * 每个窗口分开统计个数
     */
    private final AtomicInteger[] windowReqCountArr;

    public SlideCounterLimit(Long time, TemporalUnit unit, Integer countLine, Integer windowSize) {

        this.countLine = countLine;
        this.windowSize = windowSize;

        LocalDateTime now = LocalDateTime.now();
        // 限流时间范围
        long milliTime = now.plus(time, unit).toInstant(ZoneOffset.ofHours(8)).toEpochMilli() - now.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        if (milliTime % this.windowSize != 0) {
            // 窗口个数必须能被限流时间整除，否则抛出异常
            throw new RuntimeException("windowSize " + windowSize + " is not valid");
        }

        // 窗口计数，[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        this.windowReqCountArr = new AtomicInteger[this.windowSize];
        Arrays.fill(this.windowReqCountArr, new AtomicInteger(0));

        // 每个窗口的时间
        this.milliTimeOffset = milliTime / windowSize;
    }

    public boolean tryLimit() {
        LocalDateTime now = LocalDateTime.now();
        if (this.lastDateMilliTime == 0L) {
            // 初始化时间，即上次统计时间
            this.lastDateMilliTime = now.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        }
        // 经过了多长时间
        long nowMilliTimeOffset = now.toInstant(ZoneOffset.ofHours(8)).toEpochMilli() - this.lastDateMilliTime;
        if (nowMilliTimeOffset >= this.milliTimeOffset) {
            // 如果经过的时间大于一个窗口时间，到第二个窗口
            this.currentWindowIndex++;
            // 如果加一个窗口后，已经超出了最后一个窗口则回到第一个窗口，10 % 10 得到 0
            this.currentWindowIndex = this.currentWindowIndex % this.windowSize;
            if (this.currentWindowIndex == 0) {
                if (Arrays.stream(this.windowReqCountArr).mapToInt(AtomicInteger::get).sum() > this.countLine) {
                    return true;
                }
                // 回到第一个窗口，重置
                Arrays.fill(this.windowReqCountArr, new AtomicInteger(0));
                this.lastDateMilliTime = 0L;
            } else {
                // 顺位到下一个窗口
                this.windowReqCountArr[this.currentWindowIndex] = new AtomicInteger(1);
                this.lastDateMilliTime = now.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
            }
        } else {
            // 仍然在当前窗口，窗口数加 1
            this.windowReqCountArr[this.currentWindowIndex].addAndGet(1);
        }
        int sum = Arrays.stream(this.windowReqCountArr).mapToInt(AtomicInteger::get).sum();
        return sum > this.countLine;
    }

    public int getCount() {
        return Arrays.stream(this.windowReqCountArr).mapToInt(AtomicInteger::get).sum();
    }

}