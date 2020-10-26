package com.uranus.library_wallet.base.wallet.service;


import com.uranus.library_wallet.base.wallet.entity.TokenInfo;

import io.reactivex.Observable;

public interface TokenExplorerClientType {
    Observable<TokenInfo[]> fetch(String walletAddress);
}