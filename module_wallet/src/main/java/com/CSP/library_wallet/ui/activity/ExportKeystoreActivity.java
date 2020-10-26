package com.uranus.library_wallet.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andjdk.library_base.base.BaseActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.utils.SPLocalUtils;
import com.andjdk.library_base.utils.ToastUtils;
import com.andjdk.library_base.widget.TitleBar;
import com.google.gson.Gson;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.base.eth.Wallet;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 导出keystore
 */
public class ExportKeystoreActivity extends BaseActivity {


    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.edit_import_content)
    TextView editImportContent;
    @BindView(R2.id.btn_create_wallet)
    Button btnCreateWallet;

    private String keystore;

    public static void start(Context context) {
        Intent intent = new Intent(context, ExportKeystoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_export_keystore;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);

        btnCreateWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyContent();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            keystore =bundle.getString("keystore");
            editImportContent.setText(keystore);
        }


        editImportContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        editImportContent.setOnTouchListener(touchListener);
    }

    /**
     * 设置触摸事件，由于EditView与TextView都处于ScollView中，
     * 所以需要在OnTouch事件中通知父控件不拦截子控件事件
     */
    private View.OnTouchListener touchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN
                    || event.getAction() == MotionEvent.ACTION_MOVE){
                //按下或滑动时请求父节点不拦截子节点
                v.getParent().requestDisallowInterceptTouchEvent(true);
            }
            if(event.getAction() == MotionEvent.ACTION_UP){
                //抬起时请求父节点拦截子节点
                v.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        }
    };


    private void copyContent() {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", keystore);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastUtils.showShort("复制成功");
    }
}
