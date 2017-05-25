package com.bigdata.bigdatalib;

import com.bigdata.bigdatalib.kotlin.SvcClEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * user:kun
 * Date:2017/5/19 or 上午11:26
 * email:hekun@gamil.com
 * Desc:
 */

public interface ApiServer {

    //获取服务条款内容信息
    @GET("userCenter/getSvcCl")
    Observable<SvcClEntity> getSvcCl(@Query("v") String v);
}
