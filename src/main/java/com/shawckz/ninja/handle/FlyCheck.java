/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.handle;

import com.shawckz.ninja.check.Check;
import com.shawckz.ninja.check.CheckManager;
import com.shawckz.ninja.player.CheckData;

/**
 * Created by 360 on 3/27/2015.
 */
public class FlyCheck extends Check {

    public FlyCheck(CheckData checkData) {
        super(CheckManager.get().getCheck("Fly"), checkData);
    }

    @Override
    public boolean check() {

        if(!checkData.isFalling()) return true;

        final double xDistance = checkData.getTo().getX() - checkData.getFrom().getX();
        final double zDistance = checkData.getTo().getZ() - checkData.getFrom().getZ();

        final double hDistance = Math.sqrt(xDistance * xDistance + zDistance * zDistance);

        if (hDistance < 0.19) return true;

        final double total = Math.abs(hDistance * hDistance);

        final double yDistance = checkData.getTo().getY() - checkData.getFrom().getY();

        final long airTime = System.currentTimeMillis() - checkData.getAirTime();

        if (hDistance >= 0.5) {
            if (total > 0.7) {
                if(airTime > 5000){
                    //TODO: Debug airTime.
                    return false;
                }
            }
        }
        return true;
    }



}
