package com.uranus.library_wallet.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.andjdk.library_base.base.BaseFragment;
import com.andjdk.library_base.dao.ETHWallet;
import com.andjdk.library_base.utils.Lmsg;
import com.andjdk.library_base.utils.ToastUtils;
import com.andjdk.library_base.widget.TitleBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uranus.library_wallet.R;
import com.uranus.library_wallet.R2;
import com.uranus.library_wallet.base.eth.EthUtils;
import com.andjdk.library_base.dao.WalletDaoUtils;
import com.uranus.library_wallet.base.wallet.C;
import com.uranus.library_wallet.base.wallet.WalletInit;
import com.uranus.library_wallet.base.wallet.entity.Token;
import com.uranus.library_wallet.base.wallet.entity.TokenInfo;
import com.uranus.library_wallet.base.wallet.entity.TokenItem;
import com.uranus.library_wallet.base.wallet.entity.Transaction;
import com.uranus.library_wallet.base.wallet.interact.AddTokenInteract;
import com.uranus.library_wallet.base.wallet.interact.CreateTransactionInteract;
import com.uranus.library_wallet.base.wallet.interact.CreateWalletInteract;
import com.uranus.library_wallet.base.wallet.interact.FetchTokensInteract;
import com.uranus.library_wallet.base.wallet.interact.FetchTransactionsInteract;
import com.uranus.library_wallet.base.wallet.interact.FetchWalletInteract;
import com.uranus.library_wallet.base.wallet.interact.ModifyWalletInteract;
import com.uranus.library_wallet.base.wallet.repository.EthereumNetworkRepository;
import com.uranus.library_wallet.base.wallet.repository.RepositoryFactory;
import com.uranus.library_wallet.base.wallet.repository.TransactionRepositoryType;
import com.uranus.library_wallet.base.wallet.utils.ETHWalletUtils;
import com.uranus.library_wallet.ui.activity.BackupMnemoincActivity;
import com.uranus.library_wallet.ui.activity.BackupWalletActivity;
import com.uranus.library_wallet.ui.activity.ConfirmMnemoincActivity;
import com.uranus.library_wallet.ui.activity.ManagerKeyActivity;
import com.uranus.library_wallet.ui.activity.RechargeActivity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 *
 */

public class WalletFragment extends BaseFragment {

    @BindView(R2.id.title_bar)
    TitleBar titleBar;
    @BindView(R2.id.btn_create_wallet)
    Button btnCreateWallet;
    @BindView(R2.id.btn_import_wallet1)
    Button btnImportWallet1;
    @BindView(R2.id.btn_import_wallet2)
    Button btnImportWallet2;
    @BindView(R2.id.btn_export_wallet)
    Button btnExportWallet;
    @BindView(R2.id.btn_backup_mnemoinc)
    Button btnBackupMnemoinc;
    @BindView(R2.id.btn_backup_wallet)
    Button btnBackupWallet;
    @BindView(R2.id.btn_confirm_mnemoinc)
    Button btnConfirmMnemoinc;
    @BindView(R2.id.btn_export_keystore)
    Button btnExportKeystore;
    @BindView(R2.id.btn_manager)
    Button btnManager;
    @BindView(R2.id.btn_recharge)
    Button btnRecharge;
    @BindView(R2.id.btn_transaction_record)
    Button btnTransactionRecord;
    @BindView(R2.id.tv_address)
    TextView tvAddress;
@BindView(R2.id.tv_load_address)
    TextView tvLoadAddress;
@BindView(R2.id.btn_transaction)
Button btn_transaction;

    RxPermissions rxPermission;

    private CreateWalletInteract createWalletInteract;
    private ModifyWalletInteract modifyWalletInteract;

    private FetchWalletInteract findDefaultWalletInteract;
    private FetchTransactionsInteract fetchTransactionsInteract;
    private EthereumNetworkRepository ethereumNetworkRepository;
    private CreateTransactionInteract createTransactionInteract;
    private AddTokenInteract addTokenInteract;
    private FetchTokensInteract fetchTokensInteract;

    private static final long FETCH_TRANSACTIONS_INTERVAL = 1;

    public static BigInteger GAS_PRICE = BigInteger.valueOf(0x3b9aca00);
    public static BigInteger GAS_LIMIT = BigInteger.valueOf(0x493e0);



    ETHWallet wallet = null;

