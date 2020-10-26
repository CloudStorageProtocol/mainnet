package com.uranus.library_user.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.andjdk.library_base.annotation.BindEventBus;
import com.andjdk.library_base.base.BaseMVPFragment;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.router.RouterFragmentPath;
import com.andjdk.library_base.utils.GlideUtils;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.uranus.library_user.R;
import com.uranus.library_user.R2;
import com.uranus.library_user.bean.InviteCodeInfo;
import com.uranus.library_user.bean.UserInfoResult;
import com.uranus.library_user.contract.MeContract;
import com.uranus.library_user.event.UpdateUserInfoEvent;
import com.uranus.library_user.presenter.MePresenter;
import com.uranus.library_user.ui.activity.AboutActivity;
import com.uranus.library_user.ui.activity.InviteFriendsActivity;
import com.uranus.library_user.ui.activity.LoginActivity;
import com.uranus.library_user.ui.activity.SafeCenterActivity;
import com.uranus.library_user.ui.activity.UserDetailActivity;

import butterknife.BindView;


/**
 *
 */
@Route(path = RouterFragmentPath.User.PAGER_ME)
@BindEventBus
public class MeFragment extends BaseMVPFragment<MePresenter> implements MeContract.View {

    @BindView(R2.id.tv_user_name)
    TextView tvUserName;
    @BindView(R2.id.tv_level)
    TextView tvLevel;
    @BindView(R2.id.tv_user_id)
    TextView tvUserId;
    @BindView(R2.id.iv_user_head)
    ImageView ivUserHead;
    @BindView(R2.id.tv_invite_friends)
    TextView tvInviteFriends;
    @BindView(R2.id.tv_safe_center)
    TextView tvSafeCenter;
    @BindView(R2.id.tv_about)
    TextView tvAbout;
    @BindView(R2.id.btn_logout)
    Button btnLogout;
    @BindView(R2.id.lly_user_info)
    LinearLayout llyUserInfo;

    private UserInfoResult userInfoResult;

    @Override
    protected MePresenter createPresenter() {
        return new MePresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView(View rootView) {

        tvInviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(InviteFriendsActivity.class);
            }
        });

        tvSafeCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(SafeCenterActivity.class);
            }
        });

        tvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(AboutActivity.class);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.logout();
            }
        });

        llyUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(UserDetailActivity.class);
            }
        });

        ivUserHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(UserDetailActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        String json = SPLocalUtils.getInstance().getString(Constants.USER_INFO);
        if (TextUtils.isEmpty(json)) {
            presenter.getUserInfo();
        } else {
            userInfoResult = new Gson().fromJson(json, UserInfoResult.class);
            tvUserName.setText(userInfoResult.getNickname());
            tvLevel.setText("U"+userInfoResult.getLevel());
            tvUserId.setText("ID:"+userInfoResult.getUid());
            if(!TextUtils.isEmpty(userInfoResult.getAvatar())){
                GlideUtils.loadCircleImage(mContext,userInfoResult.getAvatar(),ivUserHead);
            }

            presenter.getInviteCode();
        }
    }

    @Override
    public void getUserInfoSuccess() {
        initData();
    }

    @Override
    public void getInviteCodeSuccess(InviteCodeInfo info) {
        String json = new Gson().toJson(info);
        SPLocalUtils.getInstance().put(Constants.INVITE_FRIENDS,json);
    }

    @Override
    public void logoutSuccess() {
        goActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onEventBus(Object event) {
        super.onEventBus(event);
        if(event instanceof UpdateUserInfoEvent){
            if(((UpdateUserInfoEvent) event).isUpdata()){
                initData();
            }
        }
    }
}
