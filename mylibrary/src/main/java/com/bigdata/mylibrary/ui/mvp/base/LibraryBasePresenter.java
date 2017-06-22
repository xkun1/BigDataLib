package com.bigdata.mylibrary.ui.mvp.base;

import java.util.ArrayList;
import java.util.List;

/**
 * user:kun
 * Date:21/06/2017 or 2:02 PM
 * email:hekun@gamil.com
 * Desc:
 */

public abstract class LibraryBasePresenter<M,V>{
    public M mModel;
    public V mView;

    public void attchVM(V v,M m){
        this.mModel=m;
        this.mView=v;
        this.onStart();
        List<M>list=new ArrayList<>();
    }
    public void detachVM(){
        mView=null;
        mModel=null;
    }
    public abstract void onStart();
}
