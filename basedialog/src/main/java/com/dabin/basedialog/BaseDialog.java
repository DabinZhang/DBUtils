package com.dabin.basedialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

/**
 * Created by Dabin on 2020/3/20 0020.
 * Extends Huang
 * 消息对话框，显示消息，选择内容。
 */
public class BaseDialog extends Dialog {

    private Context mContext;
    private ViewGroup mRootView;
    private ViewGroup mLlOperate;
    private TextView mBtnNegative;
    private TextView mBtnPositive;
    private TextView mBtnNext;
    private FrameLayout mFlContent;
    private TextView mTvTitle;
    private boolean mDismDefBtn = false;

    public BaseDialog(Context context) {
        super(context, R.style.DialogFullscreen);
        init(context, Location.CENTER);
    }

    public BaseDialog(Context context, BaseDialog.Location location) {
        super(context, R.style.DialogFullscreen);
        init(context, location);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context, Location.CENTER);
    }

    public BaseDialog(Context context, int themeResId, BaseDialog.Location location) {
        super(context, themeResId);
        init(context, location);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context, Location.CENTER);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener,
                         BaseDialog.Location location) {
        super(context, cancelable, cancelListener);
        init(context, location);
    }


    private void init(Context context, BaseDialog.Location location) {
        this.mContext = context;
        Window window = this.getWindow();
        switch (location) {
            case BOTTOM:
                this.mRootView = (ViewGroup) View.inflate(this.mContext, R.layout.dialog_base_top, null);
                if (window != null) {
                    window.setGravity(Gravity.BOTTOM);
                }
                break;
            case TOP:
                this.mRootView = (ViewGroup) View.inflate(this.mContext, R.layout.dialog_base_top, null);
                if (window != null) {
                    window.setGravity(Gravity.TOP);
                }
                break;
            case CENTER:
            default:
                this.mRootView = (ViewGroup) View.inflate(this.mContext, R.layout.dialog_base, null);
                if (window != null) {
                    window.setGravity(Gravity.CENTER);
                }
                break;
        }

        this.mFlContent = this.mRootView.findViewById(R.id.fl_content);
        this.mTvTitle = this.mRootView.findViewById(R.id.tv_title);
        this.mLlOperate = this.mRootView.findViewById(R.id.ll_operate);
        this.mBtnNegative = this.mRootView.findViewById(R.id.btn_cancel);
        this.mBtnPositive = this.mRootView.findViewById(R.id.btn_enter);
        this.mBtnNext = this.mRootView.findViewById(R.id.btn_next);
        this.setContentView(this.mRootView);
        this.setCanceledOnTouchOutside(false);
        this.mTvTitle.setVisibility(View.GONE);
        this.mFlContent.setVisibility(View.GONE);
        this.mBtnNegative.setVisibility(View.GONE);
        this.mBtnPositive.setVisibility(View.GONE);
        this.mBtnNext.setVisibility(View.GONE);
        this.mLlOperate.setVisibility(View.GONE);
    }

    public ViewGroup getRootView() {
        return this.mRootView;
    }

    public BaseDialog setView(View view, boolean dismDefBtn) {
        if (view != null) {
            this.mDismDefBtn = dismDefBtn;
            this.mFlContent.setVisibility(View.VISIBLE);
            this.mFlContent.addView(view);
        }
        return this;
    }

    public BaseDialog setView(@LayoutRes int layoutResID, boolean dismDefBtn) {
        View view = View.inflate(this.mContext, layoutResID, null);
        if (view != null) {
            this.mDismDefBtn = dismDefBtn;
            this.mFlContent.setVisibility(View.VISIBLE);
            this.mFlContent.addView(view);
        }
        return this;
    }

    public BaseDialog setRootView(ViewGroup view) {
        if (view != null) {
            this.mDismDefBtn = true;
            this.mRootView = view;
            this.setContentView(view);
        }
        return this;
    }

    public BaseDialog setRootView(@LayoutRes int layoutResID) {
        ViewGroup view = (ViewGroup) View.inflate(this.mContext, layoutResID, null);
        if (view != null) {
            this.mDismDefBtn = true;
            this.mRootView = view;
            this.setContentView(view);
        }
        return this;
    }

    public BaseDialog setLayoutParams(int width, int height) {
        Window window = this.getWindow();
        if (window != null) {
            window.setLayout(width, height);
        }
        return this;
    }

    public BaseDialog setGravity(int gravity) {
        Window window = this.getWindow();
        if (window != null) {
            window.setGravity(gravity);
        }
        return this;
    }

    public BaseDialog setTitleName(@StringRes int stringResID) {
        this.setTitleName(this.mContext.getResources().getString(stringResID));
        return this;
    }

    public BaseDialog setTitleName(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            this.mTvTitle.setVisibility(View.VISIBLE);
            this.mTvTitle.setText(title);
        }
        return this;
    }

    public String getTitleName() {
        return this.mTvTitle.getText().toString();
    }

    public BaseDialog setTitleSize(float size) {
        this.mTvTitle.setTextSize(size);
        return this;
    }

    public BaseDialog setTitleGravity(int gravity) {
        this.mTvTitle.setGravity(gravity);
        return this;
    }

    public BaseDialog setNegativeButton(View.OnClickListener onClickListener) {
        return this.setNegativeButton(R.string.cancel, onClickListener);
    }

    public BaseDialog setNegativeButton(@StringRes int stringRes,
                                        View.OnClickListener onClickListener) {
        return this.setNegativeButton(this.mContext.getResources().getString(stringRes),
                onClickListener);
    }

    public BaseDialog setNegativeButton(CharSequence str, View.OnClickListener onClickListener) {
        return this.setNegativeButton(str, Color.GRAY, onClickListener);
    }

    public BaseDialog setNegativeButton(CharSequence str, @ColorInt int color,
                                        View.OnClickListener onClickListener) {
        if (this.mDismDefBtn) {
            throw new RuntimeException("Already there is no this button!");
        } else {
            if (!TextUtils.isEmpty(str)) {
                this.mBtnNegative.setText(str);
            }
            this.mBtnNegative.setTextColor(color);
            this.mLlOperate.setVisibility(View.VISIBLE);
            this.mBtnNegative.setVisibility(View.VISIBLE);
            this.mBtnNegative.setOnClickListener(new MyOnClickListener(onClickListener));
            return this;
        }
    }

    public TextView getNegativeButton() {
        return this.mBtnNegative;
    }

    public TextView getPositiveButton() {
        return this.mBtnPositive;
    }

    public BaseDialog setPositiveButton(View.OnClickListener onClickListener) {
        return this.setPositiveButton(R.string.enter, onClickListener);
    }

    public BaseDialog setPositiveButton(@StringRes int stringRes,
                                        View.OnClickListener onClickListener) {
        return this.setPositiveButton(stringRes, onClickListener, false);
    }

    public BaseDialog setPositiveButton(CharSequence str, View.OnClickListener onClickListener) {
        return this.setPositiveButton(str, onClickListener, false);
    }

    public BaseDialog setPositiveButton(@StringRes int stringRes,
                                        View.OnClickListener onClickListener
            , boolean isNativeClick) {
        return this.setPositiveButton(this.mContext.getResources().getString(stringRes),
                onClickListener, isNativeClick);
    }

    public BaseDialog setPositiveButton(CharSequence str, View.OnClickListener onClickListener,
                                        boolean isNativeClick) {
        return this.setPositiveButton(str, Color.BLACK, onClickListener, isNativeClick);
    }

    public BaseDialog setPositiveButton(CharSequence str, @ColorInt int color,
                                        View.OnClickListener onClickListener,
                                        boolean isNativeClick) {
        if (this.mDismDefBtn) {
            throw new RuntimeException("Already there is no this button!");
        } else {
            if (!TextUtils.isEmpty(str)) {
                this.mBtnPositive.setText(str);
            }

            this.mBtnPositive.setTextColor(color);
            this.mBtnPositive.setVisibility(View.VISIBLE);
            this.mLlOperate.setVisibility(View.VISIBLE);
            if (isNativeClick) {
                if (onClickListener != null) {
                    this.mBtnPositive.setOnClickListener(onClickListener);
                }
            } else {
                this.mBtnPositive.setOnClickListener(new BaseDialog.MyOnClickListener(onClickListener));
            }

            return this;
        }
    }

    public BaseDialog setNextButton(View.OnClickListener onClickListener) {
        return this.setNextButton(R.string.next, onClickListener);
    }

    public BaseDialog setNextButton(@StringRes int stringRes,
                                    View.OnClickListener onClickListener) {
        return this.setNextButton(stringRes, onClickListener, false);
    }

    public BaseDialog setNextButton(CharSequence str, View.OnClickListener onClickListener) {
        return this.setNextButton(str, onClickListener, false);
    }

    public BaseDialog setNextButton(@StringRes int stringRes, View.OnClickListener onClickListener,
                                    boolean isNativeClick) {
        return this.setNextButton(this.getContext().getResources().getString(stringRes),
                onClickListener, isNativeClick);
    }

    public BaseDialog setNextButton(CharSequence str, View.OnClickListener onClickListener,
                                    boolean isNativeClick) {
        return this.setNextButton(str, Color.BLACK, onClickListener, isNativeClick);
    }

    public BaseDialog setNextButton(CharSequence str, @ColorInt int color,
                                    View.OnClickListener onClickListener, boolean isNativeClick) {
        if (this.mDismDefBtn) {
            throw new RuntimeException("Already there is no this button!");
        } else {
            if (!TextUtils.isEmpty(str)) {
                this.mBtnNext.setText(str);
            }

            this.mBtnNext.setTextColor(color);
            this.mLlOperate.setVisibility(View.VISIBLE);
            this.mBtnNext.setVisibility(View.VISIBLE);
            if (isNativeClick) {
                if (onClickListener != null) {
                    this.mBtnNext.setOnClickListener(onClickListener);
                }
            } else {
                this.mBtnNext.setOnClickListener(new BaseDialog.MyOnClickListener(onClickListener));
            }
            return this;
        }
    }

    public BaseDialog setAnimatorSet() {
        this.setAnimatorSet(BaseDialog.AnimType.PROMPT);
        return this;
    }

    public BaseDialog setAnimatorSet(BaseDialog.AnimType type) {
        switch (type) {
            case WARNING:
                BaseDialog.AnimatorSetUtil.startWarningAnimatorSet(this.getRootView());
                break;
            case PROMPT:
                BaseDialog.AnimatorSetUtil.startPromptAnimatorSet(this.getRootView());
                break;
        }
        return this;
    }

    public BaseDialog setAnim(BaseDialog.AnimType type) {


        switch (type) {
            case WARNING:
                BaseDialog.AnimatorSetUtil.startWarningAnimatorSet(this.getRootView());
                break;
            case PROMPT:
                BaseDialog.AnimatorSetUtil.startPromptAnimatorSet(this.getRootView());
                break;
            case TOPDOWN:
                this.getWindow().setWindowAnimations(R.style.DialogTop);
                break;
            case BOTTOMUP:
                this.getWindow().setWindowAnimations(R.style.DialogBottom);
                break;
        }


        return this;
    }

    private static class AnimatorSetUtil {
        private AnimatorSetUtil() {
        }

        private static void startWarningAnimatorSet(View view) {
            if (view != null) {
                float max = 3.0F;
                float normal = 0.0F;
                float min = -3.0F;
                float maxScale = 1.0F;
                float normalScale = 0.98F;
                float minScale = 0.95F;
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha",
                        normal, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F),
                        ObjectAnimator.ofFloat(view, "scaleX", normalScale, minScale,
                                minScale, maxScale, maxScale, maxScale, maxScale, maxScale,
                                maxScale, normalScale), ObjectAnimator.ofFloat(view, "scaleY",
                                normalScale, minScale, minScale, maxScale, maxScale, maxScale
                                , maxScale, maxScale, maxScale, normalScale),
                        ObjectAnimator.ofFloat(view, "rotation", normal, min, min,
                                max, min, max, min, max, min, normal));
                animatorSet.setDuration(700L);
                animatorSet.start();
            }
        }

        private static void startPromptAnimatorSet(View view) {
            if (view != null) {
                float max = 1.0F;
                float normal = 0.98F;
                float min = 0.95F;
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha",
                        0.0F, 0.5F, 1.0F, 1.0F, 1.0F, 1.0F),
                        ObjectAnimator.ofFloat(view, "scaleX", 0.0F, max, max, min,
                                min, normal), ObjectAnimator.ofFloat(view, "scaleY",
                                0.0F, max, max, min, min, normal));
                animatorSet.setDuration(700L);
                animatorSet.start();
            }
        }
    }

    public enum AnimType {
        WARNING, PROMPT, TOPDOWN, BOTTOMUP
    }

    public enum Location {BOTTOM, CENTER, TOP}

    private class MyOnClickListener implements View.OnClickListener {
        View.OnClickListener onClickListener;

        public MyOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        public void onClick(View view) {
            BaseDialog.this.dismiss();
            if (this.onClickListener != null) {
                this.onClickListener.onClick(view);
            }

        }
    }

   /* viewById.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new BaseDialog(MainActivity.this, BaseDialog.Location.TOP)
                    .setTitleName("请输入密码")
                    .setNegativeButton(clickNegativeButton)
                    .setPositiveButton(clickPositiveButton)
                    .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setAnim(BaseDialog.AnimType.TOPDOWN)
                    .show();
        }
    });
        viewById1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new BaseDialog(MainActivity.this, BaseDialog.Location.CENTER)
                    .setTitleName("请输入密码")
                    .setNegativeButton(clickNegativeButton)
                    .setPositiveButton(clickPositiveButton)
                    .setLayoutParams((int) (getWindowManager().getDefaultDisplay().getWidth() * 0.9), ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setAnimatorSet(BaseDialog.AnimType.PROMPT)
                    .show();
        }
    });

        viewById2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new BaseDialog(MainActivity.this, BaseDialog.Location.BOTTOM)
                    .setTitleName("请输入密码")
                    .setNegativeButton(clickNegativeButton)
                    .setPositiveButton(clickPositiveButton)
                    .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setAnim(BaseDialog.AnimType.BOTTOMUP)
                    .show();
        }
    });*/


}
