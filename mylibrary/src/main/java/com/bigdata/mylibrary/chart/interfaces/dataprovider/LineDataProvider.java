package com.bigdata.mylibrary.chart.interfaces.dataprovider;


import com.bigdata.mylibrary.chart.components.YAxis;
import com.bigdata.mylibrary.chart.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
