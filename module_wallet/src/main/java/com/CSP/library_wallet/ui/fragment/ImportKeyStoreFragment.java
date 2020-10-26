package com.uranus.library_wallet.ui.fragment;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andjdk.library_base.base.BaseFragment;
import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.utils.EventBusHelper;
import com.andjdk.library_base.utils.ToastUtils;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.base.wallet.interact.CreateWalletInteract;
import com.uranus.library_wallet.event.UpdateTokenEvent;

import butterknife.BindView;

/**
 * 导入keystore
 */
public class ImportKeyStoreFragment extends BaseFragment {

    @BindView(R2.id.tv_import_tip)
    TextView tvImportTip;
    @BindView(R2.id.edit_import_content)
    EditText editImportContent;
    @BindView(R2.id.edit_wallet_password)
    EditText editWalletPassword;
    @BindView(R2.id.btn_create_wallet)
    Button btnCreateWallet;

    private CreateWalletInteract createWalletInteract;

    public static ImportKeyStoreFragment create() {
        ImportKeyStoreFragment importFragment = new ImportKeyStoreFragment();
        return importFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_keystore_import;
    }

    @Override
    protected void initView(View rootView) {

        createWalletInteract = new CreateWalletInteract();

        btnCreateWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keystore = editImportContent.getText().toString();
                String password = editWalletPassword.getText().toString().trim();

                if (TextUtils.isEmpty(keystore)) {
                    ToastUtils.showShort("请输入");
                    return;
                }
                if (TextUtils.isEmpty(password) || password.length() < 8) {
                    ToastUtils.showShort("请输入不少于8个字符，建议字母、数字混合。");
                    return;
                }

                loadWalletByKeystore(keystore, password);

            }

        });
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("CheckResult")
    private void loadWalletByKeystore(final String keystore, final String pwd) {
        showDialog("导入钱包中…");

        createWalletInteract.loadWalletByKeystore(keystore, pwd).subscribe(this::loadSuccess, this::onError);

    }

    private void onError(Throwable throwable) {
        dismissDialog();
        ToastUtils.showShort("请检查keystore或密码是否正确");
    }

    private void loadSuccess(ETHWallet ethWallet) {
        dismissDialog();
        EventBusHelper.post(new UpdateTokenEvent(true));
    }
}
