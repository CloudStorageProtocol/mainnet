package com.uranus.library_user.event;

public class UpdateUserInfoEvent {

    private boolean isUpdata;

    public UpdateUserInfoEvent(boolean isUpdata) {
        this.isUpdata = isUpdata;
    }

    public boolean isUpdata() {
        return isUpdata;
    }

    public void setUpdata(boolean updata) {
        isUpdata = updata;
    }
}
