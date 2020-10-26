package com.uranus.library_wallet.event;

public class UpdateInComeEvent {
    private boolean isUpdate;

    public UpdateInComeEvent(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}
