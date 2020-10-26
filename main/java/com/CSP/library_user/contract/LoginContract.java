package com.uranus.library_user.contract;

import com.andjdk.library_base.mvp.IView;
import com.uranus.library_user.bean.LoginResult;

public interface LoginContract {

    interface View extends IView {
        void loginSuccess();
    }

    interface Presenter {
        void login(String email, String password);
    }
}