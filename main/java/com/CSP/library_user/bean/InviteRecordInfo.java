package com.uranus.library_user.bean;

public class InviteRecordInfo {


    /**
     * nickname : abn47
     * id : 16
     * uid : 121417016
     * invite_num : 0
     * avatar : http://test3.dihub.cn:3000/uploads/avatar/20190921/6f0f35f0ad40a55a773db283916bae01.png
     * small_product : 0
     * big_product : 3
     */

    private String nickname;
    private int id;
    private String uid;
    private int invite_num;
    private String avatar;
    private int small_product;
    private int big_product;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getInvite_num() {
        return invite_num;
    }

    public void setInvite_num(int invite_num) {
        this.invite_num = invite_num;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSmall_product() {
        return small_product;
    }

    public void setSmall_product(int small_product) {
        this.small_product = small_product;
    }

    public int getBig_product() {
        return big_product;
    }

    public void setBig_product(int big_product) {
        this.big_product = big_product;
    }
}
