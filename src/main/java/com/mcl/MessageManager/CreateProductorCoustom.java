/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName:
 * Author:   Administrator
 * Date:     2020/03/12 23:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.MessageManager;

import com.mcl.Custom.AbstractCoustom;
import com.mcl.Custom.Custom1;
import com.mcl.Productor.Productor;
import com.mcl.Productor.Productor1;
import io.netty.channel.Channel;


/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/12
 * @since 1.0.0
 */
public class CreateProductorCoustom {
    public static Productor getProductor(String productorname){
        return new Productor1(productorname);
    }
    public static AbstractCoustom getCoustom(String productorname,String CoustomName,  Channel channel){
        AbstractCoustom custom1 = new Custom1(CoustomName);
        custom1.setProductorNmae(productorname);
        custom1.setChannel(channel);
        return custom1;
    }
}
