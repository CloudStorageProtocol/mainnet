package com.uranus.library_user.presenter;


import android.text.TextUtils;

import com.andjdk.library_base.base.BaseObserver;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.mvp.BasePresenter;
import com.andjdk.library_base.utils.RegexUtils;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.andjdk.library_base.utils.ToastUtils;
import com.uranus.library_user.apiservice.UserCenterApiService;
import com.uranus.library_user.contract.RegisterContract;

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter {

    private final UserCenterApiService apiService;

    public RegisterPresenter() {
        apiService = create(UserCenterApiService.class);
    }

    @Override
    public void register(final String email, String code, String nickname, String password, String invite_code) {
        if (TextUtils.isEmpty(nickname)) {
            ToastUtils.showShort("请输入昵称");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            ToastUtils.showShort("请输入邮箱");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showShort("请输入邮箱验证码");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入数6~18位密码");
            return;
        }

        if (TextUtils.isEmpty(invite_code)) {
            ToastUtils.showShort("请输入邀请码");
            return;
        }

        addSubscribe(apiService.register(email, code, nickname, password, invite_code), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String data) {
                if (isViewAttached()) {
                    SPLocalUtils.getInstance().put(Constants.E_MAIL,email);
                    ToastUtils.showShort(data);
                    getView().registerSuccess();
                }
            }
        });
    }


    @Override
    public void sendCode(String email) {
        if(TextUtils.isEmpty(email) || !RegexUtils.isEmail(email)){
            ToastUtils.showShort("请输入正确的邮箱");
            return;
        }
        addSubscribe(apiService.sendCode(email, 10), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String data) {
                getView().getSmsCodeSuccess();
            }
        });
    }
}