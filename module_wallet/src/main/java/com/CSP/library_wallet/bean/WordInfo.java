package com.uranus.library_wallet.bean;

import java.io.Serializable;

/**
 * Created by mac on 2019-09-11.
 * <p>
 * time ：2019-09-11
 * desc：
 */
public class WordInfo implements Serializable {

    public final static int STYLE_1 = 0;
    public final static int STYLE_2 = 1;
    private int styleType;
    private String word;

    public WordInfo(int styleType, String word) {
        this.styleType = styleType;
        this.word = word;
    }

    public int getStyleType() {
        return styleType;
    }

    public void setStyleType(int styleType) {
        this.styleType = styleType;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
