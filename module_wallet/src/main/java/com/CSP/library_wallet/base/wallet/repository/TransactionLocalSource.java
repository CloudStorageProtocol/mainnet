package com.uranus.library_wallet.base.wallet.repository;


import com.uranus.library_wallet.base.wallet.entity.Transaction;

import io.reactivex.Single;

public interface TransactionLocalSource {
    Single<Transaction[]> fetchTransaction(String walletAddr);

    void putTransactions(String walletAddr, Transaction[] transactions);

    Single<Transaction[]> fetchTransaction(String walletAddr, String tokenAddr);

    void putTransactions(String walletAddr, String symbol, Transaction[] transactions);

    void clear();
}
