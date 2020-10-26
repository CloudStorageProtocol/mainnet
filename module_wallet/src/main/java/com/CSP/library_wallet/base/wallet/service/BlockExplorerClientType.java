package com.uranus.library_wallet.base.wallet.service;


import com.uranus.library_wallet.base.wallet.entity.Transaction;

import io.reactivex.Observable;

public interface BlockExplorerClientType {
    Observable<Transaction[]> fetchTransactions(String forAddress, String forToken);
}
