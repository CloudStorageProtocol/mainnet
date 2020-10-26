package com.uranus.library_wallet.event;


/**
 * Created by mac on 2019-09-10.
 * <p>
 * time ：2019-09-10
 * desc：
 */
public class UpdateTokenEvent {

    private boolean isUpdate;

    public UpdateTokenEvent(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}
