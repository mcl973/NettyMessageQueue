/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName:
 * Author:   Administrator
 * Date:     2020/03/13 13:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.ExecutorsPool.RunnableAndCallable.Implemants.RegisterImpl;

import com.mcl.Custom.AbstractCoustom;
import com.mcl.Custom.Custom1;
import com.mcl.ExecutorsPool.RunnableAndCallable.Register;
import com.mcl.MessageManager.CreateProductorCoustom;
import com.mcl.MessageManager.MessageManager;
import com.mcl.Productor.Productor;
import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/13
 * @since 1.0.0
 */
public class RegisterImpl implements Register {
    private Object object;
    private Channel channel;
    public RegisterImpl(Object object,Channel channel){
        this.object = object;
        this.channel = channel;
    }

    @Override
    public void register() {
        String cquptmcl = (String) object;
        String[] cquptmcls = cquptmcl.split("cquptmcl");
        MessageManager messageManager = MessageManager.getMessageManager();
        //如果之前就存在的话那么就只需要传递channel就好了。
        Productor productor = messageManager.productors.get(cquptmcls[0]);
        ConcurrentHashMap<String, AbstractCoustom> coustoms = productor.getCoustoms();
        if (coustoms.containsKey(cquptmcls[1])){
            AbstractCoustom abstractCoustom = coustoms.get(cquptmcls[1]);
            abstractCoustom.setChannel(channel);
            coustoms.put(abstractCoustom.getName(),abstractCoustom);
        }else  //否则创建新的
            messageManager.AddCoustom(CreateProductorCoustom.
                getCoustom(cquptmcls[0],cquptmcls[1],channel));
    }

    @Override
    public void run() {
        //在这里开始处理注册事件
        register();
    }
}
