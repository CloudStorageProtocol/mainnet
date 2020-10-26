package com.uranus.library_user.debug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.andjdk.library_base.base.ContainerActivity;
import com.uranus.library_user.ui.fragment.MeFragment;

/**
 * 组件单独运行时的调试界面，不会被编译进release里
 */

public class DebugActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra("fragment", MeFragment.class.getCanonicalName());
        this.startActivity(intent);
        finish();
    }
}
