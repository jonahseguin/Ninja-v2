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
public class AutoClickCheck extends Check {

    public AutoClickCheck(CheckData checkData) {
        super(CheckManager.get().getCheck("AutoClick"), checkData);
    }

    @Override
    public boolean check() {
        if(checkData.getCps() >= 18){
            if(checkData.getLastCps() >= 16){
                if((checkData.getLastCps() >= checkData.getCps() ? checkData.getLastCps() - checkData.getCps() : checkData.getCps() - checkData.getLastCps()) <= 2){
                    //If the difference between their current and last CPS is less than or equal to 2, their autoclicking is consistent and should be handled.
                    return false;
                }
                else if (checkData.getCps() > 24 && checkData.getLastCps() > 20){
                    return true;
                }
            }
        }
        return true;
    }
}
