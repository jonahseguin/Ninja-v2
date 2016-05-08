package com.shawckz.ninja.player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 360 on 3/27/2015.
 */
public class PlayerManager {

    private static Map<String, NinjaPlayer> players = new HashMap<>();

    public static void put(NinjaPlayer player){
        players.put(player.getName(),player);
    }

    public static NinjaPlayer get(String name){
        return players.get(name);
    }

    public static boolean contains(String name){
        return players.containsKey(name);
    }

    public static void remove(String name){
        if(contains(name)){
            players.remove(name);
        }
    }

}
