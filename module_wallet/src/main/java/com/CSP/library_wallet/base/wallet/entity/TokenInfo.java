package com.uranus.library_wallet.base.wallet.entity;

import android.os.Parcel;
import android.os.Parcelable;
public class TokenInfo implements Parcelable {
    public String address;
    public String name;
    public String symbol;
    public int decimals;

    public TokenInfo(String address, String name, String symbol, int decimals) {
        this.address = address;
        this.name = name;
        this.symbol = symbol;
        this.decimals = decimals;
    }

    public TokenInfo() {

    }


    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getDecimals() {
        return decimals;
    }



    private TokenInfo(Parcel in) {
        address = in.readString();
        name = in.readString();
        symbol = in.readString();
        decimals = in.readInt();

    }

    public static final Creator<TokenInfo> CREATOR = new Creator<TokenInfo>() {
        @Override
        public TokenInfo createFromParcel(Parcel in) {
            return new TokenInfo(in);
        }

        @Override
        public TokenInfo[] newArray(int size) {
            return new TokenInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(name);
        dest.writeString(symbol);
        dest.writeInt(decimals);

    }
}
