package com.uranus.library_wallet.base.wallet.service;

import com.google.gson.Gson;
import com.uranus.library_wallet.base.wallet.entity.Ticker;

import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.uranus.library_wallet.base.wallet.C.TICKER_API_URL;

/**
 * Created by andjdk on 2019-09-22
 * <p>
 * time ：2019-09-22
 * desc：
 */
public class UpWalletTickerService implements TickerService {

    //
    private final OkHttpClient httpClient;
    private final Gson gson;
    private ApiClient apiClient;

    public UpWalletTickerService(
            OkHttpClient httpClient,
            Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
        buildApiClient(TICKER_API_URL);
    }

    private void buildApiClient(String baseUrl) {
        apiClient = new Retrofit.Builder()
                .baseUrl(baseUrl + "/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiClient.class);
    }

    @Override
    public Observable<Ticker> fetchTickerPrice(String symbols, String currency) {
        return apiClient
                .fetchTickerPrice(symbols, currency)
                .lift(apiError())
                .map(r -> r.response[0])
                .subscribeOn(Schedulers.io());
    }

    private static @NonNull
    <T> ApiErrorOperator<T> apiError() {
        return new ApiErrorOperator<>();
    }

    public interface ApiClient {
        @GET("prices?")
        Observable<Response<TickerResponse>> fetchTickerPrice(@Query("symbols") String symbols, @Query("currency") String currency);
    }

    private static class TickerResponse {
        Ticker[] response;
    }

    private final static class ApiErrorOperator <T> implements ObservableOperator<T, Response<T>> {

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