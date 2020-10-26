package com.uranus.library_user.contract;


import com.andjdk.library_base.mvp.IView;

public interface ModifyUserInfoContract {

    interface View extends IView {
        void getUserInfoSuccess();
    }

    interface Presenter {
        void modifyNickname(String nickname);
        void uploadImg(String url);
        void getUserInfo();
    }
}