package com.uranus.library_wallet.base.wallet.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class Transaction implements Serializable {


    /**
     * blockNumber : 8589801
     * timeStamp : 1569031340
     * hash : 0x7acbe7b8b0306a8b79293dafa12b23567d3886fb876c551c7e18a159c1e327ec
     * nonce : 6
     * blockHash : 0xef7b8ddb4a26fa249c9513b302fd3fb6a6582f0f6c93997bb32f2cd289595707
     * from : 0x0af4c996f6f50022af6aabe8ab424a244742e9b7
     * contractAddress : 0xdac17f958d2ee523a2206206994597c13d831ec7
     * to : 0x722bf6815e1dcf968055b7bbcdc67ad24db62275
     * value : 500000
     * tokenName : Tether USD
     * tokenSymbol : USDT
     * tokenDecimal : 6
     * transactionIndex : 185
     * gas : 60000
     * gasPrice : 11000000000
     * gasUsed : 53401
     * cumulativeGasUsed : 9430289
     * input : deprecated
     * confirmations : 14059
     */

    private String blockNumber;
    private long timeStamp;
    private String hash;
    private String nonce;
    private String blockHash;
    private String from;
    private String contractAddress;
    private String to;
    private String value;
    private String tokenName;
    private String tokenSymbol;
    private int tokenDecimal;
    private String transactionIndex;
    private String gas;
    private String gasPrice;
    private String gasUsed;
    private String cumulativeGasUsed;
    private String input;
    private int confirmations;

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public void setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    public int getTokenDecimal() {
        return tokenDecimal;
    }

    public void setTokenDecimal(int tokenDecimal) {
        this.tokenDecimal = tokenDecimal;
    }

    public String getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getCumulativeGasUsed() {
        return cumulativeGasUsed;
    }

    public void setCumulativeGasUsed(String cumulativeGasUsed) {
        this.cumulativeGasUsed = cumulativeGasUsed;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }
}
