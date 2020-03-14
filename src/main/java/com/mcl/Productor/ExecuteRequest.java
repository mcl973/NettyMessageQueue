/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ExecuteRequest
 * Author:   Administrator
 * Date:     2020/03/14 13:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.Productor;

import com.mcl.Custom.AbstractCoustom;
import com.mcl.ExecutorsPool.GetThread;
import com.mcl.ExecutorsPool.RunnableAndCallable.Implemants.WriteImpl.HandlerRequestRunnableEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/14
 * @since 1.0.0
 */
public class ExecuteRequest implements Runnable{
    private BlockingQueue<AbstractCoustom> blockingQueue;
    private ExecutorService executorService = GetThread.getThreadExecutor();
    public ExecuteRequest(BlockingQueue<AbstractCoustom> blockingQueue){
        this.blockingQueue = blockingQueue;
    }
    @Override
    public void run() {
        while (true){
            try {
                AbstractCoustom take;
                if (blockingQueue.size() != 0) {
                    take = blockingQueue.take();
                    executorService.execute(new HandlerRequestRunnableEvent(take));
                }else {
//                    System.out.println("//////////////"+blockingQueue.size());
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
