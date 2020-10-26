package com.uranus.library_wallet.base.wallet.interact;

import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.dao.WalletDaoUtils;
import com.uranus.library_wallet.base.wallet.utils.ETHWalletUtils;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by andjdk on 2019-09-21
 * <p>
 * time ：2019-09-21
 * desc：
 */
public class ModifyWalletInteract {

    public ModifyWalletInteract() {
    }


    public Single<Boolean> modifyWalletName(long walletId, String name) {
        return Single.fromCallable(() -> {
            WalletDaoUtils.updateWalletName(walletId, name);
            return true;
        });

    }


    public Single<ETHWallet> modifyWalletPwd(final long walletId, final String walletName, final String oldPassword, final String newPassword) {

        return Single.fromCallable(() -> {
                    return ETHWalletUtils.modifyPassword(walletId, walletName, oldPassword, newPassword);
                }

        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<String>  deriveWalletPrivateKey(final long walletId, final String password) {

        return Single.fromCallable(() -> {
            return ETHWalletUtils.derivePrivateKey(walletId, password);
        } ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public  Single<String>  deriveWalletKeystore(final long walletId, final String password) {

        return Single.fromCallable(() -> {
            return ETHWalletUtils.deriveKeystore(walletId, password);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        // mView.showDeriveKeystore(keystore);

    }


    public Single<Boolean> deleteWallet(final long walletId) {
        return Single.fromCallable(() -> {
                    return ETHWalletUtils.deleteWallet(walletId);
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}