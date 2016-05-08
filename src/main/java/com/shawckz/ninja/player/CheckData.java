/*
 * Copyright 2015 Jonah Seguin (Shawckz.com).  All rights reserved.
 */

package com.shawckz.ninja.player;

import lombok.*;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@AllArgsConstructor
public class CheckData {

    @NonNull @Getter @Setter private Player player;

    @Getter @Setter private int ping = 0;
    @Getter @Setter private Location playerLocation = null;
    @Getter @Setter private int cps = 0;
    @Getter @Setter private int lastCps = 0;
    @Getter @Setter private GameMode gameMode = GameMode.SURVIVAL;
    @Getter @Setter private long lastAttackTime = 0;
    @Getter @Setter private double blocksPerSecond = 0;
    @Getter @Setter private double hps = 0;
    @Getter @Setter private double health = 20;
    @Getter @Setter private long bowPullTime = 0;
    @Getter @Setter private long bowPullShot = 0;
    @Getter @Setter private Arrow bowArrow = null;
    @Getter @Setter private int bowSlot = 0;
    @Getter @Setter private boolean pullingBow = false;
    @Getter @Setter private double tps = 20;
    @Getter @Setter private double blockPlacePerSecond = 0;
    @Getter @Setter private double blockBreakPerSecond = 0;
    @Getter @Setter private long eatInteract = 0;
    @Getter @Setter private long eatFinish = 0;
    @Getter @Setter private int eatSlot = 0;
    @Getter @Setter private Location lastLocation = null;
    @Getter @Setter private boolean falling = false;
    @Getter @Setter private boolean wasOnGround = false;
    @Getter @Setter private boolean wasFalling = false;
    @Getter @Setter private int hits = 0;
    @Getter @Setter private int misses = 0;
    @Getter @Setter private Location to = null;
    @Getter @Setter private Location from = null;
    @Getter @Setter private long airTime = System.currentTimeMillis();

    @Override
    public CheckData clone(){
        return new CheckData(player,ping,playerLocation,cps,lastCps,gameMode,lastAttackTime,blocksPerSecond,hps,health
        ,bowPullTime,bowPullShot,bowArrow,bowSlot,pullingBow,tps,blockPlacePerSecond,blockBreakPerSecond,eatInteract,
                eatFinish,eatSlot,lastLocation,falling,wasOnGround,wasFalling,hits,misses,to,from,airTime);
    }

}
