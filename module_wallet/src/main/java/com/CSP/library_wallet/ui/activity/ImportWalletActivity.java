package com.uranus.library_wallet.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.andjdk.library_base.annotation.BindEventBus;
import com.andjdk.library_base.base.BaseActivity;
import com.andjdk.library_base.widget.TitleBar;
import com.google.android.material.tabs.TabLayout;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.event.UpdateTokenEvent;
import com.uranus.library_wallet.ui.fragment.ImportKeyStoreFragment;
import com.uranus.library_wallet.ui.fragment.ImportMnemoincFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 导入钱包
 */

@BindEventBus
public class ImportWalletActivity extends BaseActivity {

    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.viewpager_tab)
    TabLayout viewpagerTab;
    @BindView(R2.id.viewpager)
    ViewPager viewpager;

    private List<Fragment> mFragments;
    String[] tabTitles = {"助记词", "Keystore"};



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_import_wallet;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        titleBar.setOnLeftTitleBarListener(new TitleBar.OnLeftTitleBarListener() {
            @Override
            public void onListener(View v) {
                finish();
            }
        });
        mFragments = new ArrayList<>();
        ImportMnemoincFragment fragment1 = ImportMnemoincFragment.create();
        ImportKeyStoreFragment fragment2 = ImportKeyStoreFragment.create();
        mFragments.add(fragment1);
        mFragments.add(fragment2);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }

        };
        viewpager.setAdapter(adapter);
        viewpagerTab.setupWithViewPager(viewpager);
        viewpagerTab.setTabTextColors(Color.parseColor("#808080"),Color.parseColor("#637200"));

    }

    @Override
    public void onEventBus(Object event) {
        super.onEventBus(event);
        if(event instanceof UpdateTokenEvent){
            if(((UpdateTokenEvent) event).isUpdate()){
                finish();
            }
        }
    }
}
