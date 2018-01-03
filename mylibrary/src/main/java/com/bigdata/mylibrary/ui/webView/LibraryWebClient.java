package com.bigdata.mylibrary.ui.webView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bigdata.mylibrary.util.LogUtils;

/**
 * user:kun
 * Date:2017/5/22 or 上午9:45
 * email:hekun@gamil.com
 * Desc:
 */

public class LibraryWebClient extends WebViewClient {

    private Context mContext;
    private ProgressDialog mProgressDialog;

    private MaterialDialog materialDialog;


    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    public ProgressDialog getmProgressDialog() {
        return mProgressDialog;
    }

    public void setmProgressDialog(ProgressDialog mProgressDialog) {
        this.mProgressDialog = mProgressDialog;
    }

    public LibraryWebClient(Context mContext) {
        this.mContext = mContext;
        mProgressDialog = new ProgressDialog(mContext);
        materialDialog = new MaterialDialog.Builder(mContext)
                .progress(true,0)
                .progressIndeterminateStyle(true)
                .build();
    }


    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        super.onReceivedClientCertRequest(view, request);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();//接受证书
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (!view.getSettings().getLoadsImagesAutomatically()) {
            view.getSettings().setLoadsImagesAutomatically(true);
        }
        LogUtils.d(url);
        materialDialog.dismiss();
//        mProgressDialog.dismiss();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
//        mProgressDialog.show();
        materialDialog.show();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(view.getUrl());
        return true;
    }
}
