package com.uranus.library_user.contract;


import com.andjdk.library_base.mvp.IView;
import com.uranus.library_user.bean.InviteCodeInfo;
import com.uranus.library_user.bean.InviteRecordInfo;

import java.util.List;

public interface InviteRecordContract {

    interface View extends IView {
        void getInviteRecordListSuccess(List<InviteRecordInfo> infos);
        void getInviteRecordFail();
    }

    interface Presenter {
        void getInviteRecordList(int page);
        void getInviteRecordList(int userId,int page);
    }
}