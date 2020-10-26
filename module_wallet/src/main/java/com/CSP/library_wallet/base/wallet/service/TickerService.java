package com.uranus.library_wallet.base.wallet.service;


import com.uranus.library_wallet.base.wallet.entity.Ticker;

import io.reactivex.Observable;

public interface TickerService {

    Observable<Ticker> fetchTickerPrice(String symbols, String currency);
}
