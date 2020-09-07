package com.dabin.baseframe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dabin on 2020/8/19 0019.
 */
public abstract class BaseFragment <T extends BaseFragment.BasePresenter> extends Fragment implements View.OnClickListener {

    protected Activity mActivity;
    protected T mMyPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        setPresenter();
        super.onAttach(context);
        this.mActivity = getActivity();
//        System.out.println("onAttach-------------------!!!!"+getClass().getName());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        System.out.println("onCreate-------------------!!!!"+getClass().getName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        System.out.println("onCreateView-------------------!!!!"+getClass().getName());
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
//        System.out.println("onActivityCreated-------------------!!!!"+getClass().getName());
    }

    @Override
    public void onStart() {
        super.onStart();
//        System.out.println("onStart-------------------!!!!"+getClass().getName());
    }


    @Override
    public void onResume() { // 网络数据调用
        super.onResume();
        initData();
//        System.out.println("onResume-------------------!!!!"+getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
//        System.out.println("onPause-------------------!!!!"+getClass().getName());
    }

    @Override
    public void onStop() {
        super.onStop();
//        System.out.println("onStop-------------------!!!!"+getClass().getName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        System.out.println("onDestroyView-------------------!!!!"+getClass().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        System.out.println("onDestroy-------------------!!!!"+getClass().getName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        System.out.println("onDetach-------------------!!!!"+getClass().getName());
    }

    /**
     * 初始化事件
     */
    public abstract void initEvent();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化view
     *
     * @return 返回此fragment的根控件
     */
    public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void setPresenter();

    public abstract class BasePresenter {

    }
}
