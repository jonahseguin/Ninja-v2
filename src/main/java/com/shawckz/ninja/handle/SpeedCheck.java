/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.handle;

import com.shawckz.ninja.check.Check;
import com.shawckz.ninja.check.CheckManager;
import com.shawckz.ninja.player.CheckData;

/**
 * Created by 360 on 3/31/2015.
 */
public class SpeedCheck extends Check {

    public SpeedCheck(CheckData checkData) {
        super(CheckManager.get().getCheck("Speed"), checkData);
    }

    @Override
    public boolean check() {
        if(checkData.getBlocksPerSecond() > 20){
            return false;
        }
        return true;
    }
}
