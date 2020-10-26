package com.andjdk.library_base.utils;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * Just :
 * @author by Zian
 * @date on 2019/09/18 15
 */

public class FragmentTabUtils implements RadioGroup.OnCheckedChangeListener {
    private List<Fragment> fragments;
    private RadioGroup rgs;
    private FragmentManager fragmentManager;
    private int fragmentContentId;
    private int currentTab;
    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener;


    public FragmentTabUtils(FragmentManager fragmentManager, List<Fragment> fragments, int fragmentContentId,
                            RadioGroup rgs, OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this(fragmentManager, fragments, fragmentContentId, rgs, 0, onRgsExtraCheckedChangedListener);
    }

    /**
     * @param fragmentManager
     * @param fragments
     * @param fragmentContentId fragment容器
     * @param rgs 按钮组
     * @param checkIndex 加载页面后默认选中的第几个fragment
     * @param onRgsExtraCheckedChangedListener
     */
    public FragmentTabUtils(FragmentManager fragmentManager, List<Fragment> fragments, int fragmentContentId,
                            RadioGroup rgs, int checkIndex, OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.fragments = fragments;
        this.rgs = rgs;
        this.fragmentManager = fragmentManager;
        this.fragmentContentId = fragmentContentId;
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
        if (checkIndex < fragments.size()) {
            fragmentManager.beginTransaction().add(fragmentContentId, fragments.get(checkIndex)).commit();
        } else {
            fragmentManager.beginTransaction().add(fragmentContentId, fragments.get(0)).commit();
        }
        rgs.setOnCheckedChangeListener(this);
        ((RadioButton) rgs.getChildAt(checkIndex)).setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < rgs.getChildCount(); i++) {
            if (rgs.getChildAt(i).getId() == checkedId) {
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = obtainFragmentTransaction(i);
                getCurrentFragment().onPause();
                getCurrentFragment().onStop();
                if (fragment.isAdded()) {
                    fragment.onStart();
                    fragment.onResume();
                } else {
                    ft.add(fragmentContentId, fragment);
                    ft.commit();
                }
                showTab(i);
                // 如果设置了切换tab额外功能功能接口
                if (null != onRgsExtraCheckedChangedListener) {
                    onRgsExtraCheckedChangedListener.onRgsExtraCheckedChanged(radioGroup, checkedId, i);
                }
            }
        }
    }

    /**
     * 切换tab
     *
     */
    private void showTab(int idx) {
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);
            if (idx == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        // 更新目标tab为当前tab
        currentTab = idx;
    }

    /**
     * 获取一个带动画的FragmentTransaction
     */
    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // 设置切换动画
        return ft;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

    public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
        return onRgsExtraCheckedChangedListener;
    }

    public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
    }

    /**
     * 切换tab额外功能功能接口
     */
    public interface OnRgsExtraCheckedChangedListener {
        public void onRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index);
    }
}
