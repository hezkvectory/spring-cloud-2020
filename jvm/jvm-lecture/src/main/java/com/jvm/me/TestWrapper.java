package com.jvm.me;

import java.util.Map;
import java.util.Random;

/**
 * 程序计数器、jvm虚拟机栈、本地方法栈、堆、方法区
 * 引用计数器、GC-ROOT可达性分析算法
 * 复制算法、标记清除、标记整理
 * serial(复制)、ParNew(复制)、Parallel Scavenge
 * Serial Old、CMS、Parallel Old
 * G1
 *
 * serial -> serial Old、cms
 * parnew -> serial Old、cms
 * Parallel Sacvenge -> SerialOld、Parallel Old
 *
 *  -XX:+UseSerialGC
 *  -XX:+UseParNewGC
 *  -XX:ParallelGCThreads
 *  -XX:+UseConcMarkSweepGC
 *
 * java -Xmx12m -XX:+UseParallelGC TestWrapper
 * java -Xmx10m -XX:+UseParallelGC TestWrapper
 *
 * CMS 收集器是一种以最短回收停顿时间为目标的收集器，以 “ 最短用户线程停顿时间 ” 著称。整个垃圾收集过程分为 4 个步骤：
 * ① 初始标记：标记一下 GC Roots 能直接关联到的对象，速度较快。
 * ② 并发标记：进行 GC Roots Tracing，标记出全部的垃圾对象，耗时较长。
 * ③ 重新标记：修正并发标记阶段引用户程序继续运行而导致变化的对象的标记记录，耗时较短。
 * ④ 并发清除：用标记-清除算法清除垃圾对象，耗时较长。
 *
 * G1 收集器收集器收集过程
 * ① 初始标记：标记出 GC Roots 直接关联的对象，这个阶段速度较快，需要停止用户线程，单线程执行。
 * ② 并发标记：从 GC Root 开始对堆中的对象进行可达新分析，找出存活对象，这个阶段耗时较长，但可以和用户线程并发执行。
 * ③ 最终标记：修正在并发标记阶段引用户程序执行而产生变动的标记记录。
 * ④ 筛选回收：筛选回收阶段会对各个 Region 的回收价值和成本进行排序，根据用户所期望的 GC 停顿时间来指定回收计划
 * （用最少的时间来回收包含垃圾最多的区域，这就是 Garbage First 的由来——第一时间清理垃圾最多的区块），
 *  这里为了提高回收效率，并没有采用和用户线程并发执行的方式，而是停顿用户线程。
 */
public class TestWrapper {
    public static void main(String args[]) throws Exception {
        Map map = System.getProperties();
        Random r = new Random();
        while (true) {
            map.put(r.nextInt(), "value");
        }
    }
}