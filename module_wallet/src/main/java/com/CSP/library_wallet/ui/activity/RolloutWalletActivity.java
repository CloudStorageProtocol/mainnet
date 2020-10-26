package com.uranus.library_wallet.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andjdk.library_base.annotation.BindEventBus;
import com.andjdk.library_base.base.BaseMVPActivity;
import com.andjdk.library_base.base.CaptureActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.event.ScanResultEvent;
import com.andjdk.library_base.utils.Lmsg;
import com.andjdk.library_base.utils.ToastUtils;
import com.andjdk.library_base.widget.CommonDialog;
import com.andjdk.library_base.widget.TitleBar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.andjdk.library_base.dao.WalletDaoUtils;
import com.uranus.library_wallet.base.wallet.C;
import com.uranus.library_wallet.base.wallet.WalletInit;
import com.uranus.library_wallet.base.wallet.entity.Token;
import com.uranus.library_wallet.base.wallet.interact.CreateTransactionInteract;
import com.uranus.library_wallet.base.wallet.repository.RepositoryFactory;
import com.uranus.library_wallet.base.wallet.utils.BalanceUtils;
import com.uranus.library_wallet.base.wallet.utils.EthUtils;
import com.uranus.library_wallet.contract.RollInWalletContract;
import com.uranus.library_wallet.presenter.RollInWalletPresenter;

import java.math.BigDecimal;
import java.math.BigInteger;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author time ：2019-09-06
 * desc：提现
 */

@BindEventBus
public class RolloutWalletActivity extends BaseMVPActivity<RollInWalletPresenter> implements RollInWalletContract.View {

    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.tv_coin_name)
    TextView tvCoinName;
    @BindView(R2.id.tv_balance)
    TextView tvBalance;
    @BindView(R2.id.edit_coin_num)
    EditText editCoinNum;
    @BindView(R2.id.edit_address)
    EditText editAddress;
    @BindView(R2.id.tv_remarks)
    EditText tvRemarks;
    @BindView(R2.id.tv_service_charge)
    TextView tvServiceCharge;
    @BindView(R2.id.btn_sure)
    Button btnSure;

    private RxPermissions rxPermission;

    private Token token;
    private CreateTransactionInteract createTransactionInteract;



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_roll_out_wallet;
    }

    @Override
    protected RollInWalletPresenter createPresenter() {
        return new RollInWalletPresenter();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        RepositoryFactory rf = WalletInit.repositoryFactory();
        createTransactionInteract = new CreateTransactionInteract(rf.ethereumNetworkRepository);

        onClickToolBarBack(titleBar);
        rxPermission = new RxPermissions(RolloutWalletActivity.this);
        titleBar.setOnRightTitleBarListener(new TitleBar.OnRightTitleBarListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onListener(View v) {
                rxPermission.requestEach(Manifest.permission.CAMERA).subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            goActivity(CaptureActivity.class);
                        }
                    }
                });
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            token = (Token) bundle.getParcelable(Constants.CURRENT_TOKEN_INFO);
            if (token != null) {
                tvCoinName.setText(token.tokenInfo.name);
                tvBalance.setText(String.format("余额：%s", token.balance));
                editAddress.setHint(String.format("请输入有效的 %s 地址", token.tokenInfo.name));
            }
        }

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String toAddress = editAddress.getText().toString().trim();
                final String toCoinNum = editCoinNum.getText().toString().trim();
                if (TextUtils.isEmpty(toCoinNum)) {
                    ToastUtils.showShort("请输入金额");
                    return;
                }
                if (TextUtils.isEmpty(toAddress)) {
                    ToastUtils.showShort("请输入地址");
                    return;
                }

                new CommonDialog(mContext, new CommonDialog.OnPasswordCLickListener() {

                    @SuppressLint("CheckResult")
                    @Override
                    public void onActionButtonClick(int position, String password) {
                        if (position == CommonDialog.DIALOG_OK) {
                            ETHWallet wallet = WalletDaoUtils.getCurrent();
                            if (wallet == null) {
                                return;
                            }

                            if(!TextUtils.isEmpty(password) && password.equals(wallet.getPassword())){
                                if(TextUtils.isEmpty(token.tokenInfo.address)){
                                    createTransactionInteract.createEthTransaction(wallet, toAddress, EthUtils.toBigInteger(toCoinNum), C.GAS_PRICE, C.GAS_LIMIT, password)
                                            .subscribeOn(Schedulers.io())
                                            .subscribe(this::onCreateTransaction, this::onError);
                                }else {
                                    createTransactionInteract.createERC20Transfer(wallet, toAddress,token.tokenInfo.address,
                                            BalanceUtils.tokenToWei(new BigDecimal(toCoinNum), token.tokenInfo.decimals                               ).toBigInteger(),
                                            C.GAS_PRICE, C.GAS_LIMIT, password)
                                            .subscribeOn(Schedulers.io())
                                            .subscribe(this::onCreateTransaction, this::onError);
                                }

                                ToastUtils.showShort("转账已提交");

                            }else {
                                ToastUtils.showShort("请输入正确的密码");
                            }

                        }
                    }

                    private void onError(Throwable throwable) {
                        Lmsg.d("dddd--onCreateTransaction--onError--"+throwable.toString());
                    }

                    private void onCreateTransaction(String s) {
                        Lmsg.d("dddd--onCreateTransaction----"+s);

                    }
                }).show();
            }
        });
    }


    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        super.initData();
//        presenter.getFree(1);
        createTransactionInteract.getCurrentGasPrice()
                .subscribeOn(Schedulers.io())
                .subscribe(this::getGasPriceSuccess);
    }

    private void getGasPriceSuccess(BigInteger object) {
        tvServiceCharge.post(new Runnable() {
            @Override
            public void run() {
                tvServiceCharge.setText(String.format("%s ether",  EthUtils.toStringNum(object)));
            }
        });

    }

    @Override
    public void onEventBus(Object event) {
        super.onEventBus(event);
        if (event instanceof ScanResultEvent) {
            editAddress.setText(((ScanResultEvent) event).getScanResult());
        }
    }

    @Override
    public void showLoading() {
        showDialog("加载中...");
    }

    @Override
    public void hideLoading() {
        dismissDialog();
    }

    @Override
    public void getFreeSuccess(String s) {
        tvServiceCharge.setText(String.format("%s ether", s));
    }

    @Override
    public void getTransactionSuccess(String str) {

    }
}
