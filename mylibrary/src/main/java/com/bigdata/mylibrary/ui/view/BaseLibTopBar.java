package com.bigdata.mylibrary.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigdata.mylibrary.R;
import com.bigdata.mylibrary.util.LogUtils;

/**
 * user:kun
 * Date:2017/5/25 or 下午7:28
 * email:hekun@gamil.com
 * Desc: 通用标题栏
 */

public class BaseLibTopBar extends RelativeLayout {

    private TextView topBarText;
    private TextView topLeftText;
    private TextView topRightText;

    private ImageView leftImage;
    private ImageView rightImage;

    private float mTitleSize;
    private float mTitleLeftSize;
    private float mTitleRightSize;

    private String mTitle;
    private String mLeftTitle;
    private String mRightTitle;

    private boolean mImageLeftVisible;
    private boolean mImageRightvisibe;
    private boolean mTitleVisible;

    private Drawable mLeftDrawable;
    private Drawable mRightDrawable;

    private int mTitleColor;
    private int mLeftTitleColor;
    private int mRightTitleColor;

    private LayoutParams leftParams;
    private LayoutParams rightParams;
    private LayoutParams textViewParams;

    private TopBarLeftImgClickListener topBarLeftImgClickListener;
    private TopBarLeftTvClickListener topBarLeftTvClickListener;
    private TopBarRightImgClickListener topBarRightImgClickListener;
    private TopBarRightTvClickListener topBarRightTvClickListener;

    public BaseLibTopBar(Context context) {
        super(context, null);
    }

    public BaseLibTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.topBar);
        mTitle = typedArray.getString(R.styleable.topBar_titleText);
        mLeftTitle = typedArray.getString(R.styleable.topBar_leftText);
        mRightTitle = typedArray.getString(R.styleable.topBar_rightText);
        mTitleVisible = typedArray.getBoolean(R.styleable.topBar_titleVisible, true);
        //默认18
        mTitleSize = typedArray.getFloat(R.styleable.topBar_titleSize, 18);
        mTitleLeftSize = typedArray.getFloat(R.styleable.topBar_leftTextSize, 16);
        mTitleRightSize = typedArray.getFloat(R.styleable.topBar_rightTextSize, 16);
        //默认黑色
        mTitleColor = typedArray.getColor(R.styleable.topBar_titleColor, Color.BLACK);
        mLeftTitleColor = typedArray.getColor(R.styleable.topBar_leftTextColor, Color.BLACK);
        mRightTitleColor = typedArray.getColor(R.styleable.topBar_rightTextColor, Color.BLACK);

        mLeftDrawable = typedArray.getDrawable(R.styleable.topBar_ImageLeftBackGround);
        mRightDrawable = typedArray.getDrawable(R.styleable.topBar_ImageRightBackGround);

        mImageLeftVisible = typedArray.getBoolean(R.styleable.topBar_ImageLeftVisible, false);
        mImageRightvisibe = typedArray.getBoolean(R.styleable.topBar_ImageRightVisible, false);

        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        topBarText = new TextView(context);
        topLeftText = new TextView(context);
        topRightText = new TextView(context);

        leftImage = new ImageView(context);
        rightImage = new ImageView(context);


        topBarText.setText(mTitle);
        topBarText.setTextColor(mTitleColor);
        topBarText.setTextSize(mTitleSize);

        topLeftText.setText(mLeftTitle);
        topLeftText.setTextColor(mLeftTitleColor);
        topLeftText.setTextSize(mTitleLeftSize);

        topRightText.setText(mRightTitle);
        topRightText.setTextColor(mRightTitleColor);
        topRightText.setTextSize(mTitleRightSize);

        leftImage.setImageDrawable(mLeftDrawable);
        rightImage.setImageDrawable(mRightDrawable);

        //显示或者隐藏标题
        if (mTitleVisible) {
            topBarText.setVisibility(VISIBLE);
        } else {
            topBarText.setVisibility(GONE);
        }
        if (mImageLeftVisible) {
            leftImage.setVisibility(VISIBLE);
        } else {
            leftImage.setVisibility(GONE);
        }
        if (mImageRightvisibe) {
            rightImage.setVisibility(VISIBLE);
        } else {
            rightImage.setVisibility(GONE);
        }
        //创建位置
        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftImage, leftParams);
        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(topLeftText, leftParams);


        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightImage, rightParams);
        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(topRightText, rightParams);


        textViewParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(topBarText, textViewParams);

        topLeftText.setClickable(true);
        topRightText.setClickable(true);


        //点击事件
        topLeftText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topBarLeftTvClickListener != null) {
                    topBarLeftTvClickListener.leftTvClick(v);
                }

            }
        });
        leftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topBarLeftImgClickListener != null) {
                    topBarLeftImgClickListener.leftImgClick(v);
                }
            }
        });
        topRightText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topBarRightTvClickListener != null) {
                    topBarRightTvClickListener.rightTvClick(v);
                }

            }
        });
        rightImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topBarRightImgClickListener != null) {
                    topBarRightImgClickListener.rightImgClick(v);
                }
            }
        });


    }


    /**
     * 左边的图标的点击事件
     *
     * @param topBarLeftImgClickListener
     */
    public void setTopBarLeftImgClickListener(TopBarLeftImgClickListener topBarLeftImgClickListener) {
        this.topBarLeftImgClickListener = topBarLeftImgClickListener;
    }

    /**
     * 左边文字的点击事件
     *
     * @param topBarLeftTvClickListener
     */
    public void setTopBarLeftTvClickListener(TopBarLeftTvClickListener topBarLeftTvClickListener) {
        this.topBarLeftTvClickListener = topBarLeftTvClickListener;
    }

    /**
     * 右边的图标点击事件
     *
     * @param topBarRightImgClickListener
     */
    public void setTopBarRightImgClickListener(TopBarRightImgClickListener topBarRightImgClickListener) {
        this.topBarRightImgClickListener = topBarRightImgClickListener;
    }

    /**
     * 右边文字
     *
     * @param topBarRightTvClickListener
     */
    public void setTopBarRightTvClickListener(TopBarRightTvClickListener topBarRightTvClickListener) {
        this.topBarRightTvClickListener = topBarRightTvClickListener;
    }

    /**
     * 设置title
     *
     * @param titleText
     */
    public void setTitleText(String titleText) {
        if (topBarText != null) {
            topBarText.setText(titleText);
        } else {
            LogUtils.e("TopBarText is  null");
        }

    }

    /**
     * 设置左边text
     *
     * @param text
     */
    public void setTopBarLeftText(String text) {
        if (topRightText != null) {
            topLeftText.setText(text);
        } else {
            LogUtils.e("对象为空。。。");
        }

    }

    /**
     * 设置右边
     *
     * @param text
     */
    public void setTopBarRightText(String text) {
        if (topRightText != null) {
            topRightText.setText(text);
        } else {
            LogUtils.e("对象为空。。。");
        }
    }

    /**
     * 设置左边图片
     * @param barLeftImg
     */
    public void setTopBarLeftImg(Bitmap barLeftImg){
        if (barLeftImg!=null){
            leftImage.setImageBitmap(barLeftImg);
        }
    }
    public void setTopBarLeftImgId(int Imgid){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Imgid);
        leftImage.setImageBitmap(bitmap);
    }

    interface TopBarLeftTvClickListener {
        void leftTvClick(View view);
    }

    interface TopBarLeftImgClickListener {
        void leftImgClick(View view);
    }

    interface TopBarRightTvClickListener {
        void rightTvClick(View view);
    }

    interface TopBarRightImgClickListener {
        void rightImgClick(View view);
    }
}
