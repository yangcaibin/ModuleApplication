package com.yanda.moduleapplication.start;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yanda.module_base.base.BaseActivity;
import com.yanda.module_base.entity.PosterEntity;
import com.yanda.module_base.http.Address;
import com.yanda.module_base.router.RouterActivityPath;
import com.yanda.module_base.utils.RouterUtil;
import com.yanda.moduleapplication.R;
import com.yanda.moduleapplication.http.GlideApp;

import butterknife.BindView;

/**
 * 作者：caibin
 * 时间：2018/4/28 11:05
 * 类说明：广告页
 */
@Route(path = RouterActivityPath.Main.PAGER_POSTER)
public class PosterActivity extends BaseActivity {
    @BindView(R.id.imageView)
    AppCompatImageView imageView;
    @BindView(R.id.skip)
    TextView skip;
    private PosterEntity posterEntity;  //传过来的实体
    private CountDownTimer timer;  //倒计时
    private int count = 5;

    @Override
    protected int initContentView() {
        return R.layout.activity_poster;
    }

    @Override
    public void initView() {
        posterEntity = (PosterEntity) getIntent().getSerializableExtra("entity");
        if (posterEntity != null) {
            String img_url = posterEntity.getImg_url();
            GlideApp.with(this).load(Address.IMAGE_NET + img_url)
                    .placeholder(R.drawable.start_bg)
                    .error(R.drawable.start_bg)
                    .into(imageView);
        }
        timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(
                    long millisUntilFinished) {
                count--;
                if (skip != null) {
                    skip.setText("跳转 " + count);
                }
            }

            @Override
            public void onFinish() {
                RouterUtil.launchMainActivity();
                PosterActivity.this.finish();
            }
        }.start();
    }

    @Override
    public void addOnClick() {
        imageView.setOnClickListener(this);
        skip.setOnClickListener(this);  //跳转
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.skip:  //跳转
                timer.cancel();
                timer.onFinish();
                break;
            case R.id.imageView:
                if (posterEntity != null && !TextUtils.isEmpty(posterEntity.getMore_url())) {
                    timer.cancel();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("type", "poster");
//                    bundle.putBoolean("isPoster", true);
//                    bundle.putString("url", posterEntity.getMore_url());
//                    startActivity(WebViewActivity.class, bundle);
                    PosterActivity.this.finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }
}
