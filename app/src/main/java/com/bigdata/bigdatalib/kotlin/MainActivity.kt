package com.bigdata.bigdatalib.kotlin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.bigdata.bigdatalib.ListViewActivity
import com.bigdata.bigdatalib.R
import com.bigdata.bigdatalib.dialog.OrCodeDigFragment
import com.bigdata.mylibrary.ui.view.CustomPopWindow
import com.bigdata.mylibrary.util.ActivityUtils
import com.bigdata.mylibrary.util.RxPermissionsUtils
import com.bigdata.mylibrary.util.ToastUtils
import com.bigdata.mylibrary.zxing.CaptureActivity
import com.bigdata.mylibrary.zxing.Constant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    var orCodeDigFragment: OrCodeDigFragment? = null

    var popWindow: CustomPopWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        orCodeDigFragment = OrCodeDigFragment()
        popWindow = CustomPopWindow(this)
        button_net.setOnClickListener { ActivityUtils.startActivity(this@MainActivity, NetActivity::class.java) }
        button_webView.setOnClickListener { ActivityUtils.startActivity(this@MainActivity, WebViewActivity::class.java) }
        button_orCode.setOnClickListener {
            val b = RxPermissionsUtils.setRequest(this@MainActivity, Manifest.permission.CAMERA)
            if (b) {
                startActivityForResult(Intent(this@MainActivity, CaptureActivity::class.java), RESULE_CODE!!)
            } else {
                ToastUtils.showLong("请打开相机权限")
            }
        }
        findViewById(R.id.button_setOrCode).setOnClickListener { orCodeDigFragment!!.show(fragmentManager, "") }
        findViewById(R.id.ListView).setOnClickListener { ActivityUtils.startActivity(this@MainActivity, LinkManActivity::class.java) }
        findViewById(R.id.button_chart).setOnClickListener { ActivityUtils.startActivity(this@MainActivity, ChartActivity::class.java) }
        findViewById(R.id.button_listview).setOnClickListener { ActivityUtils.startActivity(this@MainActivity, ListViewActivity::class.java) }
        findViewById(R.id.button_popWindow).setOnClickListener {
            val inflate = LayoutInflater.from(this@MainActivity).inflate(R.layout.pop_layout, null)
            CustomPopWindow.PopupWindowBuilder(this).setView(R.layout.pop_layout)
                    .create().showAsDropDown(inflate,200,210).build()
        }
    }

    override fun onActivityResult(requestCode: Int?, resultCode: Int?, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RESULE_CODE -> {
                    val stringExtra = data!!.getStringExtra(Constant.ORCODE_RESULT)
                    if (stringExtra != null) {
                        ToastUtils.showLong(stringExtra)
                    } else {
                        ToastUtils.showLong("解析为空")
                    }
                }
            }
        }
    }

    companion object {

        private var RESULE_CODE: Int? = 1
    }
}
