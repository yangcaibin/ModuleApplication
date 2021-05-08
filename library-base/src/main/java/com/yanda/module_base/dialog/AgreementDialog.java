package com.yanda.module_base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.yanda.module_base.R;
import com.yanda.module_base.utils.LogUtils;

/**
 * 作者：caibin
 * 时间：2020/2/20 14:55
 * 类说明：用户协议和隐私权限弹窗
 */
public class AgreementDialog extends Dialog {
    private Context context;
    private LinearLayout linearLayout;
    private TextView content, noText, yesText;
    private OnAuthorizationOnclickListener listener;

    public AgreementDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }

    public void setOnAuthorizationOnclickListener(OnAuthorizationOnclickListener onAuthorizationOnclickListener) {
        this.listener = onAuthorizationOnclickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_agreement);
        //按空白处不能取消
        setCanceledOnTouchOutside(false);
        linearLayout = findViewById(R.id.linearLayout);
        GradientDrawable grad = new GradientDrawable();
        grad.setStroke(0, 0);
        grad.setCornerRadius(10);
        grad.setColor(Color.WHITE);
        linearLayout.setBackground(grad);
        content = findViewById(R.id.content);
        noText = findViewById(R.id.noText);
        yesText = findViewById(R.id.yesText);
        SpannableStringBuilder spannable = new SpannableStringBuilder(content.getText().toString());
        spannable.setSpan(new TextClick(0), 78, 84, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new TextClick(1), 85, 91, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setMovementMethod(LinkMovementMethod.getInstance());
        content.setHighlightColor(Color.TRANSPARENT);
        content.setText(spannable);
        noText.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNoClick();
            }
        });

        yesText.setOnClickListener(v -> {
            if (listener != null) {
                listener.onYesClick();
            }
        });
    }

    private class TextClick extends ClickableSpan {
        private int position;

        public TextClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View widget) {
            //在此处理点击事件
//            Intent intent = new Intent(context, AgreementActivity.class);
//            // 开启新的Activity,关闭当前Activity
//            context.startActivity(intent);
            LogUtils.i(position+"////");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ContextCompat.getColor(context, R.color.color_main));
        }
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface OnAuthorizationOnclickListener {
        void onNoClick();

        void onYesClick();
    }
}
