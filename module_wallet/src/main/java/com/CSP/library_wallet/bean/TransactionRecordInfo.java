package com.uranus.library_wallet.bean;

public class TransactionRecordInfo {


    /**
     * id : 2
     * user_id : 16
     * wallet_addr : 0x01bb12fb0846bd34ad06dabad449faf5d3d92b88a32814d464b9d310f5931eb6
     * score : 6.78
     * status : 1
     * tips : 0.0050
     * created_at : 2019-09-16 08:55:38
     * updated_at : 2019-09-16 08:55:38
     * type : 1
     * fee : 0.03
     */

    private int id;
    private int user_id;
    private String wallet_addr;
    private String score;
    private int status;
    private String tips;
    private String created_at;
    private String updated_at;
    private int type;
    private String fee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getWallet_addr() {
        return wallet_addr;
    }

    public void setWallet_addr(String wallet_addr) {
        this.wallet_addr = wallet_addr;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
