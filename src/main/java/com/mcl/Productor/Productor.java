/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Productor
 * Author:   Administrator
 * Date:     2020/03/12 22:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.Productor;

import com.mcl.Custom.AbstractCoustom;
import io.netty.channel.Channel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/12
 * @since 1.0.0
 */
public abstract class Productor {
    private String productorName;
    protected ConcurrentHashMap<String, AbstractCoustom> coustoms = new ConcurrentHashMap<>();
    //用于存放从底层发送过来的请求数据，主要是存放write里的请求处理的数据
     public BlockingQueue<AbstractCoustom> blockingQueue = new LinkedBlockingQueue<>();
//    protected ConcurrentHashMap<String, ArrayList> messagelate = new ConcurrentHashMap<>();

    public Productor(String productorName){
        this.productorName = productorName;
        this.executeCoustomNeed();
    }
    public String getProductorName() {
        return productorName;
    }

    public  BlockingQueue<AbstractCoustom> getBlockingQueue() {
        return blockingQueue;
    }

    public ConcurrentHashMap<String, AbstractCoustom> getCoustoms() {
        return coustoms;
    }

    abstract public void AddCoustom(AbstractCoustom ac,String name);
    abstract  public void executeCoustomNeed();
    abstract  public void executeCoustomNeed(Channel channel,String Need,AbstractCoustom abstractCoustom);
    abstract public void BrocastToCustoms(String message);
    abstract public void UnicastToCustom(AbstractCoustom abstractCoustom,String message);
}
