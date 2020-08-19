package com.dabin.baseframe;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Dabin on 2020/8/11 0011.
 */
public class BaseActivity extends AppCompatActivity {

    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        initView();
        initDate();
        initEvent();

    }

    protected void initEvent() {
    }

    protected void initDate() {
    }

    protected void initView() {
    }



}

