package com.uranus.library_wallet.base.wallet.interact;


import com.uranus.library_wallet.base.wallet.repository.TokenRepositoryType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddTokenInteract {
    private final TokenRepositoryType tokenRepository;
    private final FetchWalletInteract  findDefaultWalletInteract;

    public AddTokenInteract(
            FetchWalletInteract findDefaultWalletInteract, TokenRepositoryType tokenRepository) {
        this.findDefaultWalletInteract = findDefaultWalletInteract;
        this.tokenRepository = tokenRepository;
    }

    public Completable add(String address, String symbol, int decimals) {
        return findDefaultWalletInteract
                .findDefault()
                .flatMapCompletable(wallet -> tokenRepository
                        .addToken(wallet.getAddress(), address, symbol, decimals)
                        .observeOn(AndroidSchedulers.mainThread()));
    }
}
