package com.shawckz.ninja.cmd;

import com.shawckz.ninja.player.PlayerManager;
import com.shawckz.ninja.util.Alert;
import com.shawckz.ninja.util.AutobanManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by 360 on 3/28/2015.
 */
public class CancelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){

            Player p = (Player) sender;

            if(args.length == 0){
                p.sendMessage(ChatColor.RED+"Usage: /cancel <player> - Cancel a player's autoban.");
                return true;
            }

            if(Bukkit.getPlayer(args[0])!=null){

                Player t = Bukkit.getPlayer(args[0]);

                if(AutobanManager.hasAutoban(t.getName())){
                    AutobanManager.getAutoban(t.getName()).setCancelled(true);
                    Alert.staffMsg(t.getDisplayName() + " §7is no longer being §cautobanned§7.");
                    PlayerManager.get(t.getName()).reset();
                }
                else{
                    sender.sendMessage(ChatColor.RED + "§7That player is not being §cautobanned§7.");
                }

            }
            else{

                if(AutobanManager.hasAutoban(args[0])){
                    AutobanManager.getAutoban(args[0]).setCancelled(true);
                    Alert.staffMsg("§f"+args[0] + " §7is no longer being autobanned.");
                    //PlayerManager.get(args[0]).reset();
                }
                else{
                    sender.sendMessage(ChatColor.RED+"That player is not being autobanned. (or does not exist)");
                }
            }

        }
        else{
            sender.sendMessage(ChatColor.RED+"This is a player only command.");
        }

        return true;
    }

}
