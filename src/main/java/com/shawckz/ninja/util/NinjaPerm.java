package com.shawckz.ninja.util;

/**
 * Created by 360 on 3/27/2015.
 */
public enum NinjaPerm {

    ALERTS_TOGGLE("ninja.alerts"),
    ALERTS_VIEW("ninja.alerts");

    private String perm;

    NinjaPerm(String perm){
        this.perm = perm;
    }

    public String getPerm() {
        return perm;
    }
}
