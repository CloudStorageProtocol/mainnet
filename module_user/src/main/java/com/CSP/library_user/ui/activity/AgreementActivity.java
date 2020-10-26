package com.uranus.library_user.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;

import com.andjdk.library_base.base.BaseActivity;
import com.uranus.library_user.R;
import com.uranus.library_user.R2;

import butterknife.BindView;

public class AgreementActivity extends BaseActivity {
    @BindView(R2.id.web_view)
    WebView webView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String html = bundle.getString("agreement");
            webView.loadData(Html.fromHtml(html).toString(), "text/html", "UTF-8");
        }

    }
}
