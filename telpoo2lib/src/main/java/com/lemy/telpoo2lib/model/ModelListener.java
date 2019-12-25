package com.lemy.telpoo2lib.model;

import android.content.Context;

import java.util.ArrayList;

public interface ModelListener {
    Context getContext();


    void onSuccess(int taskType, Object data, String msg,Integer queue);

    void onFail(int taskType, String msg,Integer queue);

    void onProgress(int taskType, int progress,Integer queue);
}
