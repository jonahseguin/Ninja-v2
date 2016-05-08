package com.shawckz.ninja.check;

import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.call.checks.*;
import com.shawckz.ninja.util.ConfigHelper;
import lombok.Getter;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 360 on 3/26/2015.
 */
public class CheckManager implements Listener {

    @Getter private List<CheckCaller> callers;
    private static CheckManager instance;

    protected CheckManager(){
        callers = new ArrayList<>();

        callers.add(new AutoClickCaller());
        callers.add(new FlyCaller());
        callers.add(new SpeedCaller());
        callers.add(new VClipCaller());
        callers.add(new XRayCaller());

        for(CheckCaller c : callers){
            c.setEnabled(ConfigHelper.get().isCheckEnabled(c));
            c.setAutoBan(ConfigHelper.get().isAutobanEnabled(c));
        }
    }

    public static CheckManager get() {
        if (instance == null) {
            synchronized (CheckManager.class) {
                if (instance == null) {
                    instance = new CheckManager();
                }
            }
        }
        return instance;
    }

    public boolean hasCheck(String name){
        for(CheckCaller c : callers){
            if(c.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public CheckCaller getCheck(String name){
        for(CheckCaller c : callers){
            if(c.getName().equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }

}
