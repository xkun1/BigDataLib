package com.bigdata.bigdatalib.kotlin

import android.graphics.Color
import android.os.Bundle
import com.bigdata.bigdatalib.R
import com.bigdata.mylibrary.chart.animation.Easing
import com.bigdata.mylibrary.chart.charts.LineChart
import com.bigdata.mylibrary.chart.components.Description
import com.bigdata.mylibrary.chart.data.Entry
import com.bigdata.mylibrary.chart.data.LineData
import com.bigdata.mylibrary.chart.data.LineDataSet
import com.bigdata.mylibrary.chart.interfaces.datasets.ILineDataSet
import java.util.*

/**
 * user:kun
 * Date:2017/5/23 or 下午2:13
 * email:hekun@gamil.com
 * Desc: 图表库的展示
 */

class ChartActivity : BaseActivity() {

     var mLineChart: LineChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        mLineChart = findViewById(R.id.lineChar) as LineChart

        initChart()

        setData()
    }

    private fun setData() {
        val yValue = ArrayList<Entry>()
        for (i in 0..19) {
            val `val` = (Math.random() * 1).toFloat() + 3
            yValue.add(Entry(i.toFloat(), `val`))
        }
        val yValue2 = ArrayList<Entry>()
        for (i in 0..19) {
            val `val` = (Math.random() * 2).toFloat() + 3
            yValue2.add(Entry(i.toFloat(), `val`))
        }
        val dataSet = LineDataSet(yValue, "第一条")
        dataSet.color = Color.RED
        dataSet.setCircleColor(Color.RED)
        dataSet.setDrawValues(false)

        val dataSet2 = LineDataSet(yValue2, "第二条")
        dataSet.color = Color.BLUE
        dataSet.setCircleColor(Color.RED)
        dataSet.setDrawValues(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(dataSet)
        dataSets.add(dataSet2)
        //构建一个LineData  将dataSets放入
        val lineData = LineData(dataSets)
        //将数据插入
        mLineChart!!.data = lineData
        //设置动画效果
        mLineChart!!.animateY(2000, Easing.EasingOption.Linear)
        mLineChart!!.animateX(2000, Easing.EasingOption.Linear)
        mLineChart!!.invalidate()

    }

    private fun initChart() {
        val description = Description()
        description.text = "测试图表"
        description.textColor = Color.RED
        description.textSize = 20f
        mLineChart!!.description = description//设置图表描述信息
        mLineChart!!.setNoDataText("没有数据熬")//没有数据时显示的文字
        mLineChart!!.setNoDataTextColor(Color.BLUE)//没有数据时显示文字的颜色
        mLineChart!!.setDrawGridBackground(false)//chart 绘图区后面的背景矩形将绘制
        mLineChart!!.setDrawBorders(false)//禁止绘制图表边框的线
    }

}
