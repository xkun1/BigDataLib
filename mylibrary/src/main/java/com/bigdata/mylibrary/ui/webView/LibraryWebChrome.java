package com.bigdata.mylibrary.ui.webView;

import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * user:kun
 * Date:2017/5/22 or 上午9:59
 * email:hekun@gamil.com
 * Desc:
 */

public class LibraryWebChrome extends WebChromeClient {

    private String webTitle;
    private Context mContext;

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        setWebTitle(title);
    }

}
