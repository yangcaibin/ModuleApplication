package com.yanda.moduleapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yanda.module_base.base.BaseFragment;
import com.yanda.module_base.utils.LogUtils;
import com.yanda.module_base.utils.RouterUtil;
import com.yanda.moduleapplication.ChildViewPagerAdapter;
import com.yanda.moduleapplication.KotlinActivity;
import com.yanda.moduleapplication.LinkMovementMethodExt;
import com.yanda.moduleapplication.MainActivity;
import com.yanda.moduleapplication.MessageSpan;
import com.yanda.moduleapplication.R;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;

/**
 * 作者：caibin
 * 时间：2021/5/18 11:40
 * 类说明：
 */
public class MyFragment extends BaseFragment implements MainActivity.MyTouchListener {
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.courseButton)
    Button courseButton;
    @BindView(R.id.examButton)
    Button examButton;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.kotlinButton)
    Button kotlinButton;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    private boolean isDown = false;
    //height:子布局初始的高度  minHeight:子布局最小的高度  maxHeight:子布局最大高度
    private int height, minHeight = 500, maxHeight = 1800;
    //indexY:按下按钮时的Y的坐标
    private int indexY, currentPosition;
    private RelativeLayout.LayoutParams layoutParams;
    private String textStr = "我是第 个fragment 阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
            "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦...";

    private static final String IMAGE = "<img title=\"\" src=\"http://g.hiphotos.baidu.com/image/pic/item/241f95cad1c8a7866f726fe06309c93d71cf5087.jpg\"  style=\"cursor: pointer;\"><br><br>" +
            "<img src=\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fa3.att.hudong.com%2F61%2F98%2F01300000248068123885985729957.jpg&refer=http%3A%2F%2Fa3.att.hudong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1624441118&t=0b83b206fbed1aa2e706e1c75730e77d\" width=\"1080\" height=\"1620\"/><a href=\"http://www.baidu.com\">baidu</a>" +
            "hello asdkjfgsduk <a href=\"http://www.jd.com\">jd</a>";

    public static MyFragment newInstance(int currentPosition) {
        MyFragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("currentPosition", currentPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity) context).registerMyTouchListener(this);
    }

    @Override
    public View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_my_fragment, container, false);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            currentPosition = arguments.getInt("currentPosition");
            textView.setText(Html.fromHtml(IMAGE, new MyImageGet(), null));
            textView.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));
//            textView.setText("我是第" + currentPosition + "个fragment 阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦" +
//                    "阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦阿斯顿发送到发送到发士大夫撒旦...");
        }
        //获取屏幕高度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        //获取屏幕的款和高
        maxHeight = dm.heightPixels;  //最大高度设置为屏幕的高度

        button.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                button.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                minHeight = button.getHeight();
            }
        });

        ViewTreeObserver observer = linearLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                height = linearLayout.getHeight();
                layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();

                RelativeLayout.LayoutParams scrollParams = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
                scrollParams.bottomMargin = layoutParams.height - button.getHeight();
                scrollView.setLayoutParams(scrollParams);
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

        viewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        viewPager.setAdapter(new ChildViewPagerAdapter(getChildFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
    }

    private Handler handler = new Handler(Objects.requireNonNull(Looper.myLooper())) {
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == 200) {
                MessageSpan ms = (MessageSpan) msg.obj;
                Object[] spans = (Object[]) ms.getObj();
                final ArrayList<String> list = new ArrayList<>();
                for (Object span : spans) {
                    if (span instanceof ImageSpan) {
                        showToast("点击了啊啊啊");
                        LogUtils.i(((ImageSpan) span).getSource());
                    }
                }
            }
        }
    };

    @Override
    public void addOnClick() {
        courseButton.setOnClickListener(this);
        examButton.setOnClickListener(this);
        kotlinButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.courseButton:  //跳课程
                RouterUtil.launchCourse();
                break;
            case R.id.examButton:  //跳考试
                RouterUtil.launchExam();
                break;
            case R.id.kotlinButton:
                startActivity(KotlinActivity.class);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
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
                    RelativeLayout.LayoutParams scrollParams = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
                    scrollParams.bottomMargin = layoutParams.height - button.getHeight();
                    scrollView.setLayoutParams(scrollParams);
                }
                break;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i("销毁了。。" + currentPosition);
        /** 触摸事件的注销 */
        ((MainActivity) Objects.requireNonNull(this.getActivity())).unRegisterMyTouchListener(this);
    }

    class MyImageGet implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(String source) {
            final LevelListDrawable drawable = new LevelListDrawable();
            Glide.with(Objects.requireNonNull(getContext())).asDrawable().load(source).into(new CustomTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    drawable.addLevel(1, 1, resource);
                    drawable.setBounds(0, 0, resource.getIntrinsicWidth(), resource.getMinimumHeight());
                    drawable.setLevel(1);
                    textView.invalidate();
                    textView.setText(textView.getText());
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
//            Glide.with(Objects.requireNonNull(getContext())).asBitmap().load(source).into(new CustomTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                    BitmapDrawable bitmapDrawable = new BitmapDrawable(null, resource);
//                    drawable.addLevel(1, 1, bitmapDrawable);
//                    drawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
//                    drawable.setLevel(1);
//                    textView.invalidate();
//                    textView.setText(textView.getText());
//                }
//
//                @Override
//                public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                }
//            });
            return drawable;
        }
    }
}
