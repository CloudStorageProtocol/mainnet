package com.uranus.library_wallet.base.wallet.repository;

import android.text.TextUtils;

import com.uranus.library_wallet.base.wallet.entity.NetworkInfo;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.uranus.library_wallet.base.wallet.C.CLASSIC_NETWORK_NAME;
import static com.uranus.library_wallet.base.wallet.C.ETC_SYMBOL;
import static com.uranus.library_wallet.base.wallet.C.ETHEREUM_MAIN_NETWORK_NAME;
import static com.uranus.library_wallet.base.wallet.C.ETH_SYMBOL;
import static com.uranus.library_wallet.base.wallet.C.KOVAN_NETWORK_NAME;
import static com.uranus.library_wallet.base.wallet.C.LOCAL_DEV_NETWORK_NAME;
import static com.uranus.library_wallet.base.wallet.C.POA_NETWORK_NAME;
import static com.uranus.library_wallet.base.wallet.C.POA_SYMBOL;
import static com.uranus.library_wallet.base.wallet.C.ROPSTEN_NETWORK_NAME;

/**
 * Created by Tiny 熊 @ Upchain.pro
 * WeiXin: xlbxiong
 */

public class EthereumNetworkRepository {

    public static EthereumNetworkRepository sSelf;

    private final NetworkInfo[] NETWORKS = new NetworkInfo[] {
            new NetworkInfo(ETHEREUM_MAIN_NETWORK_NAME, ETH_SYMBOL,
                    "https://mainnet.infura.io/4UQAYTB2HV7QARCVUD4XGKEMSZ2Q178HD1",
                    "http://etherscan.dihub.cn/",
                    "https://etherscan.io/", 1, true),
            new NetworkInfo(KOVAN_NETWORK_NAME, ETH_SYMBOL,
                    "https://api-kovan.etherscan.io/4UQAYTB2HV7QARCVUD4XGKEMSZ2Q178HD1",
                    "http://api-kovan.etherscan.io/",
                    "https://api-kovan.etherscan.io", 42, false),

            new NetworkInfo(ROPSTEN_NETWORK_NAME, ETH_SYMBOL,
                    "https://api-ropsten.etherscan.io/4UQAYTB2HV7QARCVUD4XGKEMSZ2Q178HD1",
                    "http://api-ropsten.etherscan.io/",
                    "https://api-ropsten.etherscan.io/",3, false),

            new NetworkInfo(LOCAL_DEV_NETWORK_NAME, ETH_SYMBOL,
                    "https://mainnet.infura.io/v3/85622c11cf5148c98db79a25411a0872",
                    "http://192.168.8.100:8000/",
                    "",1337, false),
    };

    private final SharedPreferenceRepository preferences;
    private NetworkInfo defaultNetwork;
    private final Set<OnNetworkChangeListener> onNetworkChangedListeners = new HashSet<>();


    public static EthereumNetworkRepository init(SharedPreferenceRepository sp) {
        if (sSelf == null) {
            sSelf = new EthereumNetworkRepository(sp);
        }
        return sSelf;
    }

    private EthereumNetworkRepository(SharedPreferenceRepository preferenceRepository) {
        this.preferences = preferenceRepository;
        defaultNetwork = getByName(preferences.getDefaultNetwork());
        if (defaultNetwork == null) {
            defaultNetwork = NETWORKS[0];
        }
    }

    private NetworkInfo getByName(String name) {
        if (!TextUtils.isEmpty(name)) {
            for (NetworkInfo NETWORK : NETWORKS) {
                if (name.equals(NETWORK.name)) {
                    return NETWORK;
                }
            }
        }
        return null;
    }

    public String getCurrency() {
        int currencyUnit =  preferences.getCurrencyUnit();
        if (currencyUnit ==0 ) {
            return "CNY";
        } else {
            return "USD";
        }
    }

    public NetworkInfo getDefaultNetwork() {
        return defaultNetwork;
    }

    public void setDefaultNetworkInfo(NetworkInfo networkInfo) {
        defaultNetwork = networkInfo;
        preferences.setDefaultNetwork(defaultNetwork.name);

        for (OnNetworkChangeListener listener : onNetworkChangedListeners) {
            listener.onNetworkChanged(networkInfo);
        }
    }

    public NetworkInfo[] getAvailableNetworkList() {
        return NETWORKS;
    }

    public void addOnChangeDefaultNetwork(OnNetworkChangeListener onNetworkChanged) {
        onNetworkChangedListeners.add(onNetworkChanged);
    }

    public Single<NetworkInfo> find() {
        return Single.just(getDefaultNetwork())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<BigInteger> getLastTransactionNonce(Web3j web3j, String walletAddress)
    {
        return Single.fromCallable(() -> {
            EthGetTransactionCount ethGetTransactionCount = web3j
                    .ethGetTransactionCount(walletAddress, DefaultBlockParameterName.PENDING)   // or DefaultBlockParameterName.LATEST
                    .send();
            return ethGetTransactionCount.getTransactionCount();
        });
    }

}
