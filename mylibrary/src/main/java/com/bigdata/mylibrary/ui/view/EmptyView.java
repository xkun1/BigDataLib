package com.bigdata.mylibrary.ui.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigdata.mylibrary.R;


/**
 * user:kun
 * Date:19/06/2017 or 5:58 PM
 * email:hekun@gamil.com
 * Desc: 该布局实现当数据为空显示
 *  相应的布局
 */

public class EmptyView extends FrameLayout {

    private View mLodingView;  //加载的布局
    private TextView mEmptyView;
    private TextView mErrorView;

    //加载状态
    public final static int TYPE_LOADING = 1;
    //为空状态
    public final static int TYPE_EMPTY = 2;
    //错误状态
    public final static int TYPE_ERROR = 3;
    //正常状态
    public final static int TYPE_COMPLETE = 4;

    //默认
    private int mEmptyType = TYPE_LOADING;

    private String mErrorMessage;
    private String mEmptyMessage;
    private String mLoadingMessage;


    public EmptyView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) return;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_layout, null);

        mLodingView = findViewById(R.id.viewLoading);
        mEmptyView = (TextView) findViewById(R.id.viewEmpty);
        mErrorView = (TextView) findViewById(R.id.viewError);
    }

    /**
     * 空状态
     */
    public void showEmpty() {
        this.mEmptyType = TYPE_EMPTY;
        changeViewState();
    }

    /**
     * loding
     */
    public void showLoading() {
        this.mEmptyType = TYPE_LOADING;
        changeViewState();
    }

    /**
     * error
     */
    public void showError() {
        this.mEmptyType = TYPE_ERROR;
        changeViewState();
    }

    /**
     * content
     */
    public void showContent() {
        this.mEmptyType = TYPE_COMPLETE;
        changeViewState();
    }

    public void setEmptyType(int emptyType) {
        this.mEmptyType = emptyType;
        changeViewState();
    }

    //错误消息
    public void setErrorMessage(String errorMessage) {
        this.mErrorMessage = errorMessage;
    }

    //空的时候显示的消息
    public void setEmptyMessage(String emptyMessage) {
        this.mEmptyMessage = emptyMessage;
    }

    //加载显示的消息
    public void setLoadingMessage(String loadingMessage) {
        this.mLoadingMessage = loadingMessage;
    }

    //empty Click
    public void setOnEmptyClickListener(OnClickListener emptyClickListener) {
        mEmptyView.setOnClickListener(emptyClickListener);
    }

    //error click
    public void setOnErrorClickListener(OnClickListener errorClickListener) {
        mErrorView.setOnClickListener(errorClickListener);
    }

    //改变状态
    private void changeViewState() {
        //刷新
        refreshMessages();
        this.setVisibility(View.VISIBLE);
        switch (mEmptyType) {
            case TYPE_EMPTY:
                mErrorView.setVisibility(GONE);
                mLodingView.setVisibility(GONE);
                mEmptyView.setVisibility(VISIBLE);
                break;
            case TYPE_LOADING:
                mErrorView.setVisibility(GONE);
                mLodingView.setVisibility(VISIBLE);
                mEmptyView.setVisibility(GONE);
                break;
            case TYPE_ERROR:
                mErrorView.setVisibility(VISIBLE);
                mLodingView.setVisibility(GONE);
                mEmptyView.setVisibility(GONE);
                break;
            case TYPE_COMPLETE:
                mErrorView.setVisibility(GONE);
                mLodingView.setVisibility(GONE);
                mEmptyView.setVisibility(GONE);
                this.setVisibility(GONE);
                break;
        }

    }

    private void refreshMessages() {
        if (mEmptyMessage != null) mEmptyView.setText(mEmptyMessage);
        if (mErrorMessage != null) mErrorView.setText(mErrorMessage);
        if (mLoadingMessage != null)
            ((TextView) mLodingView.findViewById(R.id.textViewMessage)).setText(mLoadingMessage);
    }
}
