package com.andjdk.library_base.router;

import android.app.Activity;
import android.app.Application;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.andjdk.library_base.utils.Utils;

import java.util.Map;

/**
 * Created by mac on 2019-09-10.
 * <p>
 * time ：2019-09-10
 * desc：
 */
public class RouterHelper {
    private static RouterHelper router;
    private Application context;

    public RouterHelper(Application context) {
        this.context = context;
    }


    public static RouterHelper getInstance() {
        if (router == null) {
            throw new RuntimeException("please init router!");
        }
        return router;
    }

    public static synchronized void init(Application context) {
        if (router == null) {
            router = new RouterHelper(context);
            if (Utils.isApkInDebug(context)) {
                ARouter.openLog();     // 打印日志
                ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            }
            ARouter.init(context); // 尽可能早，推荐在Application中初始化
        }

    }

    public void inject(Object thiz) {//初始化
        ARouter.getInstance().inject(thiz);
    }

    public void Skip(String path) {//不带参数跳转
        ARouter.getInstance().build(path).navigation();
    }

    public void Skip(String path, Activity mContext, int requestCode) {//返回值跳转startActivityForResult
        ARouter.getInstance().build(path).navigation(mContext, requestCode);
    }

    public void Skip(String path, String key, String value) {//带一个字符串参数跳转
        ARouter.getInstance().build(path).withString(key, value).navigation();
    }

    public void Skip(String path, String key, Boolean value) {//带一个布偶型参数跳转
        ARouter.getInstance().build(path).withBoolean(key, value).navigation();
    }

    public void Skip(String path, String key1, String value1, String key2, String value2) {//带二个字符串参数跳转
        ARouter.getInstance().build(path).withString(key1, value1).withString(key2, value2).navigation();
    }

    public void Skip(String path, String key1, String value1, String key2, String value2, String key3, String value3) {//带三个字符串参数跳转
        ARouter.getInstance().build(path).withString(key1, value1).withString(key2, value2).withString(key3, value3).navigation();
    }

    public void Skip(String path, String key1, int value1, String key2, int value2, String key3, int value3) {//带三个字符串参数跳转
        ARouter.getInstance().build(path).withInt(key1, value1).withInt(key2, value2).withInt(key3, value3).navigation();
    }

    public void Skip(String path, String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4,
                     String key5, String value5, String key6, String value6, String key7, String value7) {//带七个字符串参数跳转
        ARouter.getInstance().build(path).withString(key1, value1).withString(key2, value2).withString(key3, value3).withString(key4, value4)
                .withString(key5, value5).withString(key6, value6).withString(key7, value7).navigation();
    }

    public void Skip(String path, String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4,
                     String key5, String value5, String key6, String value6, String key7, String value7, String key8, String value8) {//带八个字符串参数跳转
        ARouter.getInstance().build(path).withString(key1, value1).withString(key2, value2).withString(key3, value3).withString(key4, value4)
                .withString(key5, value5).withString(key6, value6).withString(key7, value7).withString(key8, value8).navigation();
    }

    public void Skip(String path, String key, String value, Activity mContext, int requestCode) {//带一个字符串参数和返回值跳转
        ARouter.getInstance().build(path).withString(key, value).navigation(mContext, requestCode);
    }

    public void Skip(String path, String key, String value, String isManager, String is, Activity mContext, int requestCode) {//带一个字符串参数和一个布偶型还有返回值跳转
        ARouter.getInstance().build(path).withString(key, value).withString(isManager,is).navigation(mContext, requestCode);
    }

    public void Skip(String path, String type, String value1, String id, String value2, String name, String value, Activity mContext, int requestCode) {//带三个字符串参数和返回值跳转
        ARouter.getInstance().build(path).withString(type, value1).withString(id, value2).withString(name, value).navigation(mContext, requestCode);
    }


    public void Skip(String path, Map<String ,Object> map){
        Postcard postcard = ARouter.getInstance().build(path);
        for (String key : map.keySet()) {
            if(map.get(key) instanceof Boolean){
                postcard.withBoolean(key, (Boolean) map.get(key));
            }else if(map.get(key) instanceof Integer){
                postcard.withInt(key, (Integer) map.get(key));
            }else if(map.get(key) instanceof Double){
                postcard.withDouble(key, (Double) map.get(key));
            }else{
                postcard.withString(key,  map.get(key).toString());
            }
        }
        postcard.navigation();
    }
}
