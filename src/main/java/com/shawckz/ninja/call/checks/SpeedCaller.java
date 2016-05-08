/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.call.checks;

import com.shawckz.ninja.NinjaCore;
import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.player.CheckData;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SpeedCaller extends CheckCaller {

    public SpeedCaller() {
        super("Speed");
    }

    @Override
    public int getMaxVL() {
        return 10;
    }

    @Override
    public int getPunishVL() {
        return 30;
    }

    @Override
    public int getRaiseLevel() {
        return 5;
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent e){
        if(!isEnabled())return;
        if(e.isCancelled())return;
        Player p = e.getPlayer();

        if(!PlayerManager.contains(p.getName()))return;
        final NinjaPlayer np = PlayerManager.get(e.getPlayer().getName());
        if(p.getAllowFlight() == true) return;
        if(p.getGameMode() == GameMode.CREATIVE) return;

        if(isEnabled()){

            if(p.getVehicle() != null) return;


            if(np.getCheckData().getPing() > 400) return;

            if(!e.getFrom().getWorld().getName().equalsIgnoreCase(e.getTo().getWorld().getName())){
                return;
            }

            if(e.getTo().getBlockY() != e.getFrom().getBlockY()){
                return;
            }

            new BukkitRunnable(){
                @Override
                public void run() {
                    double move = e.getTo().distance(e.getFrom());

                    np.getCheckData().setBlocksPerSecond(np.getCheckData().getBlocksPerSecond()+move);
                }
            }.runTaskAsynchronously(NinjaCore.getPlugin());

        }

    }

    @Override
    public String getDetail(CheckData data) {
        return data.getBlocksPerSecond()+" bps";
    }

}
