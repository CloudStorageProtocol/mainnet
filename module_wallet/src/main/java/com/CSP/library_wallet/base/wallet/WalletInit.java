package com.uranus.library_wallet.base.wallet;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.uranus.library_wallet.base.wallet.repository.RepositoryFactory;
import com.uranus.library_wallet.base.wallet.repository.SharedPreferenceRepository;
import com.uranus.library_wallet.base.wallet.utils.LogInterceptor;

import io.realm.Realm;
import okhttp3.OkHttpClient;

/**
 * Created by andjdk on 2019-09-21
 * <p>
 * time ：2019-09-21
 * desc：
 */
public class WalletInit {

    private static WalletInit sSelf;
    private final SharedPreferences pref;
    public static RepositoryFactory repositoryFactory;
    public static SharedPreferenceRepository sp;
    private static OkHttpClient httpClient;


    public static WalletInit init(Context context) {

        if (sSelf == null) {
            sSelf = new WalletInit(context);
        }
        return sSelf;
    }


    private WalletInit(Context context) {

        Realm.init(context);
        sp = SharedPreferenceRepository.init(context);

        pref = context.getSharedPreferences(context.getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);

        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .build();

        Gson gson = new Gson();
        repositoryFactory = RepositoryFactory.init(sp, httpClient, gson);

    }

    public static OkHttpClient okHttpClient() {
        return httpClient;
    }

    public static RepositoryFactory repositoryFactory() {
        return  repositoryFactory;
    }

}
