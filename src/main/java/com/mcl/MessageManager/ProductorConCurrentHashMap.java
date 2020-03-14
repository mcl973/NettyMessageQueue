package com.mcl.MessageManager;

import com.mcl.Productor.Productor;

import java.util.concurrent.ConcurrentHashMap;

public interface ProductorConCurrentHashMap {
    public static ConcurrentHashMap<String, Productor> productors = new ConcurrentHashMap<>();
}
