/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.call.checks;

import com.shawckz.ninja.NinjaCore;
import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.handle.VClipCheck;
import com.shawckz.ninja.player.CheckData;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class VClipCaller extends CheckCaller {

    public VClipCaller(){
        super("VClip");
    }

    @Override
    public int getPunishVL() {
        return 5;
    }

    @Override
    public int getRaiseLevel() {
        return 5;
    }

    @Override
    public int getMaxVL() {
        return 5;
    }

    private static List<String> teleportBypass = new ArrayList<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(final PlayerMoveEvent e) {
        if (!isEnabled()) return;
        if (e.isCancelled()) return;
        if (e.getTo() == e.getFrom()) return;
        final Player p = e.getPlayer();
        if (p.getAllowFlight()) return;
        if (p.getGameMode() == GameMode.CREATIVE) return;
        if (!PlayerManager.contains(p.getName())) return;

        final NinjaPlayer ninjaPlayer = PlayerManager.get(e.getPlayer().getName());

        final Location to = e.getTo();
        final Location from = e.getFrom();

        final Location check = p.getLocation().add(0, 1, 0);
        final Location lastLoc = ninjaPlayer.getCheckData().getLastLocation().add(0, 1, 0);

        if (teleportBypass.contains(p.getName())) {
            teleportBypass.remove(p.getName());
        }

        if (!hollow.contains(check.getBlock().getType())) {//If the block at their head/eyes is not hollow, don't let it glitch
            reset(to, from, ninjaPlayer, false);
            return;
        }
        if (!hollow.contains(p.getLocation().getBlock().getType())) {//If the block at their legs is not hollow, don't let it glitch
            reset(to, from, ninjaPlayer, false);
            return;
        }

        if (hollow.contains(p.getLocation().subtract(0, 1, 0).getBlock().getType())) {//If they are standing on a hollow block, don't let it glitch
            reset(to, from, ninjaPlayer, false);
            return;
        }

        if ((check.getBlockX() == lastLoc.getBlockX() && check.getBlockZ() == lastLoc.getBlockZ())) {
            if (check.getBlockY() != lastLoc.getBlockY() && getDifference(check.getBlockY(), lastLoc.getBlockY()) >= 2) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Block b : blocksFromTwoPoints(check, lastLoc.clone())) {
                            final Material m = b.getType();
                            if (!hollow.contains(m)) {
                                teleportBypass.add(p.getName());
                                Bukkit.getPlayer(p.getName()).teleport(ninjaPlayer.getCheckData().getLastLocation());
                                int yFrom = lastLoc.getBlockY();
                                int yTo = check.getBlockY();
                                ninjaPlayer.addToQueue(new VClipCheck(ninjaPlayer.getCheckData().clone()));
                                reset(to, from, ninjaPlayer, true);
                                break;
                            }
                        }
                    }
                }.runTaskAsynchronously(NinjaCore.getPlugin());
            } else {
                reset(to, from, ninjaPlayer, false);
            }
        } else {
            reset(to, from, ninjaPlayer, false);
        }

        if (p.getLocation().subtract(0, 1.49, 0).getBlock().getType() == Material.AIR || !p.isOnGround()) {
            ninjaPlayer.getCheckData().setFalling(true);
        } else {
            ninjaPlayer.getCheckData().setFalling(false);
        }

    }

    private int getDifference(int a, int b) {
        if (a >= b) {
            return a - b;
        } else {
            return b - a;
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (!PlayerManager.contains(e.getPlayer().getName())) return;
        if (!isEnabled()) return;
        if (!teleportBypass.contains(e.getPlayer().getName())) {
            if (PlayerManager.contains(e.getPlayer().getName())) {
                NinjaPlayer ninjaPlayer = PlayerManager.get(e.getPlayer().getName());
                ninjaPlayer.getCheckData().setLastLocation(e.getTo());
            }
        } else {
            teleportBypass.remove(e.getPlayer().getName());
        }
    }

    public static ArrayList<Material> hollow = new ArrayList<>();

    public void reset(Location to, Location from, NinjaPlayer ninjaPlayer, boolean b) {
        Player p = ninjaPlayer.getBukkitPlayer();
        if (b) {
            if (to.getBlockX() != from.getBlockX() && from.getBlockZ() != to.getBlockZ()) {
                if (teleportBypass.contains(p.getName())) {
                    teleportBypass.remove(p.getName());
                }
                ninjaPlayer.getCheckData().setLastLocation(p.getLocation());

                ninjaPlayer.getCheckData().setWasFalling(ninjaPlayer.getCheckData().isFalling());

                ninjaPlayer.getCheckData().setWasOnGround(p.isOnGround());
            }
        } else {
            if (teleportBypass.contains(p.getName())) {
                teleportBypass.remove(p.getName());
            }
            ninjaPlayer.getCheckData().setLastLocation(p.getLocation());

            ninjaPlayer.getCheckData().setWasFalling(ninjaPlayer.getCheckData().isFalling());

            ninjaPlayer.getCheckData().setWasOnGround(p.isOnGround());
        }
    }

    static {
        hollow.add(Material.SAPLING);
        hollow.add(Material.WEB);
        hollow.add(Material.DEAD_BUSH);
        hollow.add(Material.FLOWER_POT);
        hollow.add(Material.RED_ROSE);
        hollow.add(Material.YELLOW_FLOWER);
        hollow.add(Material.RED_MUSHROOM);
        hollow.add(Material.BROWN_MUSHROOM);
        hollow.add(Material.TORCH);
        hollow.add(Material.LADDER);
        hollow.add(Material.SNOW);
        hollow.add(Material.VINE);
        hollow.add(Material.CARPET);
        hollow.add(Material.FENCE);
        hollow.add(Material.FENCE_GATE);
        hollow.add(Material.NETHER_FENCE);
        hollow.add(Material.IRON_FENCE);
        hollow.add(Material.PAINTING);
        hollow.add(Material.STONE_BUTTON);
        hollow.add(Material.WOOD_BUTTON);
        hollow.add(Material.LEVER);
        hollow.add(Material.GOLD_PLATE);
        hollow.add(Material.STONE_PLATE);
        hollow.add(Material.GOLD_PLATE);
        hollow.add(Material.IRON_PLATE);
        hollow.add(Material.REDSTONE_TORCH_ON);
        hollow.add(Material.REDSTONE_TORCH_OFF);
        hollow.add(Material.TRAP_DOOR);
        hollow.add(Material.ENDER_CHEST);
        hollow.add(Material.WATER);
        hollow.add(Material.STATIONARY_WATER);
        hollow.add(Material.LAVA);
        hollow.add(Material.STATIONARY_LAVA);
        hollow.add(Material.WOOD_DOOR);
        hollow.add(Material.WOODEN_DOOR);
        hollow.add(Material.IRON_DOOR);
        hollow.add(Material.IRON_DOOR_BLOCK);
        hollow.add(Material.RAILS);
        hollow.add(Material.ACTIVATOR_RAIL);
        hollow.add(Material.DETECTOR_RAIL);
        hollow.add(Material.POWERED_RAIL);
        hollow.add(Material.COBBLE_WALL);
        hollow.add(Material.SIGN);
        hollow.add(Material.SIGN_POST);
        hollow.add(Material.WALL_SIGN);
        hollow.add(Material.SKULL);
        hollow.add(Material.AIR);
        hollow.add(Material.LONG_GRASS);
        hollow.add(Material.DOUBLE_PLANT);
        hollow.add(Material.STEP);
        hollow.add(Material.WOOD_STEP);
        hollow.add(Material.PORTAL);
        hollow.add(Material.ENDER_PORTAL);
        hollow.add(Material.ENDER_PORTAL_FRAME);
        hollow.add(Material.CROPS);
        hollow.add(Material.CHEST);
        hollow.add(Material.DOUBLE_PLANT);
        hollow.add(Material.TRAPPED_CHEST);
        hollow.add(Material.ENDER_CHEST);
        hollow.add(Material.IRON_DOOR);
        hollow.add(Material.FENCE_GATE);
        hollow.add(Material.WOOD_DOOR);
        hollow.add(Material.ACACIA_STAIRS);
        hollow.add(Material.BIRCH_WOOD_STAIRS);
        hollow.add(Material.BRICK_STAIRS);
        hollow.add(Material.COBBLESTONE_STAIRS);
        hollow.add(Material.DARK_OAK_STAIRS);
        hollow.add(Material.JUNGLE_WOOD_STAIRS);
        hollow.add(Material.NETHER_BRICK_STAIRS);
        hollow.add(Material.QUARTZ_STAIRS);
        hollow.add(Material.SANDSTONE_STAIRS);
        hollow.add(Material.BIRCH_WOOD_STAIRS);
        hollow.add(Material.WOOD_STAIRS);
        hollow.add(Material.SMOOTH_STAIRS);
        hollow.add(Material.SPRUCE_WOOD_STAIRS);
        hollow.add(Material.LAVA);
        hollow.add(Material.STATIONARY_LAVA);
        hollow.add(Material.SUGAR_CANE);
        hollow.add(Material.SUGAR_CANE_BLOCK);
        hollow.add(Material.CAKE_BLOCK);
        hollow.add(Material.CAKE);
        hollow.add(Material.REDSTONE);
        hollow.add(Material.REDSTONE_WIRE);
        hollow.add(Material.REDSTONE_COMPARATOR);
        hollow.add(Material.REDSTONE_COMPARATOR_OFF);
        hollow.add(Material.REDSTONE_COMPARATOR_ON);
        hollow.add(Material.DIODE);
        hollow.add(Material.DIODE_BLOCK_ON);
        hollow.add(Material.DIODE_BLOCK_OFF);
        hollow.add(Material.MELON_STEM);
        hollow.add(Material.PUMPKIN_STEM);
        hollow.add(Material.NETHER_STALK);
        hollow.add(Material.NETHER_WARTS);
        hollow.add(Material.LADDER);
        hollow.add(Material.REDSTONE_TORCH_OFF);
        hollow.add(Material.REDSTONE_TORCH_ON);
        hollow.add(Material.VINE);
        hollow.add(Material.POTATO);
        hollow.add(Material.POTATO_ITEM);
        hollow.add(Material.BAKED_POTATO);
        hollow.add(Material.POISONOUS_POTATO);
        hollow.add(Material.BREWING_STAND);
        hollow.add(Material.BREWING_STAND_ITEM);
        hollow.add(Material.COBBLE_WALL);
        hollow.add(Material.BOAT);
        hollow.add(Material.WATER_LILY);
        hollow.add(Material.HOPPER);
        hollow.add(Material.RAILS);
        hollow.add(Material.GLASS);
        hollow.add(Material.STAINED_GLASS);
        hollow.add(Material.THIN_GLASS);
        hollow.add(Material.STAINED_GLASS_PANE);
    }

    public static List<Block> blocksFromTwoPoints(Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<Block>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }

        return blocks;
    }

    @Override
    public String getDetail(CheckData data) {
        return data.getLastLocation().getY()+"y to "+data.getTo().getY()+"y";
    }

}
