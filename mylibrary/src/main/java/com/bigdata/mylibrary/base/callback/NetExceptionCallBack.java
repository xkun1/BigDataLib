package com.bigdata.mylibrary.base.callback;

import android.util.Log;

import com.bigdata.mylibrary.base.exception.RequsetException;
import com.bigdata.mylibrary.util.NetworkUtils;
import com.google.gson.stream.MalformedJsonException;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public class NetExceptionCallBack implements Consumer<Throwable> {

    private PresenterCallBack presenterCallBack;

    private ViewCallBack viewCallBack;

    //使用mvp模式回调
    public NetExceptionCallBack(PresenterCallBack callBack) {
        Reference<PresenterCallBack> reference = new WeakReference<>(callBack);
        this.presenterCallBack = reference.get();
    }

    //使用simple模式回调
    public NetExceptionCallBack(ViewCallBack callBack) {
        Reference<ViewCallBack> reference = new WeakReference<>(callBack);
        this.viewCallBack = reference.get();
    }

    /**
     * Consume the given value.
     *
     * @param e the value
     * @throws Exception on error
     */
    @Override public void accept(Throwable e) throws Exception {
        //请求失败，提示原因
        if (!(e instanceof RequsetException)) {
            int code;
            String msg;
            if (NetworkUtils.isConnected()) {
                if (e instanceof ConnectException || e instanceof UnknownHostException) {
                    code = 10001;
                    msg = "无法连接服务器";
                } else if (e instanceof SocketTimeoutException) {
                    code = 10002;
                    msg = "服务器连接超时";
                } else if (e instanceof HttpException) {
                    code = 10003;
                    msg = "服务器异常错误";
                } else if (e instanceof MalformedJsonException) {
                    code = 10004;
                    msg = "数据异常";
                } else {
                    code = 10005;
                    msg = "未知错误";
                }
            } else {
                code = 10000;
                msg = "请打开网络";
            }

            if (presenterCallBack != null) {
                presenterCallBack.onFailure(code, msg);
            }

            if (viewCallBack != null) {
                viewCallBack.onFailure(code, msg);
            }
            Log.e("NetExceptionCallBack", Log.getStackTraceString(e));
        }
    }
}