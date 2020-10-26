package com.uranus.library_wallet.base.wallet.repository;


import com.uranus.library_wallet.base.wallet.entity.NetworkInfo;
import com.uranus.library_wallet.base.wallet.entity.TokenInfo;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface TokenLocalSource {
    Completable put(NetworkInfo networkInfo, String walletAddress, TokenInfo tokenInfo);
    Single<TokenInfo[]> fetch(NetworkInfo networkInfo, String walletAddress);
}
