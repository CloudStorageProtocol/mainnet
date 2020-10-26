package com.uranus.library_wallet.base.wallet.utils;

import android.content.Context;

import com.snappydb.SnappydbException;
import com.uranus.library_wallet.base.wallet.entity.TokenInfo;

import java.util.ArrayList;
import java.util.List;


public class DBTokenUtil extends DBUtil {

    private static final String DB_TOKEN = "db_token";

    public static void saveToken(Context context, TokenInfo TokenInfo){
        synchronized (dbObject) {
            try {
                db = openDB(context, DB_TOKEN);
                db.put(getDbKey(TokenInfo.name), TokenInfo);
                db.close();
            } catch (SnappydbException e) {
                handleException(db, e);
            }
        }
    }

    public static boolean checkTokenExist(Context context, TokenInfo TokenInfo) {
        synchronized (dbObject) {
            try {
                db = openDB(context, DB_TOKEN);
                String[] keys = db.findKeys(getDbKey(TokenInfo.name));
                db.close();
                return keys.length > 0;
            } catch (SnappydbException e) {
                handleException(db, e);
            }
            return false;
        }
    }

    public static List<TokenInfo> getAllTokens(Context context) {
        synchronized (dbObject) {
            List<TokenInfo> tokenList = new ArrayList<>();
            try {
                db = openDB(context, DB_TOKEN);
                String[] keys = db.findKeys(DB_PREFIX);
                for (String key: keys) {
                    tokenList.add(db.getObject(key, TokenInfo.class));
                }
                db.close();
            } catch (SnappydbException e) {
                handleException(db, e);
            }
            return tokenList;
        }
    }


}
