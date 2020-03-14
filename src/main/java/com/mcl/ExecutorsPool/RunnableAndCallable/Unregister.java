package com.mcl.ExecutorsPool.RunnableAndCallable;

import com.mcl.MessageManager.ProductorConCurrentHashMap;

public interface Unregister extends Runnable, ProductorConCurrentHashMap {
    public void unregister();
}
