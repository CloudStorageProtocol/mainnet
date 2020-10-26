package com.andjdk.library_base.event;

/**
 * Created by andjdk on 2019-09-22
 * <p>
 * time ：2019-09-22
 * desc：
 */
public class BuyContainerEvent  {

    private boolean isBuyed;


    public BuyContainerEvent(boolean isBuyed) {
        this.isBuyed = isBuyed;
    }

    public boolean isBuyed() {
        return isBuyed;
    }

    public void setBuyed(boolean buyed) {
        isBuyed = buyed;
    }
}
