package com.shawckz.ninja.player;

import com.shawckz.ninja.check.Check;
import com.shawckz.ninja.check.CheckManager;
import com.shawckz.ninja.check.ViolationSnapshot;
import com.shawckz.ninja.call.CheckCaller;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by 360 on 3/26/2015.
 */
public class NinjaPlayer {

    @Getter @Setter private String name;//done
    @Getter @Setter private Player bukkitPlayer;
    @Getter @Setter private boolean alertsEnabled;
    @Getter @Setter private int vl;//done
    @Getter @Setter private Map<CheckCaller,Integer> checkVl = new HashMap<>();//done
    @Getter @Setter private int previousKicks;
    @Getter @Setter private Map<String,ViolationSnapshot> violations = new HashMap<>();
    @Getter @Setter private XrayStats xrayStats;
    @Getter @Setter private boolean tptoggle;
    @Getter @Setter private CheckData checkData;

    @Getter private ConcurrentLinkedQueue<Check> checkQueue = new ConcurrentLinkedQueue<>();

    public NinjaPlayer(Player bukkitPlayer) {
        this.name = bukkitPlayer.getName();
        this.bukkitPlayer = bukkitPlayer;

        vl = 0;

        for(CheckCaller c : CheckManager.get().getCallers()){
            checkVl.put(c,0);
        }

        this.alertsEnabled = false;
        this.previousKicks = 0;
        this.violations = new HashMap<>();
        this.xrayStats = new XrayStats(this);
        this.tptoggle = false;
        this.checkData = new CheckData(bukkitPlayer);
    }

    public NinjaPlayer(String name) {
        this.name = name;
        this.bukkitPlayer = null;

        vl = 0;

        for(CheckCaller c : CheckManager.get().getCallers()){
            checkVl.put(c,0);
        }

        this.alertsEnabled = false;
        this.previousKicks = 0;
        this.violations = new HashMap<>();
        this.xrayStats = new XrayStats(this);
        this.tptoggle = false;
        this.checkData = new CheckData(null);
    }

    public void addToQueue(Check check){
        checkQueue.offer(check);
    }

    public Check getQueueNext(){
        return checkQueue.peek();
    }

    public Check doQueueNext(){
        Check c = getQueueNext();
        checkQueue.remove(c);
        return c;
    }

    public void reset(){
        this.previousKicks = 0;
        this.vl = 0;
        checkVl.clear();
        for(CheckCaller c : CheckManager.get().getCallers()){
            checkVl.put(c,0);
        }

    }

    public ViolationSnapshot getViolation(String id){
        return violations.get(id);
    }

    public int getCheckVL(CheckCaller check){
        return checkVl.get(check);
    }

    public void setCheckVL(CheckCaller check, int vl){
        checkVl.put(check, vl);
    }

    public void addVL(CheckCaller check, int vl){
        setCheckVL(check,getCheckVL(check)+vl);
        addVL(vl);
    }

    private void addVL(int vl){
        this.vl+=vl;
    }


}
