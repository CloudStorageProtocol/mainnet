package com.andjdk.library_base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.andjdk.library_base.annotation.BindEventBus;
import com.andjdk.library_base.mvp.IPresenter;
import com.andjdk.library_base.mvp.IView;
import com.andjdk.library_base.utils.EventBusHelper;

public abstract class BaseMVPFragment<P extends IPresenter> extends BaseFragment implements IView {

    protected Context mContext;
    protected P presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


//    @Override
//    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                   Bundle savedInstanceState) {
//        View rootView = inflater.inflate(getLayoutResId(), container, false);
//
//        initView(rootView);
//        initData();
//        return rootView;
//    }

    protected abstract P createPresenter();

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
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusHelper.unregister(this);
        }
    }
}
