package com.momolela.helperclass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class HC03CountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        List list = new ArrayList();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        }

        Long start = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            Thread.sleep(100);
        }
        System.out.println("=====同步执行：耗时" + (System.currentTimeMillis() - start) + "毫秒======");

        Long start1 = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < latch.getCount(); i++) {
            new Thread(new Test(latch, i, list)).start();
        }
        latch.await();
        System.out.println("=====异步执行：耗时" + (System.currentTimeMillis() - start1) + "毫秒======");
    }

    static class Test implements Runnable {
        private CountDownLatch latch;
        private int i;
        private List list;

        Test(CountDownLatch latch, int i, List list) {
            this.latch = latch;
            this.i = i;
            this.list = list;
        }

        @Override
        public void run() {
            for (int a = i * 10; a < (i + 1) * 10; a++) {
                // 执行任务逻辑
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
        }
    }
}
