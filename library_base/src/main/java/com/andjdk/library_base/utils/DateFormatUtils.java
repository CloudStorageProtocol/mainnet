package com.andjdk.library_base.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/22.
 */

public class DateFormatUtils {
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd
     */
    public static final String FORMAT_DATE = "dd/MM/yyyy";
    /**
     * HH:mm:ss
     */
    public static final String FORMAT_TIME = "HH:mm:ss";

    public static final String FORMAT_HOUR = "HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss：SSS
     */
    public static final String FORMAT_DEFAULT_Time = "yyyy-MM-dd HH:mm:ss:SSS";

    public static String date2Str(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String date2Str(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_DEFAULT);
        return df.format(date);
    }

    public static Date str2Date(String date) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_DEFAULT);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String date2Str(long date) {
        Date date1 = new Date(date);
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_DATE);
        return df.format(date1);
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String getDateToString(long milSecond) {
        Date date = new Date(milSecond*1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }
}
