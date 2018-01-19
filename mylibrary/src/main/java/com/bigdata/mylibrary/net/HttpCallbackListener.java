package com.bigdata.mylibrary.net;


public interface HttpCallbackListener {
    // 网络请求成功
    void onFinish(String response);


    // 网络请求失败
    void onError(Exception e);
}
