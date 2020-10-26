package com.uranus.library_wallet.event;

public class BackupMnemonicSuccess {

    private boolean isSuccess;


    public BackupMnemonicSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
