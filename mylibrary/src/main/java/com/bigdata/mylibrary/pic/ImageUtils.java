package com.bigdata.mylibrary.pic;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * user:kun
 * Date:2017/5/19 or 下午3:43
 * email:hekun@gamil.com
 * Desc: 图片处理
 */

public class ImageUtils {


    public static void setImage(Context mContext, ImageView imageView, String imgUrl) {
        Glide.with(mContext)
                .asBitmap()
                .load(imgUrl)
                .into(imageView);
    }

    public static void setImageGif(Context mContext, ImageView imageView, String imgUrl) {
        Glide.with(mContext)
                .asGif()
                .load(imgUrl)
                .into(imageView);
    }


}
