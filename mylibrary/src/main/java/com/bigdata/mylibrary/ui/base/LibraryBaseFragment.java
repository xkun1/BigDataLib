package com.bigdata.mylibrary.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigdata.mylibrary.net.BaseObserVable;
import com.bigdata.mylibrary.net.RetroFactory;
import com.bigdata.mylibrary.ui.mvp.base.LibraryBaseModel;
import com.bigdata.mylibrary.ui.mvp.base.LibraryBasePresenter;
import com.bigdata.mylibrary.ui.mvp.base.LibraryBaseView;
import com.bigdata.mylibrary.util.LogUtils;

/**
 * user:kun
 * Date:2017/5/20 or 下午3:19
 * email:hekun@gamil.com
 * Desc:  打造通用的Fragment
 */

public abstract class LibraryBaseFragment<P extends LibraryBasePresenter, M extends LibraryBaseModel> extends Fragment {
    protected RetroFactory mRetroFactory;
    protected BaseObserVable mBaseObserVable;

    protected String TAG=LibraryBaseFragment.class.getSimpleName();

    public M mModel;
    public P mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRetroFactory = RetroFactory.getInstance();
        mBaseObserVable = BaseObserVable.getInstance();
        if (mBaseObserVable != null && mRetroFactory != null) {

        } else {
            LogUtils.d("初始化失败,请查看Application");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return layoutId(inflater,container,savedInstanceState);
    }

    protected abstract View layoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this instanceof LibraryBaseView){
            mPresenter.attchVM(this,mModel);
            init(view);
        }else {
            LogUtils.e("实现LibraryBaseView接口");
        }
    }

    protected abstract void init(View view);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachVM();
    }


}
