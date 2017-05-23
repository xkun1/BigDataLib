package com.bigdata.mylibrary.chart.interfaces.dataprovider;


import com.bigdata.mylibrary.chart.components.YAxis;
import com.bigdata.mylibrary.chart.data.BarLineScatterCandleBubbleData;
import com.bigdata.mylibrary.chart.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(YAxis.AxisDependency axis);
    boolean isInverted(YAxis.AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
