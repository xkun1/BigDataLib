package com.bigdata.bigdatalib;

import android.app.Application;

import com.bigdata.mylibrary.Library;
import com.facebook.stetho.Stetho;

/**
 * user:kun
 * Date:2017/5/19 or 上午11:25
 * email:hekun@gamil.com
 * Desc:
 */

public class app extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Library.INSTANCE.init(this);
        Library.INSTANCE.initBaseUrl("https://bbcj.chinabigdata.com/app/");


        /**
         * 对chrome调试
         */
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
