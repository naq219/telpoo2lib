package com.lemy.example;

import android.content.Context;

import com.lemy.telpoo2lib.model.Model;
import com.lemy.telpoo2lib.model.Task;
import com.lemy.telpoo2lib.model.TaskParams;

import java.util.Random;

public class TaskExample extends Task {
    public TaskExample(){

    }

    public TaskExample(Model baseModel, int taskType, Context context) {
        super(baseModel,taskType,context);
    }

    @Override
    protected DataReturn doInBackground(TaskParams... params) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DataReturn da= new DataReturn(params);
        da.setDataSuccess("sd");
        return da;

    }
}
