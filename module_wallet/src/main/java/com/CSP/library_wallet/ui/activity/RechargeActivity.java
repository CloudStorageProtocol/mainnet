package com.uranus.library_wallet.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andjdk.library_base.base.BaseActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.utils.Utils;
import com.andjdk.library_base.widget.TitleBar;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.andjdk.library_base.dao.WalletDaoUtils;
import com.uranus.library_wallet.base.wallet.entity.Token;
import com.uranus.library_wallet.base.wallet.entity.TokenInfo;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;

/**
 * @Author time ：2019-09-07
 * desc：充值
 */
public class RechargeActivity extends BaseActivity {


    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R2.id.tv_address)
    TextView tvAddress;
    @BindView(R2.id.tv_copy_address)
    TextView tvCopyAddress;
    @BindView(R2.id.iv_coin)
    ImageView ivCoin;
    @BindView(R2.id.tv_roll_in)
    TextView tvRollIn;


    private Token token;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);
        Bundle bundle = getIntent().getExtras();
        ETHWallet wallet = WalletDaoUtils.getCurrent();
        if(bundle !=null){
            token = (Token) bundle.getParcelable(Constants.CURRENT_TOKEN_INFO);
            if(token !=null ){


                TokenInfo info = token.tokenInfo;

                if(info.symbol.equals("ETH")){
                    tvRollIn.setText("转入ETH");
                    ivCoin.setImageResource(R.mipmap.eth);
                }else if(info.symbol.equals("USDT")){
                    tvRollIn.setText("转入USDT");
                    ivCoin.setImageResource(R.mipmap.usdt);
                }else if(info.symbol.equals("URAC")){
                    tvRollIn.setText("转入URAC");
                    ivCoin.setImageResource(R.mipmap.urac);
                }

                Bitmap bitmap = CodeUtils.createImage(wallet.getAddress(),220,220,null);

                ivQrCode.setImageBitmap(bitmap);

                tvAddress.setText(wallet.getAddress());

                tvCopyAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.copyContent(mContext,wallet.getAddress());
                    }
                });
            }
        }
    }

}
