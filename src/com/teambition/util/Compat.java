package com.teambition.util;

import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-22
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public class Compat {
    private static final int SIXTY_FPS_INTERVAL = 1000 / 60;

    public static void postOnAnimation(View view, Runnable runnable) {
//        if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
//            SDK16.postOnAnimation(view, runnable);
//        } else {
            view.postDelayed(runnable, SIXTY_FPS_INTERVAL);
//        }
    }
}
