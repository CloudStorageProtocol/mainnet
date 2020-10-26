package com.uranus.library_wallet.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.andjdk.library_base.annotation.BindEventBus;
import com.andjdk.library_base.base.BaseMVPFragment;
import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.event.BuyContainerEvent;
import com.andjdk.library_base.router.RouterFragmentPath;
import com.andjdk.library_base.utils.Lmsg;
import com.andjdk.library_base.utils.ToastUtils;
import com.andjdk.library_base.utils.Utils;
import com.andjdk.library_base.widget.TitleBar;
import com.google.android.material.tabs.TabLayout;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.andjdk.library_base.dao.WalletDaoUtils;
import com.uranus.library_wallet.base.wallet.WalletInit;
import com.uranus.library_wallet.base.wallet.entity.Token;
import com.uranus.library_wallet.base.wallet.entity.TokenInfo;
import com.uranus.library_wallet.base.wallet.interact.FetchTokensInteract;
import com.uranus.library_wallet.base.wallet.repository.RepositoryFactory;
import com.uranus.library_wallet.base.wallet.utils.DBTokenUtil;
import com.uranus.library_wallet.bean.UserIncomeInfo;
import com.uranus.library_wallet.bean.UserTotalIncomeInfo;
import com.uranus.library_wallet.contract.WalletMainContract;
import com.uranus.library_wallet.event.UpdateInComeEvent;
import com.uranus.library_wallet.event.UpdateTokenEvent;
import com.uranus.library_wallet.presenter.WalletMainPresenter;
import com.uranus.library_wallet.ui.activity.CreateWalletActivity;
import com.uranus.library_wallet.ui.activity.ImportWalletActivity;
import com.uranus.library_wallet.ui.activity.ManagerKeyActivity;
import com.uranus.library_wallet.ui.activity.RollInWalletActivity;
import com.uranus.library_wallet.ui.adapter.MyFragmentPagerAdapter;
import com.uranus.library_wallet.ui.adapter.WalletListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

/**
 * @Author time ：2019-09-08
 * desc：
 */

