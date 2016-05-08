package com.shawckz.ninja.check;

import com.shawckz.ninja.Ninja;
import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.player.CheckData;
import com.shawckz.ninja.player.NinjaPlayer;
import org.bukkit.event.Listener;

/**
 * Created by 360 on 3/26/2015.
 */
public abstract class Check implements Listener{

    public CheckData checkData;
    public CheckCaller caller;

    public Check(CheckCaller caller, CheckData checkData){
        this.caller = caller;
        this.checkData = checkData;
    }


    public abstract boolean check();

    public synchronized boolean handleCheckFail(NinjaPlayer player) {
        player.addVL(caller,caller.getRaiseLevel());
        return Ninja.callCheckFailedEvent(player, caller, player.getCheckData(),"");
    }

    public synchronized boolean handleCheckFail(NinjaPlayer player, String type) {
        player.addVL(caller,caller.getRaiseLevel());
        return Ninja.callCheckFailedEvent(player, caller, player.getCheckData(),type);
    }

}
