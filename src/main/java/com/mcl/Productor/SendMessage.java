/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: SendMessage
 * Author:   Administrator
 * Date:     2020/03/13 15:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.Productor;

import com.mcl.Custom.AbstractCoustom;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/13
 * @since 1.0.0
 */
@Deprecated
public class SendMessage implements Runnable {
    private String customName;
    private String Message;
    private AbstractCoustom abstractCoustom;
    public SendMessage(String customName, String Message, AbstractCoustom abstractCoustom){
        this.customName = customName;
        this.Message = Message;
    }
    public void send(){
        while(abstractCoustom.getChannel().isActive()){

        }

    }
    @Override
    public void run() {

    }
}
