package com.lemy.telpoo2lib.utils;

import android.content.Context;
import android.util.Log;



public class Mlog {
    protected static Mlog instance;
    private static String TAG = "telpoo";
    public static Boolean isLog = true;

    public static Mlog getInstance() {
        if (instance == null)
            instance = new Mlog();
        return instance;
    }

    public static void D(String info) {


        Log.d(TAG, info + "");

    }

    public static void D(int info) {

        Log.d(TAG, info + "");

    }

    public static void D(boolean info) {

        Log.d(TAG, info + "");

    }

    public static void D(Context context, String info) {

        if (info != null) {
            Log.d(TAG, context.getClass().getSimpleName() + " - " + info);
        }
    }

    public static void E(String info) {

        if (info != null) {
            Log.e("NAQ", info);
        }
    }

    public static void I(String info) {

        if (info != null) {
            Log.i(TAG, info);
        }
    }

    public static void T(String info) {

        if (info != null) {
            Log.i(TAG, info);
        }
    }

    public static void w(String info) {

        if (info != null) {
            Log.w(TAG, info);
        }
    }



}
