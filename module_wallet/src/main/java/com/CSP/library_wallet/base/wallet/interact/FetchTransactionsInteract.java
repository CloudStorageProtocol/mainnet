package com.uranus.library_wallet.base.wallet.interact;


import com.uranus.library_wallet.base.wallet.entity.Transaction;
import com.uranus.library_wallet.base.wallet.repository.TransactionRepositoryType;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FetchTransactionsInteract {

    private final TransactionRepositoryType transactionRepository;

    public FetchTransactionsInteract(TransactionRepositoryType transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Observable<Transaction[]> fetch(String walletAddr, String token) {
        return transactionRepository
                .fetchTransaction(walletAddr, token)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
