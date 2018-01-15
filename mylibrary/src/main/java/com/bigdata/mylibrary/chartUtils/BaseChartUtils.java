package com.bigdata.mylibrary.chartUtils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.bigdata.mylibrary.chart.charts.BarChart;
import com.bigdata.mylibrary.chart.charts.BarLineChartBase;
import com.bigdata.mylibrary.chart.charts.CombinedChart;
import com.bigdata.mylibrary.chart.charts.LineChart;
import com.bigdata.mylibrary.chart.components.Description;
import com.bigdata.mylibrary.chart.components.IMarker;
import com.bigdata.mylibrary.chart.components.Legend;
import com.bigdata.mylibrary.chart.components.LimitLine;
import com.bigdata.mylibrary.chart.components.XAxis;
import com.bigdata.mylibrary.chart.components.YAxis;
import com.bigdata.mylibrary.chart.data.BarData;
import com.bigdata.mylibrary.chart.data.CombinedData;
import com.bigdata.mylibrary.chart.data.LineData;
import com.bigdata.mylibrary.chart.listener.OnChartGestureListener;
import com.bigdata.mylibrary.chart.listener.OnChartValueSelectedListener;

/**
 * user:kun
 * Date:05/01/2018 or 4:04 PM
 * email:hekun@gamil.com
 * Desc: 封装LineChart,BarChart,
 * ScatterChart,CandleStickChart and CombinedChart
 */

public class BaseChartUtils {
    //图表
    private BarLineChartBase mBaseChart;
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
    //Data设置
    private BarData mBarData;
    private LineData mLineData;
    private CombinedData mComBinedData;
    //是否画marketView
    private boolean mDrawMarkers = false;
    //图表背景颜色
    private int mBackGroudColour;
    private Drawable mBackGroudDrawable;
    //图表X轴
    private boolean mDrawXAxisLine = true;
    private boolean mDrawXAxisLables = true;
    private boolean mDrawXAxisGridLines = true;
    private XAxis.XAxisPosition mXAxisPosition;
    private boolean mDrawXAxis = true;

    //图表leftY轴
    private boolean mDrawLeftYLine = true;
    private boolean mDrawLeftYLables = true;
    private boolean mDrawLeftYGridLines = true;
    private YAxis.YAxisLabelPosition mLeftYPosition;
    private boolean mDrawZeroLine = false;
    private boolean mDrawLeftAxis = true;
    //图表RightY轴
    private boolean mDrawRightYLine = true;
    private boolean mDrawRightYLables = true;
    private boolean mDrawRightYGridLines = true;
    private YAxis.YAxisLabelPosition mRightYPosition;
    private boolean mDrawRightAxis = true;
    //图列Legend
    private boolean mChartLegndEnabled = false;

    //Y轴加基准线
    private LimitLine mYLinitLine;
    //x轴加基准线
    private LimitLine mXlinitLine;

    //设置marker
    private IMarker iMarker;
    //设置手势监听
    private OnChartGestureListener mChartGestureListener;
    //设置选中事件
    private OnChartValueSelectedListener mChartValueSelectedListener;

    private BaseChartUtils(BaseChartBuider buider) {
        this.mBorderColor = buider.mBorderColor;
        this.mBorderWidth = buider.mBorderWidth;
        this.mDescription = buider.mDescription;
        this.mDoubleTapToZoomEnabled = buider.mDoubleTapToZoomEnabled;
        this.mDragEnabled = buider.mDragEnabled;
        this.mDrawBorders = buider.mDrawBorders;
        this.mDrawGridBackground = buider.mDrawGridBackground;
        this.mBaseChart = buider.mBaseChart;
        this.mLineData = buider.mLineData;
        this.mComBinedData = buider.mCombinedData;
        this.mBarData = buider.mBarData;
        this.mScaleXEnabled = buider.mScaleXEnabled;
        this.mScaleYEnabled = buider.mScaleYEnabled;
        this.noDataText = buider.noDataText;
        this.mTouchEnabled = buider.mTouchEnabled;
        this.mDrawMarkers = buider.mDrawMarkers;
        this.mBackGroudColour = buider.mBackGroudColor;
        this.mBackGroudDrawable = buider.mBackGroudDrawable;
        this.mDrawXAxisLine = buider.mDrawXAxisLine;
        this.mDrawXAxisGridLines = buider.mDrawXAxisGridLines;
        this.mDrawXAxisLables = buider.mDrawXAxisLables;
        this.mDrawXAxis = buider.mDrawXAxis;
        this.mXAxisPosition = buider.mXAxisPosition;
        this.mLeftYPosition = buider.mLeftYPosition;
        this.mDrawLeftAxis = buider.mDrawLeftYAxis;
        this.mDrawLeftYGridLines = buider.mDrawLeftYGridLines;
        this.mDrawLeftYLables = buider.mDrawLeftYLables;
        this.mDrawLeftYLine = buider.mDrawLeftYLine;
        this.mDrawRightYLine = buider.mDrawRightYLine;
        this.mRightYPosition = buider.mRightYPosition;
        this.mDrawRightAxis = buider.mDrawRightYAxis;
        this.mDrawRightYGridLines = buider.mDrawRightYGridLines;
        this.mDrawRightYLables = buider.mDrawRightYLables;
        this.mDrawZeroLine = buider.mDrawZeroLine;
        this.mChartLegndEnabled = buider.mChartLegndEnabled;
        this.mYLinitLine = buider.mYLinitLine;
        this.mXlinitLine = buider.mXlinitLine;
        this.iMarker = buider.iMarker;
        this.mChartGestureListener = buider.mChartGestureListener;
        this.mChartValueSelectedListener = buider.mChartValueSelectedListener;

    }

