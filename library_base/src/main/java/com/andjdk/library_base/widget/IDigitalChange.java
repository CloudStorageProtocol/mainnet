package com.andjdk.library_base.widget;

public interface IDigitalChange {
    void start();

    void setNumber(float number);

    void setNumber(int number);

    void setDuration(long duration);

    boolean isRunning();
}
