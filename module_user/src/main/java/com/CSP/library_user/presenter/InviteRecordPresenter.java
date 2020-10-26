package com.uranus.library_user.presenter;


import com.andjdk.library_base.base.BaseObserver;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.mvp.BasePresenter;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.uranus.library_user.apiservice.UserCenterApiService;
import com.uranus.library_user.bean.InviteCodeInfo;
import com.uranus.library_user.bean.InviteRecordInfo;
import com.uranus.library_user.contract.InviteRecordContract;

import java.util.List;

public class InviteRecordPresenter extends BasePresenter<InviteRecordContract.View>
        implements InviteRecordContract.Presenter {

    private final UserCenterApiService apiService;

    public InviteRecordPresenter() {
        apiService = create(UserCenterApiService.class);
    }


    @Override
    public void getInviteRecordList(int page) {
        addSubscribe(apiService.getInviteRecordList(SPLocalUtils.getInstance().getInt(Constants.ID),page,Constants.PAGE_LIMIT), new BaseObserver<List<InviteRecordInfo>>(getView()) {

            @Override
            protected void onSuccess(List<InviteRecordInfo> infos) {
                if(isViewAttached()){
                    getView().getInviteRecordListSuccess(infos);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                getView().getInviteRecordFail();
            }
        });
    }

    @Override
    public void getInviteRecordList(int userId,int page) {
        addSubscribe(apiService.getInviteRecordList(userId,page,Constants.PAGE_LIMIT), new BaseObserver<List<InviteRecordInfo>>(getView()) {

            @Override
            protected void onSuccess(List<InviteRecordInfo> infos) {
                if(isViewAttached()){
                    getView().getInviteRecordListSuccess(infos);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                getView().getInviteRecordFail();
            }
        });
    }
}