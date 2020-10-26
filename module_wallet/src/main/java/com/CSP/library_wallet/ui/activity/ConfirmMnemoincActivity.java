package com.uranus.library_wallet.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andjdk.library_base.base.BaseActivity;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.utils.EventBusHelper;
import com.andjdk.library_base.widget.GridSpacingItemDecoration;
import com.andjdk.library_base.widget.TitleBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.event.BackupMnemonicSuccess;
import com.uranus.library_wallet.ui.adapter.WordItemAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 确认助记词
 */
public class ConfirmMnemoincActivity extends BaseActivity {


    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.recycler_view_world)
    RecyclerView recyclerViewWorld;
    @BindView(R2.id.recycler_view_op_world)
    RecyclerView recyclerViewOpWorld;
    @BindView(R2.id.btn_ok)
    Button btnOk;

    private WordItemAdapter opwordItemAdapter;
    private WordItemAdapter wordItemAdapter;
    private String wordCache = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_confirm_mnemoinc;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(titleBar);

        recyclerViewWorld.setLayoutManager(new GridLayoutManager(this,4));
        wordItemAdapter = new WordItemAdapter(R.layout.item_world,new ArrayList<String>());
        recyclerViewWorld.setAdapter(wordItemAdapter);
        wordItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                opwordItemAdapter.addData(wordItemAdapter.getData().get(position));
                wordItemAdapter.remove(position);
                validate();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            String mnemonic = bundle.getString(Constants.WALLET_MNEMONIC);
            if(!TextUtils.isEmpty(mnemonic) && mnemonic !=null){
                String[] words = mnemonic.split(" ");
                final List<String> datas =new ArrayList<>();
                StringBuilder stringBuffer = new StringBuilder();
                for (String word : words) {
                    datas.add(word);
                    stringBuffer.append(word);
                }
                wordCache = stringBuffer.toString();
                Collections.shuffle(datas);//打乱顺序
                recyclerViewOpWorld.setLayoutManager(new GridLayoutManager(this,4));
                recyclerViewOpWorld.addItemDecoration(new GridSpacingItemDecoration(4, 20,false));
                opwordItemAdapter = new WordItemAdapter(R.layout.item_world2,datas);
                recyclerViewOpWorld.setAdapter(opwordItemAdapter);
                opwordItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        wordItemAdapter.addData(datas.get(position));
                        opwordItemAdapter.remove(position);
                        validate();
                    }
                });
            }



        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    EventBusHelper.post(new BackupMnemonicSuccess(true));
                    finish();
                }
            }
        });

    }

    private boolean validate(){
        List<String> results = wordItemAdapter.getData();
        StringBuilder opWords = new StringBuilder();
        for (String word:results) {
            opWords.append(word);
        }

        boolean result =opWords.toString().equals(wordCache);
        btnOk.setEnabled(result);
        return result;
    }

}
