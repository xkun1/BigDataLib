package com.bigdata.mylibrary.chart.interfaces.dataprovider;


import com.bigdata.mylibrary.chart.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
