package com.bigdata.mylibrary.chart.interfaces.dataprovider;


import com.bigdata.mylibrary.chart.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
