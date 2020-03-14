/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: write
 * Author:   Administrator
 * Date:     2020/03/13 12:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.ExecutorsPool.RunnableAndCallable;

import com.mcl.MessageManager.ProductorConCurrentHashMap;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/13
 * @since 1.0.0
 */
public interface write extends Runnable, ProductorConCurrentHashMap {
    public void write();
}
