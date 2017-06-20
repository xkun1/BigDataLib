package com.bigdata.mylibrary.ui.webView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * user:kun
 * Date:2017/5/22 or 上午9:33
 * email:hekun@gamil.com
 * Desc: 封装WebView
 */

public class LibraryWebView extends WebView {


    private Context mContext;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LibraryWebView(Context context) {
        super(context);
        this.mContext = context;
        initWebView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LibraryWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initWebView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LibraryWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initWebView();
    }


    /**
     * 对webView一些初始化
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    public void initWebView() {


        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.getSettings().setSupportZoom(true);

        this.getSettings().setTextZoom(100);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        this.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        /*
        支持缩放
         */
        this.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放

        this.setWebChromeClient(new LibraryWebChrome());
        this.setWebViewClient(new LibraryWebClient(mContext));
        /*
        webView自适应屏幕
         */
        this.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.getSettings().setLoadWithOverviewMode(true);

        this.requestFocus();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getSettings().setLoadsImagesAutomatically(true);
        } else {
            getSettings().setLoadsImagesAutomatically(false);
        }
        this.getSettings().setDatabaseEnabled(true);
        this.getSettings().setDomStorageEnabled(true);
        this.setWebViewClient(new LibraryWebClient(mContext));
        this.clearCache(true);
        this.clearHistory();
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

    }
}
