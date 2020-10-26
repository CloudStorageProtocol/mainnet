package com.uranus.library_wallet.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andjdk.library_base.annotation.BindEventBus;
import com.andjdk.library_base.base.BaseActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.widget.TitleBar;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.event.BackupMnemonicSuccess;
import com.uranus.library_wallet.ui.adapter.WordItemAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 备份助记词
 */

@BindEventBus
public class BackupMnemoincActivity extends BaseActivity {


    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.recycler_view_word)
    RecyclerView recyclerViewWord;
    @BindView(R2.id.btn_ok)
    Button btnOk;

    private List<String> datas =new ArrayList<>();
    private WordItemAdapter wordItemAdapter;
    private String mnemonic;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_backup_mnemoinc;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);

        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        recyclerViewWord.setLayoutManager(layoutManager);


        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            mnemonic = bundle.getString(Constants.WALLET_MNEMONIC);
            if(!TextUtils.isEmpty(mnemonic)){
                String[] words = mnemonic.split(" ");
                datas.addAll(Arrays.asList(words));
            }

            wordItemAdapter = new WordItemAdapter(R.layout.item_world,datas);
            recyclerViewWord.setAdapter(wordItemAdapter);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.WALLET_MNEMONIC,mnemonic);
                goActivity(ConfirmMnemoincActivity.class,bundle1);
            }
        });
    }

    @Override
    public void onEventBus(Object event) {
        super.onEventBus(event);
        if(event instanceof BackupMnemonicSuccess){
            if(((BackupMnemonicSuccess) event).isSuccess()){
                finish();
            }
        }
    }
}
