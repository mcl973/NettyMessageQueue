/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: WriteImpl
 * Author:   Administrator
 * Date:     2020/03/13 13:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.ExecutorsPool.RunnableAndCallable.Implemants.WriteImpl;

import com.mcl.Custom.AbstractCoustom;
import com.mcl.Custom.Custom1;
import com.mcl.ExecutorsPool.RunnableAndCallable.write;
import com.mcl.Productor.Productor;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/13
 * @since 1.0.0
 */
public class WriteImpl implements write {
    private Object object;
    private Channel channel;
    public WriteImpl(Object object,Channel channel){
        this.object = object;
        this.channel = channel;
    }
    //这是一个重量级的转发的地方
    //后期需要使用策略模式或是其他的方法来根据用户提交的need来实现各种各样的方法。
    //现在只是简单的恢复一个消息返回个用户
    //目前是下的效果
    //
    @Override
    public void write() {
        String string = (String) object;
        String[] cquptmcls = string.split("cquptmcl");
        //先获取对应的productor
        Productor productor = productors.get(cquptmcls[0]);
        //在判断増连接在不在消费者队列中
        ConcurrentHashMap<String, AbstractCoustom> coustoms = productor.getCoustoms();
        BlockingQueue<AbstractCoustom> blockingQueue = productor.getBlockingQueue();
        AbstractCoustom abstractCoustom;
        if(!coustoms.containsKey(cquptmcls[1])) {
            abstractCoustom = new Custom1(cquptmcls[1]);
            abstractCoustom.setChannel(channel);
            productor.AddCoustom(abstractCoustom,abstractCoustom.getName());
            List<String> arrayList = abstractCoustom.getRequestlist();
            arrayList.add(cquptmcls[2]);
            try {
                if (!blockingQueue.contains(abstractCoustom))
                    blockingQueue.put(abstractCoustom);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {//如果客户端的channnel换了，那么就可以覆盖的方式在此建立连接
            abstractCoustom = coustoms.get(cquptmcls[1]);
            if (abstractCoustom.getChannel() != channel && !abstractCoustom.getChannel().isActive()) {
                if (abstractCoustom.getChannel().remoteAddress().toString().equals(channel.remoteAddress().toString()))
                    abstractCoustom.setChannel(channel);
                else
                    channel.writeAndFlush("错误，地址不对");
            }
            else if (abstractCoustom.getChannel() == channel){
                //将请求的数据放入消息队列中，让productor逐个去获取
                abstractCoustom = productor.getCoustoms().get(cquptmcls[1]);
                abstractCoustom.getRequestlist().add(cquptmcls[2]);
                try {
                    if (!blockingQueue.contains(abstractCoustom))
                        blockingQueue.put(abstractCoustom);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{//不正常的情况，其他的channel调用了本channel的key，需要被隔离，不能返回正常的数据
                //这里使用了第二种方式来应答用户的request
                productor.executeCoustomNeed(channel,
                            "你发送了错误的消息，你的名字和该消息包含的消息错误",
                            findCustom(channel,coustoms));
            }
        }
    }
    public AbstractCoustom findCustom(Channel channel,Map<String,AbstractCoustom> maps){
        for(Map.Entry<String,AbstractCoustom> map:maps.entrySet()){
            if (channel == map.getValue().getChannel())
                return map.getValue();
        }
        return null;
    }

    @Override
    public void run() {
        //处理向服务器写入事件
        write();
    }
}
