package com.andjdk.library_base.base;

import com.andjdk.library_base.http.ApiException;
import com.andjdk.library_base.http.ExceptionHandler;
import com.andjdk.library_base.mvp.IView;
import com.andjdk.library_base.utils.Lmsg;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseObserver<T> extends DisposableObserver<T> {
    private IView baseView;

    public BaseObserver() {

    }

    public BaseObserver(IView baseView) {
        this.baseView = baseView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (baseView != null) {
            baseView.showLoading();
        }
    }

    @Override
    public void onNext(T baseResponse) {
        if (baseView != null) {
            baseView.hideLoading();
        }
        onSuccess(baseResponse);
    }

    /**
     * 回调正常数据
     *
     * @param data
     */
    protected abstract void onSuccess(T data);

    /**
     * 异常处理，包括两方面数据：
     * (1) 服务端没有没有返回数据，HttpException，如网络异常，连接超时;
     * (2) 服务端返回了数据，但 errcode!=0,即服务端返回的data为空，如 密码错误,App登陆超时,token失效
     */
    @Override
    public void onError(Throwable e) {
        if (baseView != null) {
            baseView.hideLoading();
        }
        ExceptionHandler.handleException(e);
    }

    @Override
    public void onComplete() {
        if (baseView != null) {
            baseView.hideLoading();
        }
    }
}
