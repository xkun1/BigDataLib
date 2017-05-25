package com.bigdata.bigdatalib.kotlin

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.bigdata.bigdatalib.R
import com.bigdata.bigdatalib.adapter.MyViewPagerAdater
import com.bigdata.bigdatalib.dialog.OrCodeDigFragment
import com.bigdata.mylibrary.util.ActivityUtils
import com.bigdata.mylibrary.util.RxPermissionsUtils
import com.bigdata.mylibrary.util.ToastUtils
import com.bigdata.mylibrary.zxing.CaptureActivity
import com.bigdata.mylibrary.zxing.Constant

class DemoKotiln : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    var orcodeFragment: OrCodeDigFragment? = null

    var drawer: DrawerLayout? = null
    var mViewPager: ViewPager? = null
    var myViewPagerAdater:MyViewPagerAdater?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_kotiln)

        initView()

    }

    private fun initView() {
        orcodeFragment = OrCodeDigFragment()
        //toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // snackBar
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        //侧滑布局
        drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.setDrawerListener(toggle)
        toggle.syncState()

        //展开监听
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

    }

    //返回
    override fun onBackPressed() {
        drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // 右上角图标事件
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.demo_kotiln, menu)
        return true
    }

    //activity回调结果
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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

    //item 点击事件
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when (id) {
            R.id.action_settings -> ToastUtils.showLong("这是设置")
            R.id.home -> finish()
            R.id.action_share -> ToastUtils.showLong("这是分享")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        when (id) {
            R.id.nav_camera -> ActivityUtils.startActivity(this, NetActivity::class.java)
            R.id.nav_gallery -> ActivityUtils.startActivity(this, WebViewActivity::class.java)
            R.id.nav_slideshow -> orcodeFragment!!.show(fragmentManager, "")
            R.id.nav_manage -> {
                val request = RxPermissionsUtils.setRequest(this, CAMERA)
                if (request) {
                    startActivityForResult(Intent(this, CaptureActivity::class.java), RESULE_CODE)
                } else {
                    ToastUtils.showLong("打开权限")
                }
            }
            R.id.nav_share -> ActivityUtils.startActivity(this, LinkManActivity::class.java)
            R.id.nav_send -> ActivityUtils.startActivity(this, ChartActivity::class.java)
        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    companion object {
        private val RESULE_CODE = 1
    }
}
