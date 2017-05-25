package com.bigdata.bigdatalib.kotlin

import android.os.Bundle
import com.bigdata.bigdatalib.R
import com.bigdata.bigdatalib.adapter.MyAdapter
import com.bigdata.mylibrary.ui.stickylistheaders.StickyListHeadersListView

/**
 * user:kun
 * Date:2017/5/23 or 上午11:29
 * email:hekun@gamil.com
 * Desc:
 */

class LinkManActivity : BaseActivity() {

    private var listHeadersListView: StickyListHeadersListView? = null
    private var myAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linkman)
        listHeadersListView = findViewById(R.id.sticky_list) as StickyListHeadersListView

        myAdapter = MyAdapter(this)
        listHeadersListView!!.adapter = myAdapter
        myAdapter!!.notifyDataSetChanged()


    }

}
