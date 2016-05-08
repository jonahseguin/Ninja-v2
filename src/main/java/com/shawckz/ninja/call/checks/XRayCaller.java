/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.call.checks;

import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.handle.XRayCheck;
import com.shawckz.ninja.player.CheckData;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.PlayerManager;
import com.shawckz.ninja.player.XrayStats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffectType;

public class XRayCaller extends CheckCaller {

    public XRayCaller(){
        super("XRay");
    }

    @Override
    public int getPunishVL() {
        return 16;
    }

    @Override
    public int getRaiseLevel() {
        return 2;
    }

    @Override
    public int getMaxVL() {
        return 4;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(!isEnabled()) return;
        Player p = e.getPlayer();

        if(!PlayerManager.contains(p.getName()))return;

        NinjaPlayer np = PlayerManager.get(p.getName());

        if(e.getBlock().getType() == Material.DIAMOND_ORE){
            if(p.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                np.getXrayStats().addVL(XrayStats.XrayStat.DIAMOND,0.3);
            }
            else{
                np.getXrayStats().addVL(XrayStats.XrayStat.DIAMOND,1);
            }
            np.getXrayStats().check(new XRayCheck(np.getCheckData().clone()));
        }
        else if (e.getBlock().getType() == Material.GOLD_ORE){
            if(p.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                np.getXrayStats().addVL(XrayStats.XrayStat.GOLD,0.3);
            }
            else{
                np.getXrayStats().addVL(XrayStats.XrayStat.GOLD,1);
            }
            np.getXrayStats().check(new XRayCheck(np.getCheckData().clone()));
        }
        else if (e.getBlock().getType() == Material.EMERALD_ORE){
            if(p.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                np.getXrayStats().addVL(XrayStats.XrayStat.EMERALD,0.3);
            }
            else{
                np.getXrayStats().addVL(XrayStats.XrayStat.EMERALD,1);
            }
            np.getXrayStats().check(new XRayCheck(np.getCheckData().clone()));
        }
    }

    @Override
    public String getDetail(CheckData data) {
        return null;
    }

}
