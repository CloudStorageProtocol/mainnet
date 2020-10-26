package com.uranus.library_user.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andjdk.library_base.base.BaseMVPActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.andjdk.library_base.widget.TitleBar;
import com.andjdk.library_base.widget.VerticalRecycleView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uranus.library_user.R;
import com.uranus.library_user.R2;
import com.uranus.library_user.bean.InviteRecordInfo;
import com.uranus.library_user.contract.InviteRecordContract;
import com.uranus.library_user.presenter.InviteRecordPresenter;
import com.uranus.library_user.ui.adapter.InviteRecordAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author time ：2019-09-07
 * desc：邀请记录/用户邀请记录
 */
public class InviteRecordActivity extends BaseMVPActivity<InviteRecordPresenter>
        implements InviteRecordContract.View{
    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.recycler_view)
    VerticalRecycleView recyclerView;
    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private InviteRecordAdapter recyclerAdapter;
    private List<InviteRecordInfo> inviteRecordInfos;
    private int page = 1;
    private int userId = -1;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_invite_record;
    }

    @Override
    protected InviteRecordPresenter createPresenter() {
        return new InviteRecordPresenter();
    }

    @Override
    protected void initData() {
        super.initData();

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            userId= bundle.getInt("userId");
            String userName= bundle.getString("userName");
            titleBar.setTitle(userName+"邀请记录");
        }else {
            userId= SPLocalUtils.getInstance().getInt(Constants.ID);
        }
        presenter.getInviteRecordList(userId,page);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);
        inviteRecordInfos = new ArrayList<>();
        recyclerAdapter = new InviteRecordAdapter(R.layout.item_invite_record,inviteRecordInfos);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId",inviteRecordInfos.get(position).getId());
                bundle.putString("userName",inviteRecordInfos.get(position).getNickname());
                goActivity(InviteRecordActivity.class,bundle);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.getInviteRecordList(userId,page);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.getInviteRecordList(userId,page);
            }
        });
    }


    @Override
    public void getInviteRecordListSuccess(List<InviteRecordInfo> infos) {
        if(page==1){
            refreshLayout.finishRefresh();
            inviteRecordInfos.clear();
            inviteRecordInfos.addAll(infos);
            recyclerAdapter.replaceData(inviteRecordInfos);
        }else {
            refreshLayout.finishLoadMore();
            if(infos!=null && infos.size()>0){
                inviteRecordInfos.addAll(infos);
                recyclerAdapter.setNewData(inviteRecordInfos);
            }else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }

        }
    }

    @Override
    public void getInviteRecordFail() {
        if(page==1){
            refreshLayout.finishRefresh();
        }else {
            refreshLayout.finishLoadMore();
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
