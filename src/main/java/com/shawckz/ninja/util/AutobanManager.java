package com.shawckz.ninja.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 360 on 3/28/2015.
 */
public class AutobanManager {

    private static Map<String, Autoban> autobans = new HashMap<>();

    public static Autoban getAutoban(String name){
        return autobans.get(name);
    }

    public static boolean hasAutoban(String name){
        if(autobans.containsKey(name)){
            if(!autobans.get(name).isCancelled()){
                return true;
            }
        }
        return false;
    }

    public static void putAutoban(String name,Autoban autoban){
        if(!hasAutoban(name)){
            autobans.put(name,autoban);
        }
    }

}
