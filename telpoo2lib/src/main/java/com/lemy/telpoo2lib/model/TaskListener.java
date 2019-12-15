package com.lemy.telpoo2lib.model;

import android.content.Context;


import com.lemy.telpoo2lib.db.DbLib;

import java.util.ArrayList;

public interface TaskListener {
    Context getContext();

    void onSuccess(int taskType, Object data, String msg);

    void onFail(int taskType, String result);

    void onProgress(int taskType, int progress);
}
