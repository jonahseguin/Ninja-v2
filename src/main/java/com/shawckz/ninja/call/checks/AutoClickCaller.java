/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.call.checks;

import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.handle.AutoClickCheck;
import com.shawckz.ninja.player.CheckData;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

public class AutoClickCaller extends CheckCaller {

    public AutoClickCaller() {
        super("AutoClick");
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent e){
        if(!(isEnabled())) return;
        if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            if(!PlayerManager.contains(e.getPlayer().getName()))return;

            final NinjaPlayer ninja = PlayerManager.get(e.getPlayer().getName());

            if(ninja == null){
                return;
            }

            if(e.getPlayer().hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                return;
            }

            if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                if(e.getClickedBlock().getType() == Material.SAND){
                    if(e.getPlayer().getItemInHand().getType() == Material.DIAMOND_SPADE ||
                            e.getPlayer().getItemInHand().getType() == Material.IRON_SPADE ||
                            e.getPlayer().getItemInHand().getType() == Material.GOLD_SPADE){
                        return;
                    }
                }
            }
            ninja.getCheckData().setCps(ninja.getCheckData().getCps() + 1);

            ninja.addToQueue(new AutoClickCheck(ninja.getCheckData().clone()));
        }
    }

    @Override
    public int getPunishVL() {
        return 64;
    }

    @Override
    public int getRaiseLevel() {
        return 2;
    }

    @Override
    public int getMaxVL() {
        return 16;
    }

    @Override
    public String getDetail(CheckData data) {
        return data.getCps()+" cps";
    }
}
