package com.system.translationpen.exchangerate.until;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU<k,v> extends LinkedHashMap<k,v> {


    @Override
    protected boolean removeEldestEntry(Entry<k, v> eldest) {
        if (size()>5){
            return true;
        }
        return false;
    }
}
