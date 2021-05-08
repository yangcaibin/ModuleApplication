package com.yanda.moduleapplication;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yanda.module_base.base.BaseActivity;
import com.yanda.module_base.router.RouterActivityPath;
import com.yanda.module_base.utils.RouterUtil;

import butterknife.BindView;

@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity {
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.courseButton)
    Button courseButton;
    @BindView(R.id.examButton)
    Button examButton;
    private boolean isDown = false;
    private int height, minHeight = 500, maxHeight = 1800;
    private int indexY;
    private RelativeLayout.LayoutParams layoutParams;

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        ViewTreeObserver observer = linearLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                height = linearLayout.getHeight();
                layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
            }
        });

        button.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:  //按下
                    int[] location = new int[2];
                    linearLayout.getLocationOnScreen(location);
                    indexY = (int) (location[1] + event.getY());
                    isDown = true;
                    break;
                case MotionEvent.ACTION_UP:  //抬起
                    height = linearLayout.getHeight();
                    isDown = false;
                    break;
            }
            return false;
        });
    }

    @Override
    public void addOnClick() {
        courseButton.setOnClickListener(this);
        examButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.courseButton:  //跳课程
                RouterUtil.launchCourse();
                break;
            case R.id.examButton:  //跳考试
                RouterUtil.launchExam();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int currentHeight;
                if (isDown) {
                    if (ev.getY() > indexY) {
                        //说明下拉
                        currentHeight = height - (int) (ev.getY() - indexY);
                        if (currentHeight >= minHeight) {
                            layoutParams.height = currentHeight;
                        }
                    } else if (ev.getY() < indexY) {
                        //说明上拉
                        currentHeight = height + (int) (indexY - ev.getY());
                        if (currentHeight <= maxHeight) {
                            layoutParams.height = currentHeight;
                        }
                    }
                    linearLayout.setLayoutParams(layoutParams);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}