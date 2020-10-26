package com.uranus.library_user.presenter;

import android.text.TextUtils;

import com.andjdk.library_base.base.BaseObserver;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.dao.WalletDaoUtils;
import com.andjdk.library_base.mvp.BasePresenter;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.andjdk.library_base.utils.ToastUtils;
import com.google.gson.Gson;
import com.uranus.library_user.apiservice.UserCenterApiService;
import com.uranus.library_user.bean.LoginResult;
import com.uranus.library_user.bean.UserInfoResult;
import com.uranus.library_user.contract.LoginContract;

public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter {

    @Override
    public void login(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            ToastUtils.showShort("请输入邮箱地址");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入密码");
            return;
        }
        addSubscribe(create(UserCenterApiService.class).login(email, password), new BaseObserver<LoginResult>(getView()) {

            @Override
            protected void onSuccess(LoginResult data) {
                if (isViewAttached()) {
                    SPLocalUtils.getInstance().put(Constants.ACCESS_TOKEN, data.getAccess_token());
                    SPLocalUtils.getInstance().put(Constants.HEADERS, data.getHeaders());

                    getUserInfo();
                }
            }
        });
    }


    private void getUserInfo(){
        addSubscribe(create(UserCenterApiService.class).getUserInfo(), new BaseObserver<UserInfoResult>(getView()) {

            @Override
            protected void onSuccess(UserInfoResult data) {
                if (isViewAttached() && data !=null) {
                    SPLocalUtils.getInstance().put(Constants.USER_INFO, new Gson().toJson(data));
                    SPLocalUtils.getInstance().put(Constants.ID, data.getId());
                    SPLocalUtils.getInstance().put(Constants.UID, data.getUid());
                    WalletDaoUtils.updateCurrent(data.getUid());
                    ToastUtils.showShort("登录成功");
                    getView().loginSuccess();
                }
            }
        });
    }

}
