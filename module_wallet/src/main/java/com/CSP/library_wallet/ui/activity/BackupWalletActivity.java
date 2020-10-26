package com.uranus.library_wallet.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andjdk.library_base.annotation.BindEventBus;
import com.andjdk.library_base.base.BaseActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.widget.TitleBar;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.event.BackupMnemonicSuccess;

import butterknife.BindView;

/**
 * 备份钱包
 */
@BindEventBus
public class BackupWalletActivity extends BaseActivity {

    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.btn_create_wallet)
    Button btnCreateWallet;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_backup_wallet;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);
        final Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            final String mnemonic = bundle.getString(Constants.WALLET_MNEMONIC);

            btnCreateWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(Constants.WALLET_MNEMONIC,mnemonic);
                    goActivity(BackupMnemoincActivity.class,bundle1);
                }
            });
        }
    }

    @Override
    public void onEventBus(Object event) {
        super.onEventBus(event);
        if(event instanceof BackupMnemonicSuccess){
            if(((BackupMnemonicSuccess) event).isSuccess()){
                finish();
            }
        }
    }
}
