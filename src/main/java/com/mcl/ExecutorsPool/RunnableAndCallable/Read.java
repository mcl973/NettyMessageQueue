/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Read
 * Author:   Administrator
 * Date:     2020/03/13 12:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.ExecutorsPool.RunnableAndCallable;

import com.mcl.MessageManager.ProductorConCurrentHashMap;

import java.util.concurrent.Callable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/13
 * @since 1.0.0
 */
@Deprecated
public interface Read extends Runnable , ProductorConCurrentHashMap {
    public void read();

}
