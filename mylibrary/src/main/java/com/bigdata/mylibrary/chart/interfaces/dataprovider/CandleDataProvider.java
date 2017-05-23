package com.bigdata.mylibrary.chart.interfaces.dataprovider;


import com.bigdata.mylibrary.chart.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
