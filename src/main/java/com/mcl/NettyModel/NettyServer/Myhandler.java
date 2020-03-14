/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Myhandler
 * Author:   Administrator
 * Date:     2020/03/13 12:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.NettyModel.NettyServer;

import com.mcl.ExecutorsPool.GetThread;
import com.mcl.ExecutorsPool.RunnableAndCallable.Implemants.RegisterImpl.RegisterImpl;
import com.mcl.ExecutorsPool.RunnableAndCallable.Implemants.UnRegisterImpl.UnRegisterImpl;
import com.mcl.ExecutorsPool.RunnableAndCallable.Implemants.WriteImpl.WriteImpl;
import com.mcl.MessageManager.ProductorConCurrentHashMap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ExecutorService;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/13
 * @since 1.0.0
 */
/*
    后面使用profbuf来传递数据。
    客户端在登录的时候需要注意的是，必须要将自己的名字和定于的生产者发送给服务器。
    这个生产者的名字和本地的名字，保留在客户端里。
       这里相当于是集注册，取消注册，和索取数据的中转站控制器，使用线程池来缓解netty的单线程问题。
 */
public class Myhandler extends SimpleChannelInboundHandler<String> implements ProductorConCurrentHashMap {

    private static ExecutorService executorService = GetThread.getThreadExecutor();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        String[] cquptmcls = s.split("cquptmcl");
        /*
            分为四个部分，后面将使用protobuf来代替string
                1.代号
                   1.订阅或是不订阅
                        1.生产者名字
                        2.消费者名字
                        3.0 取消订阅 1 订阅
                   2.拉取数据
                        1.生产者名字
                        2.消费者名字
                        3.默认为null
                   3.传递数据
                        1.生产者名字
                        2.消费者名字
                        3.传递的数据
         */
        System.out.println(s);
        String stringBuilder = cquptmcls[1] +"cquptmcl"+
                cquptmcls[2] +"cquptmcl"+
                cquptmcls[3];
        int isregister = -1;
        if (cquptmcls[3].equals("1") || cquptmcls[3].equals("0"))
            isregister = Integer.parseInt(cquptmcls[3]);
        Runnable runnable = executorRunable(Integer.parseInt(cquptmcls[0]), isregister, stringBuilder, channel);
        executorService.execute(runnable);
    }

    public Runnable executorRunable(int k,int isregister,Object object,Channel channel){
        Runnable runnable;
        switch(k) {
            case 0:
                if (isregister == 0)  //不注册
                    runnable = new UnRegisterImpl(object);
                else runnable = new RegisterImpl(object,channel);
                break;
            case 1:
                runnable = new WriteImpl(object,channel);
                break;
            default:runnable = null;
        }
        return runnable;
    }
}