    private Disposable transactionDisposable;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initView(View rootView) {

        createWalletInteract = new CreateWalletInteract();
        modifyWalletInteract = new ModifyWalletInteract();

        RepositoryFactory rf = WalletInit.repositoryFactory();
        ethereumNetworkRepository = rf.ethereumNetworkRepository;
        findDefaultWalletInteract = new FetchWalletInteract();
        fetchTransactionsInteract = new FetchTransactionsInteract(rf.transactionRepository);
        createTransactionInteract = new CreateTransactionInteract(ethereumNetworkRepository);
        addTokenInteract = new AddTokenInteract(findDefaultWalletInteract, rf.tokenRepository);
        fetchTokensInteract = new FetchTokensInteract(rf.tokenRepository);
    }


    String tokenAddr;

    private final MutableLiveData<List<Transaction>> transactions = new MutableLiveData<>();

    private void onTransactions(Transaction[] transactions) {

        // ETH transfer ingore the contract call
        if (TextUtils.isEmpty(tokenAddr)) {
            ArrayList<Transaction> transactionList = new ArrayList<>();
            Lmsg.d("size:" + transactionList.size());

            this.transactions.postValue(transactionList);
        } else {
            this.transactions.postValue(Arrays.asList(transactions));
            Lmsg.d("dddd-onTransactions----"+transactions.length);
        }
    }


    @Override
    protected void initData() {


        wallet = WalletDaoUtils.getCurrent();
        if (wallet != null) {
            tvAddress.setText(wallet.getAddress());
            tvLoadAddress.setText(wallet.getAddress());
        }

    }


    @SuppressLint("CheckResult")
    @OnClick({R2.id.btn_create_wallet, R2.id.btn_import_wallet1,R2.id.btn_import_wallet2,
            R2.id.btn_export_wallet, R2.id.btn_backup_mnemoinc,
            R2.id.btn_backup_wallet, R2.id.btn_confirm_mnemoinc,
            R2.id.btn_export_keystore, R2.id.btn_manager,
            R2.id.btn_recharge, R2.id.btn_transaction_record,R2.id.btn_transaction,R2.id.btn_add_token,})
    public void onClick(View view) {



        int id = view.getId();
        if (id == R.id.btn_create_wallet) {//创建钱包
            RxPermissions rxPermission = new RxPermissions(getActivity());
            rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean)
                            createWalletInteract.create("蓝胖子", "12345678", "12345678", "123").subscribe(this::jumpToWalletBackUp, this::showError);
                        }

                        private void showError(Throwable errorInfo) {
                            Lmsg.e("CreateWalletActivity", errorInfo.toString());
                            ToastUtils.showShort(errorInfo.toString());
                        }

                        private void jumpToWalletBackUp(ETHWallet wallet) {
                            ToastUtils.showShort("创建钱包成功");
                            Lmsg.e("CreateWalletActivity", wallet.toString());
                            tvAddress.setText(wallet.getAddress());
                        }
                    });


        } else if (id == R.id.btn_import_wallet1) {
            String ethType = ETHWalletUtils.ETH_JAXX_TYPE;
//            String mnemonic = "raven nest upgrade enhance matrix decline deliver pink similar hope appear insect";
            String mnemonic = "letter heavy fruit uncle adult undo column fall flash ranch biology upon";
            String walletPwd = "12345678";
            createWalletInteract.loadWalletByMnemonic(ethType, mnemonic, walletPwd).subscribe(this::loadSuccess, this::onError);

//            goActivity(ImportWalletActivity.class);
        }else if (id == R.id.btn_import_wallet2) {
            String ethType = ETHWalletUtils.ETH_JAXX_TYPE;
            String keystore = "{\"address\":\"722bf6815e1dcf968055b7bbcdc67ad24db62275\",\"id\":\"d317006f-4a71-431a-83f1-a7d553f2066e\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"cipherparams\":{\"iv\":\"4c2ca86476144d08d461cf654b14b975\"},\"ciphertext\":\"531c412e9b1f7cb7fadb335e75bed0fd84328e62479b5820b6d10810a2e31c39\",\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":4096,\"p\":6,\"r\":8,\"salt\":\"8bbc8e26a836e128699de86d57e8b0170b8a73ee93b91f5eaf7093b9c3ad4df1\"},\"mac\":\"62c786245d2e7780c44a86229c512f18ebef9dbf14367e2189f218d7e4984eff\"}}";
            String walletPwd = "12345678";
            createWalletInteract.loadWalletByKeystore(keystore, walletPwd).subscribe(this::loadSuccess, this::onError);

//            goActivity(ImportWalletActivity.class);
        } else if (id == R.id.btn_export_wallet) {
//            goActivity(ExportKeystoreActivity.class);
        } else if (id == R.id.btn_backup_mnemoinc) {
            goActivity(BackupMnemoincActivity.class);
        } else if (id == R.id.btn_backup_wallet) {
            goActivity(BackupWalletActivity.class);
        } else if (id == R.id.btn_confirm_mnemoinc) {
            goActivity(ConfirmMnemoincActivity.class);
        } else if (id == R.id.btn_export_keystore) {
//            goActivity(ExportKeystoreActivity.class);

            wallet = WalletDaoUtils.getCurrent();
            if (wallet == null) {
                return;
            }
            String pwd = "12345678";
            Lmsg.e("WalletFragment----export_keystore--pwd--"+wallet.getPassword());
            modifyWalletInteract.deriveWalletKeystore(wallet.getId(), pwd).subscribe(this::showDeriveKeystore);

        } else if (id == R.id.btn_manager) {
            goActivity(ManagerKeyActivity.class);
        } else if (id == R.id.btn_recharge) {
            goActivity(RechargeActivity.class);
        }  else if (id == R.id.btn_transaction) {

            createTransactionInteract.createEthTransaction(wallet, C.kURACReceiveAddress, EthUtils.toBigInteger("0.01"), GAS_PRICE, GAS_LIMIT, "12345678")
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::onCreateTransaction, this::onError);

