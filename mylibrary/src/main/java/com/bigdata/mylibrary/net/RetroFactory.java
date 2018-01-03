package com.bigdata.mylibrary.net;

import android.annotation.SuppressLint;
import android.content.Context;

import com.bigdata.mylibrary.Library;
import com.bigdata.mylibrary.net.ssl.PersistentCookieStore;
import com.bigdata.mylibrary.util.LogUtils;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
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
        appContext = Library.INSTANCE.getmContext();
        baseUrl = Library.INSTANCE.getBaseurl();
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
        builder.cookieJar(new CookiesManager());
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

    /**
     * Cookie持久化与自动化管理
     */
    private class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore =
                new PersistentCookieStore(appContext);

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }

}
