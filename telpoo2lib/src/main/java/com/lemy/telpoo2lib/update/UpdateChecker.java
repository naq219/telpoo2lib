package com.lemy.telpoo2lib.update;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.lemy.telpoo2lib.update.CheckUpdateTask;

public class UpdateChecker {


    public static void checkForDialog(Context context) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_DIALOG, true).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }

    public static void checkListener(Context context, CheckUpdateTask.CheckUpdateTaskListener checkUpdateTaskListener) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_DIALOG, false,checkUpdateTaskListener).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }


    public static void checkForNotification(Context context) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_NOTIFICATION, false).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }

    }

    public static void setUrl(String urlCHeckUpdate,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString("updatertelpoo_url",""+urlCHeckUpdate).commit();
    }



}
