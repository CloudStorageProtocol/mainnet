package com.uranus.library_wallet.ui.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.andjdk.library_base.constants.Constants;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.base.wallet.entity.Token;
import com.uranus.library_wallet.base.wallet.entity.TokenInfo;
import com.uranus.library_wallet.base.wallet.entity.TokenItem;
import com.uranus.library_wallet.bean.MyWalletInfo;
import com.uranus.library_wallet.ui.activity.RechargeActivity;
import com.uranus.library_wallet.ui.activity.RolloutWalletActivity;
import com.uranus.library_wallet.ui.activity.TransactionRecordActivity;

import java.util.List;


public class WalletListAdapter extends BaseQuickAdapter<Token, BaseViewHolder> {

    public WalletListAdapter(int layoutResId, @Nullable List<Token> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, final Token item) {
        TokenInfo info = item.tokenInfo;
        TextView rolloutTv = helper.getView(R.id.tv_roll_out);
        if(info.symbol.equals("ETH")){
            helper.getView(R.id.lly_container).setBackgroundResource(R.mipmap.assets_eth_bg);
            rolloutTv.setBackgroundResource(R.drawable.btn_bg);
        }else if(info.symbol.equals("USDT")){
            helper.getView(R.id.lly_container).setBackgroundResource(R.mipmap.assets_usdt_bg);
            rolloutTv.setBackgroundResource(R.drawable.btn_bg);
        }else {
            helper.getView(R.id.lly_container).setBackgroundResource(R.mipmap.assets_uranus_bg);
            rolloutTv.setBackgroundResource(R.drawable.btn_bg_urac);
        }
        helper.setText(R.id.tv_coin_name,info.name);
        helper.setText(R.id.tv_balance,item.balance);
//        helper.setText(R.id.tv_equal_cny,item.getEqualCNY());

        helper.getView(R.id.tv_roll_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(RechargeActivity.class,item);
            }
        });

        helper.getView(R.id.tv_roll_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(RolloutWalletActivity.class,item);
            }
        });

        helper.getView(R.id.tv_recharge_recording).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(TransactionRecordActivity.class,item);
            }
        });
    }

    private void goActivity(Class<?> cls,Token token){
        Intent intent = new Intent(mContext,cls);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.CURRENT_TOKEN_INFO,token);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
