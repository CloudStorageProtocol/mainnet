package com.uranus.library_wallet.base.wallet.repository;


import com.uranus.library_wallet.base.wallet.entity.Token;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface TokenRepositoryType {

    Observable<Token[]> fetch(String walletAddress);

    Completable addToken(String walletAddress, String address, String symbol, int decimals);
}
