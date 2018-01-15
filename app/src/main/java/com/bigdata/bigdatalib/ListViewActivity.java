package com.bigdata.bigdatalib;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.bigdata.bigdatalib.kotlin.BaseActivity;
import com.bigdata.mylibrary.adapter.BaseAdapter;
import com.bigdata.mylibrary.ui.view.BaseLibSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * user:kun
 * Date:2017/5/26 or 下午4:01
 * email:hekun@gamil.com
 * Desc:
 */

public class ListViewActivity extends BaseActivity {

    private BaseAdapter<Integer> adapter;
    private ListView mListView;
    private BaseLibSwipeRefreshLayout libSwipeRefreshLayout;
    private List<Integer> strings;

    private int num;
    LayoutInflater inflater;
//    private RecyclerView recyclerView;
//    private BaseRecyclerViewAdapter<Integer> adapterRecycler;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_listview);
        strings = new ArrayList<>();
        inflater = LayoutInflater.from(this);
        libSwipeRefreshLayout = (BaseLibSwipeRefreshLayout) findViewById(R.id.refrshLayout);
        View inflate = inflater.inflate(R.layout.more_layout, null);
        initData();
        initListView();
//        initRecycler();
        libSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                strings.clear();
                initData();
                adapter.notifyDataSetChanged();
//                adapterRecycler.notifyDataSetChanged();
                libSwipeRefreshLayout.setRefreshing(false);
            }
        });

        libSwipeRefreshLayout.setmOnLoadListener(new BaseLibSwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                moreData();
                adapter.notifyDataSetChanged();
//                adapterRecycler.notifyDataSetChanged();
            }
        });
    }

    private void initRecycler() {
//        recyclerView = (RecyclerView) findViewById(R.id.recycler);
//        adapterRecycler = new BaseRecyclerViewAdapter<Integer>(recyclerView, strings, R.layout.item_context) {
//            @Override
//            public void convert(BaseRecyclerHolder holder, Integer item, int position, boolean isScrolling) {
//                holder.setText(R.id.txt_cont, String.valueOf(item));
//            }
//        };
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, R.drawable.recycler_divider));
//        recyclerView.setAdapter(adapterRecycler);

    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.ListView);
        adapter = new BaseAdapter<Integer>(this, strings, R.layout.item_context) {
            @Override
            public void showData(ViewHoder vHolder, Integer data, int position) {
                vHolder.setText(R.id.txt_cont, String.valueOf(data));
            }
        };
        mListView.setAdapter(adapter);

    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            strings.add(i);
            num = i;
        }
    }

    private void moreData() {
        for (int i = 0; i < 50; i++) {
            strings.add(i);
        }
    }
}
