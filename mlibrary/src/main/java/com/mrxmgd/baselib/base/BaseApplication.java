/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.base;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mrxmgd.baselib.gson.FooAnnotationExclusionStrategy;

/*
 *
 *   BaseApplication
 *   @author MRXMGD <mrxmgd@gmail.com>
 *
 */
public class BaseApplication extends Application {
    public Gson mGson;


    @Override
    public void onCreate() {
        super.onCreate();
        Resources res = getApplicationContext().getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //android 7.0系统解决拍照的问题
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    builder.detectFileUriExposure();
                }
                mGson = new GsonBuilder().setExclusionStrategies(new FooAnnotationExclusionStrategy())
                        .create();
            }
        }, 1000);
    }


}
