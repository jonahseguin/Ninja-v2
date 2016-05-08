/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.check;

import com.shawckz.ninja.player.NinjaPlayer;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckResult {

    @NonNull @Getter private Check check;
    @NonNull @Getter private NinjaPlayer ninjaPlayer;
    @NonNull @Getter private ViolationSnapshot data;

}
