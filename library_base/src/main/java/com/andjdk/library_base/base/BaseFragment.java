package com.andjdk.library_base.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.andjdk.library_base.R;
import com.andjdk.library_base.annotation.BindEventBus;
import com.andjdk.library_base.utils.EventBusHelper;
import com.andjdk.library_base.widget.loading.CustomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    private Unbinder mUnbinder = null;
    private CustomDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        if (view.getRootView() != null) {
            mUnbinder = ButterKnife.bind(this, view.getRootView());
        }
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusHelper.register(this);
        }

        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected abstract int getLayoutResId();

    protected abstract void initView(View rootView);

    protected abstract void initData();


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public boolean isBackPressed() {
        return false;
    }

    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    protected void goActivity(Class<?> activity) {
        Intent intent = new Intent();
        intent.setClass(mContext, activity);
        startActivity(intent);
    }

    protected void goActivity(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void goActivity(Class<?> activity, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext, activity);
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

//    private AlertDialog alertDialog;
//
//    public void showLoadingDialog() {
//        alertDialog = new AlertDialog.Builder(mContext).create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
//        alertDialog.setCancelable(false);
//        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK)
//                    return true;
//                return false;
//            }
//        });
//        alertDialog.show();
//        alertDialog.setContentView(R.layout.loading_alert);
//        alertDialog.setCanceledOnTouchOutside(false);
//    }
//
//    public void dismissLoadingDialog() {
//        if (null != alertDialog && alertDialog.isShowing()) {
//            alertDialog.dismiss();
//        }
//    }


    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(getActivity());
            dialog.setCancelable(false);
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
