package com.shawckz.ninja.cmd;

import com.shawckz.ninja.Ninja;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.PlayerManager;
import com.shawckz.ninja.util.NinjaPerm;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by 360 on 3/28/2015.
 */
public class AlertsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(p.hasPermission(NinjaPerm.ALERTS_TOGGLE.getPerm())){

                NinjaPlayer ninjaPlayer = PlayerManager.get(p.getName());

                if(ninjaPlayer.isAlertsEnabled()){
                    ninjaPlayer.setAlertsEnabled(false);
                    p.sendMessage(Ninja.NINJA_PREFIX + "Alerts have been §cdisabled§7.");
                }
                else{
                    ninjaPlayer.setAlertsEnabled(true);
                    p.sendMessage(Ninja.NINJA_PREFIX + "Alerts have been §aenabled§7.");
                }

            }
            else{
                p.sendMessage(ChatColor.RED+"No permission");
            }

        }
        else{
            sender.sendMessage(ChatColor.RED+"This is a player only command.");
        }

        return true;
    }
}
