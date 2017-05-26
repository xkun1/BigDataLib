package com.bigdata.mylibrary.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bigdata.mylibrary.R;
import com.bigdata.mylibrary.util.LogUtils;

/**
 * user:kun
 * Date:2017/5/26 or 下午2:33
 * email:hekun@gamil.com
 * Desc: 实现上啦加载更多
 */

public class BaseLibSwipeRefreshLayout extends SwipeRefreshLayout {

    //记录滑动最下面
    private float mToupSlop;

    private static int TYPE = 0; //0：listView 1:RecyclerView

    private float mDownY, mUpY;

    private ListView mListView;
    private RecyclerView mRecyclerView;
    private OnLoadListener mOnLoadListener;

    public BaseLibSwipeRefreshLayout(Context context) {
        super(context, null);
    }

    public BaseLibSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 控件移动的最小距离，手移动的距离大于此才能移动控件
        mToupSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseLibSwipeRefreshLayout);
        TYPE = a.getInteger(R.styleable.BaseLibSwipeRefreshLayout_refreshLayout_type, 0);
        a.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mRecyclerView == null && mListView == null) {
            if (getChildCount() > 0) {
                if (TYPE == 0) {
                    View childAt = getChildAt(1);
                    if (childAt instanceof ListView) {
                        LogUtils.d("is ListView");
                        mListView = (ListView) childAt;
                        setChildViewOnScroll();
                    }
                    TYPE = 0;
                } else {
                    View childAt = getChildAt(1);
                    if (childAt instanceof RecyclerView) {
                        LogUtils.d("is RecyclerView");
                        mRecyclerView = (RecyclerView) childAt;
                        setChildViewOnScroll();
                    }
                    TYPE = 1;
                }


            }
        }
    }

    private void setChildViewOnScroll() {
        if (TYPE == 0) {
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (canLoadMore(mDownY - mUpY)) {
                        loadMoreData();
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        } else {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (canLoadMore(mDownY - mUpY)) {
                        loadMoreData();
                    }
                }
            });
        }
    }

    public void setmOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (canLoadMore(mDownY - ev.getY())) {
                    loadMoreData();
                } else {
                    mDownY = 0;
                    mUpY = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                mUpY = ev.getY();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    private void loadMoreData() {
        System.out.println("loading data...");
        if (mOnLoadListener != null) {
            mOnLoadListener.onLoad();
        }
    }

    private boolean canLoadMore(float mSlop) {
        boolean b = mSlop >= mToupSlop;
        if (b) {
            LogUtils.d("move....up");
        } else {
            LogUtils.d("move....down");
        }
        boolean islast = false;
        if (TYPE == 0) {
            if (mListView != null && mListView.getAdapter() != null) {
                islast = mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
            }
        } else if (TYPE == 1) {
            if (mRecyclerView != null && mRecyclerView.getAdapter() != null) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if (layoutManager != null) {
                    islast = layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1;
                }
            }
        }
        if (islast) {
            LogUtils.d("last.....");
        }


        return islast && !isRefreshing();
    }

    public interface OnLoadListener {
        void onLoad();
    }
}
