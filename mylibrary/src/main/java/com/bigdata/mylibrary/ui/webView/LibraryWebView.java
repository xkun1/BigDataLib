package com.bigdata.mylibrary.ui.webView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.bigdata.mylibrary.ui.view.ProgressWheel;

/**
 * user:kun
 * Date:2017/5/22 or 上午9:33
 * email:hekun@gamil.com
 * Desc: 封装WebView
 */

public class LibraryWebView extends WebView {


    private Context mContext;

    private String webTitle;

    private ProgressWheel progressWheel;

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

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
        progressWheel=new ProgressWheel(mContext);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LibraryWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initWebView();
        progressWheel=new ProgressWheel(mContext);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LibraryWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initWebView();
        progressWheel=new ProgressWheel(mContext);
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
        this.setWebViewClient(new LibraryWebClient());
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
        this.clearCache(true);
        this.clearHistory();
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    private  class LibraryWebChrome extends WebChromeClient{
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setWebTitle(title);
        }

        /**
         * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
         * window.prompt('请输入您的域名地址', '618119.com');
         */
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setMessage(message)
                    .setPositiveButton("确定", null);

            // 不需要绑定按键事件
            // 屏蔽keycode等于84之类的按键
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Log.v("onJsAlert", "keyCode==" + keyCode + "event=" + event);
                    return true;
                }
            });
            // 禁止响应按back键的事件
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
            return true;
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url,
                                        String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }


        /**
         * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    result.cancel();
                }
            });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
                    return true;
                }
            });
            // 禁止响应按back键的事件
            // builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        /**
         * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
         * window.prompt('请输入您的域名地址', '618119.com');
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setMessage(message);

            final EditText et = new EditText(view.getContext());
            et.setSingleLine();
            et.setText(defaultValue);
            builder.setView(et)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm(et.getText().toString());
                        }

                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Log.v("onJsPrompt", "keyCode==" + keyCode + "event=" + event);
                    return true;
                }
            });

            // 禁止响应按back键的事件
            // builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

    }
    private class LibraryWebClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressWheel.resetCount();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressWheel.stopSpinning();
        }

    }

}
