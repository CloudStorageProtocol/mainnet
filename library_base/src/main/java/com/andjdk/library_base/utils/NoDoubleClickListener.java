package com.andjdk.library_base.utils;

import android.view.View;

/**
 * creator tanxiongliang
 * 2018/10/29
 * desc:
 **/
public abstract class NoDoubleClickListener implements View.OnClickListener {

    private long lastClickTime = 0;

    @Override
    public synchronized void onClick(View v) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500)
            return;
        lastClickTime = time;
        noDoubleClick(v);
    }

    public abstract void noDoubleClick(View v);
}

