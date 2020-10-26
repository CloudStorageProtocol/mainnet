package com.andjdk.library_base.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @Author time ：2019-09-10
 * desc：
 */
public class SoftKeyboardUtil {
    private SoftKeyboardUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
