package com.shawckz.ninja.util;

import com.shawckz.ninja.Ninja;
import com.shawckz.ninja.NinjaCore;
import com.shawckz.ninja.check.ViolationSnapshot;
import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.type.BanMethod;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by 360 on 3/28/2015.
 */
public class Autoban implements Listener{
    private String name;
    private NinjaPlayer ninjaPlayer;
    private int cd;
    private boolean cancelled;
    private CheckCaller check;
    private ViolationSnapshot violationSnapshot;
    Freeze freeze;
    public Autoban(NinjaPlayer ninjaPlayer, int cd, CheckCaller check, ViolationSnapshot violationSnapshot) {
        this.ninjaPlayer = ninjaPlayer;
        this.name = ninjaPlayer.getName();
        this.cd = cd;
        this.check = check;
        this.cancelled = false;
        Bukkit.getServer().getPluginManager().registerEvents(this, NinjaCore.getPlugin());
        this.violationSnapshot = violationSnapshot;
        this.freeze = new Freeze(ninjaPlayer.getBukkitPlayer());
    }

    public ViolationSnapshot getViolationSnapshot() {
        return violationSnapshot;
    }

    public void run(){

        if(ninjaPlayer.getBukkitPlayer() == null) return;

        /*if(ninjaPlayer.getBukkitPlayer()!=null){
            freeze.run();
            ninjaPlayer.getBukkitPlayer().setAllowFlight(true);
            ninjaPlayer.getBukkitPlayer().sendMessage("§4§l╔==============================");
            ninjaPlayer.getBukkitPlayer().sendMessage("§4§l║§7Hey there, §cMr. Cheater§7!");
            ninjaPlayer.getBukkitPlayer().sendMessage("§4§l║§7You will be banned for hacking [§c"+check.getName()+"§7] in §c"+cd+"§7 seconds.");
            ninjaPlayer.getBukkitPlayer().sendMessage("§4§l║§7Thanks! :)");
            ninjaPlayer.getBukkitPlayer().sendMessage("§4§l╚==============================");
        }*/

        FancyMessage fancyMessage = new FancyMessage(Ninja.NINJA_PREFIX);

        if(ninjaPlayer.getBukkitPlayer()!=null){
            fancyMessage.then(ninjaPlayer.getBukkitPlayer().getDisplayName());
            fancyMessage.tooltip("§cView the Violation Snapshot for this autoban on "+name);
            fancyMessage.command("/ninja vlookup "+name+" "+violationSnapshot.getId());
        }
        else{
            fancyMessage.then("§f"+name);
        }

        fancyMessage.then(" §7will be automatically §cbanned§7 §7[§c"+check.getName()+"§7] in §c"+cd+" §7seconds.  §7To cancel this, ");
        fancyMessage.then("§4§lCLICK HERE").tooltip("§aCancel autoban on "+name).command("/cancel "+name);
        fancyMessage.then("§7 or type §c/cancel "+name+"§7.");

        Alert.staffMsg(fancyMessage);

        new BukkitRunnable(){

            @Override
            public void run() {

                if(cancelled){
                    cancel();
                    return;
                }

                if(cd > 0){
                    cd--;
                    FancyMessage fancyMessage = new FancyMessage(Ninja.NINJA_PREFIX);
                    fancyMessage.then("§f"+(Bukkit.getPlayer(name) == null ? name : Bukkit.getPlayer(name).getDisplayName()));
                    fancyMessage.then(" §7will be automatically §cbanned§7 §7[§c"+check.getName()+"§7] in §c"+cd+" §7seconds.  §4§lCLICK HERE §7to cancel.");
                    fancyMessage.tooltip("§aCancel autoban on "+name).command("/cancel "+name);
                    if(cd % 5 == 0){
                        Alert.staffMsg(fancyMessage);
                    }
                }
                else{
                    ban();
                    cancel();
                }

            }


        }.runTaskTimer(NinjaCore.getPlugin(),20L,20L);
    }

    public void ban(){
        if(Ninja.banMethod == BanMethod.CONSOLE_COMMAND){
            String reason = NinjaCore.getPlugin().getConfig().getString("ninja.ban-message");
            reason = reason.replaceAll("%hack%",check.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"ban "+name+" "+reason);
        }
    }

    public NinjaPlayer getNinjaPlayer() {
        return ninjaPlayer;
    }

    public int getCd() {
        return cd;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
      /*  if(cancelled){
            if(Bukkit.getPlayer(name)!=null){
                Player p = Bukkit.getPlayer(name);
                freeze.setCancelled(true);
                Freeze.removeFreeze(p);
                p.setAllowFlight(false);
                p.sendMessage("§2§l╔==============================");
                p.sendMessage("§2§l║§7You are no longer being§c automatically banned§7 for hacking.");
                p.sendMessage("§2§l║§aSorry for any inconvenience!");
                p.sendMessage("§2§l╚==============================");
            }
        }*/
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public CheckCaller getCheck() {
        return check;
    }
}