//            createTransactionInteract.createERC20Transfer(wallet, C.kURACReceiveAddress,C.USDTtokenAddres, EthUtils.toBigInteger("0.01"), GAS_PRICE, GAS_LIMIT, "12345678")
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(this::onCreateTransaction, this::onError);

        } else if (id == R.id.btn_transaction_record) {
//            goActivity(TransactionRecordActivity.class);

            wallet = WalletDaoUtils.getCurrent();
            if (wallet == null) {
                return;
            }

//            fetchTransactionsInteract.fetch(wallet)
//
//
//            ethereumNetworkRepository
//                    .find()
//                    .subscribe(this::onDefaultNetwork, this::onError);
//            findDefaultWalletInteract.findDefault();
            fetchTransactionsInteract.fetch(wallet.getAddress(),C.USDTtokenAddres).subscribe(this::onTransactions, this::onError);
        }else if(id==R.id.btn_add_token){
            List<TokenItem> mItems = new ArrayList<TokenItem>();
//            mItems.add(new TokenItem(new TokenInfo("", "ETH", "ETH", 18), true, R.mipmap.eth));
            mItems.add(new TokenItem(new TokenInfo(C.USDTtokenAddres, "USDT", "USDT", 6), true, R.mipmap.usdt));
            mItems.add(new TokenItem(new TokenInfo(C.UtokenAddres, "URAC", "URAC", 18), true, R.mipmap.urac));

//            for (TokenItem item :mItems) {
//                addTokenInteract
//                        .add(item.tokenInfo.address, item.tokenInfo.symbol, item.tokenInfo.decimals)
//                        .subscribe(this::onSaved, this::onError);
//            }


            fetchTokensInteract
                    .fetch(wallet.getAddress())
                    .subscribe(this::onTokens, this::onError);
        }


    }

    private void onTokens(Token[] tokens) {
        for (Token token : tokens ) {
            Lmsg.d("dddd----token--"+token.balance +"  ---  " +token.tokenInfo.symbol+"----tokens  "+ tokens.length);
        }
    }


    private void onSaved() {
        fetchTokensInteract
                .fetch(wallet.getAddress())
                .subscribe(this::onTokens, this::onError);
    }

    private void onCreateTransaction(String s) {

        Lmsg.e("WalletFragment---onCreateTransaction---", s);
    }

    private void showDeriveKeystore(String s) {
        Lmsg.e("WalletFragment-showDeriveKeystore--", s);
    }

    private void onError(Throwable throwable) {
        Lmsg.e("WalletFragment---onError---", throwable.toString());
        ToastUtils.showShort(throwable.toString());

    }

    private void loadSuccess(ETHWallet ethWallet) {
        ToastUtils.showShort("创建钱包成功");
        Lmsg.e("WalletFragment---loadSuccess---", ethWallet.toString());

        tvLoadAddress.setText(ethWallet.getAddress());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (transactionDisposable != null && !transactionDisposable.isDisposed()) {
            transactionDisposable.dispose();
        }
    }
}

