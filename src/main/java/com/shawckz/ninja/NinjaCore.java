/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja;

import com.shawckz.ninja.check.CheckManager;
import com.shawckz.ninja.cmd.AlertsCommand;
import com.shawckz.ninja.cmd.CancelCommand;
import com.shawckz.ninja.cmd.NinjaCommand;
import com.shawckz.ninja.cmd.command.InspectViolation;
import com.shawckz.ninja.player.CacheHandler;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.PlayerManager;
import com.shawckz.ninja.util.ConfigHelper;
import com.shawckz.ninja.util.Lag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by 360 on 3/26/2015.
 */
public class NinjaCore extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        ConfigHelper.get();

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Lag(), 100L, 1L);

        CheckManager.get();

        Ninja.setEnabled(ConfigHelper.get().isEnabled());

        Ninja.DISABLE_TPS = ConfigHelper.get().getConfig().getDouble("ninja.disable-tps");

        Ninja.tick();

        getServer().getPluginManager().registerEvents(new CacheHandler(), this);
        getServer().getPluginManager().registerEvents(new InspectViolation(), this);

        for (Player pl : Bukkit.getOnlinePlayers()) {
            PlayerManager.put(new NinjaPlayer(pl));
        }

        getCommand("alerts").setExecutor(new AlertsCommand());
        getCommand("cancel").setExecutor(new CancelCommand());
        getCommand("ninja").setExecutor(new NinjaCommand());
        getCommand("freeze").setExecutor(new NinjaCommand());

    }

    @Override
    public void onDisable() {

        plugin = null;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

}
