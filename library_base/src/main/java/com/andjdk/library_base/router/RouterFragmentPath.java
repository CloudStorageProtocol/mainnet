package com.andjdk.library_base.router;

/**
 * 用于组件开发中，ARouter多Fragment跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 */

public class RouterFragmentPath {
    /**
     * 首页组件
     */
    public static class Home {
        private static final String HOME = "/home";
        /*首页*/
        public static final String PAGER_HOME = HOME + "/Home";
    }


    /**
     * 用户组件
     */
    public static class User {
        private static final String USER = "/user";
        /*我的*/
        public static final String PAGER_ME = USER + "/Me";

    }

    /**
     * 钱包组件
     */
    public static class Wallet {
        private static final String Wallet = "/wallet";
        /*我的*/
        public static final String PAGER_WalletMain = Wallet + "/WalletMain";

        public static final String PAGER_Wallet = Wallet + "/Wallet";
    }
}
