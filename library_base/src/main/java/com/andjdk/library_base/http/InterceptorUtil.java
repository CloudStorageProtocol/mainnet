package com.andjdk.library_base.http;

import android.text.TextUtils;

import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.utils.SPLocalUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class InterceptorUtil {
    public static Interceptor headerInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder();
                builder.addHeader("X-OS", "Android");

                String token = SPLocalUtils.getInstance().getString(Constants.ACCESS_TOKEN);
                if (!TextUtils.isEmpty(token)) {
                    builder.addHeader("Authorization", token);
                }
                Request request = builder.build();
                return chain.proceed(request);
            }
        };
    }

    public static Interceptor logInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
