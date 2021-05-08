package com.yanda.module_base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanda.module_base.R;

/**
 * 作者：caibin
 * 时间：2016/9/13 10:23
 * 类说明：自定义加载数据的dialog
 */

public class LoadingProgressDialog extends Dialog {
    private View inflate;  //dialog布局
    private TextView textView;
    private ImageView imageView;
    private AnimationDrawable animationDrawable;
    public LoadingProgressDialog(Context context) {
        this(context, R.style.my_progress_dialog);
    }

    public LoadingProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }
    private void init(){
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.loading_dialog, null);
        imageView = inflate.findViewById(R.id.imageView);
        textView = inflate.findViewById(R.id.name);
        setContentView(inflate);
//        setCancelable(false);// 不可以用“返回键”取消
    }
    public void setTextContent(String content){
        textView.setText(content);
    }
    public void showDialog(){
        if (animationDrawable == null) {
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
        }
        animationDrawable.start();
        show();
    }
    public void dismissProgressDialog(){
        animationDrawable.stop();
        dismiss();
    }
}
