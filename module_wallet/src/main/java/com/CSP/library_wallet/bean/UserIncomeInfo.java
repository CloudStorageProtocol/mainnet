package com.uranus.library_wallet.bean;

public class UserIncomeInfo {


    /**
     * id : 7
     * type : 2
     * score : 1.75
     * product_id : 0
     * mac : 0
     * user_id : 15
     * user_nickname : admin
     * user_level : 0
     * user_content : admin 获得 1.75算力
     * created_at : 2019-09-14 20:17:31
     * updated_at : 2019-09-14 20:17:31
     * title : 每日算力奖励
     * to_user : 0
     */

    private int id;
    private int type;
    private String score;
    private int product_id;
    private String mac;
    private int user_id;
    private String user_nickname;
    private int user_level;
    private String user_content;
    private String created_at;
    private String updated_at;
    private String title;
    private int to_user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public String getUser_content() {
        return user_content;
    }

    public void setUser_content(String user_content) {
        this.user_content = user_content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTo_user() {
        return to_user;
    }

    public void setTo_user(int to_user) {
        this.to_user = to_user;
    }
}
