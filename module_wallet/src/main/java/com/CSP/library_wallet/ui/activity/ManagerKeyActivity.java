package com.uranus.library_wallet.ui.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.andjdk.library_base.base.BaseActivity;
import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.utils.ToastUtils;
import com.andjdk.library_base.widget.CommonDialog;
import com.andjdk.library_base.widget.TitleBar;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.andjdk.library_base.dao.WalletDaoUtils;
import com.uranus.library_wallet.base.wallet.interact.ModifyWalletInteract;

import butterknife.BindView;

/**
 * 管理
 */
public class ManagerKeyActivity extends BaseActivity {

    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.lly_export_mn)
    LinearLayout llyExportMn;
    @BindView(R2.id.lly_export_keystore)
    LinearLayout llyExportKeystore;

    private ETHWallet currentWallet;
    private ModifyWalletInteract modifyWalletInteract;



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_manager_key;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);
        currentWallet = WalletDaoUtils.getCurrent();
        modifyWalletInteract = new ModifyWalletInteract();

        llyExportKeystore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPassword(true);
            }
        });

        llyExportMn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPassword(false);
            }
        });





    }

    private void inputPassword(final boolean isKeyStore) {
        new CommonDialog(this, new CommonDialog.OnPasswordCLickListener() {

            @SuppressLint("CheckResult")
            @Override
            public void onActionButtonClick(int position, String password) {
                if (position == CommonDialog.DIALOG_OK) {

                    if(!TextUtils.isEmpty(password) && password.equals(currentWallet.getPassword())){
                        if (isKeyStore) {
                            modifyWalletInteract.deriveWalletKeystore(currentWallet.getId(), password).subscribe(this::showDeriveKeystore);
                        } else {
                            copyContent();
                        }
                    }else {
                        ToastUtils.showShort("密码错误");
                    }
                }
            }

            private void showDeriveKeystore(String s) {
                Bundle bundle = new Bundle();
                bundle.putString("keystore",s);
                goActivity(ExportKeystoreActivity.class,bundle);
            }
        }).show();
    }

    private void copyContent() {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", currentWallet.getMnemonic());
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastUtils.showShort("复制成功");
    }
}
