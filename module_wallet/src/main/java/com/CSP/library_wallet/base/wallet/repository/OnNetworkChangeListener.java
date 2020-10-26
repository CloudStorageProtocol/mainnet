package com.uranus.library_wallet.base.wallet.repository;


import com.uranus.library_wallet.base.wallet.entity.NetworkInfo;


public interface OnNetworkChangeListener {
	void onNetworkChanged(NetworkInfo networkInfo);
}
