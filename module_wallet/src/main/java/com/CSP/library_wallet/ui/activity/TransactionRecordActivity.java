package com.uranus.library_wallet.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.andjdk.library_base.base.BaseActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.dao.WalletDaoUtils;
import com.andjdk.library_base.widget.TitleBar;
import com.google.android.material.tabs.TabLayout;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.base.wallet.WalletInit;
import com.uranus.library_wallet.base.wallet.entity.Token;
import com.uranus.library_wallet.base.wallet.entity.Transaction;
import com.uranus.library_wallet.base.wallet.interact.FetchTransactionsInteract;
import com.uranus.library_wallet.base.wallet.repository.RepositoryFactory;
import com.uranus.library_wallet.ui.fragment.TransactionRecordFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @Author time ：2019-09-07
 * desc：交易记录
 */
public class TransactionRecordActivity extends BaseActivity {

    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.viewpager_tab)
    TabLayout viewpagerTab;
    @BindView(R2.id.viewpager)
    ViewPager viewpager;

    private List<Fragment> mFragments;
    String[] tabTitles = {"充值记录", "提现记录"};

    private FetchTransactionsInteract fetchTransactionsInteract;
    private Token token;
    private ETHWallet wallet;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_transaction_record;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        super.initData();

        RepositoryFactory rf = WalletInit.repositoryFactory();
        fetchTransactionsInteract = new FetchTransactionsInteract(rf.transactionRepository);
        wallet = WalletDaoUtils.getCurrent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            token = (Token) bundle.getParcelable(Constants.CURRENT_TOKEN_INFO);
            if (token != null) {
                fetchTransactionsInteract.fetch(wallet.getAddress(), token.tokenInfo.address).subscribe(this::onTransactions, this::onError);
            }
        }
    }

    private void onError(Throwable throwable) {

    }

    private void onTransactions(Transaction[] transactions) {
        List<Transaction> transactionList = (List<Transaction>) Arrays.asList(transactions);
        ArrayList<Transaction> rollInDatas = new ArrayList<>();
        ArrayList<Transaction> rolloutDatas = new ArrayList<>();
        if(transactionList !=null && transactionList.size()>0){
            for (int i = 0; i < transactionList.size(); i++) {
                //充值记录
                if(wallet.getAddress().equalsIgnoreCase(transactionList.get(i).getTo())){
                    rollInDatas.add(transactionList.get(i));
                }else if(wallet.getAddress().equalsIgnoreCase(transactionList.get(i).getFrom())){//提现记录
                    rolloutDatas.add(transactionList.get(i));
                }
            }
        }

        mFragments = new ArrayList<>();
        TransactionRecordFragment fragment1 = TransactionRecordFragment.create(1,rollInDatas);
        TransactionRecordFragment fragment2 = TransactionRecordFragment.create(2,rolloutDatas);
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
}
