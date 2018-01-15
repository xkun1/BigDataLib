package com.bigdata.bigdatalib;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.bigdata.mylibrary.chart.charts.BarChart;
import com.bigdata.mylibrary.chart.charts.LineChart;
import com.bigdata.mylibrary.chart.components.Description;
import com.bigdata.mylibrary.chart.components.MarkerView;
import com.bigdata.mylibrary.chart.components.XAxis;
import com.bigdata.mylibrary.chart.components.YAxis;
import com.bigdata.mylibrary.chart.data.BarData;
import com.bigdata.mylibrary.chart.data.BarDataSet;
import com.bigdata.mylibrary.chart.data.BarEntry;
import com.bigdata.mylibrary.chart.data.Entry;
import com.bigdata.mylibrary.chart.data.LineData;
import com.bigdata.mylibrary.chart.data.LineDataSet;
import com.bigdata.mylibrary.chart.highlight.Highlight;
import com.bigdata.mylibrary.chart.listener.ChartTouchListener;
import com.bigdata.mylibrary.chart.listener.OnChartGestureListener;
import com.bigdata.mylibrary.chart.listener.OnChartValueSelectedListener;
import com.bigdata.mylibrary.chartUtils.BaseChartUtils;
import com.bigdata.mylibrary.ui.base.LibraryBaseActivity;

import java.util.ArrayList;

/**
 * user:kun
 * Date:15/01/2018 or 10:08 AM
 * email:hekun@gamil.com
 * Desc:
 */

public class ChartDemo extends LibraryBaseActivity {

    LineChart mLineChart;
    BarChart mBarChart;


    @Override
    protected void init() {
        setContentView(R.layout.activity_chartdemo);
        mLineChart = (LineChart) findViewById(R.id.mLineChart);
        mBarChart = (BarChart) findViewById(R.id.mBarChart);

        Description mDescription = new Description();
        mDescription.setText("这是图表描述");
        mDescription.setTextSize(18);
        mDescription.setTextColor(Color.BLACK);

        new BaseChartUtils.BaseChartBuider(mLineChart)
                .noDataText("暂无数据")
                .mDescription(mDescription)
                .mTouchEnabled(true)
                .mDragEnabled(false)
                .mDrawBorders(true)
                .mScaleYEnabled(false)
                .mScaleXEnabled(false)
                .mDrawGridBackground(true)
                .mDoubleTapToZoomEnabled(true)
                .mBorderWidth(2)
                .mBorderColor(Color.BLACK)
                .mChartGestureListener(new myGetureListener())
                .mChartValueSelectedListener(new myValuseListener())
                .mDrawMarkers(true)
                .iMarker(new myMarkerView(this, R.layout.marker_view))
                .mXAxisPosition(XAxis.XAxisPosition.BOTTOM_INSIDE)
                .mDrawXAxisLine(true)
                .mDrawXAxisLables(true)
                .mDrawXAxisGridLines(false)
                .mDrawLeftYLables(true)
                .mDrawLeftYGridLines(false)
                .mDrawZeroLine(false)
                .mDrawLeftYLine(true)
                .mLeftYPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
                .mDrawRightYAxis(false)
                .mChartLegndEnabled(false)
                .mLineData(getLineData())
                .init();




        new BaseChartUtils.BaseChartBuider(mBarChart)
                .noDataText("暂无数据")
                .mDescription(mDescription)
                .mTouchEnabled(true)
                .mDragEnabled(false)
                .mDrawBorders(true)
                .mScaleYEnabled(false)
                .mScaleXEnabled(false)
                .mDrawGridBackground(true)
                .mDoubleTapToZoomEnabled(true)
                .mBorderWidth(2)
                .mBorderColor(Color.BLACK)
                .mChartGestureListener(new myBarGetureListener())
                .mChartValueSelectedListener(new myBarValuseListener())
                .mDrawMarkers(true)
                .iMarker(new myMarkerView(this, R.layout.marker_view))
                .mXAxisPosition(XAxis.XAxisPosition.BOTTOM_INSIDE)
                .mDrawXAxisLine(true)
                .mDrawXAxisLables(true)
                .mDrawXAxisGridLines(false)
                .mDrawLeftYLables(true)
                .mDrawLeftYGridLines(false)
                .mDrawZeroLine(false)
                .mDrawLeftYLine(true)
                .mLeftYPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
                .mDrawRightYAxis(false)
                .mChartLegndEnabled(false)
                .mBarData(getBarData())
                .init();

    }

    private LineData getLineData() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            entries.add(new Entry(i, i));
        }
        LineDataSet dataSet = new LineDataSet(entries, "第一条");
        LineData lineData = new LineData();
        lineData.addDataSet(dataSet);
        return lineData;
    }

    private BarData getBarData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            entries.add(new BarEntry(i, i));
        }
        BarDataSet dataSet = new BarDataSet(entries, "第一条");
        BarData barData = new BarData();
        barData.addDataSet(dataSet);
        return barData;
    }

    private class myGetureListener implements OnChartGestureListener {

        @Override
        public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            Log.d(TAG, "onChartGestureStart: ");
        }

        @Override
        public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            Log.d(TAG, "onChartGestureEnd: ");
        }

        @Override
        public void onChartLongPressed(MotionEvent me) {
            Log.d(TAG, "onChartLongPressed: ");
        }

        @Override
        public void onChartDoubleTapped(MotionEvent me) {
            Log.d(TAG, "onChartDoubleTapped: ");
        }

        @Override
        public void onChartSingleTapped(MotionEvent me) {
            Log.d(TAG, "onChartSingleTapped: ");
        }

        @Override
        public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
            Log.d(TAG, "onChartFling: ");
        }

        @Override
        public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
            Log.d(TAG, "onChartScale: ");
        }

        @Override
        public void onChartTranslate(MotionEvent me, float dX, float dY) {
            Log.d(TAG, "onChartTranslate: ");
        }
    }

    private class myValuseListener implements OnChartValueSelectedListener {

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            mLineChart.highlightValues(new Highlight[]{h});
        }

        @Override
        public void onNothingSelected() {

        }
    }

    private class myMarkerView extends MarkerView {

        /**
         * Constructor. Sets up the MarkerView with a custom layout resource.
         *
         * @param context
         * @param layoutResource the layout resource to use for the MarkerView
         */
        TextView textView;

        public myMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            textView = (TextView) findViewById(R.id.mTv);
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            super.refreshContent(e, highlight);
            textView.setText(e.getY() + "");
        }
    }

    private class myBarGetureListener implements OnChartGestureListener {
        @Override
        public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        }

        @Override
        public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        }

        @Override
        public void onChartLongPressed(MotionEvent me) {

        }

        @Override
        public void onChartDoubleTapped(MotionEvent me) {

        }

        @Override
        public void onChartSingleTapped(MotionEvent me) {

        }

        @Override
        public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

        }

        @Override
        public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

        }

        @Override
        public void onChartTranslate(MotionEvent me, float dX, float dY) {

        }
    }

    private class myBarValuseListener implements OnChartValueSelectedListener {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            mBarChart.highlightValues(new Highlight[]{h});
        }

        @Override
        public void onNothingSelected() {
            mBarChart.highlightValues(null);
        }
    }
}
