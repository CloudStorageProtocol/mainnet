package com.uranus.library_wallet.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uranus.library_wallet.R;
import java.util.List;

/**
 * Created by mac on 2019-09-10.
 * <p>
 * time ：2019-09-10
 * desc：
 */
public class WordItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public WordItemAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_word,item);
    }
}
