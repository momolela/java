package com.momolela.helperclass;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class HC01Semaphore {

    public static void main(String[] args) throws InterruptedException {
        // 设置一个信号量的时候，就相当于一个互斥锁
        BlockQueueObj<String> blockQueueObj = new BlockQueueObj<>(1);
        blockQueueObj.add("sunzj");
        System.out.println("first add" + blockQueueObj);
        blockQueueObj.add("hufy"); // 第二次 add 会阻塞
        System.out.println("second add" + blockQueueObj);
    }


    /**
     * 用 Semaphore 实现阻塞队列
     * 维护一个队列，设置队列边界 size，即 Semaphore 的许可数
     * add 操作前先通过 Semaphore 的 acquire 获取许可（没空位会阻塞），获取许可（说明队列还有空）后再add
     * remove 操作后自动通过 Semaphore 的 release 释放许可，说明队列又有空位了
     */
    static class BlockQueueObj<T> {

        private final Set<T> set;

        private final Semaphore semaphore;

        public BlockQueueObj(int size) {
            semaphore = new Semaphore(size);
            set = Collections.synchronizedSet(new HashSet<>());
        }

        public boolean add(T t) throws InterruptedException {
            boolean wasAdded = false;
            semaphore.acquire();
            try {
                wasAdded = set.add(t);
            } finally {
                if (!wasAdded) {
                    semaphore.release();
                }
            }
            return wasAdded;
        }

        public boolean remove(T t) {
            boolean wasRemoved = set.remove(t);
            if (wasRemoved) {
                semaphore.release();
            }
            return wasRemoved;
        }

        @Override
        public String toString() {
            return "BlockQueueObj{" +
                    "set=" + set +
                    '}';
        }
    }

}
