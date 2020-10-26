package com.uranus.library_wallet.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.andjdk.library_base.base.BaseFragment;
import com.andjdk.library_base.base.BaseMVPFragment;
import com.andjdk.library_base.widget.VerticalRecycleView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.bean.UserIncomeInfo;
import com.uranus.library_wallet.bean.UserTotalIncomeInfo;
import com.uranus.library_wallet.contract.WalletMainContract;
import com.uranus.library_wallet.presenter.WalletMainPresenter;
import com.uranus.library_wallet.ui.adapter.ProfitAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author time ：2019-09-08
 * desc：邀请收益、直推收益，容器收益，加权收益
 */
public class ProfitFragment extends BaseMVPFragment<WalletMainPresenter> implements WalletMainContract.View {


    @BindView(R2.id.recycler_view)
    VerticalRecycleView recyclerView;
    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private int type;//奖励类型  3推荐注册奖励 4容器固定收益 5购买容器收益 6加权奖励
    private int page=1;
    private ProfitAdapter recyclerAdapter;
    private List<UserIncomeInfo> userIncomeInfos;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_profit;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getArguments();
        if(bundle!=null){
            type = bundle.getInt("type");
        }

    }

    @Override
    protected WalletMainPresenter createPresenter() {
        return new WalletMainPresenter();
    }

    @Override
    protected void initView(View rootView) {

        userIncomeInfos = new ArrayList<>();

        recyclerAdapter = new ProfitAdapter(R.layout.item_profit,userIncomeInfos);
        recyclerView.setAdapter(recyclerAdapter);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.getUserIncomeInfo(type,page);
            }
        });


    }

    @Override
    protected void initData() {

        presenter.getUserIncomeInfo(type,page);
    }

    @Override
    public void getUserTotalIncomeInfoSuccess(UserTotalIncomeInfo incomeInfo) {

    }

    @Override
    public void getUserIncomeInfo(List<UserIncomeInfo> datas) {
        if(page==1){
            refreshLayout.finishRefresh();
            userIncomeInfos.clear();
            userIncomeInfos.addAll(datas);
            recyclerAdapter.replaceData(userIncomeInfos);
        }else {
            refreshLayout.finishLoadMore();
            if(datas!=null && datas.size()>0){
                userIncomeInfos.addAll(datas);
                recyclerAdapter.setNewData(userIncomeInfos);
            }else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void getUserIncomeFail() {
        refreshLayout.finishRefreshWithNoMoreData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
