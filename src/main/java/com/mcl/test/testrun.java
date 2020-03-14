/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: testrun
 * Author:   Administrator
 * Date:     2020/03/13 18:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.test;

import com.mcl.ExecutorsPool.GetThread;
import com.mcl.MessageManager.CreateProductorCoustom;
import com.mcl.MessageManager.MessageManager;
import com.mcl.NettyModel.NettyServer.NettyServer;
import com.mcl.Productor.Productor;
import java.util.Map;

import java.util.concurrent.ExecutorService;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/13
 * @since 1.0.0
 */
public class testrun {
    public static void main(String[] args) {
        MessageManager messageManager = MessageManager.getMessageManager();
        //获取线程池
        ExecutorService threadExecutor = GetThread.getThreadExecutor();
        //一句代码产生生产者
        messageManager.AddProductor(CreateProductorCoustom.getProductor("Ack"));
//        messageManager.AddCoustom(CreateProductorCoustom.getCoustom("ack",channel));
        //开启netty服务
        threadExecutor.execute(new NettyServer());

        //模拟netty业务
        for(Map.Entry<String,Productor> map:messageManager.productors.entrySet()) {
            threadExecutor.execute(new testproductor(map.getValue()));
        }
    }
    static class testproductor implements Runnable{
        Productor productor;
        public testproductor(Productor productor){
            this.productor = productor;
        }

        @Override
        public void run() {
            while(true){
                productor.BrocastToCustoms("这是测试数据，不用管"+System.currentTimeMillis());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
