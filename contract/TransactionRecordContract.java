package com.uranus.library_wallet.contract;


import com.andjdk.library_base.mvp.IView;
import com.uranus.library_wallet.bean.TransactionRecordInfo;

import java.util.List;

public interface TransactionRecordContract {

    interface View extends IView {

        void getTransactionRecordSuccess(List<TransactionRecordInfo> incomeInfoList);
        void getTransactionRecordFail();
    }

    interface Presenter {

        void getTransactionRecord(int type, int page);


    }
}