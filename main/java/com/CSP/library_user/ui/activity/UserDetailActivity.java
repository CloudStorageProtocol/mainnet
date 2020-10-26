package com.uranus.library_user.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.andjdk.library_base.base.BaseMVPActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.manager.Glide4Engine;
import com.andjdk.library_base.router.RouterActivityPath;
import com.andjdk.library_base.utils.EventBusHelper;
import com.andjdk.library_base.utils.FileUtils;
import com.andjdk.library_base.utils.GlideUtils;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.andjdk.library_base.widget.CommonDialog;
import com.andjdk.library_base.widget.TitleBar;
import com.google.gson.Gson;
import com.huantansheng.easyphotos.EasyPhotos;
import com.uranus.library_user.R;
import com.uranus.library_user.R2;
import com.uranus.library_user.bean.UserInfoResult;
import com.uranus.library_user.contract.ModifyUserInfoContract;
import com.uranus.library_user.event.UpdateUserInfoEvent;
import com.uranus.library_user.presenter.ModifyUserInfoPresenter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 用户详情
 */
@Route(path = RouterActivityPath.User.PAGER_USERDETAIL)
public class UserDetailActivity extends BaseMVPActivity<ModifyUserInfoPresenter>
        implements ModifyUserInfoContract.View {


    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R2.id.fly_user_head)
    FrameLayout flyUserHead;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.fly_nickname)
    FrameLayout flyNickname;

    private String nickname;
    private UserInfoResult userInfoResult ;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_detail;
    }

    @Override
    protected ModifyUserInfoPresenter createPresenter() {
        return new ModifyUserInfoPresenter();
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);

        flyUserHead.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                EasyPhotos.createAlbum(UserDetailActivity.this, false, Glide4Engine.getInstance())
                        .setCleanMenu(false)
                        .setPuzzleMenu(false)
                        .start(Constants.REQUEST_CODE_CHOOSE);
            }
        });

        flyNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModifyNicknameDialog();
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        String json = SPLocalUtils.getInstance().getString(Constants.USER_INFO);
        if (!TextUtils.isEmpty(json)) {
            userInfoResult = new Gson().fromJson(json, UserInfoResult.class);
            if(!TextUtils.isEmpty(userInfoResult.getAvatar())){
                GlideUtils.loadCircleImage(mContext,userInfoResult.getAvatar(),ivAvatar);
            }
            tvNickname.setText(userInfoResult.getNickname());

        }
    }

    private void showModifyNicknameDialog(){
        View view = View.inflate(this,R.layout.dialog_edit_nickname,null);
        final EditText editText = view.findViewById(R.id.edit_nickname);
        editText.setText(userInfoResult.getNickname());
        new CommonDialog(this, view, new CommonDialog.OnButtonCLickListener() {
            @Override
            public void onActionButtonClick(int position) {
                if(CommonDialog.DIALOG_OK==position){
                    nickname = editText.getText().toString().trim();

                    presenter.modifyNickname(nickname);
                }

            }
        }).show();
    }


    @Override
    public void getUserInfoSuccess() {
        initData();
        EventBusHelper.post(new UpdateUserInfoEvent(true));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&requestCode == Constants.REQUEST_CODE_CHOOSE) {
            ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
            presenter.uploadImg(FileUtils.imageToBase64(resultPaths.get(0)));
        }
    }
}

