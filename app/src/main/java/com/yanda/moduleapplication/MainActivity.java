package com.yanda.moduleapplication;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yanda.library_db.ObjectBox;
import com.yanda.library_db.entity.MyObjectBox;
import com.yanda.library_db.entity.Note;
import com.yanda.module_base.base.BaseActivity;
import com.yanda.module_base.router.RouterActivityPath;
import com.yanda.module_base.utils.LogUtils;
import com.yanda.module_base.utils.RouterUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.objectbox.Box;

@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    //    @BindView(R.id.button)
//    Button button;
//    @BindView(R.id.linearLayout)
//    LinearLayout linearLayout;
//    @BindView(R.id.courseButton)
//    Button courseButton;
//    @BindView(R.id.examButton)
//    Button examButton;
//    @BindView(R.id.textView)
//    TextView textView;
    private boolean isDown = false;
    private int height, minHeight = 500, maxHeight = 1800;
    private int indexY;
    private RelativeLayout.LayoutParams layoutParams;
    private Box<Note> noteBox;
    /**
     * 保存MyTouchListener接口的列表
     */
    private ArrayList<MyTouchListener> MyTouchListener = new ArrayList<>();

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        viewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        noteBox = ObjectBox.getBoxStore().boxFor(Note.class);

        List<Note> all = noteBox.getAll();
        if (all != null && all.size() > 0) {
            for (Note note : all) {
                LogUtils.i(note.getText());
            }
        } else {
            LogUtils.i("为空了啊啊啊");
        }

        File file = null;
        try {
            file = new File(getFilesDir(), "data.mdb");
            InputStream is = getAssets().open("data.mdb");
            FileOutputStream fos = null;

            fos = new FileOutputStream(file);

            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            is.close();

            ObjectBox.getBuilder().initialDbFile(file)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("lala", e.getMessage());
        }

        all = noteBox.getAll();
        if (all != null && all.size() > 0) {
            for (Note note : all) {
                LogUtils.i(note.getText());
            }
        } else {
            LogUtils.i("为空了啊啊啊");
        }

//        Note note = new Note();
//        note.setId(12345);
//        note.setText("自定义Id");
//        note.setCreatedAt(new Date());
//        noteBox.put(note);

//        Realm realm = RealmHelper.getRealm();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                Student student = realm.createObject(Student.class);
//                student.setName("yangcaibin");
//                student.setAge(30);
//            }
//        });
//
//
//        RealmResults<Student> allList = realm.where(Student.class).findAll();
//        if (allList!=null && allList.size()>0) {
//            for (Student student : allList) {
//                Log.i("lala",student.getName());
//            }
//        }


//        Note note = new Note();
//        note.setText("我很好");
//        note.setCreatedAt(new Date());
//        noteBox.put(note);

//        ViewTreeObserver observer = linearLayout.getViewTreeObserver();
//        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                height = linearLayout.getHeight();
//                layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
//            }
//        });
//
//        button.setOnTouchListener((v, event) -> {
//            int action = event.getAction();
//            switch (action) {
//                case MotionEvent.ACTION_DOWN:  //按下
//                    int[] location = new int[2];
//                    linearLayout.getLocationOnScreen(location);
//                    indexY = (int) (location[1] + event.getY());
//                    isDown = true;
//                    break;
//                case MotionEvent.ACTION_UP:  //抬起
//                    height = linearLayout.getHeight();
//                    isDown = false;
//                    break;
//            }
//            return false;
//        });
    }

    @Override
    public void addOnClick() {
//        courseButton.setOnClickListener(this);
//        examButton.setOnClickListener(this);
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
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : MyTouchListener) {
            listener.onTouchEvent(ev);
        }
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int currentHeight;
//                if (isDown) {
//                    if (ev.getY() > indexY) {
//                        //说明下拉
//                        currentHeight = height - (int) (ev.getY() - indexY);
//                        if (currentHeight >= minHeight) {
//                            layoutParams.height = currentHeight;
//                        }
//                    } else if (ev.getY() < indexY) {
//                        //说明上拉
//                        currentHeight = height + (int) (indexY - ev.getY());
//                        if (currentHeight <= maxHeight) {
//                            layoutParams.height = currentHeight;
//                        }
//                    }
//                    linearLayout.setLayoutParams(layoutParams);
//                }
//                break;
//        }
        return super.dispatchTouchEvent(ev);
    }

    //自定义事件的接口
    public interface MyTouchListener {
        boolean onTouchEvent(MotionEvent ev);
    }

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     */
    public void registerMyTouchListener(MyTouchListener listener) {
        MyTouchListener.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        MyTouchListener.remove(listener);
    }
}