package com.bigdata.mylibrary.ui.base;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * user:kun
 * Date:19/01/2018 or 2:35 PM
 * email:hekun@gamil.com
 * Desc:
 */

public abstract class LibraryBaseFragmentDiglog extends DialogFragment {


    private float digLogWidth = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        return inflater.inflate(setLayoutId(), container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setInit();
    }

    public void setDigLogWidth(float digLogWidth) {
        this.digLogWidth = digLogWidth;
    }

    protected void showFragment(FragmentManager manager) {
        if (manager != null) {
            show(manager, "");
        }
    }

    protected void dissFragment() {
        dismiss();
    }

    /**
     * 已经显示啦
     */
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout((int) (dm.widthPixels * digLogWidth), ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    protected abstract void setInit();

    /**
     * 加载布局文件
     *
     * @return
     */
    protected abstract int setLayoutId();
}
