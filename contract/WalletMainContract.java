package com.uranus.library_wallet.contract;


import com.andjdk.library_base.mvp.IView;
import com.uranus.library_wallet.bean.UserIncomeInfo;
import com.uranus.library_wallet.bean.UserTotalIncomeInfo;

import java.util.List;

public interface WalletMainContract {

    interface View extends IView {
        void getUserTotalIncomeInfoSuccess(UserTotalIncomeInfo incomeInfo);
        void getUserIncomeInfo(List<UserIncomeInfo> incomeInfoList);
        void getUserIncomeFail();
    }

    interface Presenter {

        void getUserTotalIncomeInfo();
        void getUserIncomeInfo(int type,int page);


    }
}