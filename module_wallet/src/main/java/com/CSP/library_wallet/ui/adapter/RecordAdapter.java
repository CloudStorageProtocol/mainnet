package com.uranus.library_wallet.ui.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.andjdk.library_base.utils.DateFormatUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.base.wallet.entity.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

public class RecordAdapter extends BaseQuickAdapter<Transaction, BaseViewHolder> {

    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public RecordAdapter(int layoutResId, @Nullable List<Transaction> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, Transaction item) {
        String tokenSymbol = item.getTokenSymbol();
        int tokenDecimal = item.getTokenDecimal();
        if(TextUtils.isEmpty(tokenSymbol)){
            tokenSymbol = "ETH";
            tokenDecimal = 18;
        }
        helper.setText(R.id.tv_coin_name,tokenSymbol);
        helper.setText(R.id.tv_time, DateFormatUtils.getDateToString(item.getTimeStamp()));

        helper.setText(R.id.tv_price,(type==1 ?"+" : "-") +getScaledValue(item.getValue(),tokenDecimal));

        helper.setText(R.id.tv_state,item.getConfirmations()>7 ? "" : "确认中" );
    }

    private String getScaledValue(String valueStr, long decimals) {
        // Perform decimal conversion
        BigDecimal value = new BigDecimal(valueStr);
        value = value.divide(new BigDecimal(Math.pow(10, decimals)));
        int scale = 3 - value.precision() + value.scale();
        return value.setScale(scale, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }
}