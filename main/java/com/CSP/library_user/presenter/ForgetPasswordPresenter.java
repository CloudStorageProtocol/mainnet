package com.uranus.library_user.presenter;


import android.text.TextUtils;

import com.andjdk.library_base.base.BaseObserver;
import com.andjdk.library_base.mvp.BasePresenter;
import com.andjdk.library_base.utils.RegexUtils;
import com.andjdk.library_base.utils.ToastUtils;
import com.uranus.library_user.apiservice.UserCenterApiService;
import com.uranus.library_user.contract.ForgetPasswordContract;

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordContract.View>
        implements ForgetPasswordContract.Presenter {

    private final UserCenterApiService apiService;

    public ForgetPasswordPresenter() {
        apiService = create(UserCenterApiService.class);
    }


    @Override
    public void modifyPassword(String email, String code, String password) {
        if (TextUtils.isEmpty(email)) {
            ToastUtils.showShort("请输入邮箱");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showShort("请输入邮箱验证码");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length()<6) {
            ToastUtils.showShort("请输入数6~18位密码");
            return;
        }

        addSubscribe(apiService.forgetPassword(email, code, password,password), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String data) {
                ToastUtils.showShort(data);
                getView().modifyPasswordSuccess();
            }
        });
    }

    @Override
    public void resetPassword(String oldPassword, String newPassword, String rePassword) {
        if (TextUtils.isEmpty(oldPassword) || oldPassword.length()<6) {
            ToastUtils.showShort("请输入数6~18位密码");
            return;
        }
        if (TextUtils.isEmpty(newPassword) || newPassword.length()<6) {
            ToastUtils.showShort("请输入数6~18位密码");
            return;
        }
        if (TextUtils.isEmpty(rePassword) || rePassword.length()<6) {
            ToastUtils.showShort("请输入数6~18位密码");
            return;
        }

        addSubscribe(apiService.resetPassword(oldPassword, newPassword, rePassword), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String data) {
                ToastUtils.showShort(data);
                getView().resetPasswordSuccess();
            }
        });
    }

    @Override
    public void sendCode(String email) {
        if(TextUtils.isEmpty(email) || !RegexUtils.isEmail(email)){
            ToastUtils.showShort("请输入正确的邮箱");
            return;
        }
        addSubscribe(apiService.sendCode(email, 20), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String data) {
                getView().getSmsCodeSuccess();
                ToastUtils.showShort(data);
            }
        });
    }
}