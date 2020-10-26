package com.uranus.library_user.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.andjdk.library_base.base.BaseActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.utils.GlideUtils;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.andjdk.library_base.utils.SharePicUtils;
import com.andjdk.library_base.widget.TitleBar;
import com.google.gson.Gson;
import com.uranus.library_user.R;
import com.uranus.library_user.R2;
import com.uranus.library_user.bean.InviteCodeInfo;

import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;

/**
 * @Author time ：2019-09-07
 * desc：
 */
public class InviteFriendsActivity extends BaseActivity {
    private static final String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/QrCode.png";

    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.tv_invite_code)
    TextView tvInviteCode;
    @BindView(R2.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R2.id.fly_container)
    FrameLayout flyContainer;
    @BindView(R2.id.tv_share)
    TextView tvShare;



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_invite_friends;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);
        titleBar.setOnRightTitleBarListener(new TitleBar.OnRightTitleBarListener() {
            @Override
            public void onListener(View v) {
                goActivity(InviteRecordActivity.class);
            }
        });

        String json = SPLocalUtils.getInstance().getString(Constants.INVITE_FRIENDS);
        InviteCodeInfo inviteCodeInfo = new Gson().fromJson(json,InviteCodeInfo.class);
        tvInviteCode.setText(inviteCodeInfo.getInvite_code());
        GlideUtils.loadImage(mContext,inviteCodeInfo.getQr_code(),ivQrCode);

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    private void share(){
        try {
            SharePicUtils.savePic(savePath, SharePicUtils.getCacheBitmapFromView(flyContainer));
            SharePicUtils.SharePic(InviteFriendsActivity.this, savePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
