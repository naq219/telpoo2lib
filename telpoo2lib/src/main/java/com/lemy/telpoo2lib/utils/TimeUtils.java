package com.lemy.telpoo2lib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {

    public static Long getTimeMillis() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }




    public static String calToString(Calendar cal, String format) {
        // String format1 = format.toLowerCase();

        String res = format.replaceAll("dd", "" + daylength2(cal.get(Calendar.DAY_OF_MONTH)));
        res = res.replaceAll("MM", "" + monthlength2((cal.get(Calendar.MONTH) + 1)));
        if (format.contains("yyyy"))
            res = res.replaceAll("yyyy", "" + cal.get(Calendar.YEAR));

        if (format.contains("yy"))
            res = res.replaceAll("yy", ("" + cal.get(Calendar.YEAR)).substring(2));
        //
        if (format.contains("HH"))
            res = res.replaceAll("HH", "" + cal.get(Calendar.HOUR_OF_DAY));


        if (format.contains("mm"))
            res = res.replaceAll("mm", "" + cal.get(Calendar.MINUTE));

        if (format.contains("ss"))
            res = res.replaceAll("ss", "" + cal.get(Calendar.SECOND));


        if (format.contains("SSSSSS"))
            res = res.replaceAll("SSSSSS", "" + cal.get(Calendar.MILLISECOND));

        return res;
    }

    /**
     * yyyy-MM-dd HH:mm:ss.SSSSSS
     *
     * @param cal
     * @param format
     * @return
     * @deprecated use @calToString
     */
    public static String cal2String(Calendar cal, String format) {
        String format1 = format.toLowerCase();

        String res = format1.replaceAll("dd", "" + daylength2(cal.get(Calendar.DAY_OF_MONTH)));
        res = res.replaceAll("mm", "" + monthlength2((cal.get(Calendar.MONTH) + 1)));
        if (format.contains("yyyy"))
            res = res.replaceAll("yyyy", "" + cal.get(Calendar.YEAR));

        if (format.contains("yy"))
            res = res.replaceAll("yy", ("" + cal.get(Calendar.YEAR)).substring(2));
        return res;
    }

    public static Calendar String2Calendar(String value, String format) {
        if (value == null) {
            Mlog.E("String2Calendar- null value");
            return null;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            cal.setTime(sdf.parse(value));

            return cal;
        } catch (ParseException e) {
            Mlog.E("String2Calendar - " + e);
            return null;
        }


    }

    private static String monthlength2(int i) {
        if (i < 10) return "0" + i;
        return i + "";
    }

    private static String daylength2(int i) {
        if (i < 10) return "0" + i;
        return i + "";
    }

    public static Long minute2Milisecond(int minute) {
        return minute * 60 * 1000l;
    }

    public static String milisend2Date(String s, String format) {
        Long time = Long.parseLong(s);
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(time);
        return calToString(ca, format);
    }

    public static Calendar startDayCal() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal;
    }
    public static Calendar endDayCal() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        return cal;
    }

    public static Calendar startDayYesterdayCal() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,-1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal;

    }

    public static Calendar endDayYesterdayCal() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,-1);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        return cal;

    }

    public static Calendar startDaythisMonCal() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal;

    }

//    public static Calendar endDaythisMonCal() {
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
//        cal.set(Calendar.HOUR_OF_DAY,0);
//        cal.set(Calendar.MINUTE,0);
//        cal.set(Calendar.SECOND,0);
//        return cal;
//
//    }



}
