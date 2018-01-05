package com.bigdata.mylibrary.chartUtils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.bigdata.mylibrary.chart.charts.LineChart;
import com.bigdata.mylibrary.chart.components.Description;
import com.bigdata.mylibrary.chart.components.IMarker;
import com.bigdata.mylibrary.chart.data.LineData;
import com.bigdata.mylibrary.chart.listener.OnChartGestureListener;
import com.bigdata.mylibrary.chart.listener.OnChartValueSelectedListener;

/**
 * user:kun
 * Date:05/01/2018 or 4:04 PM
 * email:hekun@gamil.com
 * Desc: 封装LineChart的使用
 */

public class LineChartUtils {
    //图表
    private LineChart mLineChart;
    //空数据显示
    private String noDataText;
    //是否可以拖动
    private boolean mDragEnabled = true;
    //描述设置
    private Description mDescription;
    //是否画网格背景
    private boolean mDrawGridBackground = false;
    //是否画边框线
    private boolean mDrawBorders = false;
    //边框线宽度
    private float mBorderWidth = 1f;
    //边框线颜色
    private int mBorderColor = Color.BLACK;
    //是否开启触摸
    private boolean mTouchEnabled = true;
    //是否双击缩放
    private boolean mDoubleTapToZoomEnabled = true;
    //是否开启Y轴缩放
    private boolean mScaleYEnabled = true;
    //是否开启X轴缩放
    private boolean mScaleXEnabled = true;
    //LineData设置
    private LineData mLineData;
    //是否画marketView
    private boolean mDrawMarkers=false;
    //图表背景颜色
    private int mBackGroudColour;
    private Drawable mBackGroudDrawable;
    //设置marker
    private IMarker iMarker;
    //设置手势监听
    private OnChartGestureListener mChartGestureListener;
    //设置选中事件
    private OnChartValueSelectedListener mChartValueSelectedListener;

    private LineChartUtils(LineChartBuider buider) {
        this.mBorderColor = buider.mBorderColor;
        this.mBorderWidth = buider.mBorderWidth;
        this.mDescription = buider.mDescription;
        this.mDoubleTapToZoomEnabled = buider.mDoubleTapToZoomEnabled;
        this.mDragEnabled = buider.mDragEnabled;
        this.mDrawBorders = buider.mDrawBorders;
        this.mDrawGridBackground = buider.mDrawGridBackground;
        this.mLineChart = buider.mLineChart;
        this.mLineData = buider.mLineData;
        this.mScaleXEnabled = buider.mScaleXEnabled;
        this.mScaleYEnabled = buider.mScaleYEnabled;
        this.noDataText = buider.noDataText;
        this.mTouchEnabled = buider.mTouchEnabled;
        this.mDrawMarkers=buider.mDrawMarkers;
        this.mBackGroudColour=buider.mBackGroudColor;
        this.mBackGroudDrawable=buider.mBackGroudDrawable;
        this.iMarker=buider.iMarker;
        this.mChartGestureListener=buider.mChartGestureListener;
        this.mChartValueSelectedListener=buider.mChartValueSelectedListener;
    }


    public LineChart getmLineChart() {
        return mLineChart;
    }

    public String getNoDataText() {
        return noDataText;
    }

    public boolean ismDragEnabled() {
        return mDragEnabled;
    }

    public Description getmDescription() {
        return mDescription;
    }

    public boolean ismDrawGridBackground() {
        return mDrawGridBackground;
    }

    public boolean ismDrawBorders() {
        return mDrawBorders;
    }

    public float getmBorderWidth() {
        return mBorderWidth;
    }

    public int getmBorderColor() {
        return mBorderColor;
    }

    public boolean ismTouchEnabled() {
        return mTouchEnabled;
    }

    public boolean ismDoubleTapToZoomEnabled() {
        return mDoubleTapToZoomEnabled;
    }

    public boolean ismScaleYEnabled() {
        return mScaleYEnabled;
    }

    public boolean ismScaleXEnabled() {
        return mScaleXEnabled;
    }
    public LineData getmLineData() {
        return mLineData;
    }

    public boolean ismDrawMarkers() {
        return mDrawMarkers;
    }

    public int getmBackGroudColour() {
        return mBackGroudColour;
    }

    public Drawable getmBackGroudDrawable() {
        return mBackGroudDrawable;
    }

    public IMarker getiMarker() {
        return iMarker;
    }

    public OnChartGestureListener getmChartGestureListener() {
        return mChartGestureListener;
    }

    public OnChartValueSelectedListener getmChartValueSelectedListener() {
        return mChartValueSelectedListener;
    }

