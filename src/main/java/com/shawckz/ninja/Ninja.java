/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja;

import com.shawckz.ninja.check.Check;
import com.shawckz.ninja.check.CheckManager;
import com.shawckz.ninja.check.ViolationSnapshot;
import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.fail.CheckCallbackResult;
import com.shawckz.ninja.fail.CheckFail;
import com.shawckz.ninja.player.CheckData;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.PlayerManager;
import com.shawckz.ninja.player.XrayStats;
import com.shawckz.ninja.type.BanMethod;
import com.shawckz.ninja.util.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by 360 on 3/27/2015.
 */
public class Ninja {

    private static double TPS = 20;

    public static double DISABLE_TPS = 14;

    public static double getTPS() {
        return TPS;
    }

    public static void setTPS(double TPS) {
        Ninja.TPS = TPS;
    }

    public static float getMaxVL(){
        return 50F;
    }

    public static final String NINJA_PREFIX = "§8[§cNinja§8]§7: §7";

    public static final int PUNISH_VL = 50;

    private static boolean enabled = true;

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        Ninja.enabled = enabled;
        ConfigHelper.get().setEnabled(enabled);
    }

    public static BanMethod banMethod = BanMethod.CONSOLE_COMMAND;

    public static boolean callCheckFailedEvent(NinjaPlayer player,CheckCaller check,CheckData data,String type){
        HashSet<CheckCallbackResult> result = new CheckFail(player,check,data).call().checkFailed();

        if(result.contains(CheckCallbackResult.ALERTED)){
            Map<CheckCaller,Integer> checkVL = new HashMap<>();
            int VL = 0;
            VL += player.getVl();
            for(CheckCaller c : player.getCheckVl().keySet()){
                checkVL.put(c,player.getCheckVl().get(c));
            }

            Map<XrayStats.XrayStat, Double> xray = new HashMap<>();
            for(XrayStats.XrayStat stat : player.getXrayStats().getVl().keySet()){
                xray.put(stat,player.getXrayStats().getVl().get(stat));
            }

            final XrayStats xrayStats = new XrayStats(player,xray);

            final ViolationSnapshot violationSnapshot = new ViolationSnapshot(player,player.getCheckData().clone(),VL,checkVL,xrayStats);
            player.getViolations().put(violationSnapshot.getId(),violationSnapshot);


            if(type!=null){
                if(!type.equalsIgnoreCase("")){
                    Alert.send(player, check,violationSnapshot,type);
                }
                else{
                    Alert.send(player, check,violationSnapshot);
                }
            }
            else{
                Alert.send(player, check,violationSnapshot);
            }
        }
        if (result.contains(CheckCallbackResult.KICKED)){
            player.setPreviousKicks(player.getPreviousKicks()+1);
            Alert.staffMsg(player.getBukkitPlayer().getDisplayName() + " §7was kicked for potential hacks.");
            player.getBukkitPlayer().kickPlayer(Ninja.NINJA_PREFIX + "You have been kicked.\nKicked for potential hacks.");
        }
        if (result.contains(CheckCallbackResult.BANNED)){
            if(ConfigHelper.get().autobanEnabled()){
                Map<CheckCaller,Integer> checkVL = new HashMap<>();
                int VL = 0;
                VL += player.getVl();
                for(CheckCaller c : player.getCheckVl().keySet()){
                    checkVL.put(c,player.getCheckVl().get(c));
                }

                Map<XrayStats.XrayStat, Double> vl = new HashMap<>();
                for(XrayStats.XrayStat stat : player.getXrayStats().getVl().keySet()){
                    vl.put(stat,player.getXrayStats().getVl().get(stat));
                }

                final XrayStats xrayStats = new XrayStats(player,vl);

                final ViolationSnapshot violationSnapshot = new ViolationSnapshot(player,player.getCheckData(),VL,checkVL,xrayStats);
                player.getViolations().put(violationSnapshot.getId(),violationSnapshot);


                Autoban autoban = new Autoban(player,15,check,violationSnapshot);
                AutobanManager.putAutoban(player.getName(), autoban);
                autoban.run();
            }
        }

        if(check.getName().equalsIgnoreCase("xray")){
            for(XrayStats.XrayStat stat : player.getXrayStats().getVl().keySet()){
                if(player.getXrayStats().getVL(stat) >= stat.getMax()){
                    player.getXrayStats().setVL(stat,(player.getXrayStats().getVL(stat)/2));
                }
            }
        }

        return result.contains(CheckCallbackResult.CANCELLED);
    }

    public static boolean isStaffOnline(){
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(pl.hasPermission("ninja.alerts")){
                return true;
            }
        }
        return false;
    }

    public static void tick(){
        new BukkitRunnable(){

            @Override
            public void run() {
                double tps = Lag.getTPS();
                setTPS(tps);

                if(Ninja.isEnabled()){
                    if(Ninja.getTPS() <= Ninja.DISABLE_TPS){
                        Ninja.setEnabled(false);
                        Alert.staffMsg("Ninja was §cdisabled §7because the TPS went below "+Ninja.DISABLE_TPS+".  It will automatically re-enable once the TPS is stable.");
                    }
                }
                else{
                    if(Ninja.getTPS() > Ninja.DISABLE_TPS){
                        Ninja.setEnabled(true);
                        Alert.staffMsg("Ninja was re-§aenabled§7 because the TPS is now stable.");
                    }
                }

                for(Player pl : Bukkit.getOnlinePlayers()){
                    if(!PlayerManager.contains(pl.getName()))continue;
                    NinjaPlayer p = PlayerManager.get(pl.getName());

                    if(p!=null){
                        if(p.getCheckData()!=null){

                            for(int i = 0; i < 10; i ++){//10 checks per player per second
                                Check c = p.doQueueNext();
                                if(c != null){
                                    CheckCaller caller = c.caller;
                                    if(!c.check()){
                                        if(caller.getDetail(p.getCheckData()) != null){
                                            c.handleCheckFail(p,caller.getDetail(p.getCheckData()));
                                        }
                                        else{
                                            c.handleCheckFail(p);
                                        }
                                        p.getCheckQueue().clear();
                                        break;//Only allow one fail per second
                                    }
                                }
                                else{
                                    break;
                                }
                            }

                            p.getCheckData().setLastLocation(pl.getLocation());
                            p.getCheckData().setBlocksPerSecond(0);
                            p.getCheckData().setHps(0);
                            p.getCheckData().setLastCps(p.getCheckData().getCps());
                            p.getCheckData().setCps(0);
                            p.getCheckData().setTps(tps);
                            p.getCheckData().setGameMode(pl.getGameMode());
                            p.getCheckData().setHealth(pl.getHealth());
                            p.getCheckData().setBlockPlacePerSecond(0);
                            p.getCheckData().setBlockBreakPerSecond(0);
                            if(p.getCheckData().getPing() == 0){
                                p.getCheckData().setPing(((CraftPlayer)pl).getHandle().ping);
                            }
                        }
                    }
                }

            }

        }.runTaskTimer(NinjaCore.getPlugin(), 20L, 20L);

          /*  new BukkitRunnable(){
                @Override
                public void run() {
                    for(Player pl : Bukkit.getOnlinePlayers()){
                        if(!PlayerManager.contains(pl.getName()))continue;
                        NinjaPlayer p = PlayerManager.get(pl.getName());

                        p.getCheckData().setHits(0);
                        p.getCheckData().setMisses(0);
                    }
                }
            }.runTaskTimerAsynchronously(NinjaCore.getPlugin(),(60*2)*20,(60*2)*20);*/

        new BukkitRunnable(){
            @Override
            public void run() {

                for(Player pl : Bukkit.getOnlinePlayers()){
                    if(!PlayerManager.contains(pl.getName()))continue;
                    NinjaPlayer p = PlayerManager.get(pl.getName());
                    p.getXrayStats().reset();
                }

            }
        }.runTaskTimerAsynchronously(NinjaCore.getPlugin(), 72000, 72000);
    }



}
