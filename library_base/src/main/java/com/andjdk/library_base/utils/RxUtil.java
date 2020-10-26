package com.andjdk.library_base.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Just :
 *
 * @author by Zian
 * @date on 2019/09/27 09
 */
public class RxUtil {

    /**
     * 倒计时
     *
     * @param times 时间
     * @return
     */
    public static Observable<Integer> timeCountDown(int times) {
        int countTime = times;
        if (countTime < 0) {
            countTime = 0;
        }
        int finalCountTime = countTime;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return finalCountTime - aLong.intValue();
                    }
                }).take((times + 1))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
