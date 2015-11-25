package com.xuie.util;

import android.text.format.Time;

/**
 * Created by xuie on 16-3-23.
 * @see android.text.format.DateUtils
 */
public class DateUtils {
    /**
     * @return true if the supplied when is today else false
     */
    public static boolean isToday(long when) {
        Time time = new Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (thenMonthDay == time.monthDay);
    }


}
