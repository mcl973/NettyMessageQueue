/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ReadImpl
 * Author:   Administrator
 * Date:     2020/03/13 13:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.ExecutorsPool.RunnableAndCallable.Implemants.ReadImpl;

import com.mcl.Custom.AbstractCoustom;
import com.mcl.Custom.Custom1;
import com.mcl.ExecutorsPool.RunnableAndCallable.Read;
import com.mcl.ExecutorsPool.RunnableAndCallable.Register;
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
@Deprecated
public class ReadImpl implements Read {
    private Object object;
    private Channel channel;
    public ReadImpl(Object object,Channel channel){
        this.object = object;
        this.channel = channel;
    }
    @Override
    public void read() {
        String string = (String) object;
        String[] cquptmcls = string.split("cquptmcl");
        //先获取对应的productor
        Productor productor = productors.get(cquptmcls[0]);
        //在判断増连接在不在消费者队列中
        ConcurrentHashMap<String, AbstractCoustom> coustoms = productor.getCoustoms();
        if(!coustoms.containsKey(cquptmcls[1])) {
            AbstractCoustom ac = new Custom1(cquptmcls[1]);
            ac.setChannel(channel);
            productor.AddCoustom(ac,ac.getName());
        }
        //获取数据，将数据
        AbstractCoustom abstractCoustom = coustoms.get(cquptmcls[1]);
    }

    @Override
    public void run() {
        read();
    }
}
