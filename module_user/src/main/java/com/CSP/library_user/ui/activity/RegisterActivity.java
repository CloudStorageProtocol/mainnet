package com.uranus.library_user.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andjdk.library_base.base.BaseMVPActivity;
import com.andjdk.library_base.utils.RxUtil;
import com.andjdk.library_base.utils.ToastUtils;
import com.andjdk.library_base.widget.TitleBar;
import com.uranus.library_user.R;
import com.uranus.library_user.R2;
import com.uranus.library_user.contract.RegisterContract;
import com.uranus.library_user.presenter.RegisterPresenter;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author time ：2019-09-06
 * desc：注册
 */
public class RegisterActivity extends BaseMVPActivity<RegisterPresenter>
        implements RegisterContract.View {


    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.edit_username)
    EditText editUsername;
    @BindView(R2.id.edit_email)
    EditText editEmail;
    @BindView(R2.id.edit_password)
    EditText editPassword;
    @BindView(R2.id.edit_invite_code)
    EditText editInviteCode;
    @BindView(R2.id.btn_register)
    Button btnRegister;
    @BindView(R2.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R2.id.edit_email_code)
    EditText editEmailCode;
    @BindView(R2.id.tv_email_error_tip)
    TextView tvEmailErrorTip;
    @BindView(R2.id.tv_password_error_tip)
    TextView tvPasswordErrorTip;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_regitster;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        titleBar.setOnLeftTitleBarListener(new TitleBar.OnLeftTitleBarListener() {
            @Override
            public void onListener(View v) {
                finish();
            }
        });

        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendCode(editEmail.getText().toString());
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.register(editEmail.getText().toString().trim(),
                        editEmailCode.getText().toString().trim(),
                        editUsername.getText().toString().trim(),
                        editPassword.getText().toString().trim(),
                        editInviteCode.getText().toString().trim());
            }
        });


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

    @Override
    public void registerSuccess() {
        goActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void getSmsCodeSuccess() {
        timeCount();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(disposable !=null){
            disposable.dispose();
        }
    }
}
