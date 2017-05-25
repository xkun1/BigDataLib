package com.bigdata.bigdatalib.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigdata.bigdatalib.R;
import com.bigdata.mylibrary.ui.stickylistheaders.StickyListHeadersAdapter;

import java.util.ArrayList;

/**
 * user:kun
 * Date:2017/5/23 or 上午11:33
 * email:hekun@gamil.com
 * Desc:
 */

public class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context mContext;
    private String[] mCountries;
    private int[] mSectionIndices;
    private LayoutInflater mInflater;

    public MyAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        mCountries = mContext.getResources().getStringArray(R.array.countries);
        mSectionIndices = getmSectionIndices();
    }

    private int[] getmSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
        char lastFirstChar = mCountries[0].charAt(0);
        sectionIndices.add(0);
        for (int i = 1; i < mCountries.length; i++) {
            if (mCountries[i].charAt(0) != lastFirstChar) {
                lastFirstChar = mCountries[i].charAt(0);
                sectionIndices.add(i);
            }
        }
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeadViewHodler headViewHodler;
        if (convertView == null) {
            headViewHodler = new HeadViewHodler();
            convertView = mInflater.inflate(R.layout.item_context, parent, false);
            convertView.setBackgroundColor(Color.BLACK);
            headViewHodler.textView = (TextView) convertView.findViewById(R.id.txt_cont);
            convertView.setTag(headViewHodler);
        } else {
            headViewHodler = (HeadViewHodler) convertView.getTag();
        }
        CharSequence headerChar = mCountries[position].subSequence(0, 1);
        headViewHodler.textView.setText(headerChar);
        headViewHodler.textView.setTextColor(Color.WHITE);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return mCountries[position].subSequence(0, 1).charAt(0);
    }

    @Override
    public int getCount() {
        return mCountries.length;
    }

    @Override
    public Object getItem(int position) {
        return mCountries[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler;
        if (convertView == null) {
            viewHoler = new ViewHoler();
            convertView = mInflater.inflate(R.layout.item_context, parent, false);
            viewHoler.textView = (TextView) convertView.findViewById(R.id.txt_cont);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        CharSequence headerChar = mCountries[position];
        viewHoler.textView.setText(headerChar);
        return convertView;
    }

    class ViewHoler {
        TextView textView;
    }

    class HeadViewHodler {
        TextView textView;
    }
}
