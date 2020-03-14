/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: AbstractCoustom
 * Author:   Administrator
 * Date:     2020/03/12 22:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.Custom;

import io.netty.channel.Channel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/12
 * @since 1.0.0
 */
public abstract class AbstractCoustom {
    protected String ProductorName;
    protected String name;
    protected Channel channel;
    public List<String> arrayList = Collections.synchronizedList(new ArrayList<String>());
    public List<String> requestlist =  Collections.synchronizedList(new ArrayList<String>());
    abstract  public List<String> getArrayList();

    public AbstractCoustom(){}

    public AbstractCoustom(String name){
        this.name = name;
    }

    abstract public Channel getChannel();

    abstract public void setChannel(Channel channel);

    abstract public String getProductorName();

    abstract public String getName();

    abstract public void setProductorNmae(String productorname);

    abstract public List<String> getRequestlist();
}
