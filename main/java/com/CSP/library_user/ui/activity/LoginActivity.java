package com.uranus.library_user.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.andjdk.library_base.base.BaseMVPActivity;
import com.andjdk.library_base.router.RouterActivityPath;
import com.andjdk.library_base.router.RouterHelper;
import com.andjdk.library_base.utils.SoftKeyboardUtil;
import com.andjdk.library_base.utils.ToastUtils;
import com.andjdk.library_base.widget.TitleBar;
import com.uranus.library_user.R;
import com.uranus.library_user.R2;
import com.uranus.library_user.bean.LoginResult;
import com.uranus.library_user.contract.LoginContract;
import com.uranus.library_user.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author time ：2019-09-07
 * desc：用户登录
 */
@Route(path = RouterActivityPath.User.PAGER_LOGIN)
public class LoginActivity extends BaseMVPActivity<LoginPresenter>
        implements LoginContract.View, View.OnClickListener {

    @BindView(R2.id.edit_email)
    EditText editEmail;
    @BindView(R2.id.edit_password)
    EditText editPassword;
    @BindView(R2.id.btn_login)
    Button btnLogin;
    @BindView(R2.id.tv_enter_modify_password)
    TextView tvEnterModifyPassword;
    @BindView(R2.id.tv_enter_register)
    TextView tvEnterRegister;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @OnClick({R2.id.btn_login,R2.id.tv_enter_modify_password,R2.id.tv_enter_register})
    public void onClick(View view){
        int id = view.getId();
        if(id == R.id.btn_login){
            SoftKeyboardUtil.hideSoftKeyboard(view);
            presenter.login(editEmail.getText().toString().trim(),editPassword.getText().toString().trim());
        }else if (id == R.id.tv_enter_modify_password) {
            goActivity(FindPasswordActivity.class);
        } else if (id == R.id.tv_enter_register) {
            goActivity(RegisterActivity.class);
        }
    }


    @Override
    public void loginSuccess() {
        RouterHelper.getInstance().Skip(RouterActivityPath.Main.PAGER_MAIN);
        finish();
    }

    @Override
    public void showLoading() {
        showDialog("登录中...");
    }

    @Override
    public void hideLoading() {
        dismissDialog();
    }
}
