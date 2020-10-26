package com.uranus.library_user.contract;


import com.andjdk.library_base.mvp.IView;

public interface AboutContract {

    interface View extends IView {
        void getAboutMeSuccess();
        void getLatestVersion();
        void getAgreementSuccess(String string);
    }

    interface Presenter {
        void getAboutMe();
        void getLatestVersion();
        void getAgreement();
    }
}