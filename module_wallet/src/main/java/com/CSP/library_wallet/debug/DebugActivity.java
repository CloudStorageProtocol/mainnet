package com.uranus.library_wallet.debug;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.andjdk.library_base.base.ContainerActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uranus.library_wallet.ui.fragment.WalletFragment;

import io.reactivex.functions.Consumer;


/**
 * 组件单独运行时的调试界面，不会被编译进release里
 */

public class DebugActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra("fragment", WalletFragment.class.getCanonicalName());
        this.startActivity(intent);
        finish();
    }
}
