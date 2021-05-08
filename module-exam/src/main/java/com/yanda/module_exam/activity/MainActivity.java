package com.yanda.module_exam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yanda.module_base.utils.RouterUtil;
import com.yanda.module_exam.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RouterUtil.launchLogin();
    }
}
