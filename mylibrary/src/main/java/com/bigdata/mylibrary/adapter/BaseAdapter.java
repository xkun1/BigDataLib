package com.bigdata.mylibrary.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.mylibrary.util.LogUtils;

import java.util.List;

/**
 * user:kun
 * Date:2017/5/19 or 下午10:39
 * email:hekun@gamil.com
 * Desc:打造通用的Adapter针对ListView
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    private Context mContext;
    private List<T> datas;
    private int layoutId;

    public BaseAdapter(Context mContext, List<T> datas, int layoutId) {
        this.mContext = mContext;
        if (datas != null) {
            this.datas = datas;
        } else {
            LogUtils.d("datas 数据集为空");
        }
        this.layoutId = layoutId;
    }


    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder vHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(layoutId, parent, false);
            vHolder = new ViewHoder(convertView);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHoder) convertView.getTag();
        }
        showData(vHolder, getItem(position), position);
        return convertView;
    }

    public abstract void showData(ViewHoder vHolder, T data, int position);

    public static class ViewHoder {
        private SparseArray<View> mViews;
        private View itemView;

        public ViewHoder(View itemView) {
            this.itemView = itemView;
        }

        public <T extends View> T getView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                if (view != null) {

                    mViews.put(id, view);
                }
            }
            return (T) view;
        }

        /**
         * @param viewId
         * @param text
         * @return
         */
        public ViewHoder setText(int viewId, String text) {
            TextView textView = getView(viewId);
            textView.setText(text);
            return this;
        }


        /**
         * 给view打标记
         *
         * @param viewId
         * @param tag
         * @return
         */
        public ViewHoder setTag(int viewId, Object tag) {
            View view = getView(viewId);
            view.setTag(tag);
            return this;
        }

        /**
         * @param viewId
         * @param picId
         * @return
         */
        public ViewHoder setImageView(int viewId, int picId) {
            ImageView iView = getView(viewId);
            iView.setImageResource(picId);
            return this;
        }
    }
}
