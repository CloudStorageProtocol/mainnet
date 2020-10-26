package com.uranus.library_wallet.ui.fragment;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andjdk.library_base.base.BaseFragment;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.utils.EventBusHelper;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.andjdk.library_base.utils.ToastUtils;
import com.google.gson.Gson;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.base.wallet.interact.CreateWalletInteract;
import com.uranus.library_wallet.base.wallet.utils.ETHWalletUtils;
import com.uranus.library_wallet.event.UpdateTokenEvent;


import butterknife.BindView;

/**
 * 通过助记词导入钱包
 */

public class ImportMnemoincFragment extends BaseFragment {

    @BindView(R2.id.tv_import_tip)
    TextView tvImportTip;
    @BindView(R2.id.edit_import_content)
    EditText editImportContent;
    @BindView(R2.id.edit_wallet_password)
    EditText editWalletPassword;
    @BindView(R2.id.edit_re_wallet_password)
    EditText editReWalletPassword;
    @BindView(R2.id.edit_password_tip)
    EditText editPasswordTip;
    @BindView(R2.id.btn_create_wallet)
    Button btnCreateWallet;

    private CreateWalletInteract createWalletInteract;

    public static ImportMnemoincFragment create() {
        ImportMnemoincFragment importFragment = new ImportMnemoincFragment();
        return importFragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_wallet_import;
    }

    @Override
    protected void initView(View rootView) {

        btnCreateWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    loadWalletByMnemonic(editImportContent.getText().toString(),editWalletPassword.getText().toString().trim());

                }
            }
        });
    }


    private boolean check() {
        String content = editImportContent.getText().toString();
        String password = editWalletPassword.getText().toString().trim();
        String password2 = editReWalletPassword.getText().toString().trim();
        String passwordTip = tvImportTip.getText().toString().trim();

        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShort("请输入");
            return false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 8) {
            ToastUtils.showShort("请输入不少于8个字符，建议字母、数字混合。");
            return false;
        }
        if (TextUtils.isEmpty(password2) || password2.length() < 8) {
            ToastUtils.showShort("请输入不少于8个字符，建议字母、数字混合。");
            return false;
        }
        if (!password.equals(password2)) {
            ToastUtils.showShort("两次密码输入不一致");
            return false;
        }
        return true;
    }

    @Override
    protected void initData() {
        createWalletInteract = new CreateWalletInteract();
    }

    @SuppressLint("CheckResult")
    private void loadWalletByMnemonic(final String mnemonic, final String pwd) {
        showDialog("导入钱包中…");
        createWalletInteract.loadWalletByMnemonic(ETHWalletUtils.ETH_JAXX_TYPE, mnemonic, pwd).subscribe(this::loadSuccess, this::onError);
    }

    private void onError(Throwable throwable) {
        ToastUtils.showShort("导入失败，请检查输入是否正确");
    }

    private void loadSuccess(ETHWallet ethWallet) {
        dismissDialog();
        EventBusHelper.post(new UpdateTokenEvent(true));
    }


}
