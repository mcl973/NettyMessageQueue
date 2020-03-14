/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: MessageManager
 * Author:   Administrator
 * Date:     2020/03/12 22:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.MessageManager;

import com.mcl.Custom.AbstractCoustom;
import com.mcl.MessageManager.Messages.AckMessage;
import com.mcl.Productor.Productor;
import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/12
 * @since 1.0.0
 */
public class MessageManager implements ProductorConCurrentHashMap{
    public volatile static MessageManager messageManager;
    private final static Object o = new Object();
    private MessageManager(){

    }
    public static MessageManager getMessageManager(){
        if(messageManager == null){
            synchronized (o){
                if (messageManager == null)
                    messageManager = new MessageManager();
                return messageManager;
            }
        }
        return messageManager;
    }
    public void AddProductor(Productor productor){
        productors.put(productor.getProductorName(),productor);
    }
    public void AddCoustom(AbstractCoustom ac){
        String productorName = ac.getProductorName();
        Productor productor = productors.get(productorName);
        productor.AddCoustom(ac,ac.getName());
        productor.UnicastToCustom(ac,"欢迎订阅");
    }

    public String getResponse(String productorName){
        return AckMessage.AckMessages(productorName);
    }

}
