package com.uuzuche.lib_zxing.decoding;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heng on 2018/3/21.
 */

public final class CheckPermissionUtils {

    //检测存储权限
    public static String[] checkPermissionExternal(Context context) {
        //需要申请的权限
        String[] permissionsExternal = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,};
        List<String> data = new ArrayList<>();//存储未申请的权限
        for (String permission : permissionsExternal) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, permission);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {//未申请
                data.add(permission);
            }
        }
        return data.toArray(new String[data.size()]);
    }
}
