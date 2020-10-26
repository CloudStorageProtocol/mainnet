package com.uranus.library_wallet.presenter;


import com.andjdk.library_base.base.BaseObserver;
import com.andjdk.library_base.mvp.BasePresenter;
import com.uranus.library_wallet.apiservice.WalletApiService;
import com.uranus.library_wallet.contract.RollInWalletContract;

public class RollInWalletPresenter extends BasePresenter<RollInWalletContract.View>
        implements RollInWalletContract.Presenter {

    private final WalletApiService apiService;

    public RollInWalletPresenter() {
        apiService = create(WalletApiService.class);
    }

    @Override
    public void getFree(int type) {
        addSubscribe(apiService.getFree(1), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String string) {
                if(isViewAttached()){
                    getView().getFreeSuccess(string);
                }
            }
        });
    }

    @Override
    public void getTransaction(String walletAddress, String score, String tips) {
        addSubscribe(apiService.getTransaction(walletAddress,score,tips,2,"u-score"), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String string) {
                if(isViewAttached()){
                    getView().getTransactionSuccess(string);
                }
            }
        });
    }
}