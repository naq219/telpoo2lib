package com.lemy.telpoo2lib.model;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.lemy.telpoo2lib.net.Dataget;
import com.lemy.telpoo2lib.utils.Mlog;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model implements TaskListener {
    String TAG=Model.class.getSimpleName();
    protected static int MODEL_NTHREADS = 5;

    protected static ExecutorService modelExecutor = Executors.newFixedThreadPool(MODEL_NTHREADS);

    protected ModelListener modelListener = null;

    /**
     * @param modelListener
     *            the modelListener to set
     */
    public void setModelListener1(ModelListener modelListener) {
        this.setModelListener(modelListener);
    }

    @Override
    public Context getContext() {
        if (modelListener != null)
            return modelListener.getContext();
        return null;
    }



    public void exeTask(TaskParams params, AsyncTask<TaskParams, Void, Dataget> task) {

//		AsyncTask<TaskParams, Void, Boolean> task=null;
//		task=new Task(this, taskType, list, getContext());
        if (task != null) {
            executeAsyncTask(modelExecutor, task, new TaskParams[]{params});
        }
        else{
            Mlog.E(TAG+"-exeTask-task is null");
        }


    }

    public static void executeAsyncTask(Executor executor, AsyncTask<TaskParams, Void, Dataget> asyncTask, TaskParams[] params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            asyncTask.executeOnExecutor(executor, params);

        } else {
            asyncTask.execute(params);
        }
    }



    @Override
    public void onSuccess(int taskType, Object data, String msg,Integer queue) {
        if (modelListener != null)
            modelListener.onSuccess(taskType, data, msg,queue);
    }

    @Override
    public void onFail(int taskType, String msg,Integer queue) {
        if (modelListener != null)
            modelListener.onFail(taskType, msg,queue);
    }

    @Override
    public void onProgress(int taskType, int progress,Integer queue) {
        if (modelListener != null)
            modelListener.onProgress(taskType, progress,queue);
    }

    /**
     * @return the modelListener
     */
    public ModelListener getModelListener() {
        return modelListener;
    }

    /**
     * @param modelListener
     *            the modelListener to set
     */
    public void setModelListener(ModelListener modelListener) {
        this.modelListener = modelListener;
    }

    /*
     *
     */
}
