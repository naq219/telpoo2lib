package com.lemy.telpoo2lib.model;

import android.content.Context;

import java.util.ArrayList;

public interface ModelListener {
    Context getContext();

    void onSuccess(int taskType, ArrayList<?> list, String msg);
    void onSuccess(int taskType, Object data, String msg);

    void onFail(int taskType, String msg);

    void onProgress(int taskType, int progress);
}
