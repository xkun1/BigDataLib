package com.bigdata.mylibrary.net;

import android.annotation.SuppressLint;
import android.content.Context;

import com.bigdata.mylibrary.Library;
import com.bigdata.mylibrary.util.LogUtils;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * user:kun
 * Date:2017/5/19 or 下午1:52
 * email:hekun@gamil.com
 * Desc:对Retrofit2的基本设置
 */

public class RetroFactory {

    private String baseUrl;
    private Context appContext;
    private Retrofit mRetrofit;
    private OkHttpClient okHttpClient;


    public static RetroFactory getInstance() {
        return SingtonInstances.instance;
    }

    private static class SingtonInstances {
        @SuppressLint("StaticFieldLeak")
        private static RetroFactory instance = new RetroFactory();
    }

    private RetroFactory() {
        appContext = Library.getmContext();
        baseUrl = Library.getBaseurl();
        if (appContext != null && baseUrl != null) {
            createNetworkConfig();
        } else {
            LogUtils.d("上下文环境为空,BaseUrl为空");
        }

    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    private void createNetworkConfig() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.d(message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BASIC));
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        okHttpClient = builder.build();
        mRetrofit = buildRetrofit();
    }

    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build();
    }

}
