package com.dabin.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.dabin.widget.interfaces.NameValueUnit;
import com.dabin.widget.utils.WidgetTextUtil;


/**
 * 一个带文字、数值和单位的水平自定义View
 */
public class HorizontaliDataItem extends View implements NameValueUnit {

    //最小宽度
    private int mMinWidth = 140, mMinHeight = 18;
    private int mTitleWidth = mMinWidth / 5 * 2, mUnitWidth = mMinWidth / 5;
    private float mDivideWidth = 0.1f; // 分割线宽度
    // 默认比重
    private int mWeightTitle = 2, mWeightValue = 2, mWeightUnit = 1;
    //View宽高
    private int mWidth, mHeight;
    //画笔
    private Paint mTitlePaint, mDividePaint, mValuePaint, mUnitPaint;
    //文字
    private String mTitle = "", mValue = "", mUnit = "";
    //是否显示分隔线
    private boolean isDrawDivideLine = true, isDrawBottomLine = false;
    //是否占位单位
    private boolean isOccupiedUnit = false;
    //默认颜色
    private int mTitleColor = Color.WHITE, mValueColor = Color.WHITE, mUnitColor = Color.WHITE;
    private int mDivideColor = Color.GRAY;
    //文字默认Size
    private int mTitleSize = 11, mValueSize = 12, mUnitSize = 8;

    public HorizontaliDataItem(Context context) {
        super(context);
        init(null);
    }

    public HorizontaliDataItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HorizontaliDataItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mMinWidth = dip2px(mMinWidth);
        mMinHeight = dip2px(mMinHeight);
        mTitleWidth = dip2px(mTitleWidth);
        mDivideWidth = dip2px(mDivideWidth);
        mUnitWidth = dip2px(mUnitWidth);

