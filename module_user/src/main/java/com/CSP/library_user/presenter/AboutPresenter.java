package com.uranus.library_user.presenter;


import com.andjdk.library_base.base.BaseObserver;
import com.andjdk.library_base.mvp.BasePresenter;
import com.uranus.library_user.apiservice.UserCenterApiService;
import com.uranus.library_user.contract.AboutContract;

public class AboutPresenter extends BasePresenter<AboutContract.View>
        implements AboutContract.Presenter {

    private final UserCenterApiService apiService;

    public AboutPresenter() {
        apiService = create(UserCenterApiService.class);
    }


    @Override
    public void getAboutMe() {
        addSubscribe(apiService.getAboutMe(), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String infos) {
                if(isViewAttached()){
                    getView().getAboutMeSuccess();
                }
            }
        });
    }

    @Override
    public void getLatestVersion() {
        addSubscribe(apiService.getAboutMe(), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String infos) {
                if(isViewAttached()){
                    getView().getLatestVersion();
                }
            }
        });
    }

    @Override
    public void getAgreement() {
        addSubscribe(apiService.getAgreement(), new BaseObserver<String>(getView()) {

            @Override
            protected void onSuccess(String infos) {
                if(isViewAttached()){
                    getView().getAgreementSuccess(infos);
                }
            }
        });
    }
}