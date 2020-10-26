package com.andjdk.library_base.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * creator tanxiongliang
 * 2018/10/31
 * desc: 垂直显示的recyclerview
 **/
public class VerticalRecycleView extends RecyclerView {
    public VerticalRecycleView(Context context) {
        super(context);
        init(context);
    }

    public VerticalRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VerticalRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        setLayoutManager(layoutManager);
    }
}