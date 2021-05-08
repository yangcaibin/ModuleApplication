package com.yanda.moduleapplication.start;

import android.Manifest;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.yanda.module_base.base.BaseActivity;
import com.yanda.module_base.dialog.AgreementDialog;
import com.yanda.module_base.utils.SPKey;
import com.yanda.module_base.utils.SPUtils;
import com.yanda.moduleapplication.MainActivity;
import com.yanda.moduleapplication.R;
import com.yanda.moduleapplication.start.adapter.ViewPagerAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 作者：caibin
 * 时间：2020/12/18 15:07
 * 类说明：引导页
 */
public class GuiDeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.experience)
    Button experience;
    private List<View> viewList; // 存放引导页的布局
    private boolean isHl;  //是否忽略了通知的权限
    private String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};  //需要开启的权限
    private int RC_LOCATION_CONTACTS_PERM = 124;  //权限码
    private Bitmap bitmap;
    private AgreementDialog agreementDialog;

    @Override
    protected int initContentView() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        setFitsSystemWindows(null);
        //显示提示用户协议和隐私政策的对话框
        showAgreementDialog();
        //设置数据和适配器
        setViewAdapter();
    }

    @Override
    public void addOnClick() {
        viewPager.addOnPageChangeListener(this);
        experience.setOnClickListener(this);  //立即体验的按钮
    }

    //显示提示用户协议和隐私政策的对话框
    private void showAgreementDialog() {
        agreementDialog = new AgreementDialog(this);
        agreementDialog.setOnAuthorizationOnclickListener(new AgreementDialog.OnAuthorizationOnclickListener() {
            @Override
            public void onNoClick() {
                int pid = Process.myPid();
                Process.killProcess(pid);
                System.exit(0);
            }

            @Override
            public void onYesClick() {
                agreementDialog.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR
                    ActivityCompat.requestPermissions(GuiDeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA}, 123);
                }
            }
        });
        agreementDialog.show();
    }

    //设置数据和适配器
    private void setViewAdapter() {
        viewList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageView image = new ImageView(this);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            switch (i) {
                case 0:
                    //获取资源图片
                    bitmap = decodeBitmapResource(getResources(), R.drawable.guide_one);
                    image.setImageBitmap(this.bitmap);
                    break;
                case 1:
                    bitmap = decodeBitmapResource(getResources(), R.drawable.guide_two);
                    image.setImageBitmap(this.bitmap);
                    break;
                case 2:
                    bitmap = decodeBitmapResource(getResources(), R.drawable.guide_three);
                    image.setImageBitmap(this.bitmap);
                    break;
            }
            viewList.add(image);
        }
        // 绑定适配器
        viewPager.setAdapter(new ViewPagerAdapter(viewList));
    }

    //加载图片
    public Bitmap decodeBitmapResource(Resources resources, int id) {
        Bitmap bitmap;
        InputStream is = resources.openRawResource(id);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeStream(is, null, opts);
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.experience:  //立即体验的按钮
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (EasyPermissions.hasPermissions(this, permission)) {
                        startSelectMajorActivity();
                    } else {
                        EasyPermissions.requestPermissions(this, getString(R.string.permission),
                                RC_LOCATION_CONTACTS_PERM, permission);
                    }
                } else {
                    startSelectMajorActivity();
                }
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        if (position == 2) {
            experience.setVisibility(View.VISIBLE);
        } else {
            experience.setVisibility(View.GONE);
        }
    }

    //进入选择专业的方法
    private void startSelectMajorActivity() {
        SPUtils.put(this, SPKey.ISFRIST, false);
        startActivity(MainActivity.class);
        GuiDeActivity.this.finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //同意权限
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    //拒绝权限
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.permission_warn))
                    .setRationale(getResources().getString(R.string.open_storage_permission))
                    .setPositiveButton(getResources().getString(R.string.go_open))
                    .build()
                    .show();
        }
    }
}
