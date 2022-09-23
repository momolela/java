package com.momolela.jmh;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) // 计算平均耗时，Throughput：吞吐量，
@Fork(1) // 指定 fork 出多少个子进程来执行同一基准测试方法。假设我们不需要多个进程，那么 可以使用 @Fork 指定为进程数为 1
@Measurement(iterations = 5) // 测试 5 次
@Warmup(iterations = 3) // 为了数据准确，预热 5 次，jvm 使用 JIT 即时编译器，一定的预热次数可让 JIT 对方法的调用链路完成编译，去掉解释执行对测试结果的影响
@OutputTimeUnit(TimeUnit.NANOSECONDS) // 指定输出的耗时时长的单位
public class JMHTest {
    static int[] ARRAY = new int[1000_000_00];

    static {
        Arrays.fill(ARRAY, 1);
    }

    @Benchmark
    public int run1() throws Exception {
        int[] array = ARRAY;
        FutureTask<Integer> t1 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < array.length / 4; i++) {
                sum += array[i];
            }
            return sum;
        });
        FutureTask<Integer> t2 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < array.length / 4; i++) {
                sum += array[i];
            }
            return sum;
        });
        FutureTask<Integer> t3 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < array.length / 4; i++) {
                sum += array[i];
            }
            return sum;
        });
        FutureTask<Integer> t4 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < array.length / 4; i++) {
                sum += array[i];
            }
            return sum;
        });
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
        new Thread(t4).start();
        return t1.get() + t2.get() + t3.get() + t4.get();
    }

    @Benchmark
    public int run2() throws Exception {
        FutureTask<Integer> t1 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < 1000_000_00; i++) {
                sum += ARRAY[i];
            }
            return sum;
        });
        new Thread(t1).start();
        return t1.get();
    }
}
