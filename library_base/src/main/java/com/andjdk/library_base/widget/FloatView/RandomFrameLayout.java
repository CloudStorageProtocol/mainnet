package com.andjdk.library_base.widget.FloatView;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.andjdk.library_base.R;
import com.andjdk.library_base.utils.Lmsg;
import com.uuzuche.lib_zxing.DisplayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by andjdk on 2019-09-12.
 * <p>
 * time ：2019-09-12
 * desc：
 */
public class RandomFrameLayout extends FrameLayout {

    private List<TagViewBean> tagViews;
    private TagViewBean tagBean;
    private int termX, termY;
    private int[] angles = {0,45,90,135,180,225,270,315};//angles为弧度

    private List<RandomFloatView> textList;
    private OnRemoveListener onRemoveListener;

    private MyHandler mHandler = new MyHandler();

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RandomFloatView textView = new RandomFloatView(getContext());
            FloatData floatData = (FloatData) msg.obj;
            textView.setType(floatData.getType());

            textView.setText(floatData.getValue());
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = msg.arg1;
            params.topMargin = msg.arg2;
            addView(textView, params);
            textList.add(textView);
            textView.setOnRemoveListener(new OnRemoveListener() {
                @Override
                public void remove(RandomFloatView randomView, FloatData data) {

                    if (onRemoveListener != null)
                        onRemoveListener.remove(randomView, data);
                }
            });
        }
    }



    public RandomFrameLayout(@NonNull Context context) {
        super(context);
        initData();
    }

    public RandomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public RandomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    public void setOnRemoveListener(OnRemoveListener onRemoveListener) {
        this.onRemoveListener = onRemoveListener;
    }

    private void initData() {
        tagViews = Collections.synchronizedList(new ArrayList<TagViewBean>());
        textList = new ArrayList<>();
        termX = 80;
        termY = 80;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }



    public void updateView(List<FloatData> datas) {
        initTagViews();
        for (int i = 0; i < datas.size(); i++) {
            initXY(datas.get(i),i);
        }

    }


    private List<TagViewBean> tagViewsTemp;

    private void initTagViews(){

        tagViewsTemp = new ArrayList<>();

        int width = getWidth();
        int height = getHeight();


        int centerX = width / 2 -20;//centerX为圆心所在的X坐标
        int centerY = height / 2;//centerY为圆心所在Y坐标
        int currentAdius = width / 2 - termX * 2 ; //currentAdius为圆的半径

        for (int i = 0; i < 8; i++) {

            double radian = Math.toRadians(angles[i]);
            int y;
            if(i==2){
                y = (int) (centerY + Math.sin(radian) * currentAdius)- DisplayUtil.dip2px(getContext(),15);
            }else if(i==6) {
                y = (int) (centerY + Math.sin(radian) * currentAdius)+ DisplayUtil.dip2px(getContext(),15);
            }else {
                y = (int) (centerY + Math.sin(radian) * currentAdius);
            }
            int x = (int) (centerX + Math.cos(radian) * currentAdius);


            TagViewBean tagViewBean = new TagViewBean();
            tagViewBean.setX(x);
            tagViewBean.setY(y);

            tagViewsTemp.add(tagViewBean);
        }

        Collections.shuffle(tagViewsTemp);//打乱顺序
    }

    private void initXY(final FloatData value,int index) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (tagViews) {

                    TagViewBean tagViewBean = tagViewsTemp.get(index);

                    tagViews.add(tagViewBean);
                    Message message = Message.obtain();
                    message.obj = value;
                    message.what = 0;
                    message.arg1 = tagViewBean.getX();
                    message.arg2 = tagViewBean.getY();
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }


}
