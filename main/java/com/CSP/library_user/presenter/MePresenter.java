package com.uranus.library_user.presenter;

import com.andjdk.library_base.base.BaseApplication;
import com.andjdk.library_base.base.BaseObserver;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.dao.ETHWalletDao;
import com.andjdk.library_base.mvp.BasePresenter;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.andjdk.library_base.utils.ToastUtils;
import com.google.gson.Gson;
import com.uranus.library_user.apiservice.UserCenterApiService;
import com.uranus.library_user.bean.InviteCodeInfo;
import com.uranus.library_user.bean.LoginResult;
import com.uranus.library_user.bean.UserInfoResult;
import com.uranus.library_user.contract.MeContract;

import java.util.List;

public class MePresenter extends BasePresenter<MeContract.View>
        implements MeContract.Presenter {

    private final UserCenterApiService apiService;

    public MePresenter() {
        apiService = create(UserCenterApiService.class);
    }

    @Override
    public void getUserInfo() {
        addSubscribe(apiService.getUserInfo(), new BaseObserver<UserInfoResult>(getView()) {

            @Override
            protected void onSuccess(UserInfoResult data) {
                if (isViewAttached() && data !=null) {
                    SPLocalUtils.getInstance().put(Constants.USER_INFO, new Gson().toJson(data));

                    getView().getUserInfoSuccess();
                }
            }
        });
    }

    @Override
    public void getInviteCode() {
        addSubscribe(apiService.getInviteCode(), new BaseObserver<List<InviteCodeInfo>>(getView()) {
            @Override
            protected void onSuccess(List<InviteCodeInfo> infos) {
                if(infos !=null && infos.size()>0){
                    getView().getInviteCodeSuccess(infos.get(0));
                }
            }
        });
    }

    @Override
    public void logout() {
        addSubscribe(apiService.logout(), new BaseObserver<String>(getView()) {
            @Override
            protected void onSuccess(String s) {
                SPLocalUtils.getInstance().clear();
//                BaseApplication.getApplication().getDaoSession().getETHWalletDao().deleteAll();
                ToastUtils.showShort(s);
                getView().logoutSuccess();
            }
        });
    }
}
