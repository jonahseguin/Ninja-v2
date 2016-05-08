/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.call.checks;

import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.handle.FlyCheck;
import com.shawckz.ninja.player.CheckData;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.PlayerManager;
import com.shawckz.ninja.util.Alert;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FlyCaller extends CheckCaller {

    public FlyCaller() {
        super("Fly");
    }

    @Override
    public int getPunishVL() {
        return 20;
    }

    @Override
    public int getRaiseLevel() {
        return 5;
    }

    @Override
    public int getMaxVL() {
        return 10;
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        if (!isEnabled()) return;
        if (e.getReason().equalsIgnoreCase("Flying is not enabled on this server")) {
            Alert.staffMsg(e.getPlayer().getDisplayName() + " §7was kicked for flying.");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(final PlayerMoveEvent e) {
        if (!isEnabled()) return;

        if (e.isCancelled()) return;
        final Player p = e.getPlayer();
        if (!PlayerManager.contains(p.getName())) return;
        final NinjaPlayer np = PlayerManager.get(p.getName());

        if(onGround(p)){
            np.getCheckData().setAirTime(System.currentTimeMillis());
            np.getCheckData().setFalling(false);
        }

        if (p.getAllowFlight() == true) return;
        if (p.getGameMode() == GameMode.CREATIVE) return;


        if (np.getCheckData().getPing() > 400) return;

        if (!e.getFrom().getWorld().getName().equalsIgnoreCase(e.getTo().getWorld().getName())) {
            return;
        }

        if (p.getVehicle() != null) return;

        if (p.getAllowFlight() == true) {
            return;
        }
        if (p.isOnGround()) {
            return;
        }
        if (p.getLocation().getWorld().getBlockAt(p.getLocation().subtract(0, 3, 0)) != null) {
            if (p.getLocation().getWorld().getBlockAt(p.getLocation().subtract(0, 3, 0)).getType() != Material.AIR) {
                return;
            }
        }
        if (p.getLocation().getWorld().getBlockAt(p.getLocation().subtract(0, 2, 0)) != null) {
            if (p.getLocation().getWorld().getBlockAt(p.getLocation().subtract(0, 2, 0)).getType() != Material.AIR) {
                return;
            }
        }
        if (p.getLocation().getWorld().getBlockAt(p.getLocation().subtract(0, 1, 0)) != null) {
            if (p.getLocation().getWorld().getBlockAt(p.getLocation().subtract(0, 1, 0)).getType() != Material.AIR) {
                return;
            }
        }

        np.getCheckData().setAirTime((System.currentTimeMillis() + np.getCheckData().getAirTime()) + np.getCheckData().getAirTime());//Current air time, + different between current time and last air time

        np.getCheckData().setTo(e.getTo());
        np.getCheckData().setFrom(e.getFrom());
        np.addToQueue(new FlyCheck(np.getCheckData().clone()));

    }

    public boolean onGround(Player p){
        return p.getLocation().subtract(0,1,0).getBlock().getType() != Material.AIR;
    }

    @Override
    public String getDetail(CheckData data) {
        return null;
    }
}
