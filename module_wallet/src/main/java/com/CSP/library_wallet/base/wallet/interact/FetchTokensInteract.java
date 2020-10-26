package com.uranus.library_wallet.base.wallet.interact;


import com.uranus.library_wallet.base.wallet.entity.Token;
import com.uranus.library_wallet.base.wallet.repository.TokenRepositoryType;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FetchTokensInteract {

    private final TokenRepositoryType tokenRepository;

    public FetchTokensInteract(TokenRepositoryType tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Observable<Token[]> fetch(String walletAddress) {
        return tokenRepository.fetch(walletAddress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
