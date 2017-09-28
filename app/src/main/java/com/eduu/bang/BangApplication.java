package com.eduu.bang;

import android.app.Application;

/**
 * Created by wikipeng on 2017/9/28.
 */
public class BangApplication extends Application {
    private static BangApplication sBangApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sBangApplication = this;
    }

    public static BangApplication getInstance() {
        return sBangApplication;
    }
}
