package com.uranus.library_wallet.presenter;


import com.andjdk.library_base.base.BaseObserver;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.mvp.BasePresenter;
import com.uranus.library_wallet.apiservice.WalletApiService;
import com.uranus.library_wallet.bean.UserIncomeInfo;
import com.uranus.library_wallet.bean.UserTotalIncomeInfo;
import com.uranus.library_wallet.contract.WalletMainContract;

import java.util.List;

public class WalletMainPresenter extends BasePresenter<WalletMainContract.View>
        implements WalletMainContract.Presenter {

    private final WalletApiService apiService;

    public WalletMainPresenter() {
        apiService = create(WalletApiService.class);
    }

    @Override
    public void getUserTotalIncomeInfo() {
        addSubscribe(apiService.getUserTotalIncome(), new BaseObserver<UserTotalIncomeInfo>(getView()) {

            @Override
            protected void onSuccess(UserTotalIncomeInfo infos) {
                if(isViewAttached()){
                    getView().getUserTotalIncomeInfoSuccess(infos);
                }
            }
        });
    }

    @Override
    public void getUserIncomeInfo(int type, int page) {
        addSubscribe(apiService.getUserIncome(type,page, Constants.PAGE_LIMIT), new BaseObserver<List<UserIncomeInfo>>(getView()) {

            @Override
            protected void onSuccess(List<UserIncomeInfo> infos) {
                if(isViewAttached()){
                    getView().getUserIncomeInfo(infos);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                getView().getUserIncomeFail();
            }
        });
    }
}