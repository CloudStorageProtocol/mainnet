package com.andjdk.library_base.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import com.andjdk.library_base.R;
import com.andjdk.library_base.annotation.BindEventBus;
import com.andjdk.library_base.http.NetworkChangeReceiver;
import com.andjdk.library_base.utils.EventBusHelper;
import com.andjdk.library_base.utils.StatusBarUtil;
import com.andjdk.library_base.widget.TitleBar;
import com.andjdk.library_base.widget.loading.CustomDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private NetworkChangeReceiver receiver;
    private Unbinder mUnbinder = null;
    private CustomDialog dialog;//


    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mUnbinder = ButterKnife.bind(this);
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this, Color.parseColor("#161618"));

        mContext = this;
        // 基类中注册 eventbus
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusHelper.register(this);
        }

        registerNetworkChangeReceiver();
        initView(savedInstanceState);
        initData();
    }

    protected abstract int getLayoutResId();

    protected abstract void initView(Bundle savedInstanceState);

    protected void initData() {

    }

    public void onClickToolBarBack(TitleBar titleBar){
        titleBar.setOnLeftTitleBarListener(new TitleBar.OnLeftTitleBarListener() {
            @Override
            public void onListener(View v) {
                finish();
            }
        });
    }

    /**
     * 注册网络监听广播
     */
    private void registerNetworkChangeReceiver() {
        receiver = new NetworkChangeReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver.onDestroy();
            receiver = null;
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        // 取消注册
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusHelper.unregister(this);
        }

    }


    protected void goActivity(Class<?> activity) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        startActivity(intent);
    }

    protected void goActivity(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void goActivity(Class<?> activity, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        startActivityForResult(intent, requestCode);
    }

    protected void goActivity(Class<?> activity, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 子类接收事件 重写该方法
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Object event){

    }

    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog(String progressTip) {
        getDialog().show();
        if (progressTip != null) {
            getDialog().setTvProgress(progressTip);
        }
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
