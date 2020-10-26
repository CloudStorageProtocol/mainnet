package com.uranus.library_wallet.ui.adapter;

import android.os.Parcelable;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragList;
    private String[] titleList;
    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragList, String[] titleList) {
        super(fm);
        this.fragList=fragList;
        this.titleList=titleList;
    }

    @Override
    public Fragment getItem(int arg0) {

        return fragList.get(arg0);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup arg0, int arg1) {
        return super.instantiateItem(arg0, arg1);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }
}