    public boolean ismDrawXAxis() {
        return mDrawXAxis;
    }

    public boolean ismDrawLeftAxis() {
        return mDrawLeftAxis;
    }

    public boolean ismDrawRightAxis() {
        return mDrawRightAxis;
    }

    public LineData getmLineData() {
        return mLineData;
    }

    public CombinedData getmComBinedData() {
        return mComBinedData;
    }

    public boolean ismDrawXAxisLine() {
        return mDrawXAxisLine;
    }

    public boolean ismDrawXAxisLables() {
        return mDrawXAxisLables;
    }

    public boolean ismDrawXAxisGridLines() {
        return mDrawXAxisGridLines;
    }

    public XAxis.XAxisPosition getmXAxisPosition() {
        return mXAxisPosition;
    }

    public boolean ismDrawLeftYLine() {
        return mDrawLeftYLine;
    }

    public boolean ismDrawLeftYLables() {
        return mDrawLeftYLables;
    }

    public boolean ismDrawLeftYGridLines() {
        return mDrawLeftYGridLines;
    }

    public YAxis.YAxisLabelPosition getmLeftYPosition() {
        return mLeftYPosition;
    }

    public boolean ismDrawZeroLine() {
        return mDrawZeroLine;
    }

    public boolean ismDrawRightYLine() {
        return mDrawRightYLine;
    }

    public boolean ismDrawRightYLables() {
        return mDrawRightYLables;
    }

    public boolean ismDrawRightYGridLines() {
        return mDrawRightYGridLines;
    }

    public YAxis.YAxisLabelPosition getmRightYPosition() {
        return mRightYPosition;
    }

    public boolean ismChartLegndEnabled() {
        return mChartLegndEnabled;
    }

    public LimitLine getmYLinitLine() {
        return mYLinitLine;
    }

    public LimitLine getmXlinitLine() {
        return mXlinitLine;
    }

    public BarLineChartBase getmBarChart() {
        return mBaseChart;
    }

