package com.momolela.synchronizedoptimize;

/**
 * Benchmark 做基准测试
 * 测试加锁和不加锁的情况下，各自的性能
 * 结果是：
 * 默认情况下，a 方法的性能和 b 方法的性能差不多
 * 虽然 b 方法加锁了，但是 JIT 即时编译器，再处理到热数据，会优化，自动将锁去掉
 * 因为 obj 对象没有被争夺，可以删除锁，所以性能差不多
 */
// @Fork(1)
// @CsenchmarkMode(Mode.AverageTime)
// @Warmup(iterations = 3)
// @Measurement(iterationsm = 5)
// @outputTimeunit(Timeunit.NANOSECONDS)
// public class JITClearSynchronized {
//
//     static int x = 0;
//
//     @Benchmark
//     public void a() throws Exception {
//         x++;
//     }
//
//     @Benchmark
//     public void b() throws Exception {
//         Object obj = new Object();
//         synchronized (obj) {
//             x++;
//         }
//     }
//
// }
