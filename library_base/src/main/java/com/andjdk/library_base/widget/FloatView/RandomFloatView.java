package com.andjdk.library_base.widget.FloatView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.andjdk.library_base.R;
import com.andjdk.library_base.utils.DensityUtil;
import com.andjdk.library_base.utils.ScreenUtils;

/**
 * Created by andjdk on 2019-09-12.
 *
 * time ：2019-09-12
 * desc：
 */
public class RandomFloatView extends AppCompatTextView implements View.OnClickListener{

    public static final int U_SCORE = 1;
    public static final int POWER = 2;

    private OnRemoveListener removeListener;

    private int type;

    public RandomFloatView(Context context) {
        super(context);
        init();
    }

    public RandomFloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RandomFloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_home_ore);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        setCompoundDrawables(null, null, null, drawable);
        setCompoundDrawablePadding(DensityUtil.dp2px(getContext(), 2));
        setTextColor(Color.WHITE);
        setTextSize(11);
        setGravity(Gravity.CENTER);
        startAnimation(animation());
        setOnClickListener(this);
    }


    private ScaleAnimation animation() {
        ScaleAnimation  animation = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }


    @Override
    public void onClick(View v) {
        this.clearAnimation();
        this.startAnimation(translateAnimation(v));
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        if(type==U_SCORE){
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_home_power);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            setCompoundDrawables(null, null, null, drawable);
        }else{
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_home_ore);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            setCompoundDrawables(null, null, null, drawable);
        }
    }

    private Animation translateAnimation(View view) {
        setEnabled(false);
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation animation;
        ScaleAnimation  scaleAnimation;
        if(getType()==U_SCORE){
            int width = ScreenUtils.getScreenWidth(getContext());
            int tempX;
            if(getX()>width/2){
                tempX = - (int) (getX()-width/2);
            }else {
                tempX = (int) (width/2 - getX());
            }
            animation = new TranslateAnimation(0, tempX , 0, -getY()+ScreenUtils.dpToPx(getContext(),100));

            scaleAnimation = new ScaleAnimation(1f,0.1f,1f,0.1f,Animation.ABSOLUTE,tempX,Animation.ABSOLUTE,-getY()+ScreenUtils.dpToPx(getContext(),100));
        }else {
            float tempX = -getX()+ScreenUtils.dpToPx(getContext(),30);
            float tempY = ScreenUtils.getScreenHeight(getContext())-getY()-ScreenUtils.dpToPx(getContext(),130);

            animation = new TranslateAnimation(0, tempX, 0,tempY);
            scaleAnimation = new ScaleAnimation(1f,0.1f,1f,0.1f,Animation.ABSOLUTE,tempX,Animation.ABSOLUTE,tempY);
        }
        animationSet.addAnimation(animation);
        animationSet.addAnimation(scaleAnimation);

        animationSet.setDuration(1000);
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                post(new Runnable() {

                    @Override

                    public void run() {
                        ((RandomFrameLayout)getParent()).removeView(RandomFloatView.this);
                    }

                });
                if (removeListener!=null){
                    FloatData data = new FloatData();
                    data.setType(getType());
                    data.setValue(getText().toString());
                    removeListener.remove(RandomFloatView.this,data);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return animationSet;
    }

    public void setOnRemoveListener(OnRemoveListener removeListener){
        this.removeListener=removeListener;
    }
}
