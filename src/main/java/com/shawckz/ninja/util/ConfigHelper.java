package com.shawckz.ninja.util;

import com.shawckz.ninja.NinjaCore;
import com.shawckz.ninja.check.CheckManager;
import com.shawckz.ninja.call.CheckCaller;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Created by 360 on 3/29/2015.
 */
public class ConfigHelper {

    private FileConfiguration config;

    private static ConfigHelper instance;
    private Plugin plugin;

    public ConfigHelper(Plugin plugin){
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public static ConfigHelper get() {
        if (instance == null) {
            synchronized (CheckManager.class) {
                if (instance == null) {
                    instance = new ConfigHelper(NinjaCore.getPlugin());
                }
            }
        }
        return instance;
    }

    public void setCheckEnabled(CheckCaller check,boolean enabled){
        getConfig().set("checks."+check.getName()+".enabled",enabled);
        saveConfig();
        check.setEnabled(enabled);
    }

    public void setAutobanEnabled(CheckCaller check,boolean enabled){
        getConfig().set("checks."+check.getName()+".autoban",enabled);
        saveConfig();
        check.setAutoBan(enabled);
    }

    public boolean autobanEnabled(){
        return getConfig().getBoolean("ninja.autoban");
    }

    public void setAutoban(boolean autoban){
        getConfig().set("ninja.autoban",autoban);
        saveConfig();
    }

    public void setEnabled(boolean enabled){
        getConfig().set("ninja.enabled",enabled);
        saveConfig();
    }

    public boolean isEnabled(){
        return getConfig().isBoolean("ninja.enabled");
    }

    public boolean isCheckEnabled(CheckCaller check){
        return getConfig().getBoolean("checks."+check.getName()+".enabled");
    }

    public boolean isAutobanEnabled(CheckCaller check){
        return getConfig().getBoolean("checks."+check.getName()+".autoban");
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig(){
        plugin.saveConfig();
    }

}
