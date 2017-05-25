package com.bigdata.bigdatalib.kotlin

import android.os.Bundle
import android.widget.TextView
import com.bigdata.bigdatalib.R

import com.bigdata.mylibrary.net.ResultSubScriber
import com.bigdata.mylibrary.net.SubscriberOnNextListener

/**
 * user:kun
 * Date:2017/5/22 or 上午10:32
 * email:hekun@gamil.com
 * Desc:
 */

class NetActivity : BaseActivity() {

    private var txt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.net_activity)
        txt = findViewById(R.id.txt) as TextView
        mBaseObserVable?.setObservale(mApiServer!!.getSvcCl("1.0"),
                ResultSubScriber(SubscriberOnNextListener<SvcClEntity> { svcClEntity -> txt!!.text = svcClEntity.params!!.cl_cont }, this@NetActivity))
    }
}
