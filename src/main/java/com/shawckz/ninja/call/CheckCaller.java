/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.call;

import com.shawckz.ninja.Ninja;
import com.shawckz.ninja.NinjaCore;
import com.shawckz.ninja.player.CheckData;
import com.shawckz.ninja.player.NinjaPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class CheckCaller implements Listener {

    @Getter private String name;
    private boolean enabled;
    @Getter @Setter private boolean autoBan;

    public CheckCaller(String name) {
        this.name = name;
        if(enabled){
            Bukkit.getServer().getPluginManager().registerEvents(this, NinjaCore.getPlugin());
        }
    }

    public void unregister(){
        HandlerList.unregisterAll(this);
    }

    public boolean isEnabled() {
        return enabled && Ninja.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if(enabled){
            Bukkit.getServer().getPluginManager().registerEvents(this,NinjaCore.getPlugin());
        }
        else{
            HandlerList.unregisterAll(this);
        }
    }

    public abstract int getMaxVL();

    public abstract int getRaiseLevel();

    public abstract int getPunishVL();

    public abstract String getDetail(CheckData data);

}
