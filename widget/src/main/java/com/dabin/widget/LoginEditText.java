package com.dabin.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dabin.widget.utils.DensityUtil;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;


import java.io.Serializable;


/**
 * 登陆输入框
 */
public class LoginEditText extends RelativeLayout implements Serializable, View.OnClickListener
        , CompoundButton.OnCheckedChangeListener {

    private EditText mEditText;
    private ImageView mIconView;
    private ViewGroup mOperate;
    private CheckBox mCheckBox;
    private ImageView mDeleteButton;

    private Context mContext;
    private String mText;
    private String mHint;
    private int mTextColor = Color.BLACK, mTextColorHint = Color.GRAY;
    private int mInputHintSize = 14;
    private int mMaxLines = 1;
    private int mMaxLength = 50;
    private int mDefPaddingLeft = 6, mDefPaddingRight = 0;
    private float mTextSize = -1;
    private float mPaddingLeft, mPaddingRight;
    private int mInputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

    private Drawable mBackground;
    private Drawable mDrawableLeft;
    private boolean isInputPassword = false;

    public LoginEditText(Context context) {
        super(context);
        init(null);
    }

    public LoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LoginEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mContext = getContext();
        initView();
        initEvent();

        mInputHintSize = DensityUtil.dip2px(mContext, mInputHintSize);
        mDefPaddingLeft = DensityUtil.dip2px(mContext, mDefPaddingLeft);
        mDefPaddingRight = DensityUtil.dip2px(mContext, mDefPaddingRight);
        initAttributeSet(attrs);

        //设置Hint大小
        mEditText.setText(mText);
        mEditText.setTextColor(mTextColor);
        //设置Hint
        mEditText.setHintTextColor(mTextColorHint);
        mEditText.setHint(mHint);
        //设置Hint大小
        setHintSize(mEditText, mHint, mInputHintSize, false);

        if (mTextSize > -1) {
            mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        }
        //设置最大长度
        setMaxLength(mMaxLength);
        //设置最大行数
        setMaxLines(mMaxLines);
        //是否设置了输入密码模式
        setInputPassword(isInputPassword());
        //是否设置DrawableLeft
        setDrawableLeft(mDrawableLeft);
        updatePadding();
        setBackground(mBackground);

        mEditText.addTextChangedListener(new MyTextWatcher());
        mIconView.setVisibility(getDrawableLeft() != null ? View.VISIBLE : View.GONE);
        mDeleteButton.setVisibility(getInputText().length() > 0 ? View.VISIBLE : View.GONE);

        initOperateLocation();//初始化右边操作按钮的位置
    }

    private void initEvent() {
        mCheckBox.setOnCheckedChangeListener(this);
        mDeleteButton.setOnClickListener(this);
    }

    private void initView() {
        addView(View.inflate(mContext, R.layout.edittext_login, null));
        mEditText = findViewById(R.id.et_input);
        mIconView = findViewById(R.id.iv_drawable_left);
        mOperate = findViewById(R.id.ll_operate);
        mCheckBox = findViewById(R.id.cb_input_visible);
        mDeleteButton = findViewById(R.id.ib_delete_input);
    }

    private void initAttributeSet(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs,
                    R.styleable.LoginEditText);
            mText = typedArray.getString(R.styleable.LoginEditText_text);
            mHint = typedArray.getString(R.styleable.LoginEditText_inputHint);
            mTextColor = typedArray.getColor(R.styleable.LoginEditText_textColor, mTextColor);
            mTextColorHint = typedArray.getColor(R.styleable.LoginEditText_textColorHint,
                    mTextColorHint);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.LoginEditText_textSize,
                    (int) mTextSize);
            mInputHintSize =
                    typedArray.getDimensionPixelSize(R.styleable.LoginEditText_inputHintSize,
                            mInputHintSize);
            mMaxLength = typedArray.getInteger(R.styleable.LoginEditText_maxLength, mMaxLength);
            mMaxLines = typedArray.getInteger(R.styleable.LoginEditText_maxLines, mMaxLines);
            mPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.LoginEditText_paddingLeft
                    , mDefPaddingLeft);
            mPaddingRight =
                    typedArray.getDimensionPixelSize(R.styleable.LoginEditText_paddingRight,
                            mDefPaddingRight);
            mInputType = typedArray.getInteger(R.styleable.LoginEditText_inputType, mInputType);
            mDrawableLeft = typedArray.getDrawable(R.styleable.LoginEditText_drawableLeft);
            isInputPassword = typedArray.getBoolean(R.styleable.LoginEditText_inputPassword,
                    isInputPassword);
            mBackground = typedArray.getDrawable(R.styleable.LoginEditText_background);
            typedArray.recycle();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setPasswordVisibility(mEditText, isChecked);//设置密码是否可见
    }

    /**
     * 删除输入文本
     */
    @Override
    public void onClick(View v) {
        setInputText("");
    }

    /**
     * 监听输入文本改变
     */
    private class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (textWatcher != null) {
                textWatcher.beforeTextChanged(s, start, count, after);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (textWatcher != null) {
                textWatcher.onTextChanged(s, start, before, count);
            }
            textChanged(s);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (textWatcher != null) {
                textWatcher.afterTextChanged(s);
            }
        }
    }

    private void textChanged(CharSequence s) {
        //有输入数据才显示删除按钮，没有则隐藏
        boolean isShow = s != null && s.length() > 0;
        int isVisibility = isShow ? View.VISIBLE : View.GONE;
        if (mDeleteButton.getVisibility() != isVisibility) {
            setViewVisibility(mDeleteButton, isShow);
        }
    }

    private TextWatcher textWatcher;

    public void addTextChangedListener(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    /**
     * 设置View是否可见
     */
    void setViewVisibility(View view, boolean isVisibility) {
        if (view != null) {
            view.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置密码是否可见
     */
    public boolean setPasswordVisibility(EditText editText, boolean isShowPassword) {
        boolean result = false;
        if (editText != null) {
            int inputType;
            if (isShowPassword) {//显示密码
                inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
            } else {
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }
            editText.setInputType(inputType);
            editText.setSelection(editText.getText().toString().length());
            result = true;
        }
        return result;
    }

    /**
     * 初始化右边操作按钮的位置
     */
    private void initOperateLocation() {
        LayoutParams layoutParams = (LayoutParams) mOperate.getLayoutParams();
        layoutParams.rightMargin = getPaddingRight();
    }

    public CharSequence getInputText() {
        return mEditText.getText();
    }

    public void setInputText(CharSequence text) {
        mEditText.setText(text);
    }

    /**
     * Convenience for {@link Selection#setSelection(Spannable, int, int)}.
     */
    public void setSelection(int start, int stop) {
        mEditText.setSelection(start, stop);
    }

    /**
     * Convenience for {@link Selection#setSelection(Spannable, int)}.
     */
    public void setSelection(int index) {
        mEditText.setSelection(index);
    }

    public CharSequence getInputHint() {
        return mEditText.getHint();
    }

    public void setInputHint(@StringRes int id) {
        setInputHint(mContext.getResources().getString(id));
    }

    public void setInputHint(CharSequence text) {
        mEditText.setHint(text);
    }

    public ColorStateList getTextColor() {
        return mEditText.getTextColors();
    }

    public void setTextColor(@ColorInt int color) {
        mEditText.setTextColor(color);
    }

    public void setTextColor(ColorStateList color) {
        mEditText.setTextColor(color);
    }

    public ColorStateList getHintTextColor() {
        return mEditText.getHintTextColors();
    }

    public void setHintTextColor(@ColorInt int color) {
        mEditText.setHintTextColor(color);
    }

    public void setHintTextColor(ColorStateList color) {
        mEditText.setHintTextColor(color);
    }

    public float getTextSize() {
        return mEditText.getTextSize();
    }

    public void setTextSize(int size) {
        mEditText.setTextSize(size);
    }

    public float getMaxLength() {
        return mMaxLength;
    }

    public void setMaxLength(int maxLength) {
        this.mMaxLength = maxLength;
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public void setMaxLines(int maxLines) {
        this.mMaxLines = maxLines;
        mEditText.setMaxLines(maxLines);
    }

    public float getMaxLines() {
        return mMaxLines;
    }

    public void setPaddingLeft(int value) {
        mPaddingLeft = value;
        updatePadding();
    }

    public int getPaddingLeft() {
        return (int) mPaddingLeft;
    }

    public void setPaddingTop(int value) {
        mEditText.setPadding(getPaddingLeft(), DensityUtil.dip2px(mContext, value),
                getPaddingRight(), getPaddingBottom());
    }

    public int getPaddingTop() {
        return mEditText.getPaddingTop();
    }

    public void setPaddingRight(int value) {
        mPaddingRight = value;
        updatePadding();
        initOperateLocation();//初始化右边操作按钮的位置
    }

    public int getPaddingRight() {
        return (int) mPaddingRight;
    }

    public void setPaddingBottom(int value) {
        mEditText.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(),
                DensityUtil.dip2px(mContext, value));
    }

    public int getPaddingBottom() {
        return mEditText.getPaddingBottom();
    }

    public void setInputType(int value) {
        mEditText.setInputType(value);
    }

    public int getInputType() {
        return mEditText.getInputType();
    }

    public Drawable getBackground() {
        return mEditText.getBackground();
    }

    public void setBackground(Drawable background) {
        this.mBackground = background;
        if (mEditText != null && background != null) {
            mEditText.setBackgroundDrawable(background);
        }
    }

    public void setBackground(@DrawableRes int background) {
        setBackground(mContext.getResources().getDrawable(background));
    }

    /**
     * 设置DrawableLeft
     */
    public void setDrawableLeft(int res) {
        setDrawableLeft(mContext.getResources().getDrawable(res));
    }

    /**
     * 设置DrawableLeft
     */
    public void setDrawableLeft(Drawable drawable) {
        mIconView.setImageDrawable(drawable);
        //如果设置了DrawableLeft
        if (drawable != null) {
            mIconView.setVisibility(View.VISIBLE);
            int left =
                    (int) (mIconView.getWidth() + DensityUtil.dip2px(mContext, 8 * 2) + mPaddingLeft * 2);
            if (left > mPaddingLeft) {
                mPaddingLeft = left;
            }
        }
        updatePadding();
    }

    public Drawable getDrawableLeft() {
        return mIconView.getDrawable();
    }

    /**
     * 是否设置了输入密码模式
     */
    public void setInputPassword(boolean inputPassword) {
        isInputPassword = inputPassword;
        mCheckBox.setVisibility(inputPassword ? View.VISIBLE : View.GONE);
        //如果是输入密码
        if (inputPassword) {
            mInputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }
        updatePadding();
        setInputType(mInputType);
    }

    public boolean isInputPassword() {
        return isInputPassword;
    }

    public void updatePadding() {
        int width = getOperateWidth();
        int right = getPaddingRight() + (width <= 0 ? DensityUtil.dip2px(mContext, 35) : width);
        //getOperateWidth();
        mEditText.setPadding(getPaddingLeft(), getPaddingTop(), right, getPaddingBottom());
    }

    private int getOperateWidth() {
        return getViewWidth(mOperate);
    }

    public int getViewWidth(View view) {
        int width = -1;
        if (view != null) {
            width = view.getWidth();
            if (width <= 0) {
                view.measure(0, 0);
                width = view.getMeasuredWidth();
            }
        }
        return width;
    }

    public void setInputEnabled(boolean enabled) {
        mEditText.setEnabled(enabled);
    }

    public boolean isInputEnabled() {
        return mEditText.isEnabled();
    }

    /**
     * 设置Hint大小
     */
    private void setHintSize(EditText editText, String hint, int size, boolean dip) {
        if (editText == null || TextUtils.isEmpty(hint) || size <= 0) {
            return;
        }
        SpannableString ss = new SpannableString(hint);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, dip);//设置字体大小 true表示单位是dp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }
}
