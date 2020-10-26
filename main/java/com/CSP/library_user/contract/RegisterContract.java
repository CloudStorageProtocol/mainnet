package com.uranus.library_user.contract;


import com.andjdk.library_base.mvp.IView;

public interface RegisterContract {

    interface View extends IView {
        void registerSuccess();
        void getSmsCodeSuccess();
    }

    interface Presenter {
        void register(String email, String code, String nickname, String password, String invite_code);
        void sendCode(String email);
    }
}