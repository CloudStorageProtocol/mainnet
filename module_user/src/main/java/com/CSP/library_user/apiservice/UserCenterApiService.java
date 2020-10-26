package com.uranus.library_user.apiservice;

import com.uranus.library_user.bean.InviteCodeInfo;
import com.uranus.library_user.bean.InviteRecordInfo;
import com.uranus.library_user.bean.LoginResult;
import com.uranus.library_user.bean.UserInfoResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserCenterApiService {
    /**
     * 注册
     *
     * @param email
     * @param code
     * @param nickname
     * @param password
     * @param invite_code
     * @return
     */
    @POST("api/v1/register")
    @FormUrlEncoded
    Observable<String> register(@Field("email") String email,
                                                      @Field("code") String code,
                                                      @Field("nickname") String nickname,
                                                      @Field("password") String password,
                                                      @Field("invite_code") String invite_code
                                                      );


    /**
     * 登录
     *
     * @param email
     * @param password
     * @return
     */
    @POST("api/v1/login")
    @FormUrlEncoded
    Observable<LoginResult> login(@Field("email") String email,
                                                @Field("password") String password);


    /**
     * 忘记密码
     *
     * @param email
     * @param password
     * @return
     */
    @POST("api/v1/forget/password")
    @FormUrlEncoded
    Observable<String> forgetPassword(@Field("email") String email,
                                                    @Field("code") String code,
                                                    @Field("password") String password,
                                                    @Field("password_confirm") String password_confirm
    );

    /**
     * 修改密码
     *
     * @param password
     * @return
     */
    @POST("api/v1/reset/pwd")
    @FormUrlEncoded
    Observable<String> resetPassword( @Field("password") String password,
                                                    @Field("new_password_confirmation") String new_password_confirmation,
                                                    @Field("password_confirmation") String password_confirmation
    );

    /**
     * 修改个人信息
     * @param nickname
     * @return
     */
    @POST("api/v1/user/update")
    @FormUrlEncoded
    Observable<String> updateUserName(@Field("nickname") String nickname);


    /**
     * 修改个人信息
     * @param avatar
     * @return
     */
    @POST("api/v1/user/avatar")
    @FormUrlEncoded
    Observable<String> uploadAvatar(@Field("avatar")String  avatar);

    /**
     * 发送验证码
     * @param email
     * @param type type = 10 为注册，20忘记密码，30其他
     * @return
     */
    @POST("api/v1/send/code")
    @FormUrlEncoded
    Observable<String> sendCode(@Field("email") String email,
                                                @Field("type") int type);

    /**
     * 发送验证码
     * @return
     */
    @GET("api/v1/user/info")
    Observable<UserInfoResult> getUserInfo();


    /**
     * 获取邀请码
     * @return
     */
    @GET("api/v1/invite")
    Observable<List<InviteCodeInfo>> getInviteCode();

    /**
     * 获取邀请记录列表
     * @return
     */
    @GET("api/v1/invite/list")
    Observable<List<InviteRecordInfo>> getInviteRecordList(@Query("user_id")int user_id, @Query("page")int page, @Query("limit")int limit);


    /**
     * 退出
     * @return
     */
    @GET("api/v1/logout")
    Observable<String> logout();

    /**
     * 关于我们
     * @return
     */
    @GET("api/v1/about")
    Observable<String> getAboutMe();

    /**
     * 获取最新版本
     * @return
     */
    @GET("api/v1/about")
    Observable<String> getLatestVersion();

    /**
     * 获取最新版本
     * @return
     */
    @GET("agreement")
    Observable<String> getAgreement();



}
