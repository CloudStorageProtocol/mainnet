package com.uranus.library_wallet.presenter;


import com.andjdk.library_base.base.BaseObserver;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.mvp.BasePresenter;
import com.uranus.library_wallet.apiservice.WalletApiService;
import com.uranus.library_wallet.bean.TransactionRecordInfo;
import com.uranus.library_wallet.contract.TransactionRecordContract;

import java.util.List;

public class TransactionRecordPresenter extends BasePresenter<TransactionRecordContract.View>
        implements TransactionRecordContract.Presenter {

    private final WalletApiService apiService;

    public TransactionRecordPresenter() {
        apiService = create(WalletApiService.class);
    }

    @Override
    public void getTransactionRecord(int type, int page) {
        addSubscribe(apiService.getTransactionRecord(type,page, Constants.PAGE_LIMIT), new BaseObserver<List<TransactionRecordInfo>>(getView()) {

            @Override
            protected void onSuccess(List<TransactionRecordInfo> infos) {
                if(isViewAttached()){
                    getView().getTransactionRecordSuccess(infos);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                getView().getTransactionRecordFail();
            }
        });
    }
}