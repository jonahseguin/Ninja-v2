/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.player;

import com.shawckz.ninja.Ninja;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by 360 on 5/20/2015.
 */
public class CacheHandler implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(!PlayerManager.contains(p.getName())){
            NinjaPlayer ninjaPlayer = new NinjaPlayer(p);
            PlayerManager.put(ninjaPlayer);
        }

        if(p.hasPermission("ninja.alerts")){
            PlayerManager.get(p.getName()).setAlertsEnabled(true);
            p.sendMessage(Ninja.NINJA_PREFIX + "Alerts have been §aenabled§7.");
        }

    }

}
