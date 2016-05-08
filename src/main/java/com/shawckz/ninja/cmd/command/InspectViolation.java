package com.shawckz.ninja.cmd.command;

import com.shawckz.ninja.check.ViolationSnapshot;
import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.PlayerManager;
import com.shawckz.ninja.player.XrayStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 360 on 3/28/2015.
 */
public class InspectViolation implements Listener{

    private static ItemStack skullFromString(String s, String name) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(s);
        if ( name != null )
        {
            meta.setDisplayName(name);
        }
        skull.setItemMeta(meta);
        return skull;
    }

    private static String locationToString(Location loc){
        return "X: "+loc.getBlockX()+", Y: "+loc.getBlockY()+", Z: "+loc.getBlockZ();
    }

    public static Inventory getPlayerInspectInventory(String name, ViolationSnapshot violationSnapshot){

        Inventory inv = Bukkit.createInventory(null,45,"[Ninja] Inspecting VL#"+violationSnapshot.getId());

        inv.setItem(4,skullFromString(name, ChatColor.YELLOW+"Inspecting player: §a"+name));

        {
            ItemStack i = new ItemStack(Material.PAPER,1);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName("§aViolation Levels");
            List<String> lore = new ArrayList<>();
            lore.add("§7xVL: §e"+violationSnapshot.getVl());
            for(CheckCaller c : violationSnapshot.getCheckVl().keySet()){
                lore.add("§c"+c.getName()+" §7VL: §e"+violationSnapshot.getCheckVl().get(c));
            }
            lore.add(ChatColor.GRAY+"Click to view all VLs");
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setItem(19,i);
        }

        {
            ItemStack i = new ItemStack(Material.DIAMOND_ORE,1);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName("§aXRay Statistics");
            List<String> lore = new ArrayList<>();
            lore.add("§eOre per hour:");
            for(XrayStats.XrayStat stat : violationSnapshot.getXrayStats().getVl().keySet()){
                lore.add("§c"+stat.toString()+"§7("+stat.getMax()+" MAX): §e"+violationSnapshot.getXrayStats().getVL(stat));
            }
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setItem(22,i);
        }

        {
            ItemStack i = new ItemStack(Material.NAME_TAG,1);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName("§aPlayer Data");
            List<String> lore = new ArrayList<>();

            lore.add("§7Ping: §e"+violationSnapshot.getCheckData().getPing());
            lore.add("§7Clicks/s: §e"+violationSnapshot.getCheckData().getCps());
            lore.add("§7Health: §e"+violationSnapshot.getCheckData().getHealth());
            lore.add("§7Health/s: §e"+violationSnapshot.getCheckData().getHps());
            lore.add("§7GameMode: §e"+violationSnapshot.getCheckData().getGameMode().toString());
            lore.add("§7Blocks/s: §e"+violationSnapshot.getCheckData().getBlocksPerSecond());
            lore.add("§7Location: §e" + locationToString(violationSnapshot.getCheckData().getPlayerLocation()));
            lore.add("§7Last arrow fired on: §e"+violationSnapshot.getCheckData().getBowPullShot()+"ms");
            lore.add("§7Bow pulled at: §e"+violationSnapshot.getCheckData().getBowPullTime()+"ms");
            lore.add("§7Pulling bow: §e"+violationSnapshot.getCheckData().isPullingBow());
            lore.add("§7Bow pull length: §e"+(violationSnapshot.getCheckData().getBowPullShot()-violationSnapshot.getCheckData().getBowPullTime())+"ms");
            lore.add("§7Accuracy: §e"+Math.round(violationSnapshot.getCheckData().getHits() / violationSnapshot.getCheckData().getMisses() *100)+"%");
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setItem(25,i);
        }

        {
            ItemStack i = new ItemStack(Material.WATCH,1);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName("§aServer");
            List<String> lore = new ArrayList<>();

            lore.add("§7TPS: §e"+violationSnapshot.getCheckData().getTps());
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setItem(40,i);
        }


        return inv;
    }

    public static Inventory getInspectInventory(String name, ViolationSnapshot violationSnapshot){

        Inventory inv = Bukkit.createInventory(null,45,"[Ninja] Inspecting #"+violationSnapshot.getId());

        inv.setItem(4,skullFromString(name, ChatColor.YELLOW+"Inspecting: §a"+name));

        {
            ItemStack i = new ItemStack(Material.PAPER,1);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName("§aViolation Levels");
            List<String> lore = new ArrayList<>();
            lore.add("§7xVL: §e"+violationSnapshot.getVl());
            for(CheckCaller c : violationSnapshot.getCheckVl().keySet()){
                lore.add("§c"+c.getName()+" §7VL: §e"+violationSnapshot.getCheckVl().get(c));
            }
            lore.add(ChatColor.GRAY+"Click to view all VLs");
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setItem(19,i);
        }

        {
            ItemStack i = new ItemStack(Material.DIAMOND_ORE,1);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName("§aXRay Statistics");
            List<String> lore = new ArrayList<>();
            lore.add("§eOre per hour:");
            for(XrayStats.XrayStat stat : violationSnapshot.getXrayStats().getVl().keySet()){
                lore.add("§c"+stat.toString()+"§7("+stat.getMax()+" MAX): §e"+violationSnapshot.getXrayStats().getVL(stat));
            }
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setItem(22,i);
        }

        {
            ItemStack i = new ItemStack(Material.NAME_TAG,1);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName("§aPlayer Data");
            List<String> lore = new ArrayList<>();

            lore.add("§7Ping: §e"+violationSnapshot.getCheckData().getPing());
            lore.add("§7Clicks/s: §e"+violationSnapshot.getCheckData().getCps());
            lore.add("§7Health: §e"+violationSnapshot.getCheckData().getHealth());
            lore.add("§7Health/s: §e"+violationSnapshot.getCheckData().getHps());
            lore.add("§7GameMode: §e"+violationSnapshot.getCheckData().getGameMode().toString());
            lore.add("§7Blocks/s: §e"+violationSnapshot.getCheckData().getBlocksPerSecond());
            lore.add("§7Location: §e" + locationToString(violationSnapshot.getCheckData().getPlayerLocation()));
            lore.add("§7Last arrow fired on: §e"+violationSnapshot.getCheckData().getBowPullShot()+"ms");
            lore.add("§7Bow pulled at: §e"+violationSnapshot.getCheckData().getBowPullTime()+"ms");
            lore.add("§7Pulling bow: §e"+violationSnapshot.getCheckData().isPullingBow());
            lore.add("§7Bow pull length: §e"+(violationSnapshot.getCheckData().getBowPullShot()-violationSnapshot.getCheckData().getBowPullTime())+"ms");
            lore.add("§7Accuracy: §e"+Math.round(violationSnapshot.getCheckData().getHits() / violationSnapshot.getCheckData().getMisses() *100)+"%");
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setItem(25,i);
        }

        {
            ItemStack i = new ItemStack(Material.WATCH,1);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName("§aServer");
            List<String> lore = new ArrayList<>();

            lore.add("§7TPS: §e"+violationSnapshot.getCheckData().getTps());
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setItem(40,i);
        }


        return inv;
    }

    private static Inventory getVLsInventory(NinjaPlayer ninjaPlayer){

        Inventory inv = Bukkit.createInventory(null,54,"VLs for "+ninjaPlayer.getName());

        {
            int count =  0;

            for(String s : ninjaPlayer.getViolations().keySet()){
                ItemStack i = new ItemStack(Material.PAPER);
                ItemMeta im = i.getItemMeta();
                im.setDisplayName("§6#"+s);
                List<String> lore = new ArrayList<>();
                ViolationSnapshot vs = ninjaPlayer.getViolation(s);
                for(CheckCaller c : vs.getCheckVl().keySet()){
                    lore.add(ChatColor.GOLD+c.getName()+": "+ChatColor.RED+vs.getCheckVl().get(c)+ChatColor.GRAY+"VL");
                }
                lore.add(ChatColor.GRAY+"Click to view full details");
                im.setLore(lore);
                i.setItemMeta(im);
                inv.addItem(i);
            }
        }
        return inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getInventory().getName().startsWith("[Ninja] Inspecting")){
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            if(e.getCurrentItem()!=null){
                if(e.getCurrentItem().getType()!=Material.AIR){
                    String name = e.getInventory().getName().split(" ")[2];
                    if(Bukkit.getPlayer(name)!=null){
                        NinjaPlayer ninjaPlayer = PlayerManager.get(name);

                        if(e.getCurrentItem().getType() == Material.PAPER){
                            ((Player)e.getWhoClicked()).openInventory(getVLsInventory(ninjaPlayer));
                        }
                    }

                }
            }

        }
        else if (e.getInventory().getName().startsWith("VLs for")){
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if(e.getCurrentItem()!=null){
                if(e.getCurrentItem().getType()!=Material.AIR){

                    if(e.getCurrentItem().getType() == Material.PAPER){
                        String name = e.getInventory().getName().split(" ")[2];
                        ((Player)e.getWhoClicked()).closeInventory();
                        ((Player)e.getWhoClicked()).performCommand("ninja vlookup " + name + " " + ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().split("#")[1]));
                    }

                }
            }
        }
    }

}
