package com.shawckz.ninja.fail;

import com.shawckz.ninja.call.CheckCaller;
import com.shawckz.ninja.player.CheckData;
import com.shawckz.ninja.player.NinjaPlayer;
import com.shawckz.ninja.util.AutobanManager;

import java.util.HashSet;

/**
 * Created by 360 on 3/27/2015.
 */
public class CheckFail {

    private NinjaPlayer p;
    private CheckCaller check;
    private CheckData checkData;

    public CheckFail(NinjaPlayer p, CheckCaller check, CheckData checkData) {
        this.p = p;
        this.check = check;
        this.checkData = checkData;
    }

    public CheckData getCheckData() {
        return checkData;
    }

    public NinjaPlayer getPlayer() {
        return p;
    }

    public CheckCaller getCheck() {
        return check;
    }

    public CheckCallback call() {
        return new CheckCallback() {
            @Override
            public HashSet<CheckCallbackResult> checkFailed() {

                HashSet<CheckCallbackResult> results = new HashSet<>();

                if (p.getCheckVL(check) >= check.getPunishVL() && check.isAutoBan()) {
                    if (!AutobanManager.hasAutoban(p.getName())) {
                        if ((p.getCheckVL(check) >= check.getMaxVL() && p.getCheckVL(check) % check.getMaxVL() == 0)) {
                            results.add(CheckCallbackResult.ALERTED);
                        }
                        results.add(CheckCallbackResult.BANNED);
                        results.add(CheckCallbackResult.CANCELLED);
                    } else {
                        results.add(CheckCallbackResult.CANCELLED);
                    }
                } else if ((p.getCheckVL(check) >= check.getMaxVL() && p.getCheckVL(check) % check.getMaxVL() == 0) || p.getCheckVL(check) == check.getRaiseLevel()) {
                    results.add(CheckCallbackResult.CANCELLED);
                    results.add(CheckCallbackResult.ALERTED);
                }

                if (results.isEmpty()) {
                    results.add(CheckCallbackResult.CANCELLED);
                }

                return results;
            }
        };
    }

}
