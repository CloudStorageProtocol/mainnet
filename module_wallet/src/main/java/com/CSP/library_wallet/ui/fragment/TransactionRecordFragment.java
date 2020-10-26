package com.uranus.library_wallet.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.andjdk.library_base.base.BaseFragment;
import com.andjdk.library_base.widget.VerticalRecycleView;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.base.wallet.entity.Transaction;
import com.uranus.library_wallet.ui.adapter.RecordAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 交易记录 充值 / 提现
 */
public class TransactionRecordFragment extends BaseFragment{


    @BindView(R2.id.recycler_view)
    VerticalRecycleView recyclerView;

    private int type = 1;//类型 1=充值 2 =提现

    private RecordAdapter recyclerAdapter;
    private List<Transaction> rollDatas= new ArrayList<>();

    public static TransactionRecordFragment create(int type, ArrayList<Transaction> rollDatas) {
        TransactionRecordFragment importFragment = new TransactionRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putSerializable("rollDatas", rollDatas);
        importFragment.setArguments(bundle);
        return importFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = getArguments().getInt("type");
            rollDatas = (List<Transaction>) getArguments().getSerializable("rollDatas");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_transaction_record;
    }

    @Override
    protected void initView(View rootView) {

        recyclerAdapter = new RecordAdapter(R.layout.item_record_list,rollDatas);
        recyclerAdapter.setType(type);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void initData() {

    }

}