        mTitleSize = dip2px(mTitleSize);
        mValueSize = dip2px(mValueSize);
        mUnitSize = dip2px(mUnitSize);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                    R.styleable.HorizontaliDataItem);

            String title = null, valueText = null, unitText = null;
            title = typedArray.getString(R.styleable.HorizontaliDataItem_titleText);
            mTitleSize = typedArray.getDimensionPixelSize(R.styleable.HorizontaliDataItem_titleSize,
                    mTitleSize);
            mTitleColor = typedArray.getColor(R.styleable.HorizontaliDataItem_titleColor,
                    mTitleColor);

            valueText = typedArray.getString(R.styleable.HorizontaliDataItem_valueText);
            mValueSize = typedArray.getDimensionPixelSize(R.styleable.HorizontaliDataItem_valueSize,
                    mValueSize);
            mValueColor = typedArray.getColor(R.styleable.HorizontaliDataItem_valueColor,
                    mValueColor);

            unitText = typedArray.getString(R.styleable.HorizontaliDataItem_unitText);
            mUnitSize = typedArray.getDimensionPixelSize(R.styleable.HorizontaliDataItem_unitSize
                    , mUnitSize);
            mUnitColor = typedArray.getColor(R.styleable.HorizontaliDataItem_unitColor, mUnitColor);

            isDrawDivideLine =
                    typedArray.getBoolean(R.styleable.HorizontaliDataItem_bottomLineEnable,
                            isDrawDivideLine());
            isDrawBottomLine =
                    typedArray.getBoolean(R.styleable.HorizontaliDataItem_bottomLineEnable,
                            isDrawBottomLine());
            isOccupiedUnit =
                    typedArray.getBoolean(R.styleable.HorizontaliDataItem_occupiedUnitEnable,
                            isOccupiedUnit());
            mWeightTitle = typedArray.getInteger(R.styleable.HorizontaliDataItem_titleWeight,
                    mWeightTitle);
            mWeightValue = typedArray.getInteger(R.styleable.HorizontaliDataItem_valueWeight,
                    mWeightValue);
            mWeightUnit = typedArray.getInteger(R.styleable.HorizontaliDataItem_unitWeight,
                    mWeightUnit);
            typedArray.recycle();
            if (!TextUtils.isEmpty(title)) {
                this.mTitle = title;
            }
            if (!TextUtils.isEmpty(valueText)) {
                this.mValue = valueText;
            }
            if (!TextUtils.isEmpty(unitText)) {
                this.mUnit = unitText;
            }
        }
        setPadding(dip2px(2), 0, dip2px(2), 0);

        mTitlePaint = new Paint();
        mDividePaint = new Paint();
        mValuePaint = new Paint();
        mUnitPaint = new Paint();

        // 抗锯齿
        mTitlePaint.setAntiAlias(true);
        mDividePaint.setAntiAlias(true);
        mValuePaint.setAntiAlias(true);
        mUnitPaint.setAntiAlias(true);

        mTitlePaint.setColor(getTitleColor());
        mDividePaint.setColor(getDivideColor());
        mValuePaint.setColor(getValueColor());
        mUnitPaint.setColor(getUnitColor());

        mTitlePaint.setTextSize(getTitleSize());
        mValuePaint.setTextSize(getValueSize());
        mUnitPaint.setTextSize(getUnitSize());
        mDividePaint.setStrokeWidth(getDivideWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        sizeChanged();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureSize(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制标题文字
        drawTitleText(canvas);

        //绘制中间分隔线
        drawDivideLine(canvas);

        //绘制数值
        drawValue(canvas);

        //绘制单位
        drawUnit(canvas);

        //绘制底部分隔线
        drawBottomLine(canvas);
    }

    /**
     * 绘制标题文字
     */
    private void drawTitleText(Canvas canvas) {
        String text = getTitle();
        float textWidth = WidgetTextUtil.measureTextWidth(mTitlePaint, text);  //测量字符串宽度
        float x = (getTitleWidth() - textWidth) / 2 + getPaddingLeft();  //字符串左下角的点的x坐标
        int y = WidgetTextUtil.getLeftDownY(getViewHeight(), mTitlePaint);  //字符串左下角的点的y坐标
        if (!TextUtils.isEmpty(text)) {
            canvas.drawText(text, x, y, mTitlePaint);
        }
    }

    /**
     * 绘制中间分隔线
     */
    private void drawDivideLine(Canvas canvas) {
        if (isDrawDivideLine()) {
            int divideX = getTitleWidth() + getPaddingLeft();
            canvas.drawLine(divideX, 0, divideX, getViewHeight(), mDividePaint);
        }
    }

    /**
     * 绘制数值
     */
    private void drawValue(Canvas canvas) {
        //如果有单位，要计算单位的宽度
        int unitWidth = !TextUtils.isEmpty(getUnit()) ? getUnitWidth() : 0;
        //如果没有单位，要判断是否占位单位的宽度
        if (unitWidth <= 0) {
            unitWidth = isOccupiedUnit() ? getUnitWidth() : 0;
        }

        String text = getValue();
        float textWidth = WidgetTextUtil.measureTextWidth(mValuePaint, text);  //测量字符串宽度
        int otherWidth = getTitleWidth() + unitWidth + getPaddingLeft() + getPaddingRight();
        float x =
                getTitleWidth() + getPaddingLeft() + (getViewWidth() - otherWidth - textWidth) / 2;
        int y = WidgetTextUtil.getLeftDownY(getViewHeight(), mValuePaint);
        if (!TextUtils.isEmpty(text)) {
            canvas.drawText(text, x, y, mValuePaint);
        }
    }

    /**
     * 绘制单位
     */
    private void drawUnit(Canvas canvas) {
        String text = getUnit();
        float textWidth = WidgetTextUtil.measureTextWidth(mUnitPaint, text);  //测量字符串宽度
        float x =
                getViewWidth() - getUnitWidth() - getPaddingRight() + (getUnitWidth() - textWidth) / 2;
        int y = WidgetTextUtil.getLeftDownY(getViewHeight(), mUnitPaint);
        if (!TextUtils.isEmpty(text)) {
            canvas.drawText(text, x, y, mUnitPaint);
        }
    }

    /**
     * 绘制底部分隔线
     */
    private void drawBottomLine(Canvas canvas) {
        if (isDrawBottomLine()) {
            float divideY = getViewHeight() - getDivideWidth();
            canvas.drawLine(0, divideY, getViewWidth(), divideY, mDividePaint);
        }
    }


    private void sizeChanged() {
        mWidth = getWidth();
        mHeight = getHeight();
    }

    private void measureSize(int widthMeasureSpec, int heightMeasureSpec) {

        //分别拿到宽高的大小和测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int mWidth, mHeight;//最终宽度高度
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;//如果是指定大小或填充窗体（指定大小）,直接设置最终宽度
        } else {
            //如果是包裹内容
            mWidth = mMinWidth;
        }
        if (mWeightUnit == 0) {
            mTitleWidth = 0;
        } else {
            mTitleWidth = mWidth / (mWeightValue + mWeightTitle + mWeightUnit) * mWeightTitle;
        }
        if (mWeightUnit == 0) {
            mUnitWidth = 0;
        } else {
            mUnitWidth = mWidth / (mWeightValue + mWeightTitle + mWeightUnit) * mWeightUnit;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            //获取value画笔的文字高度
            Paint.FontMetricsInt fontMetrics = mValuePaint.getFontMetricsInt();
            int height = fontMetrics.bottom - fontMetrics.top;
            mHeight = mMinHeight > height ? mMinHeight :
                    height + getPaddingBottom() + getPaddingTop();
        }
        setMeasuredDimension(mWidth, mHeight);//设置View的宽高，测量结束
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public boolean isDrawDivideLine() {
        return isDrawDivideLine;
    }

    public void setDrawDivideLine(boolean drawDivideLine) {
        isDrawDivideLine = drawDivideLine;
        invalidate();
    }

    public boolean isDrawBottomLine() {
        return isDrawBottomLine;
    }

    public void setDrawBottomLine(boolean drawBottomLine) {
        isDrawBottomLine = drawBottomLine;
        invalidate();
    }

    public boolean isOccupiedUnit() {
        return isOccupiedUnit;
    }

    public void setOccupiedUnit(boolean occupiedUnit) {
        isOccupiedUnit = occupiedUnit;
        invalidate();
    }

    public int getMinWidth() {
        return mMinWidth;
    }

    public void setMinWidth(int mMinWidth) {
        this.mMinWidth = mMinWidth;
        setMeasuredDimension(mMinWidth, getMinHeight());//设置View的宽高
    }

    public int getMinHeight() {
        return mMinHeight;
    }

    public void setMinHeight(int mMinHeight) {
        this.mMinHeight = mMinHeight;
        setMeasuredDimension(getMinHeight(), mMinHeight);//设置View的宽高
    }

    public int getTitleWidth() {
        return mTitleWidth;
    }

    public void setTitleWidth(int mTitleWidth) {
        this.mTitleWidth = mTitleWidth;
        invalidate();
    }

    public int getUnitWidth() {
        return mUnitWidth;
    }

    public void setUnitWidth(int mUnitWidth) {
        this.mUnitWidth = mUnitWidth;
        invalidate();
    }

    public float getDivideWidth() {
        return mDivideWidth;
    }

    public void setDivideWidth(float mDivideWidth) {
        this.mDivideWidth = mDivideWidth;
        invalidate();
    }

    public int getViewWidth() {
        return mWidth;
    }

    public int getViewHeight() {
        return mHeight;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
        invalidate();
    }

    @Override
    public String getName() {
//        return getTitle();
        return (String) getTag();
    }

    @Override
    public void setName(String key) {
//        setTitle(key);
        setTag(key);
    }

    @Override
    public String getValue() {
        return mValue;
    }

    @Override
    public void setValue(String mValue) {
        this.mValue = mValue;
        invalidate();
    }

    @Override
    public String getUnit() {
        return mUnit;
    }

    @Override
    public void setUnit(String mUnit) {
        this.mUnit = mUnit;
        invalidate();
    }

    public int getTitleColor() {
        return mTitleColor;
    }

    public void setTitleColor(int mTitleColor) {
        this.mTitleColor = mTitleColor;
        invalidate();
    }

    public int getValueColor() {
        return mValueColor;
    }

    public void setValueColor(int mValueColor) {
        this.mValueColor = mValueColor;
        invalidate();
    }

    public int getUnitColor() {
        return mUnitColor;
    }

    public void setUnitColor(int mUnitColor) {
        this.mUnitColor = mUnitColor;
        invalidate();
    }

    public int getDivideColor() {
        return mDivideColor;
    }

    public void setDivideColor(int mDivideColor) {
        this.mDivideColor = mDivideColor;
        invalidate();
    }

    public int getTitleSize() {
        return mTitleSize;
    }

    public void setTitleSize(int mTitleSize) {
        this.mTitleSize = mTitleSize;
        mTitlePaint.setTextSize(getTitleSize());
        invalidate();
    }

    public int getValueSize() {
        return mValueSize;
    }

    public void setValueSize(int mValueSize) {
        this.mValueSize = mValueSize;
        mValuePaint.setTextSize(getValueSize());
        invalidate();
    }

    public int getUnitSize() {
        return mUnitSize;
    }

    public void setUnitSize(int mUnitSize) {
        this.mUnitSize = mUnitSize;
        mUnitPaint.setTextSize(getUnitSize());
        invalidate();
    }

}
