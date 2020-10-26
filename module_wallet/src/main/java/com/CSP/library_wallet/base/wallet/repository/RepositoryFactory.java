package com.uranus.library_wallet.base.wallet.repository;

import com.andjdk.library_base.utils.Lmsg;
import com.google.gson.Gson;
import com.uranus.library_wallet.base.wallet.service.BlockExplorerClient;
import com.uranus.library_wallet.base.wallet.service.EthplorerTokenService;
import com.uranus.library_wallet.base.wallet.service.TokenExplorerClientType;

import okhttp3.OkHttpClient;

/**
 * Created by andjdk on 2019-09-21
 * <p>
 * time ：2019-09-21
 * desc：
 */
public class RepositoryFactory {

    public TokenRepository tokenRepository;
    public TransactionRepository transactionRepository;
    public EthereumNetworkRepository ethereumNetworkRepository;

    public static RepositoryFactory sSelf;

    private RepositoryFactory(SharedPreferenceRepository sp, OkHttpClient httpClient, Gson gson) {
        ethereumNetworkRepository = EthereumNetworkRepository.init(sp);

        TokenLocalSource tokenLocalSource = new RealmTokenSource();

        TokenExplorerClientType tokenExplorerClientType =  new EthplorerTokenService(httpClient, gson);
        BlockExplorerClient blockExplorerClient = new BlockExplorerClient(httpClient, gson, ethereumNetworkRepository);

        tokenRepository = new TokenRepository(httpClient, ethereumNetworkRepository, tokenExplorerClientType, tokenLocalSource);

        TransactionLocalSource inMemoryCache = new TransactionInMemorySource();

        transactionRepository = new TransactionRepository(ethereumNetworkRepository, inMemoryCache, null, blockExplorerClient);
    }

    public static RepositoryFactory init (SharedPreferenceRepository sp, OkHttpClient httpClient, Gson gson) {
        if (sSelf == null) {
            sSelf = new RepositoryFactory(sp, httpClient, gson);
        }
        return sSelf;
    }
}