    public BarData getmBarData() {
        return mBarData;
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

    public static class BaseChartBuider {
        //图表
        private BarLineChartBase mBaseChart;
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
        private BarData mBarData;
        private LineData mLineData;
        private CombinedData mCombinedData;
        //是否画marketView
        private boolean mDrawMarkers = false;
        //图表背景颜色
        private int mBackGroudColor;
        private Drawable mBackGroudDrawable;

        //图表X轴
        private boolean mDrawXAxisLine = true;
        private boolean mDrawXAxisLables = true;
        private boolean mDrawXAxisGridLines = true;
        private XAxis.XAxisPosition mXAxisPosition;
        private boolean mDrawXAxis = true;
        //图表leftY轴
        private boolean mDrawLeftYLine = true;
        private boolean mDrawLeftYLables = true;
        private boolean mDrawLeftYGridLines = true;
        private YAxis.YAxisLabelPosition mLeftYPosition;
        private boolean mDrawZeroLine = false;
        private boolean mDrawLeftYAxis = true;
        //图表RightY轴
        private boolean mDrawRightYLine = true;
        private boolean mDrawRightYLables = true;
        private boolean mDrawRightYGridLines = true;
        private YAxis.YAxisLabelPosition mRightYPosition;
        private boolean mDrawRightYAxis = true;
        //图列Legend
        private boolean mChartLegndEnabled = false;

        //Y轴加基准线
        private LimitLine mYLinitLine;
        //x轴加基准线
        private LimitLine mXlinitLine;

        //设置marker
        private IMarker iMarker;
        //设置手势监听
        private OnChartGestureListener mChartGestureListener;
        //设置选中事件
        private OnChartValueSelectedListener mChartValueSelectedListener;

        public BaseChartBuider(BarLineChartBase mBaseChart) {
            this.mBaseChart = mBaseChart;
        }

        public BaseChartBuider noDataText(String noDataText) {
            this.noDataText = noDataText;
            return this;
        }

        public BaseChartBuider mDragEnabled(boolean mDragEnabled) {
            this.mDragEnabled = mDragEnabled;
            return this;
        }

        public BaseChartBuider mDescription(Description mDescription) {
            this.mDescription = mDescription;
            return this;
        }

        public BaseChartBuider mDrawGridBackground(boolean mDrawGridBackground) {
            this.mDrawGridBackground = mDrawGridBackground;
            return this;
        }

        public BaseChartBuider mDrawBorders(boolean mDrawBorders) {
            this.mDrawBorders = mDrawBorders;
            return this;
        }

        public BaseChartBuider mBorderWidth(float mBorderWidth) {
            this.mBorderWidth = mBorderWidth;
            return this;
        }

        public BaseChartBuider mBorderColor(int mBorderColor) {
            this.mBorderColor = mBorderColor;
            return this;
        }

        public BaseChartBuider mTouchEnabled(boolean mTouchEnabled) {
            this.mTouchEnabled = mTouchEnabled;
            return this;
        }

        public BaseChartBuider mDoubleTapToZoomEnabled(boolean mDoubleTapToZoomEnabled) {
            this.mDoubleTapToZoomEnabled = mDoubleTapToZoomEnabled;
            return this;
        }

        public BaseChartBuider mScaleYEnabled(boolean mScaleYEnabled) {
            this.mScaleYEnabled = mScaleYEnabled;
            return this;
        }

        public BaseChartBuider mScaleXEnabled(boolean mScaleXEnabled) {
            this.mScaleXEnabled = mScaleXEnabled;
            return this;
        }

        public BaseChartBuider mBarData(BarData mBarData) {
            this.mBarData = mBarData;
            return this;
        }

        public BaseChartBuider mLineData(LineData mLineData) {
            this.mLineData = mLineData;
            return this;
        }

        public BaseChartBuider mCombinedData(CombinedData mCombinedData) {
            this.mCombinedData = mCombinedData;
            return this;
        }

        public BaseChartBuider mDrawMarkers(boolean mDrawMarkers) {
            this.mDrawMarkers = mDrawMarkers;
            return this;
        }

        public BaseChartBuider mBackGroudColor(int mBackGroudColor) {
            this.mBackGroudColor = mBackGroudColor;
            return this;
        }

        public BaseChartBuider mBackGroudDrawable(Drawable mBackGroudDrawable) {
            this.mBackGroudDrawable = mBackGroudDrawable;
            return this;
        }

        public BaseChartBuider mDrawXAxisLine(boolean mDrawXAxisLine) {
            this.mDrawXAxisLine = mDrawXAxisLine;
            return this;
        }

        public BaseChartBuider mDrawXAxisLables(boolean mDrawXAxisLables) {
            this.mDrawXAxisLables = mDrawXAxisLables;
            return this;
        }

        public BaseChartBuider mDrawXAxisGridLines(boolean mDrawXAxisGridLines) {
            this.mDrawXAxisGridLines = mDrawXAxisGridLines;
            return this;
        }

        public BaseChartBuider mDrawXAxis(boolean mDrawXAxis) {
            this.mDrawXAxis = mDrawXAxis;
            return this;
        }

        public BaseChartBuider mXAxisPosition(XAxis.XAxisPosition mXAxisPosition) {
            this.mXAxisPosition = mXAxisPosition;
            return this;
        }

        public BaseChartBuider mDrawLeftYLine(boolean mDrawLeftYLine) {
            this.mDrawLeftYLine = mDrawLeftYLine;
            return this;
        }

        public BaseChartBuider mDrawLeftYLables(boolean mDrawLeftYLables) {
            this.mDrawLeftYLables = mDrawLeftYLables;
            return this;
        }

        public BaseChartBuider mDrawLeftYGridLines(boolean mDrawLeftYGridLines) {
            this.mDrawLeftYGridLines = mDrawLeftYGridLines;
            return this;
        }

        public BaseChartBuider mLeftYPosition(YAxis.YAxisLabelPosition mLeftYPosition) {
            this.mLeftYPosition = mLeftYPosition;
            return this;
        }

        public BaseChartBuider mDrawLeftYAxis(boolean mDrawLeftYAxis) {
            this.mDrawLeftYAxis = mDrawLeftYAxis;
            return this;
        }

        public BaseChartBuider mDrawZeroLine(boolean mDrawZeroLine) {
            this.mDrawZeroLine = mDrawZeroLine;
            return this;
        }

        public BaseChartBuider mDrawRightYLine(boolean mDrawRightYLine) {
            this.mDrawRightYLine = mDrawRightYLine;
            return this;
        }

        public BaseChartBuider mDrawRightYLables(boolean mDrawRightYLables) {
            this.mDrawRightYLables = mDrawRightYLables;
            return this;
        }

        public BaseChartBuider mDrawRightYGridLines(boolean mDrawRightYGridLines) {
            this.mDrawRightYGridLines = mDrawRightYGridLines;
            return this;
        }

        public BaseChartBuider mRightYPosition(YAxis.YAxisLabelPosition mRightYPosition) {
            this.mRightYPosition = mRightYPosition;
            return this;
        }

        public BaseChartBuider mDrawRightYAxis(boolean mDrawRightYAxis) {
            this.mDrawRightYAxis = mDrawRightYAxis;
            return this;
        }

        public BaseChartBuider mChartLegndEnabled(boolean mChartLegndEnabled) {
            this.mChartLegndEnabled = mChartLegndEnabled;
            return this;
        }

        public BaseChartBuider mYLinitLine(LimitLine mYLinitLine) {
            this.mYLinitLine = mYLinitLine;
            return this;
        }

        public BaseChartBuider mXlinitLine(LimitLine mXlinitLine) {
            this.mXlinitLine = mXlinitLine;
            return this;
        }

        public BaseChartBuider iMarker(IMarker iMarker) {
            this.iMarker = iMarker;
            return this;
        }

        public BaseChartBuider mChartGestureListener(OnChartGestureListener mChartGestureListener) {
            this.mChartGestureListener = mChartGestureListener;
            return this;
        }

        public BaseChartBuider mChartValueSelectedListener(OnChartValueSelectedListener mChartValueSelectedListener) {
            this.mChartValueSelectedListener = mChartValueSelectedListener;
            return this;
        }

        public void init() {
            if (mBaseChart != null) {
                XAxis xAxis = mBaseChart.getXAxis();
                YAxis axisLeft = mBaseChart.getAxisLeft();
                YAxis axisRight = mBaseChart.getAxisRight();
                xAxis.setDrawAxisLine(mDrawXAxisLine);
                xAxis.setDrawGridLines(mDrawXAxisGridLines);
                xAxis.setPosition(mXAxisPosition);
                xAxis.setDrawLabels(mDrawXAxisLables);
                if (mXlinitLine != null) {
                    xAxis.addLimitLine(mXlinitLine);
                }
                xAxis.setEnabled(mDrawXAxis);

                axisLeft.setDrawZeroLine(mDrawZeroLine);
                axisLeft.setDrawAxisLine(mDrawLeftYLine);
                axisLeft.setDrawGridLines(mDrawLeftYGridLines);
                axisLeft.setDrawLabels(mDrawLeftYLables);
                axisLeft.setPosition(mLeftYPosition);
                if (mYLinitLine != null) {
                    axisLeft.addLimitLine(mYLinitLine);
                }
                axisLeft.setEnabled(mDrawLeftYAxis);

                axisRight.setDrawLabels(mDrawRightYLables);
                axisRight.setDrawGridLines(mDrawRightYGridLines);
                axisRight.setDrawAxisLine(mDrawRightYLine);
                axisRight.setPosition(mRightYPosition);
                axisRight.setEnabled(mDrawRightYAxis);

                Legend legend = mBaseChart.getLegend();
                legend.setEnabled(mChartLegndEnabled);

                mBaseChart.setNoDataText(noDataText);
                mBaseChart.setDescription(mDescription);
                if (mBorderColor == 0) {
                    mBaseChart.setBackgroundDrawable(mBackGroudDrawable);
                } else {
                    mBaseChart.setBackgroundColor(mBackGroudColor);
                }
                mBaseChart.setDrawGridBackground(mDrawGridBackground);
                mBaseChart.setDrawMarkers(mDrawMarkers);
                mBaseChart.setDrawBorders(mDrawBorders);
                mBaseChart.setDragEnabled(mDragEnabled);
                mBaseChart.setBorderColor(mBorderColor);
                mBaseChart.setBorderWidth(mBorderWidth);
                mBaseChart.setTouchEnabled(mTouchEnabled);
                mBaseChart.setDoubleTapToZoomEnabled(mDoubleTapToZoomEnabled);
                mBaseChart.setScaleXEnabled(mScaleXEnabled);
                mBaseChart.setScaleYEnabled(mScaleYEnabled);
                mBaseChart.setMarker(iMarker);
                mBaseChart.setOnChartValueSelectedListener(mChartValueSelectedListener);
                mBaseChart.setOnChartGestureListener(mChartGestureListener);
                if (mBaseChart instanceof LineChart) {
                    mBaseChart.setData(mLineData);
                } else if (mBaseChart instanceof BarChart) {
                    mBaseChart.setData(mBarData);
                } else if (mBaseChart instanceof CombinedChart) {
                    mBaseChart.setData(mCombinedData);
                }
                mBaseChart.invalidate();
            }
        }

    }


}
