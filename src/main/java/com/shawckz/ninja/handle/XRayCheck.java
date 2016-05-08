/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.handle;

import com.shawckz.ninja.check.Check;
import com.shawckz.ninja.check.CheckManager;
import com.shawckz.ninja.player.CheckData;

/**
 * Created by 360 on 3/30/2015.
 */
public class XRayCheck extends Check {

    public XRayCheck(CheckData checkData) {
        super(CheckManager.get().getCheck("XRay"), checkData);
    }

    /**
     * Always pass this check, as it only gets called when it is failed, and is manually handled
     * outside of this class.
     * @return true
     */
    @Override
    public boolean check() {
        return true;
    }
}
