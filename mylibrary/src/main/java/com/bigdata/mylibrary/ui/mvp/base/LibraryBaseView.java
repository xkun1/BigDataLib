package com.bigdata.mylibrary.ui.mvp.base;

import android.content.Context;

/**
 * user:kun
 * Date:21/06/2017 or 2:07 PM
 * email:hekun@gamil.com
 * Desc: 所有的view实现这个接口
 */

public interface LibraryBaseView {

    Context getCotext();

    void showError(String msg);

}
