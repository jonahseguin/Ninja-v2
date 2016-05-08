/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.handle;

import com.shawckz.ninja.check.Check;
import com.shawckz.ninja.check.CheckManager;
import com.shawckz.ninja.player.CheckData;

/**
 * Created by 360 on 4/3/2015.
 */
public class VClipCheck extends Check {


    public VClipCheck(CheckData checkData) {
        super(CheckManager.get().getCheck("VClip"), checkData);
    }

    /**
     * This will be left as false, as we will only be calling this when we know a player has been vclipping
     * @return true
     */
    @Override
    public boolean check() {
        return false;
    }
}
