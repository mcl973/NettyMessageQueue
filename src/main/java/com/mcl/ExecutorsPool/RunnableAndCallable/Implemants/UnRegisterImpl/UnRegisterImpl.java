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

import com.mcl.ExecutorsPool.RunnableAndCallable.Unregister;
import com.mcl.Productor.Productor;
import io.netty.channel.Channel;

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
    public UnRegisterImpl(Object object){
        this.object = object;
    }
    @Override
    public void unregister() {
        String string = (String) object;
        String[] cquptmcls = string.split("cquptmcl");
        //通过productor的名字找到具体的productor
        Productor productor = productors.get(cquptmcls[0]);
        //删除相应的custom
        productor.getCoustoms().remove(cquptmcls[1]);
    }

    @Override
    public void run() {
        //处理不注册事件
        unregister();
    }
}
