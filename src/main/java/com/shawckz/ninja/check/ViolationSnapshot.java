package com.shawckz.ninja.check;

import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.player.CheckData;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.player.XrayStats;

import java.util.Map;
import java.util.UUID;

/**
 * Created by 360 on 3/28/2015.
 */
public class ViolationSnapshot {
    private final NinjaPlayer ninjaPlayer;
    private final CheckData checkData;
    private final int vl;
    private final Map<CheckCaller,Integer> checkVl;
    private final String id;
    private final XrayStats xrayStats;
    public ViolationSnapshot(NinjaPlayer ninjaPlayer, CheckData checkData, int vl, Map<CheckCaller, Integer> checkVl,XrayStats xrayStats) {
        this.ninjaPlayer = ninjaPlayer;
        this.checkData = checkData;
        this.vl = vl;
        this.checkVl = checkVl;
        this.id = UUID.randomUUID().toString().substring(0,6).toLowerCase();
        this.xrayStats = xrayStats;
    }

    public ViolationSnapshot(NinjaPlayer ninjaPlayer, CheckData checkData, int vl, Map<CheckCaller, Integer> checkVl, String id,XrayStats xrayStats) {
        this.ninjaPlayer = ninjaPlayer;
        this.checkData = checkData;
        this.vl = vl;
        this.checkVl = checkVl;
        this.id = id;
        this.xrayStats = xrayStats;
    }

    public XrayStats getXrayStats() {
        return xrayStats;
    }

    public String getId() {
        return id;
    }

    public NinjaPlayer getNinjaPlayer() {
        return ninjaPlayer;
    }

    public CheckData getCheckData() {
        return checkData;
    }

    public int getVl() {
        return vl;
    }

    public Map<CheckCaller, Integer> getCheckVl() {
        return checkVl;
    }



}
