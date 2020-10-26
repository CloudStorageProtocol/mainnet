package com.uranus.library_wallet.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andjdk.library_base.annotation.BindEventBus;
import com.andjdk.library_base.base.BaseActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.utils.EventBusHelper;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.andjdk.library_base.utils.ToastUtils;
import com.andjdk.library_base.widget.TitleBar;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.base.wallet.WalletInit;
import com.uranus.library_wallet.base.wallet.interact.CreateWalletInteract;
import com.uranus.library_wallet.base.wallet.interact.FetchTokensInteract;
import com.uranus.library_wallet.base.wallet.repository.RepositoryFactory;
import com.uranus.library_wallet.event.BackupMnemonicSuccess;
import com.uranus.library_wallet.event.UpdateTokenEvent;

import butterknife.BindView;

/**
 * 创建钱包
 */

@BindEventBus
public class CreateWalletActivity extends BaseActivity {

    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.edit_wallet_name)
    EditText editWalletName;
    @BindView(R2.id.edit_wallet_password)
    EditText editWalletPassword;
    @BindView(R2.id.edit_re_wallet_password)
    EditText editReWalletPassword;
    @BindView(R2.id.edit_password_tip)
    EditText editPasswordTip;
    @BindView(R2.id.btn_create_wallet)
    Button btnCreateWallet;


    private String walletName;
    private String walletPassword;
    private String walletPasswordTip;
    private CreateWalletInteract createWalletInteract;
    private FetchTokensInteract fetchTokensInteract;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_create_wallet;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);
        createWalletInteract = new CreateWalletInteract();

        RepositoryFactory rf = WalletInit.repositoryFactory();
        fetchTokensInteract = new FetchTokensInteract(rf.tokenRepository);


        btnCreateWallet.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                if (check()) {
                    showDialog("创建钱包中…");
                    createWalletInteract.create(walletName, walletPassword, walletPassword, walletPasswordTip).subscribe(this::jumpToWalletBackUp, this::showError);
                }
            }

            private void showError(Throwable throwable) {
                dismissDialog();
                ToastUtils.showShort("钱包创建失败");
            }

            @SuppressLint("CheckResult")
            private void jumpToWalletBackUp(ETHWallet ethWallet) {
                dismissDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.WALLET_MNEMONIC, ethWallet.getMnemonic());
                goActivity(BackupWalletActivity.class, bundle);
                EventBusHelper.post(new UpdateTokenEvent(true));
            }

        });
    }

    private boolean check() {
        walletName = editWalletName.getText().toString().trim();
        if (TextUtils.isEmpty(walletName)) {
            ToastUtils.showShort("请输入钱包名称");
            return false;
        }
        if (SPLocalUtils.getInstance().getString(Constants.WALLET_NAME).equals(walletName)) {
            ToastUtils.showShort("钱包名已存在");
            return false;
        }

        walletPassword = editWalletPassword.getText().toString().trim();
        String password2 = editReWalletPassword.getText().toString().trim();
        if (TextUtils.isEmpty(walletPassword)) {
            ToastUtils.showShort("请输入钱包密码");
            return false;
        }
        if (walletPassword.length() < 8) {
            ToastUtils.showShort("请输入不少于8个字符，建议字母、数字混合。");
            return false;
        }
        if (TextUtils.isEmpty(password2)) {
            ToastUtils.showShort("请输入确认钱包密码");
            return false;
        }
        if (!walletPassword.equals(password2)) {
            ToastUtils.showShort("两次输入的密码不一致，请重新输入");
            editWalletPassword.setText("");
            editReWalletPassword.setText("");
            return false;
        }

        walletPasswordTip = editWalletPassword.getText().toString().trim();
        if (TextUtils.isEmpty(editPasswordTip.getText().toString().trim())) {
            ToastUtils.showShort("请输入密码提示信息");
            return false;
        }

        return true;
    }


    @Override
    public void onEventBus(Object event) {
        super.onEventBus(event);
        if (event instanceof BackupMnemonicSuccess) {
            if (((BackupMnemonicSuccess) event).isSuccess()) {
                finish();
            }
        }
    }
}
