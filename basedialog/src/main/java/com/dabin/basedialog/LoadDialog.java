package com.dabin.basedialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Dabin on 2020/8/5 0005.
 * 加载对话框
 */
public class LoadDialog extends BaseDialog {

    private TextView mTvLoad;
    private ProgressBar mPbLoad;

    public LoadDialog(Context context, Location location, boolean cancelable,
                      View.OnClickListener onClickListener) {
        super(context, location);
        init(cancelable, onClickListener);
    }

    private void init(boolean cancelable, View.OnClickListener onClickListener) {
        FrameLayout midView = setMidView(R.layout.dialog_load, !cancelable).getMidView();
        setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (cancelable)
            setNegativeButton(onClickListener);
        mTvLoad = midView.findViewById(R.id.tv_load);
        mPbLoad = midView.findViewById(R.id.pb_load);
    }

    /**
     * 更新进度条
     *
     * @param progress p
     */
    public void update(int progress) {
        mPbLoad.setProgress(progress);
        setText(progress);
        if(progress == 100) {
            dismiss();
            Log.i("LoadDialog", "----------------加载完成---------------");
        }
    }

    /**
     * 设置进度条文字
     *
     * @param progress p
     */
    private void setText(int progress) {
        String process = String.valueOf(progress);
        mTvLoad.setText(mContext.getString(R.string.loading, process));
    }

}
