package com.bigdata.bigdatalib.dialog;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bigdata.bigdatalib.R;
import com.bigdata.mylibrary.util.ImageUtils;
import com.bigdata.mylibrary.util.ToastUtils;
import com.bigdata.mylibrary.zxing.codeUtils.OneCodeUtils;

/**
 * user:kun
 * Date:2017/5/22 or 下午1:52
 * email:hekun@gamil.com
 * Desc:
 */

public class OrCodeDigFragment extends DialogFragment {

    private EditText editText;
    private Button mButton;
    private Button diss;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orcode, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (dm.widthPixels * 0.90));
        window.setAttributes(params);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = (EditText) view.findViewById(R.id.edit_txt);
        mButton = (Button) view.findViewById(R.id.button_creat);
        diss = (Button) view.findViewById(R.id.button_diss);
        imageView = (ImageView) view.findViewById(R.id.orcode_pic);
        view.findViewById(R.id.button_creat_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0) {
                    String s = editText.getText().toString();
                    Bitmap bitmap = ImageUtils.drawable2Bitmap(getResources().getDrawable(R.drawable.im_pic));
                    Bitmap qrImage = OneCodeUtils.createQRImage(s, 500, 500,bitmap);
                    imageView.setImageBitmap(qrImage);
                } else {
                    ToastUtils.showLong("请输入内容");
                }
            }
        });

        diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0) {
                    String s = editText.getText().toString();
                    Bitmap qrImage = OneCodeUtils.createQRImage(s, 500, 500,null);
                    imageView.setImageBitmap(qrImage);
                } else {
                    ToastUtils.showLong("请输入内容");
                }
            }
        });
    }
}
