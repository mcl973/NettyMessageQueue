/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName:
 * Author:   Administrator
 * Date:     2020/03/14 13:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.ExecutorsPool.RunnableAndCallable.Implemants.WriteImpl;

import com.mcl.Custom.AbstractCoustom;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/14
 * @since 1.0.0
 */
public class HandlerRequestRunnableEvent implements Runnable{
    private AbstractCoustom abstractCoustom;
    private Channel channel = null;
    private String Need = null;
    public HandlerRequestRunnableEvent(AbstractCoustom abstractCoustom){
        this.abstractCoustom = abstractCoustom;
    }
    public HandlerRequestRunnableEvent(String Need,AbstractCoustom abstractCoustom){
        this.Need = Need;
        this.abstractCoustom = abstractCoustom;
    }
    public HandlerRequestRunnableEvent(String Need,Channel channel,AbstractCoustom abstractCoustom){
        this.Need = Need;
        this.channel = channel;
        this.abstractCoustom = abstractCoustom;
    }
    @Override
    public void run() {
        //在这里只是简单的演示一下，具体的操作需要实际的去完成。
        if (channel == null)
            channel = abstractCoustom.getChannel();
        List<String> arrayList = abstractCoustom.getRequestlist();
        StringBuffer sb = new StringBuffer();
        if (channel!=null&&Need!=null){
            sb.append(Need);
            sb.append("\n");
            sb.append("return Custom's need are :{\n");
            sb.append(abstractCoustom.getName());
            sb.append(":{\n生产者");
            sb.append(abstractCoustom.getProductorName());
            sb.append("}\n}");
            channel.writeAndFlush(sb.toString());
        }
        for (String s : arrayList) {
            channel.writeAndFlush("this is you request "+s+sb.toString()
                    + " " + "and my answer is this are you ok "+System.currentTimeMillis());
        }
        arrayList.clear();
    }
}
