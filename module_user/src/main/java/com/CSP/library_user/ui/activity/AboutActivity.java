package com.uranus.library_user.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andjdk.library_base.base.BaseMVPActivity;
import com.andjdk.library_base.base.BaseWebActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.utils.ToastUtils;
import com.andjdk.library_base.widget.TitleBar;
import com.uranus.library_user.R;
import com.uranus.library_user.R2;
import com.uranus.library_user.contract.AboutContract;
import com.uranus.library_user.presenter.AboutPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author time ：2019-09-07
 * desc：关于我们
 */
public class AboutActivity extends BaseMVPActivity<AboutPresenter>
        implements AboutContract.View {

    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.tv_user_agreement)
    TextView tvUserAgreement;
    @BindView(R2.id.tv_latest_version)
    TextView tvLatestVersion;
    @BindView(R2.id.fly_version)
    FrameLayout flyVersion;

    private String agreementContent;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected AboutPresenter createPresenter() {
        return new AboutPresenter();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);

        tvUserAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle =new Bundle();
                bundle.putString(Constants.WEB_URL, Constants.WEB_URL_AGREEMENT);

                goActivity(BaseWebActivity.class,bundle);
            }
        });
        flyVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                bundle.putString(Constants.WEB_URL,Constants.WEB_URL_VERSION);
                goActivity(BaseWebActivity.class,bundle);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void getAboutMeSuccess() {

    }

    @Override
    public void getLatestVersion() {

    }

    @Override
    public void getAgreementSuccess(String string) {
        agreementContent = string;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
