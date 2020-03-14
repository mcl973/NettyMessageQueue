/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: AckMessage
 * Author:   Administrator
 * Date:     2020/03/13 19:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.MessageManager.Messages;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/13
 * @since 1.0.0
 */
public class AckMessage {
    public static String AckMessages(String productorName){
        return productorName + " ack";
    }
}
