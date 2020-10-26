package com.andjdk.library_base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.andjdk.library_base.R;
import com.andjdk.library_base.utils.NoDoubleClickListener;


/**
 * creator tanxiongliang
 * 2018/10/29
 * desc:
 **/
public class TitleBar extends LinearLayout {

    private FrameLayout leftView;
    private FrameLayout rightView;
    private ImageView leftViewImg;
    private ImageView rightViewImg;

    private TextView leftViewText;
    private TextView titleText;
    private TextView rightViewText;

    private String title;
    private String leftText;
    private String rightText;
    private int titleTextColor;
    private int leftTextColor;
    private int rightTextColor;

    //标题默认字体颜色
    private static final int TITLE_TEXT_COLOR = Color.parseColor("#ffffff");
    //标题右边字体颜色
    private static final int TITLE_RIGHT_TEXT_COLOR = Color.parseColor("#ffffff");


    private OnLeftTitleBarListener onLeftTitleBarListener;

    private OnRightTitleBarListener onRightTitleBarListener;


    public void setOnLeftTitleBarListener(OnLeftTitleBarListener onListener) {
        this.onLeftTitleBarListener = onListener;
    }


    public void setOnRightTitleBarListener(OnRightTitleBarListener onListener) {
        this.onRightTitleBarListener = onListener;
    }

    public TitleBar(Context context) {
        this(context, null);
        init(null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = inflate(getContext(), R.layout.layout_title_bar, null);
        leftView = view.findViewById(R.id.title_bar_view_left);
        leftViewImg = view.findViewById(R.id.title_bar_left_img);
        leftViewText = view.findViewById(R.id.title_bar_left_text);
        titleText = view.findViewById(R.id.title_bar_title_text);
        rightView = view.findViewById(R.id.title_bar_view_right);
        rightViewImg = view.findViewById(R.id.title_bar_right_img);
        rightViewText = view.findViewById(R.id.title_bar_right_text);


        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);
        title = a.getString(R.styleable.TitleBar_title);
        leftText = a.getString(R.styleable.TitleBar_left_text);
        rightText = a.getString(R.styleable.TitleBar_right_text);
        leftTextColor = a.getColor(R.styleable.TitleBar_left_text_color, TITLE_RIGHT_TEXT_COLOR);
        titleTextColor = a.getColor(R.styleable.TitleBar_title_color, TITLE_TEXT_COLOR);
        rightTextColor = a.getColor(R.styleable.TitleBar_right_text_color, TITLE_RIGHT_TEXT_COLOR);

        int mLeftImageDrawableId = a.getResourceId(R.styleable.TitleBar_left_image, 0);
        int mRightImageDrawableId = a.getResourceId(R.styleable.TitleBar_right_image, 0);

        a.recycle();

        addView(view);


        if (!TextUtils.isEmpty(title) && titleText != null) {
            titleText.setText(title);
            titleText.setTextColor(titleTextColor);
        }

        if (!TextUtils.isEmpty(leftText) && mLeftImageDrawableId == 0 && leftViewText != null) {

            leftViewText.setText(leftText);
            leftViewText.setTextColor(leftTextColor);

        }

        if (!TextUtils.isEmpty(rightText) && mRightImageDrawableId == 0 && rightViewText !=null) {
            rightViewText.setText(rightText);
            rightViewText.setTextColor(rightTextColor);
        }

        if (mLeftImageDrawableId != 0 && leftViewImg !=null) {
            leftViewImg.setImageResource(mLeftImageDrawableId);
        }

        if (mRightImageDrawableId != 0 && rightViewImg !=null) {
            rightViewImg.setImageResource(mRightImageDrawableId);
        }

        initListener();
    }


    @Override
    public void setMinimumHeight(int minHeight) {
        setMinimumHeight(100);
    }

    public void setTitle(String title) {
        this.title = title;
        titleText.setText(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        titleText.setText(title);
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public void setLeftViewImg(int resId) {
        leftViewImg.setImageResource(resId);
    }

    public void setRightViewImg(int resId) {
        rightViewImg.setImageResource(resId);
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
        leftViewText.setTextColor(leftTextColor);
    }

    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
        rightViewText.setTextColor(rightTextColor);
    }

    private void initListener() {
        if(leftView !=null){
            leftView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void noDoubleClick(View v) {
                    if (onLeftTitleBarListener != null) {
                        onLeftTitleBarListener.onListener(v);
                    }
                }
            });
        }
        if(rightView !=null){
            rightView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void noDoubleClick(View v) {
                    if (onRightTitleBarListener != null) {
                        onRightTitleBarListener.onListener(v);
                    }
                }
            });

        }
    }

    public interface OnLeftTitleBarListener {
        void onListener(View v);
    }

    public interface OnRightTitleBarListener {
        void onListener(View v);
    }


}
