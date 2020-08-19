package com.dabin.baseframe;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Dabin on 2020/8/11 0011.
 */
public abstract class BaseActivity <T extends BaseActivity.BasePresenter>extends AppCompatActivity {

    protected Activity mActivity;
    protected T mMyPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setPresenter();
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

    public abstract void setPresenter();

    public abstract class BasePresenter {

    }

}

