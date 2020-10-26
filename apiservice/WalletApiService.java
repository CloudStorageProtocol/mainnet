package com.uranus.library_wallet.apiservice;

import com.uranus.library_wallet.bean.TransactionRecordInfo;
import com.uranus.library_wallet.bean.UserIncomeInfo;
import com.uranus.library_wallet.bean.UserTotalIncomeInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WalletApiService {


    /**
     * 我的总收益
     * @return
     */
    @GET("api/v1/user/total/income")
    Observable<UserTotalIncomeInfo> getUserTotalIncome();
    /**
     * 矿工费
     * @return 手续费类型，1=矿工费
     */
    @GET("api/v1/fee")
    Observable<String> getFree(@Query("type")int type);

    /**
     * 我的收益（直推/加权等）
     * @return
     */
    @GET("api/v1/user/income")
    Observable<List<UserIncomeInfo>> getUserIncome(@Query("type")int type, @Query("page")int page, @Query("limit")int limit);


    /**
     * 交易记录
     * @return
     */
    @GET("api/v1/wallet/list")
    Observable<List<TransactionRecordInfo>> getTransactionRecord(@Query("type")int type, @Query("page")int page, @Query("limit")int limit);


    /**
     * 币种充值/提现/记录
     * @return
     */
    @POST("api/v1/wallet/log")
    @FormUrlEncoded
    Observable<String> getTransaction(@Field("wallet_addr") String wallet_addr, @Field("integral")String integral, @Field("tips")String tips, @Field("type")int type, @Field("coin")String coin);



}
