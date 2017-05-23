package com.bigdata.mylibrary.util;

import android.support.v4.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * user:kun
 * Date:2017/5/19 or 下午8:45
 * email:hekun@gamil.com
 * Desc: 权限处理
 */

public class RxPermissionsUtils {

    public static boolean setRequest(FragmentActivity activity, final String... permissions) {
        final boolean[] isflag = {false};
        new RxPermissions(activity)
                .request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        isflag[0] = aBoolean;
                    }
                });
        return isflag[0];
    }
}
