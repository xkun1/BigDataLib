package com.bigdata.mylibrary.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bigdata.mylibrary.net.ssl.PersistentCookieStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

import static android.content.ContentValues.TAG;

/**
 * user:kun
 * Date:18/12/2017 or 10:05 AM
 * email:hekun@gamil.com
 * Desc: 网络请求
 */

public class HttpUtils {
    OkHttpClient okHttpClient;
    static Context mContext;

    Handler mHandler = new Handler(Looper.getMainLooper());

    private HttpUtils() {
    }

    private static class HttpUtil {
        HttpUtils httpUtil = new HttpUtils();
    }

    public static HttpUtils getNewHttp(Context context) {
        mContext = context;
        return new HttpUtil().httpUtil;
    }

    private class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore =
                new PersistentCookieStore(mContext);

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies.size() > 0) {
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

    private OkHttpClient okHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("okHttp:------->", message);
                }
            });
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookiesManager())
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .addNetworkInterceptor(logInterceptor)
                    .build();
        }
        return okHttpClient;
    }

    public void sendHttpUrl(final String url, final HttpCallbackListener listener) {

        Log.d(TAG, "sendHttpUrl: url=="+url);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Call call = okHttpClient().newCall(request);
                try {
                    final ResponseBody body = call.execute().body();
                    if (body != null) {
                        if (listener != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        listener.onFinish(body.string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        listener.onError(e);
                                    }
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void downloadFile(final String url, final String fileName, final OnDownloadListener downloadListener){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Call call = okHttpClient().newCall(request);
                InputStream is=null;
                byte[] buf=new byte[2048];
                int len=0;
                FileOutputStream fos=null;
                try {
                    ResponseBody body = call.execute().body();
                    //储存下载文件的目录
                    String savePath=isExistDir(Confing.BASE_FILE);
                    assert body != null;
                    is=body.byteStream();
                    long contentLength = body.contentLength();
                    final File file=new File(savePath,fileName);
                    fos=new FileOutputStream(file);
                    long sum=0;
                    while((len = is.read(buf))!=-1){
                        fos.write(buf,0,len);
                        sum+=len;
                        final int progress= Math.abs((int)(sum*1.0f/contentLength*100));
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                downloadListener.onDownloading(progress);
                            }
                        });
                    }
                    fos.flush();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            downloadListener.onDownloadSuccess(file.getAbsolutePath());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    downloadListener.onDownloadFailed();
                }finally {
                        try {
                            if (is!=null)
                            is.close();
                            if (fos!=null)
                                fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                }
            }
        }).start();

    }

    private String isExistDir(String saveDir) throws IOException {
        File downloadFile=new File(saveDir);
        if(!downloadFile.mkdirs()){
            downloadFile.createNewFile();
        }
        String savePath=downloadFile.getAbsolutePath();
        return savePath;
    }
    public interface OnDownloadListener{
        /**
         * 下载成功
         */
        void onDownloadSuccess(String path);
        /**
         * 下载进度
         * @param progress
         */
        void onDownloading(int progress);
        /**
         * 下载失败
         */
        void onDownloadFailed();
    }




}
