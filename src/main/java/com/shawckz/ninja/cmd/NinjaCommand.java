package com.shawckz.ninja.cmd;

import com.shawckz.ninja.Ninja;
import com.shawckz.ninja.NinjaCore;
import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.check.CheckManager;
import com.shawckz.ninja.check.ViolationSnapshot;
import com.shawckz.ninja.cmd.command.InspectViolation;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.PlayerManager;
import com.shawckz.ninja.util.Alert;
import com.shawckz.ninja.util.ConfigHelper;
import com.shawckz.ninja.util.Freeze;
import com.shawckz.ninja.util.Lag;
import mkremins.fanciful.FancyMessage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by 360 on 3/28/2015.
 */
public class NinjaCommand implements CommandExecutor {

    /**
     * Note 4/7/2016 -Shawckz
     *
     * This class is terrible, but I'm not going to fix as this is now an inactive project.
     *
     */

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (p.hasPermission("ninja.use")) {

                if(cmd.getName().equalsIgnoreCase("freeze")){
                    if(!sender.hasPermission("ninja.freeze")){
                        sender.sendMessage(ChatColor.RED+"No permission.");
                        return true;
                    }
                    if(args.length > 0){

                        Player t = Bukkit.getPlayer(args[0]);

                        if(t!=null){

                            if(Freeze.hasFreeze(t)){
                                Freeze.removeFreeze(t);
                                t.sendMessage(Ninja.NINJA_PREFIX+ChatColor.GOLD+"You have been §cunfrozen.");
                                p.sendMessage(Ninja.NINJA_PREFIX + ChatColor.RED + t.getName() + ChatColor.GOLD + " has been §cunfrozen.");
                            }
                            else{
                                new Freeze(t).run();
                                t.sendMessage(Ninja.NINJA_PREFIX+ChatColor.GOLD+"You have been §cfrozen.");
                                p.sendMessage(Ninja.NINJA_PREFIX+ChatColor.RED+t.getName()+ChatColor.GOLD+" has been §cfrozen.");
                            }

                        }
                        else{
                            p.sendMessage(ChatColor.RED+"Player '"+args[1]+"' not found.");
                        }

                    }
                    else{
                        p.sendMessage(ChatColor.RED+"Usage: /freeze <player>");
                    }
                    return true;
                }

                if(args.length == 0){
                    p.sendMessage("§7*** §8[§cNinja§8] §7***");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cinspect§7 <player>");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cvlookup§7 <player> <ID>");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cfreeze§7 <player>");
                    p.sendMessage(ChatColor.GRAY+"/ninja §clag");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cdisable");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cenable");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cabtoggle");
                    p.sendMessage(ChatColor.GRAY+"/ninja §ccheck §7<check> <enable|disable|autoban> [enable|disable]");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cstatus");
                    p.sendMessage(ChatColor.GRAY+"/ninja §ctptoggle");
                    return true;
                }

                if(args[0].equalsIgnoreCase("vlookup")){
                    if(args.length >= 2){
                        if(Bukkit.getPlayer(args[1])!=null){

                            NinjaPlayer ninjaPlayer = PlayerManager.get(Bukkit.getPlayer(args[1]).getName());

                            if(ninjaPlayer.getViolations().containsKey(args[2].toLowerCase())){

                                p.openInventory(InspectViolation.getInspectInventory(Bukkit.getPlayer(args[1]).getName(),ninjaPlayer.getViolation(args[2].toLowerCase())));

                            }
                            else{
                                p.sendMessage(ChatColor.RED+"That player does not have a violation by ID '"+args[2].toLowerCase()+"'");
                            }

                        }
                        else{
                            p.sendMessage("todo find offline player data");
                            //TODO: Fetch from database
                        }
                    }
                    else{
                        p.sendMessage(ChatColor.RED+"Usage: /ninja vlookup <player> <id>");
                    }
                }
                else if (args[0].equalsIgnoreCase("inspect")){
                    if(args.length > 1){

                        if(Bukkit.getPlayer(args[1])!=null){
                            NinjaPlayer ninjaPlayer = PlayerManager.get(Bukkit.getPlayer(args[1]).getName());

                            ViolationSnapshot sn = new ViolationSnapshot(ninjaPlayer,ninjaPlayer.getCheckData().clone(),ninjaPlayer.getVl(),ninjaPlayer.getCheckVl(),"INSPECT",ninjaPlayer.getXrayStats().clone());
                            p.openInventory(InspectViolation.getPlayerInspectInventory(ninjaPlayer.getName(),sn));

                        }
                        else{
                            p.sendMessage("todo find offline player data");
                            //TODO: Fetch from database
                        }

                    }
                    else{
                        p.sendMessage(ChatColor.RED+"Usage: /ninja inspect <player>");
                    }
                }
                else if (args[0].equalsIgnoreCase("freeze")||args[0].equalsIgnoreCase("unfreeze")){
                    if(!sender.hasPermission("ninja.freeze")){
                        sender.sendMessage(ChatColor.RED+"No permission.");
                        return true;
                    }
                    if(args.length > 1){

                        Player t = Bukkit.getPlayer(args[1]);

                        if(t!=null){

                            if(Freeze.hasFreeze(t)){
                                Freeze.removeFreeze(t);
                                t.sendMessage(Ninja.NINJA_PREFIX+ChatColor.GOLD+"You have been §cunfrozen.");
                                p.sendMessage(Ninja.NINJA_PREFIX + ChatColor.RED + t.getName() + ChatColor.GOLD + " has been §cunfrozen.");
                            }
                            else{
                                new Freeze(t).run();
                                t.sendMessage(Ninja.NINJA_PREFIX+ChatColor.GOLD+"You have been §cfrozen.");
                                p.sendMessage(Ninja.NINJA_PREFIX+ChatColor.RED+t.getName()+ChatColor.GOLD+" has been §cfrozen.");
                            }

                        }
                        else{
                            p.sendMessage(ChatColor.RED+"Player '"+args[1]+"' not found.");
                        }

                    }
                    else{
                        p.sendMessage(ChatColor.RED+"Usage: /ninja freeze <player>");
                    }
                }
                else if (args[0].equalsIgnoreCase("lag")){
                    sender.sendMessage(Ninja.NINJA_PREFIX+"§7Current server TPS: §c"+ Lag.getTPS() + "§7(§c"+Lag.getLagPerecentage()+"% lag§7)");
                }
                else if (args[0].equalsIgnoreCase("enable")){
                    if(!sender.hasPermission("ninja.toggle")){
                        sender.sendMessage(ChatColor.RED+"No permission.");
                        return true;
                    }
                    for(CheckCaller c : CheckManager.get().getCallers()){
                        c.setEnabled(true);
                    }
                    FancyMessage m = new FancyMessage(Ninja.NINJA_PREFIX+"Ninja has been §aenabled§7. ");
                    m.then("§c§lCLICK HERE").tooltip("§cDisable Ninja").command("/ninja disable");
                    m.then(" ");
                    m.then("§7to §cdisable §7it.");
                    Alert.staffMsg(m);
                }
                else if (args[0].equalsIgnoreCase("disable")){
                    if(!sender.hasPermission("ninja.toggle")){
                        sender.sendMessage(ChatColor.RED+"No permission.");
                        return true;
                    }
                    for(CheckCaller c : CheckManager.get().getCallers()){
                        c.setEnabled(false);
                    }
                    FancyMessage m = new FancyMessage(Ninja.NINJA_PREFIX+"Ninja has been §cdisabled§7. ");
                    m.then("§c§lCLICK HERE").tooltip("§aEnable Ninja").command("/ninja enable");
                    m.then(" ");
                    m.then("§7to §are-enable §7it.");
                    Alert.staffMsg(m);
                }
                else if (args[0].equalsIgnoreCase("abtoggle")){

                    ConfigHelper.get().setAutoban(!ConfigHelper.get().autobanEnabled());

                    Alert.staffMsg("Autoban has been "+(ConfigHelper.get().autobanEnabled() ? "§aenabled" : "§cdisabled")+"§7.");

                }
                else if (args[0].equalsIgnoreCase("tptoggle")){

                    NinjaPlayer ninjaPlayer = PlayerManager.get(sender.getName());

                    ninjaPlayer.setTptoggle(!ninjaPlayer.isTptoggle());

                    p.sendMessage(Ninja.NINJA_PREFIX+"TP Toggle: "+(ninjaPlayer.isTptoggle() ? "§aenabled" : "§cdisabled"));

                }
                else if (args[0].equalsIgnoreCase("version")){
                    sender.sendMessage(ChatColor.RED+"Ninja version "+ NinjaCore.getPlugin().getDescription().getVersion());
                }
                else if (args[0].equalsIgnoreCase("check")){
                    if(!sender.hasPermission("ninja.check.toggle")){
                        sender.sendMessage(ChatColor.RED+"No permission.");
                        return true;
                    }
                    if(args.length > 2){

                        if(CheckManager.get().hasCheck(args[1])){
                            CheckCaller check = CheckManager.get().getCheck(args[1]);

                            if(args[2].equalsIgnoreCase("enable")){
                                check.setEnabled(true);
                                ConfigHelper.get().setCheckEnabled(check,true);
                                Alert.staffMsg("The check '"+check.getName()+"' has been "+(check.isEnabled() ? "§aenabled" : "§cdisabled")+"§7.");
                            }
                            else if (args[2].equalsIgnoreCase("disable")){
                                check.setEnabled(false);
                                ConfigHelper.get().setCheckEnabled(check,false);
                                Alert.staffMsg("The check '"+check.getName()+"' has been "+(check.isEnabled() ? "§aenabled" : "§cdisabled")+"§7.");
                            }
                            else if (args[2].equalsIgnoreCase("autoban")){
                                if(args.length > 3){
                                    if(args[3].equalsIgnoreCase("enable")){
                                        check.setAutoBan(true);
                                        ConfigHelper.get().setAutobanEnabled(check,true);
                                        Alert.staffMsg("Autoban has been "+(check.isEnabled() ? "§aenabled" : "§cdisabled")+"§7 for '"+check.getName()+"'.");
                                    }
                                    else if (args[3].equalsIgnoreCase("disable")){
                                        check.setAutoBan(false);
                                        ConfigHelper.get().setAutobanEnabled(check,false);
                                        Alert.staffMsg("Autoban has been "+(check.isEnabled() ? "§aenabled" : "§cdisabled")+"§7 for '"+check.getName()+"'.");
                                    }
                                    else{
                                        p.sendMessage(ChatColor.RED+"Usage: /ninja check <check> <enable|disable|autoban> [enable|disable]");
                                    }
                                }
                                else{
                                    p.sendMessage(ChatColor.RED+"Usage: /ninja check <check> <enable|disable|autoban> [enable|disable]");
                                }
                            }
                            else{
                                p.sendMessage(ChatColor.RED+"Usage: /ninja check <check> <enable|disable|autoban> [enable|disable]");
                            }

                        }
                        else{
                            p.sendMessage(ChatColor.RED+"Check not found.");
                        }

                    }
                    else{
                        p.sendMessage(ChatColor.RED+"Usage: /ninja check <check> <enable|disable>");
                    }
                }
                else if (args[0].equalsIgnoreCase("status")){
                    p.sendMessage("§7*** §cNinja Status §7***");
                    p.sendMessage(ChatColor.GRAY+"Version: "+ChatColor.RED+ NinjaCore.getPlugin().getDescription().getVersion());
                    List<String> checksEnabled = new ArrayList<>();
                    List<String> checksDisabled = new ArrayList<>();
                    for(CheckCaller c : CheckManager.get().getCallers()){
                        if(c.isEnabled()){
                            checksEnabled.add(c.getName());
                        }
                        else{
                            checksDisabled.add(c.getName());
                        }
                    }
                    String checkStatus = "";
                    for(String s : checksDisabled){
                        checkStatus+="§c"+s+"§7, ";
                    }
                    for(String s : checksEnabled){
                        checkStatus+="§a"+s+"§7, ";
                    }
                    checkStatus = checkStatus.substring(0, checkStatus.length()-2);
                    p.sendMessage(ChatColor.GRAY+"Checks: "+checkStatus);

                    p.sendMessage(ChatColor.GRAY+"Global Autoban: "+(ConfigHelper.get().autobanEnabled() ? "§aenabled" : "§cdisabled"));

                    String autoban = "";

                    for(CheckCaller c : CheckManager.get().getCallers()){
                        if(c.isAutoBan()){
                            autoban+="§a"+c.getName()+"§7, ";
                        }
                        else{
                            autoban+="§c"+c.getName()+"§7, ";
                        }
                    }

                    p.sendMessage(ChatColor.GRAY+"Autoban: "+autoban.substring(0,autoban.length()-2));

                    p.sendMessage("§7Current server TPS: §c"+ Lag.getTPS() + "§7(§c"+Lag.getLagPerecentage()+"% lag§7)");

                }
                else{
                    p.sendMessage("§7*** §8[§cNinja§8] §7***");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cinspect§7 <player>");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cvlookup§7 <player> <ID>");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cfreeze§7 <player>");
                    p.sendMessage(ChatColor.GRAY+"/ninja §clag");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cdisable");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cenable");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cabtoggle");
                    p.sendMessage(ChatColor.GRAY+"/ninja §ccheck §7<check> <enable|disable|autoban> [enable|disable]");
                    p.sendMessage(ChatColor.GRAY+"/ninja §cstatus");
                    p.sendMessage(ChatColor.GRAY+"/ninja §ctptoggle");
                }

            }
            else{
                p.sendMessage(ChatColor.RED+"No permission.");
            }

        }


        return true;
    }
}