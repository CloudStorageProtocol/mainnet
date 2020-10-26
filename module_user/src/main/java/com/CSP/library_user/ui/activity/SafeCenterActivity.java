package com.uranus.library_user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andjdk.library_base.base.BaseActivity;
import com.andjdk.library_base.base.BaseMVPActivity;
import com.andjdk.library_base.widget.TitleBar;
import com.uranus.library_user.R;
import com.uranus.library_user.R2;
import com.uranus.library_user.contract.ForgetPasswordContract;
import com.uranus.library_user.presenter.ForgetPasswordPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author time ：2019-09-07
 * desc：
 */
public class SafeCenterActivity extends BaseMVPActivity<ForgetPasswordPresenter>
        implements ForgetPasswordContract.View {

    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.edit_old_password)
    EditText editOldPassword;
    @BindView(R2.id.edit_new_password)
    EditText editNewPassword;
    @BindView(R2.id.edit_re_password)
    EditText editRePassword;
    @BindView(R2.id.btn_ok)
    Button btnOk;

    public static void start(Context context) {
        Intent intent = new Intent(context, SafeCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_safe_center;
    }

    @Override
    protected ForgetPasswordPresenter createPresenter() {
        return new ForgetPasswordPresenter();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.resetPassword(editOldPassword.getText().toString().trim(),editNewPassword.getText().toString().trim(),editRePassword.getText().toString().trim());
            }
        });
    }

    @Override
    public void modifyPasswordSuccess() {

    }

    @Override
    public void resetPasswordSuccess() {
        finish();
    }

    @Override
    public void getSmsCodeSuccess() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
