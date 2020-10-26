package com.uranus.library_user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andjdk.library_base.base.BaseMVPActivity;
import com.andjdk.library_base.utils.RxUtil;
import com.uranus.library_user.R;
import com.uranus.library_user.R2;
import com.uranus.library_user.contract.ForgetPasswordContract;
import com.uranus.library_user.presenter.ForgetPasswordPresenter;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author time ：2019-09-06
 * desc：找回密码
 */
public class FindPasswordActivity extends BaseMVPActivity<ForgetPasswordPresenter>
        implements ForgetPasswordContract.View {


    @BindView(R2.id.edit_email)
    EditText editEmail;
    @BindView(R2.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R2.id.edit_email_code)
    EditText editEmailCode;
    @BindView(R2.id.tv_email_error_tip)
    TextView tvEmailErrorTip;
    @BindView(R2.id.edit_password)
    EditText editPassword;
    @BindView(R2.id.tv_enter_register)
    TextView tvEnterRegister;
    @BindView(R2.id.btn_ok)
    Button btnOk;
    @BindView(R2.id.tv_cancel)
    TextView tvCancel;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_find_password;
    }

    @Override
    protected ForgetPasswordPresenter createPresenter() {
        return new ForgetPasswordPresenter();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendCode(editEmail.getText().toString().trim());
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.modifyPassword(editEmail.getText().toString().trim(), editEmailCode.getText().toString().trim(), editPassword.getText().toString().trim());
            }
        });

        tvEnterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(RegisterActivity.class);
            }
        });
    }

    @Override
    public void modifyPasswordSuccess() {
        finish();
    }

    @Override
    public void resetPasswordSuccess() {

    }

    @Override
    public void getSmsCodeSuccess() {
        timeCount();
    }

    private Disposable disposable;

    private void timeCount() {
        RxUtil.timeCountDown(60)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        tvGetCode.setEnabled(false);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        tvGetCode.setText(String.format("%ss", integer));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        tvGetCode.setEnabled(true);
                        tvGetCode.setText("获取验证码");
                    }
                });
    }

    public void onDestroy() {
        super.onDestroy();
        if(disposable !=null){
            disposable.dispose();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
