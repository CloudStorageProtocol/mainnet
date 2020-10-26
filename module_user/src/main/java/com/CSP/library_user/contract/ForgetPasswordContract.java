package com.uranus.library_user.contract;


import com.andjdk.library_base.mvp.IView;

public interface ForgetPasswordContract {

    interface View extends IView {
        void modifyPasswordSuccess();
        void resetPasswordSuccess();
        void getSmsCodeSuccess();
    }

    interface Presenter {
        void modifyPassword(String email, String code, String password);
        void resetPassword(String oldPassword,String newPassword,String rePassword);
        void sendCode(String email);
    }
}