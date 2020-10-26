package com.uranus.library_wallet.base.wallet.service;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.uranus.library_wallet.base.wallet.C;
import com.uranus.library_wallet.base.wallet.entity.NetworkInfo;
import com.uranus.library_wallet.base.wallet.entity.Transaction;
import com.uranus.library_wallet.base.wallet.repository.EthereumNetworkRepository;

import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class BlockExplorerClient implements BlockExplorerClientType {

    private final OkHttpClient httpClient;
    private final Gson gson;
    private final EthereumNetworkRepository networkRepository;

    private TransactionsApiClient transactionsApiClient;

    public BlockExplorerClient(
            OkHttpClient httpClient,
            Gson gson,
            EthereumNetworkRepository networkRepository) {
        this.httpClient = httpClient;
        this.gson = gson;
        this.networkRepository = networkRepository;
        this.networkRepository.addOnChangeDefaultNetwork(this::onNetworkChanged);
        NetworkInfo networkInfo = networkRepository.getDefaultNetwork();
        onNetworkChanged(networkInfo);
    }

    private void buildApiClient(String baseUrl) {
        transactionsApiClient = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TransactionsApiClient.class);
    }

    @Override
    public Observable<Transaction[]> fetchTransactions(String address, String tokenAddr) {
        if (TextUtils.isEmpty(tokenAddr)) {
            return transactionsApiClient
                    .fetchTransactions1(address, C.API_KEY)
                    .lift(apiError(gson))
                    .map(r -> r.result)
                    .subscribeOn(Schedulers.io());
        } else {
            return transactionsApiClient
                    .fetchTransactions2(address, tokenAddr,C.API_KEY)
                    .lift(apiError(gson))
                    .map(r -> r.result)
                    .subscribeOn(Schedulers.io());
        }

    }

    private void onNetworkChanged(NetworkInfo networkInfo) {
        buildApiClient(networkInfo.backendUrl);
    }

    private static @NonNull
    <T> ApiErrorOperator<T> apiError(Gson gson) {
        return new ApiErrorOperator<>(gson);
    }


    //	http://localhost:8000/transactions?address=0x856e604698f79cef417aab0c6d6e1967191dba43
    private interface TransactionsApiClient {
        @GET("/wallet/api/v1/record/get?1=1")
        Observable<Response<EtherScanResponse>> fetchTransactions(
                @Query("address") String address);

        @GET("/transactions?limit=50")
        Observable<Response<EtherScanResponse>> fetchTransactions(
                @Query("address") String address, @Query("contract") String contract);


        @GET("api?module=account&action=txlist&startblock=0&endblock=999999999&sort=desc")
        Observable<Response<EtherScanResponse>> fetchTransactions1(@Query("address") String address,@Query("apikey") String apikey);

        @GET("api?module=account&action=tokentx&startblock=0&endblock=999999999&sort=desc")
        Observable<Response<EtherScanResponse>> fetchTransactions2(@Query("address") String address,@Query("contractaddress") String contractaddress,@Query("apikey") String apikey);
    }

    private final static class EtherScanResponse {
        Transaction[] result;
    }

    private final static class ApiErrorOperator<T> implements ObservableOperator<T, Response<T>> {

        private final Gson gson;

        public ApiErrorOperator(Gson gson) {
            this.gson = gson;
        }

        @Override
        public Observer<? super Response<T>> apply(Observer<? super T> observer) throws Exception {
            return new DisposableObserver<Response<T>>() {
                @Override
                public void onNext(Response<T> response) {
                    observer.onNext(response.body());
                    observer.onComplete();
                }

                @Override
                public void onError(Throwable e) {
                    observer.onError(e);
                }

                @Override
                public void onComplete() {
                    observer.onComplete();
                }
            };
        }
    }
}
