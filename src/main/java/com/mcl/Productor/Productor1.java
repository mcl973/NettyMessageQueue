/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Productor1
 * Author:   Administrator
 * Date:     2020/03/12 22:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.Productor;


import com.mcl.Custom.AbstractCoustom;
import com.mcl.ExecutorsPool.GetThread;
import io.netty.channel.Channel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/12
 * @since 1.0.0
 */
public class Productor1 extends Productor {
    private ExecutorService executorService = GetThread.getThreadExecutor();

    public Productor1(String productorName){
        super(productorName);
    }

    @Override
    public void AddCoustom(AbstractCoustom ac, String name) {
        coustoms.put(name,ac);
    }

    //这里需要使用线程池去处理相应的任务
    @Override
    public void executeCoustomNeed() {
        /*
            此时需要按照need进行分类，如查找数据库，或是执行其他的操作。

            但是这是测试阶段，那么只会返回此用户的channel信息。
         */
        if (executorService == null)
            executorService = GetThread.getThreadExecutor();
        ExecuteRequest executeRequest = new ExecuteRequest(blockingQueue);
        executorService.execute(executeRequest);
    }

    @Override
    public void executeCoustomNeed(Channel channel, String Need, AbstractCoustom abstractCoustom) {
        StringBuilder sb = new StringBuilder();
        sb.append(Need);
        sb.append("\n");
        sb.append("return Custom's need are :{\n");
        sb.append(abstractCoustom.getName());
        sb.append(":{\n生产者");
        sb.append(abstractCoustom.getProductorName());
        sb.append("}\n}");
        channel.writeAndFlush(sb.toString());
    }

    @Override
    public void BrocastToCustoms(String message) {
        for(Map.Entry<String, AbstractCoustom> map:coustoms.entrySet()){
            AbstractCoustom value = map.getValue();
            Channel channel = value.getChannel();
            if (channel.isActive()) {
                List<String> arrayList = value.getArrayList();
                //如果有之前的数据由于客户端的channel断掉了，那么就在这次发出去
                if (arrayList.size()>0){
                    for (String s : arrayList) {
                        message += s + "\n";
                    }
                }
                arrayList.clear();
                channel.writeAndFlush(message);
            }else {//如果客户端的channel数down的窗台，那么就将其暂存到对象中。
                value.arrayList.add(message);
            }
        }
    }

    @Override
    public void UnicastToCustom(AbstractCoustom abstractCoustom,String message) {
        Channel channel = abstractCoustom.getChannel();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n目前订阅的人有：");
        for(Map.Entry<String, AbstractCoustom> map:coustoms.entrySet()){
            stringBuilder.append(map.getKey());
            stringBuilder.append("  ");
        }
        channel.writeAndFlush(message+getProductorName()+"频道\n"+stringBuilder.toString());
    }
}