@Route(path = RouterFragmentPath.Wallet.PAGER_WalletMain)
@BindEventBus
public class WalletMainFragment extends BaseMVPFragment<WalletMainPresenter> implements WalletMainContract.View {


    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.viewpager_tab)
    TabLayout viewpagerTab;
    @BindView(R2.id.viewpager)
    ViewPager viewpager;
    @BindView(R2.id.tv_create_wallet)
    TextView tvCreateWallet;
    @BindView(R2.id.tv_import_wallet)
    TextView tvImportWallet;
    @BindView(R2.id.tv_total_income)
    TextView tvTotalIncome;
    @BindView(R2.id.tv_rate)
    TextView tvRate;
    @BindView(R2.id.tv_roll_in_wallet)
    TextView tvRollInWallet;
    @BindView(R2.id.iv_manager)
    ImageView ivManager;
    @BindView(R2.id.lly_create)
    LinearLayout llyCreate;

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;

    private ETHWallet currentWallet;

    private FetchTokensInteract fetchTokensInteract;

    private List<Token> tokenList = new ArrayList<>();
    private Token currentToken;

    private WalletListAdapter walletListAdapter;



    private List<Fragment> mFragments;
    private String[] tabTitles = {"邀请收益", "直推奖励", "容器收益", "加权收益", "级差收益"};

    private String totalIncome;



    @Override
    protected WalletMainPresenter createPresenter() {
        return new WalletMainPresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_wallet_main;
    }

    @Override
    protected void initView(View rootView) {

        RepositoryFactory rf = WalletInit.repositoryFactory();
        fetchTokensInteract = new FetchTokensInteract(rf.tokenRepository);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper(){
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                int targetPos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);

                currentToken = tokenList.get(targetPos);
                return targetPos;
            }
        };
        pagerSnapHelper.attachToRecyclerView(recyclerView);


        walletListAdapter = new WalletListAdapter(R.layout.item_wallet_list, tokenList);
        recyclerView.setAdapter(walletListAdapter);

        tvRollInWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(WalletDaoUtils.getCurrent()!=null){
                    Bundle bundle = new Bundle();
                    bundle.putString("total_income",totalIncome);
                    goActivity(RollInWalletActivity.class,bundle);
                }else {
                    ToastUtils.showShort("请先创建或者导入钱包");
                }

            }
        });


        mFragments = new ArrayList<>();

        ProfitFragment fragment1 = initFragment(3);
        ProfitFragment fragment2 = initFragment(5);
        ProfitFragment fragment3 = initFragment(4);
        ProfitFragment fragment4 = initFragment(6);
        ProfitFragment fragment5 = initFragment(7);

        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        mFragments.add(fragment4);
        mFragments.add(fragment5);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),mFragments,tabTitles);

        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(5);
        viewpagerTab.setupWithViewPager(viewpager);
        viewpagerTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tvCreateWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(CreateWalletActivity.class);
            }
        });

        tvImportWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(ImportWalletActivity.class);
            }
        });

        ivManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(ManagerKeyActivity.class);
            }
        });


        showWallet();
    }

    private ProfitFragment initFragment(int type){
        ProfitFragment fragment =new ProfitFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void showWallet(){
        currentWallet = WalletDaoUtils.getCurrent();

        if (currentWallet != null) {
            List<TokenInfo> tokens = DBTokenUtil.getAllTokens(Utils.getContext());
            tokenList.clear();
            for (int i = 0; i < tokens.size(); i++) {
                tokenList.add(new Token(tokens.get(i),"***"));
            }
            listSort();

            getToken();

            llyCreate.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            ivManager.setVisibility(View.VISIBLE);
        } else {
            llyCreate.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            ivManager.setVisibility(View.GONE);
        }
    }

    private void listSort(){
        List<Token> newDatas = new ArrayList<>(tokenList);
        for (int i = 0; i < tokenList.size(); i++) {
            TokenInfo tokenInfo = tokenList.get(i).tokenInfo;
            if(tokenInfo.getSymbol().equals("ETH")){
                newDatas.set(2,tokenList.get(i));
            }else if(tokenInfo.getSymbol().equals("URAC")){
                newDatas.set(1,tokenList.get(i));
            }else {
                newDatas.set(0,tokenList.get(i));
            }
        }
        walletListAdapter.replaceData(newDatas);
    }

    @Override
    protected void initData() {
        presenter.getUserTotalIncomeInfo();
    }

    @Override
    public void getUserTotalIncomeInfoSuccess(UserTotalIncomeInfo incomeInfo) {
        totalIncome= incomeInfo.getU_score();
        tvTotalIncome.setText(incomeInfo.getU_score()+" URAC");
        tvRate.setText("≈" + incomeInfo.getTotal_income() + " CNY");
    }

    @Override
    public void getUserIncomeInfo(List<UserIncomeInfo> incomeInfoList) {

    }

    @Override
    public void getUserIncomeFail() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onEventBus(Object event) {
        super.onEventBus(event);
        if (event instanceof UpdateTokenEvent) {
            if(((UpdateTokenEvent) event).isUpdate()){
                showWallet();
            }
        }else if(event instanceof UpdateInComeEvent){
            if(((UpdateInComeEvent) event).isUpdate()){
                presenter.getUserTotalIncomeInfo();
            }
        }else if(event instanceof BuyContainerEvent){
            if(((BuyContainerEvent) event).isBuyed()){
                getToken();
            }
        }
    }

    @SuppressLint("CheckResult")
    private void getToken(){
        fetchTokensInteract
                .fetch(currentWallet.getAddress())
                .subscribe(this::onTokens, this::onError);

    }

    private void onError(Throwable throwable) {
        Lmsg.d("ddd--onError--"+throwable.toString());

    }

    private void onTokens(Token[] tokens) {
        List<Token> list= Arrays.asList(tokens);
        tokenList.clear();
        tokenList = new ArrayList<>(list);
        listSort();
//        walletListAdapter.replaceData(Arrays.asList(tokens));
    }
}
