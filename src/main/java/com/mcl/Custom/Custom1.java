/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Custom1
 * Author:   Administrator
 * Date:     2020/03/12 22:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.Custom;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/12
 * @since 1.0.0
 */
public class Custom1 extends AbstractCoustom{
    public Custom1(String name){
        super(name);
    }
    @Override
    public void setProductorNmae(String productorName) {
        ProductorName = productorName;
    }

    @Override
    public synchronized List<String> getRequestlist() {
        return requestlist;
    }

    public synchronized List<String> getArrayList() {
        return arrayList;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getProductorName() {
        return ProductorName;
    }

    public String getName() {
        return name;
    }

}
