package com.uranus.library_wallet.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andjdk.library_base.base.BaseMVPActivity;
import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.utils.EventBusHelper;
import com.andjdk.library_base.widget.TitleBar;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.andjdk.library_base.dao.WalletDaoUtils;
import com.uranus.library_wallet.contract.RollInWalletContract;
import com.uranus.library_wallet.event.UpdateInComeEvent;
import com.uranus.library_wallet.presenter.RollInWalletPresenter;

import butterknife.BindView;

/**
 * @Author time ：2019-09-06
 * desc：转入钱包
 */
public class RollInWalletActivity extends BaseMVPActivity<RollInWalletPresenter> implements RollInWalletContract.View {

    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.tv_roll_in_wallet)
    TextView tvRollInWallet;
    @BindView(R2.id.edit_coin_num)
    EditText editCoinNum;
    @BindView(R2.id.tv_all_coin_num)
    TextView tvAllCoinNum;
    @BindView(R2.id.tv_all_roll_in)
    TextView tvAllRollIn;
    @BindView(R2.id.tv_service_charge)
    TextView tvServiceCharge;
    @BindView(R2.id.btn_sure)
    Button btnSure;

    private String totalIncome;
    private String tips;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_roll_in_wallet;
    }

    @Override
    protected RollInWalletPresenter createPresenter() {
        return new RollInWalletPresenter();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            totalIncome =  bundle.getString("total_income");
            tvAllCoinNum.setText(String.format("总收益：%s U",totalIncome));
        }
        tvAllRollIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCoinNum.setText(totalIncome);
            }
        });

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value= editCoinNum.getText().toString().trim();
                ETHWallet ethWallet =WalletDaoUtils.getCurrent();
                if(!TextUtils.isEmpty(value) && ethWallet !=null){
                    presenter.getTransaction(ethWallet.getAddress(),value,tips);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getFree(1);
    }

    @Override
    public void getFreeSuccess(String s) {
        tips = s;
        tvServiceCharge.setText(String.format("%s U",s));
    }

    @Override
    public void getTransactionSuccess(String str) {
        EventBusHelper.post(new UpdateInComeEvent(true));
        finish();
    }

    @Override
    public void showLoading() {
        showDialog("");
    }

    @Override
    public void hideLoading() {
        dismissDialog();
    }
}
