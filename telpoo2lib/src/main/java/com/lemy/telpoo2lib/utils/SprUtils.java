package com.lemy.telpoo2lib.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SprUtils {
//    pu
    private static final String KEY_DEFAULT="telpoo_spr";
    public static void saveString(String key,String value,Context context){
        SharedPreferences spr = context.getSharedPreferences(KEY_DEFAULT, Context.MODE_PRIVATE);
        spr.edit().putString(key,value).commit();
    }



    public static String getString(String key,Context context){
        SharedPreferences spr = context.getSharedPreferences(KEY_DEFAULT, Context.MODE_PRIVATE);
        String ss=spr.getString(key,null);
        if ("null".equals(ss)) return null;
        return ss;

    }

    public static void saveLong(String key,Long value,Context context){
        SharedPreferences spr = context.getSharedPreferences(KEY_DEFAULT, Context.MODE_PRIVATE);
        spr.edit().putLong(key,value).commit();
    }
    public static Long getLong(String key,Context context){
        return getLong(key,context,null);
    }
    public static Long getLong(String key,Context context,Long defaultv){
        SharedPreferences spr = context.getSharedPreferences(KEY_DEFAULT, Context.MODE_PRIVATE);
        long ss=spr.getLong(key,-997297291928l);
        if(ss==-997297291928l) return defaultv;
        return ss;

    }
}