    public static class LineChartBuider {
        //图表
        private LineChart mLineChart;
        //空数据显示
        private String noDataText;
        //是否可以拖动
        private boolean mDragEnabled = true;
        //描述设置
        private Description mDescription;
        //是否画网格背景
        private boolean mDrawGridBackground = false;
        //是否画边框线
        private boolean mDrawBorders = false;
        //边框线宽度
        private float mBorderWidth = 1f;
        //边框线颜色
        private int mBorderColor = Color.BLACK;
        //是否开启触摸
        private boolean mTouchEnabled = true;
        //是否双击缩放
        private boolean mDoubleTapToZoomEnabled = true;
        //是否开启Y轴缩放
        private boolean mScaleYEnabled = true;
        //是否开启X轴缩放
        private boolean mScaleXEnabled = true;
        //LineData设置
        private LineData mLineData;
        //是否画marketView
        private boolean mDrawMarkers=false;
        //图表背景颜色
        private int mBackGroudColor;
        private Drawable mBackGroudDrawable;
        //设置marker
        private IMarker iMarker;
        //设置手势监听
        private OnChartGestureListener mChartGestureListener;
        //设置选中事件
        private OnChartValueSelectedListener mChartValueSelectedListener;

        public LineChartBuider(LineChart mLineChart) {
            this.mLineChart = mLineChart;
        }

        public LineChartBuider noDataText(String noDataText) {
            this.noDataText = noDataText;
            return this;
        }

        public LineChartBuider mDragEnabled(boolean mDragEnabled) {
            this.mDragEnabled = mDragEnabled;
            return this;
        }

        public LineChartBuider mDescription(Description mDescription) {
            this.mDescription = mDescription;
            return this;
        }

        public LineChartBuider mDrawGridBackground(boolean mDrawGridBackground) {
            this.mDrawGridBackground = mDrawGridBackground;
            return this;
        }

        public LineChartBuider mDrawBorders(boolean mDrawBorders) {
            this.mDrawBorders = mDrawBorders;
            return this;
        }

        public LineChartBuider mBorderWidth(float mBorderWidth) {
            this.mBorderWidth = mBorderWidth;
            return this;
        }

        public LineChartBuider mBorderColor(int mBorderColor) {
            this.mBorderColor = mBorderColor;
            return this;
        }

        public LineChartBuider mTouchEnabled(boolean mTouchEnabled) {
            this.mTouchEnabled = mTouchEnabled;
            return this;
        }

        public LineChartBuider mDoubleTapToZoomEnabled(boolean mDoubleTapToZoomEnabled) {
            this.mDoubleTapToZoomEnabled = mDoubleTapToZoomEnabled;
            return this;
        }

        public LineChartBuider mScaleYEnabled(boolean mScaleYEnabled) {
            this.mScaleYEnabled = mScaleYEnabled;
            return this;
        }

        public LineChartBuider mScaleXEnabled(boolean mScaleXEnabled) {
            this.mScaleXEnabled = mScaleXEnabled;
            return this;
        }
        public LineChartBuider mLineData(LineData mLineData) {
            this.mLineData = mLineData;
            return this;
        }
        public LineChartBuider mDrawMarkers(boolean mDrawMarkers) {
            this.mDrawMarkers = mDrawMarkers;
            return this;
        }
        public LineChartBuider mBackGroudColor(int mBackGroudColor) {
            this.mBackGroudColor = mBackGroudColor;
            return this;
        }
        public LineChartBuider mBackGroudDrawable(Drawable mBackGroudDrawable) {
            this.mBackGroudDrawable = mBackGroudDrawable;
            return this;
        }
        public LineChartBuider iMarker(IMarker iMarker) {
            this.iMarker = iMarker;
            return this;
        }
        public LineChartBuider mChartGestureListener(OnChartGestureListener mChartGestureListener) {
            this.mChartGestureListener = mChartGestureListener;
            return this;
        }
        public LineChartBuider mChartValueSelectedListener(OnChartValueSelectedListener mChartValueSelectedListener) {
            this.mChartValueSelectedListener = mChartValueSelectedListener;
            return this;
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void init() {
            if (mLineChart != null) {
                mLineChart.setNoDataText(noDataText);
                mLineChart.setDescription(mDescription);
                if (mBorderColor==0){
                    mLineChart.setBackground(mBackGroudDrawable);
                }else {
                    mLineChart.setBorderColor(mBackGroudColor);
                }
                mLineChart.setDrawGridBackground(mDrawGridBackground);
                mLineChart.setDrawMarkers(mDrawMarkers);
                mLineChart.setDrawBorders(mDrawBorders);
                mLineChart.setDragEnabled(mDragEnabled);
                mLineChart.setBorderColor(mBorderColor);
                mLineChart.setBorderWidth(mBorderWidth);
                mLineChart.setTouchEnabled(mTouchEnabled);
                mLineChart.setDoubleTapToZoomEnabled(mDoubleTapToZoomEnabled);
                mLineChart.setScaleXEnabled(mScaleXEnabled);
                mLineChart.setScaleYEnabled(mScaleYEnabled);
                mLineChart.setMarker(iMarker);
                mLineChart.setOnChartValueSelectedListener(mChartValueSelectedListener);
                mLineChart.setOnChartGestureListener(mChartGestureListener);
                mLineChart.setData(mLineData);
                mLineChart.invalidate();
            }
        }

    }


}
