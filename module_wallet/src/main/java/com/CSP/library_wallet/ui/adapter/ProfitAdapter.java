package com.uranus.library_wallet.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.bean.UserIncomeInfo;

import java.util.List;

/**
 * @Author time ：2019-09-08
 * desc：
 */
public class ProfitAdapter extends BaseQuickAdapter<UserIncomeInfo, BaseViewHolder> {

    public ProfitAdapter(int layoutResId, @Nullable List<UserIncomeInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, UserIncomeInfo item) {
        helper.setText(R.id.tv_user_name,item.getTitle());
        helper.setText(R.id.tv_time,item.getUpdated_at());
        helper.setText(R.id.tv_income,item.getScore()+"U");

    }
}
