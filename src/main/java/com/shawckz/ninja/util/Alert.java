package com.shawckz.ninja.util;

import com.shawckz.ninja.Ninja;
import com.shawckz.ninja.NinjaCore;
import com.shawckz.ninja.check.ViolationSnapshot;
import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.PlayerManager;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by 360 on 3/27/2015.
 */
public class Alert {

    public static void send(final NinjaPlayer p,final CheckCaller check,final ViolationSnapshot violationSnapshot){
        new BukkitRunnable(){
            @Override
            public void run() {
                FancyMessage fancyMessage = new FancyMessage(Ninja.NINJA_PREFIX);
                fancyMessage.then(p.getBukkitPlayer().getDisplayName());
                fancyMessage.tooltip("§cClick to inspect this VL on " + p.getBukkitPlayer().getDisplayName());
                fancyMessage.command("/ninja vlookup "+p.getBukkitPlayer().getName()+" "+violationSnapshot.getId());
                fancyMessage.then(" ");
                fancyMessage.then("§7(§c" + violationSnapshot.getVl() + "§7xVL)");
                fancyMessage.then(" ");
                fancyMessage.then("§7failed ").then("§c"+check.getName());
                fancyMessage.then("§7(§c" + violationSnapshot.getCheckVl().get(check) + "§7VL)");
                for(Player pl : Bukkit.getOnlinePlayers()){
                    NinjaPlayer np = PlayerManager.get(pl.getName());
                    if(np.isAlertsEnabled() && pl.hasPermission(NinjaPerm.ALERTS_VIEW.getPerm())){
                        fancyMessage.send(pl);
                    }
                    if(p.getBukkitPlayer()!=null){
                        if(np.isTptoggle()){
                            np.getBukkitPlayer().teleport(p.getBukkitPlayer());
                        }
                    }
                }
            }
        }.runTask(NinjaCore.getPlugin());
    }

    public static void send(final NinjaPlayer p, final CheckCaller check,final ViolationSnapshot violationSnapshot,final String type){
        new BukkitRunnable(){
            @Override
            public void run() {
                FancyMessage fancyMessage = new FancyMessage(Ninja.NINJA_PREFIX);
                fancyMessage.then(p.getBukkitPlayer().getDisplayName());
                fancyMessage.tooltip("§cClick to inspect this VL on " + p.getBukkitPlayer().getDisplayName());
                fancyMessage.command("/ninja vlookup "+p.getBukkitPlayer().getName()+" "+violationSnapshot.getId());
                fancyMessage.then(" ");
                fancyMessage.then("§7(§c" + violationSnapshot.getVl() + "§7xVL)");
                fancyMessage.then(" ");
                fancyMessage.then("§7failed ").then("§c"+check.getName()+"§8[§c"+type+"§8]");
                fancyMessage.then("§7(§c" + violationSnapshot.getCheckVl().get(check) + "§7VL)");
                for(Player pl : Bukkit.getOnlinePlayers()){
                    NinjaPlayer np = PlayerManager.get(pl.getName());
                    if(np.isAlertsEnabled() && pl.hasPermission(NinjaPerm.ALERTS_VIEW.getPerm())){
                        fancyMessage.send(pl);
                    }
                    if(p.getBukkitPlayer()!=null){
                        if(np.isTptoggle()){
                            np.getBukkitPlayer().teleport(p.getBukkitPlayer());
                        }
                    }
                }
            }
        }.runTask(NinjaCore.getPlugin());
    }

    public static void staffMsg(String msg){
        for(Player pl : Bukkit.getOnlinePlayers()){
            NinjaPlayer p = PlayerManager.get(pl.getName());
            if(p.isAlertsEnabled() && pl.hasPermission(NinjaPerm.ALERTS_VIEW.getPerm())){
                pl.sendMessage(Ninja.NINJA_PREFIX+msg);
            }
        }
    }

    public static void staffMsg(FancyMessage fancyMessage){
        for(Player pl : Bukkit.getOnlinePlayers()){
            NinjaPlayer p = PlayerManager.get(pl.getName());
            if(p.isAlertsEnabled() && pl.hasPermission(NinjaPerm.ALERTS_VIEW.getPerm())){
                fancyMessage.send(pl);
            }
        }
    }

}
