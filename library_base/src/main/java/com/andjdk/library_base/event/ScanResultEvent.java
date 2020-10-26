package com.andjdk.library_base.event;

/**
 * Created by andjdk on 2019-09-18
 * <p>
 * time ：2019-09-18
 * desc：
 */
public class ScanResultEvent  {

    private String scanResult;

    public ScanResultEvent(String scanResult) {
        this.scanResult = scanResult;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }
}
