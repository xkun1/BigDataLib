package com.bigdata.mylibrary.net;


/**
 * user:kun
 * Date:2017/1/23 or 上午10:31
 * email:hekun@gamil.com
 * Desc: 这个接口只处理onNext里面的方法
 */

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onError();
}
