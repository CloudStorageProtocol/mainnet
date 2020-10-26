package com.uranus.library_wallet.base.wallet.interact;

import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.dao.WalletDaoUtils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class FetchWalletInteract {


    public FetchWalletInteract() {
    }

    public Single<List<ETHWallet>> fetch() {


        return Single.fromCallable(() -> {
            return WalletDaoUtils.loadAll();
        }).observeOn(AndroidSchedulers.mainThread());

    }

    public Single<ETHWallet> findDefault() {

        return Single.fromCallable(() -> {
            return WalletDaoUtils.getCurrent();
        });

    }
}
