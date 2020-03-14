/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: UnRegisterImpl
 * Author:   Administrator
 * Date:     2020/03/13 13:06
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.ExecutorsPool.RunnableAndCallable.Implemants.UnRegisterImpl;

import com.mcl.Custom.AbstractCoustom;
import com.mcl.ExecutorsPool.RunnableAndCallable.Unregister;
import com.mcl.Productor.Productor;
import io.netty.channel.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/13
 * @since 1.0.0
 */
public class UnRegisterImpl implements Unregister {
    private Object object;
    private Channel channel;
    public UnRegisterImpl(Object object,Channel channel){
        this.object = object;
        this.channel = channel;
    }
    @Override
    public void unregister() {
        String string = (String) object;
        String[] cquptmcls = string.split("cquptmcl");
        //通过productor的名字找到具体的productor
        Productor productor = productors.get(cquptmcls[0]);
        ConcurrentHashMap<String, AbstractCoustom> coustoms = productor.getCoustoms();
        //删除相应的custom
        if (findcustom(coustoms,cquptmcls[1]))
            productor.getCoustoms().remove(cquptmcls[1]);
        else {
            channel.writeAndFlush("");
        }
    }
    public boolean findcustom(Map<String, AbstractCoustom> abstractCoustomMap,String customName){
        for(Map.Entry<String, AbstractCoustom> map:abstractCoustomMap.entrySet()){
            if (map.getValue().getChannel() == channel){
                if (customName.equals(map.getKey()))
                    return true;
            }else if (customName.equals(map.getKey())&&!map.getValue().getChannel().isActive()){
                if (channel.remoteAddress().toString().equals(map.getValue().getChannel().remoteAddress().toString()))
                    return true;
            }
        }
        return false;
    }
    @Override
    public void run() {
        //处理不注册事件
        unregister();
    }
}
