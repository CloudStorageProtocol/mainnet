package com.uranus.library_user.contract;

import com.andjdk.library_base.mvp.IView;
import com.uranus.library_user.bean.InviteCodeInfo;

public interface MeContract {

    interface View extends IView {
        void getUserInfoSuccess();
        void getInviteCodeSuccess(InviteCodeInfo info);
        void logoutSuccess();
    }

    interface Presenter {
        void getUserInfo();
        void logout();
        void getInviteCode();
    }
}