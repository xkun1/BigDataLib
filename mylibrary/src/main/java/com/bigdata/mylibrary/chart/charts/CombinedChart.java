
package com.bigdata.mylibrary.chart.charts;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.bigdata.mylibrary.chart.data.BarData;
import com.bigdata.mylibrary.chart.data.BubbleData;
import com.bigdata.mylibrary.chart.data.CandleData;
import com.bigdata.mylibrary.chart.data.CombinedData;
import com.bigdata.mylibrary.chart.data.LineData;
import com.bigdata.mylibrary.chart.data.ScatterData;
import com.bigdata.mylibrary.chart.highlight.CombinedHighlighter;
import com.bigdata.mylibrary.chart.highlight.Highlight;
import com.bigdata.mylibrary.chart.interfaces.dataprovider.CombinedDataProvider;
import com.bigdata.mylibrary.chart.renderer.CombinedChartRenderer;


/**
 * This chart class allows the combination of lines, bars, scatter and candle
 * data all displayed in one chart area.
 *
 * @author Philipp Jahoda
 */
public class CombinedChart extends BarLineChartBase<CombinedData> implements CombinedDataProvider {

    /**
     * if set to true, all values are drawn above their bars, instead of below
     * their top
     */
    private boolean mDrawValueAboveBar = true;


    /**
     * flag that indicates whether the highlight should be full-bar oriented, or single-value?
     */
    protected boolean mHighlightFullBarEnabled = false;

    /**
     * if set to true, a grey area is drawn behind each bar that indicates the
     * maximum value
     */
    private boolean mDrawBarShadow = false;

    protected DrawOrder[] mDrawOrder;

    /**
     * enum that allows to specify the order in which the different data objects
     * for the combined-chart are drawn
     */
    public enum DrawOrder {
        BAR, BUBBLE, LINE, CANDLE, SCATTER
    }

    public CombinedChart(Context context) {
        super(context);
    }

    public CombinedChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CombinedChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        // Default values are not ready here yet
        mDrawOrder = new DrawOrder[]{
                DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.LINE, DrawOrder.CANDLE, DrawOrder.SCATTER
        };

        setHighlighter(new CombinedHighlighter(this, this));

        // Old default behaviour
        setHighlightFullBarEnabled(true);

        mRenderer = new CombinedChartRenderer(this, mAnimator, mViewPortHandler);
    }

    @Override
    public CombinedData getCombinedData() {
        return mData;
    }

    @Override
    public void setData(CombinedData data) {
        super.setData(data);
        setHighlighter(new CombinedHighlighter(this, this));
        ((CombinedChartRenderer)mRenderer).createRenderers();
        mRenderer.initBuffers();
    }

    /**
     * Returns the Highlight object (contains x-index and DataSet index) of the selected value at the given touch
     * point
     * inside the CombinedChart.
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public Highlight getHighlightByTouchPoint(float x, float y) {

        if (mData == null) {
            Log.e(LOG_TAG, "Can't select by touch. No data set.");
            return null;
        } else {
            Highlight h = getHighlighter().getHighlight(x, y);
            if (h == null || !isHighlightFullBarEnabled()) return h;

            // For isHighlightFullBarEnabled, remove stackIndex
            return new Highlight(h.getX(), h.getY(),
                    h.getXPx(), h.getYPx(),
                    h.getDataSetIndex(), -1, h.getAxis());
        }
    }

    @Override
    public LineData getLineData() {
        if (mData == null)
            return null;
        return mData.getLineData();
    }

    @Override
    public BarData getBarData() {
        if (mData == null)
            return null;
        return mData.getBarData();
    }

    @Override
    public ScatterData getScatterData() {
        if (mData == null)
            return null;
        return mData.getScatterData();
    }

    @Override
    public CandleData getCandleData() {
        if (mData == null)
            return null;
        return mData.getCandleData();
    }

    @Override
    public BubbleData getBubbleData() {
        if (mData == null)
            return null;
        return mData.getBubbleData();
    }

    @Override
    public boolean isDrawBarShadowEnabled() {
        return mDrawBarShadow;
    }

    @Override
    public boolean isDrawValueAboveBarEnabled() {
        return mDrawValueAboveBar;
    }

    /**
     * If set to true, all values are drawn above their bars, instead of below
     * their top.
     *
     * @param enabled
     */
    public void setDrawValueAboveBar(boolean enabled) {
        mDrawValueAboveBar = enabled;
    }


    /**
     * If set to true, a grey area is drawn behind each bar that indicates the
     * maximum value. Enabling his will reduce performance by about 50%.
     *
     * @param enabled
     */
    public void setDrawBarShadow(boolean enabled) {
        mDrawBarShadow = enabled;
    }

    /**
     * Set this to true to make the highlight operation full-bar oriented,
     * false to make it highlight single values (relevant only for stacked).
     *
     * @param enabled
     */
    public void setHighlightFullBarEnabled(boolean enabled) {
        mHighlightFullBarEnabled = enabled;
    }

    /**
     * @return true the highlight operation is be full-bar oriented, false if single-value
     */
    @Override
    public boolean isHighlightFullBarEnabled() {
        return mHighlightFullBarEnabled;
    }

    /**
     * Returns the currently set draw order.
     *
     * @return
     */
    public DrawOrder[] getDrawOrder() {
        return mDrawOrder;
    }

    /**
     * Sets the order in which the provided data objects should be drawn. The
     * earlier you place them in the provided array, the further they will be in
     * the background. e.g. if you provide new DrawOrer[] { DrawOrder.BAR,
     * DrawOrder.LINE }, the bars will be drawn behind the lines.
     *
     * @param order
     */
    public void setDrawOrder(DrawOrder[] order) {
        if (order == null || order.length <= 0)
            return;
        mDrawOrder = order;
    }
}