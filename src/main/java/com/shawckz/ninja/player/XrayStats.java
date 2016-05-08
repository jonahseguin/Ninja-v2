package com.shawckz.ninja.player;

import com.shawckz.ninja.handle.XRayCheck;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 360 on 3/30/2015.
 */
public class XrayStats {


    public static enum XrayStat {
        DIAMOND(32), EMERALD(40), GOLD(45);

        private double max;
        XrayStat(double max){
            this.max = max;
        }

        public double getMax() {
            return max;
        }

        public static boolean exists(String s){
            for(XrayStat stat : XrayStat.values()){
                if(stat.toString().equalsIgnoreCase(s)){
                    return true;
                }
            }
            return false;
        }
    }

    private Map<XrayStat, Double> vl = new HashMap<>();
    private NinjaPlayer ninjaPlayer;
    public XrayStats(NinjaPlayer ninjaPlayer) {
        this.ninjaPlayer = ninjaPlayer;
        for(XrayStat stat : XrayStat.values()){
            this.vl.put(stat,0.0);
        }
    }

    public XrayStats(NinjaPlayer ninjaPlayer,Map<XrayStat, Double> xvl) {
        this.ninjaPlayer = ninjaPlayer;
        this.vl = xvl;
    }


    public void check(XRayCheck check){
        for(XrayStat stat : vl.keySet()){
            if(vl.get(stat) >= stat.getMax()){
                check.handleCheckFail(ninjaPlayer, stat.toString().toLowerCase());
            }
        }
    }

    public void setVL(XrayStat stat, double vl){
        this.vl.put(stat,vl);
    }

    public void addVL(XrayStat stat, double vl){
        setVL(stat, (getVL(stat) + vl));
    }

    public void setVl(Map<XrayStat, Double> vl) {
        this.vl = vl;
    }

    public Map<XrayStat, Double> getVl() {
        return vl;
    }

    public Double getVL(XrayStat stat){
        return vl.get(stat);
    }


    public void reset(){
        for(XrayStat stat : XrayStat.values()){
            vl.put(stat,0.0);
        }
    }

    public Map<String, Double> toSerialized(){
        Map<String, Double> vls = new HashMap<>();
        for(XrayStats.XrayStat st : this.getVl().keySet()){
            vls.put(st.toString(),this.getVl().get(st));
        }
        return vls;
    }

    @Override
    public XrayStats clone(){
        return new XrayStats(ninjaPlayer,vl);
    }

}
