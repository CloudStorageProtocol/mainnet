package com.uranus.library_user.presenter;


import com.andjdk.library_base.base.BaseObserver;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.mvp.BasePresenter;
import com.andjdk.library_base.utils.Lmsg;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.andjdk.library_base.utils.ToastUtils;
import com.google.gson.Gson;
import com.uranus.library_user.apiservice.UserCenterApiService;
import com.uranus.library_user.bean.UserInfoResult;
import com.uranus.library_user.contract.ModifyUserInfoContract;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class ModifyUserInfoPresenter extends BasePresenter<ModifyUserInfoContract.View>
        implements ModifyUserInfoContract.Presenter {

    private final UserCenterApiService apiService;

    public ModifyUserInfoPresenter() {
        apiService = create(UserCenterApiService.class);
    }



    @Override
    public void modifyNickname(String nickname) {
        addSubscribe(apiService.updateUserName(nickname), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String info) {
                ToastUtils.showShort(info);
                getUserInfo();
            }

        });
    }


    @Override
    public void uploadImg(String url) {
        addSubscribe(apiService.uploadAvatar(url), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String info) {
                ToastUtils.showShort(info);
                getUserInfo();
            }

        });
    }

    @Override
    public void getUserInfo() {
        addSubscribe(create(UserCenterApiService.class).getUserInfo(), new BaseObserver<UserInfoResult>(getView()) {

            @Override
            protected void onSuccess(UserInfoResult data) {
                if (isViewAttached() && data !=null) {
                    SPLocalUtils.getInstance().put(Constants.USER_INFO, new Gson().toJson(data));
                    SPLocalUtils.getInstance().put(Constants.ID, data.getId());
                    getView().getUserInfoSuccess();
                }
            }
        });
    }
}