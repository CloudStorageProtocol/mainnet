package com.uranus.library_wallet.contract;


import com.andjdk.library_base.mvp.IView;
import com.uranus.library_wallet.bean.TransactionRecordInfo;

import java.util.List;

public interface RollInWalletContract {

    interface View extends IView {

        void getFreeSuccess(String s);

        void getTransactionSuccess(String str);

    }

    interface Presenter {

        void getFree(int type);

        void getTransaction(String walletAddress,String score,String tips);

    }
}