package com.uranus.library_wallet.base.wallet;

import java.math.BigInteger;

/**
 * Created by andjdk on 2019-09-21
 * <p>
 * time ：2019-09-21
 * desc：
 */
public class C {


    // U币官方收款地址
    public static String kURACReceiveAddress = "0xB505c08Ec21bf2F53cAb03DCc9258BD0D7560c58";

    //合约地址
    public static String USDTtokenAddres = "0xdAC17F958D2ee523a2206206994597C13D831ec7";
    public static String UtokenAddres = "0xff8be4b22cedc440591dcb1e641eb2a0dd9d25a5";

    public static String API_KEY = "4UQAYTB2HV7QARCVUD4XGKEMSZ2Q178HD1";


    public static final String DEFAULT_GAS_PRICE = "21000000000";
    public static final String DEFAULT_GAS_LIMIT = "90000";
    public static final String DEFAULT_GAS_LIMIT_FOR_TOKENS = "144000";


    public static final String ETHEREUM_MAIN_NETWORK_NAME = "Mainnet";
    public static final String CLASSIC_NETWORK_NAME = "Ethereum Classic";
    public static final String POA_NETWORK_NAME = "POA Network";
    public static final String KOVAN_NETWORK_NAME = "Kovan";
    public static final String ROPSTEN_NETWORK_NAME = "Ropsten";

    public static final String LOCAL_DEV_NETWORK_NAME = "local_dev";


    public static final String ETH_SYMBOL = "ETH";
    public static final String POA_SYMBOL = "POA";
    public static final String ETC_SYMBOL = "ETC";

    public static final int ETHER_DECIMALS = 18;
    public static final int USDT_DECIMALS = 6;
    public static final int URAC_DECIMALS = 18;


    // 获取实时价格（行情 Ticker ） URL
    public static final String TICKER_API_URL = "http://47.93.151.65:8000";

    public static BigInteger GAS_PRICE = BigInteger.valueOf(0x3b9aca00);
    public static BigInteger GAS_LIMIT = BigInteger.valueOf(0x493e0);


    public interface ErrorCode {

        int UNKNOWN = 1;
        int CANT_GET_STORE_PASSWORD = 2;
    }
}
