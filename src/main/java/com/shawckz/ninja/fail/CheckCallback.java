package com.shawckz.ninja.fail;

import java.util.HashSet;

/**
 * Created by 360 on 3/27/2015.
 */
public interface CheckCallback {

    public HashSet<CheckCallbackResult> checkFailed();

}
