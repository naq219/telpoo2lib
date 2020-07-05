package com.lemy.telpoo2lib.model;

import android.content.Context;
import android.os.AsyncTask;

import com.lemy.telpoo2lib.net.Dataget;
import com.lemy.telpoo2lib.utils.Mlog;

public class Task extends AsyncTask<TaskParams, Void, Dataget> {

    private static final String DEFAULT_KEY_PARRAM = "DEFAULT_KEY_PARRAM";
    protected Model baseModel;
    protected int taskType;
    protected Context context;

    private Integer queue=0;


    public void setQueue(Integer queue) {
        this.queue=queue;
    }

    public Integer getQueue() {
        return queue;
    }

    protected Task() {

    }

    public Task(Model baseModel, int taskType) {
        this.baseModel = baseModel;
        this.taskType = taskType;
    }





    public Task(Model baseModel, Context context) {
        this.baseModel = baseModel;

        this.context = context;
    }

    public Task(Model baseModel, int taskType, Context context) {
        this.baseModel = baseModel;
        this.taskType = taskType;
        this.context = context;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }
    public void setContext(Context context) {
        this.context=context;
    }



    public void exe(TaskParams taskParams) {
        this.baseModel.exeTask(taskParams, Task.this);
    }
    public void exeOj(Object oj) {
        TaskParams taskParams=new TaskParams();
        taskParams.setTaskParramDeFault(oj);
        this.baseModel.exeTask(taskParams, Task.this);
    }


    @Override
    protected void onPreExecute() {
        switch (taskType) {
            default:
        }
    }

    @Override
    protected Dataget doInBackground(TaskParams... params) {
        return doInBackground(params[0]);
    }
    protected Dataget doInBackground(TaskParams param0){
        return new Dataget();
    }


    @Override
    protected void onPostExecute(Dataget result) {
        boolean isSucces= result.isSuccess();
        if (isCancelled()) {
            Mlog.D("Canceled TaskType=" + taskType);
            return;
        }

        if (isSucces)baseModel.onSuccess(taskType, result.getData(), result.getMsg(),queue);
        else baseModel.onFail(taskType, result.getMsg(),queue);

    }


    public void setModel(Model baseModel) {

        this.baseModel = baseModel;
    }


}
