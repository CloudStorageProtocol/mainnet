package com.uranus.library_wallet.debug;


import androidx.multidex.MultiDex;

import com.andjdk.library_base.base.BaseApplication;
import com.uranus.library_wallet.base.wallet.WalletInit;

/**
 * Created by goldze on 2018/6/25 0025.
 * debug包下的代码不参与编译，仅作为独立模块运行时初始化数据
 */

public class DebugApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        WalletInit.init(getApplicationContext());
    }
}
