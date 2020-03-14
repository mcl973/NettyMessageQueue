/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: GetThread
 * Author:   Administrator
 * Date:     2020/03/12 22:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.ExecutorsPool;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

import java.util.concurrent.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/12
 * @since 1.0.0
 */
public class GetThread {
    private static final int Default_Thread_Number = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors()*2));
    private static ExecutorService executorService1 = null;
    private final static int maxtrytimes = 10;
    private final static Object o = new Object();
    private static class MyRejectHandler implements RejectedExecutionHandler{
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            int i = 0;
            while(i<maxtrytimes){
                executor.execute(r);
                i++;
            }
            System.out.println("----log----:"+r.getClass().getName()+"被拒绝");
        }
    }

    public static ExecutorService getThreadExecutor(){
        if (executorService1 == null) {
            synchronized (o) {
                if (executorService1 == null)
                    executorService1 = new ThreadPoolExecutor(Default_Thread_Number, Default_Thread_Number, 60, TimeUnit.SECONDS,
                            new ArrayBlockingQueue<Runnable>(Default_Thread_Number*2), Executors.defaultThreadFactory(),
                            new MyRejectHandler());
                return executorService1;
            }
        }
        return executorService1;
    }

    public static ExecutorService getThreadExecutor(int corePoolSize,
                                                    int maximumPoolSize,
                                                    long keepAliveTime,
                                                    TimeUnit unit,
                                                    BlockingQueue<Runnable> workQueue){
        return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, unit,
                workQueue,Executors.defaultThreadFactory(),
                new MyRejectHandler());
    }

    public static ExecutorService getThreadExecutor(int corePoolSize,
                                                    int maximumPoolSize,
                                                    long keepAliveTime,
                                                    TimeUnit unit,
                                                    BlockingQueue<Runnable> workQueue,RejectedExecutionHandler handler){
        return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, unit,
                workQueue,Executors.defaultThreadFactory(),
                handler);
    }
}
