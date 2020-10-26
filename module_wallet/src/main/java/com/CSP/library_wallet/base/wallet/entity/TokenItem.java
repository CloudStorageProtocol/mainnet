package com.uranus.library_wallet.base.wallet.entity;

/**
 * Created by andjdk on 2019-09-21
 * <p>
 * time ：2019-09-21
 * desc：
 */
public class TokenItem {

    public final TokenInfo tokenInfo;
    public boolean added;
    public int iconId;

    public TokenItem(TokenInfo tokenInfo, boolean added, int id) {
        this.tokenInfo = tokenInfo;
        this.added = added;
        this.iconId = id;
    }
}
