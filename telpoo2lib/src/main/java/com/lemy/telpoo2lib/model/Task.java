package com.lemy.telpoo2lib.model;

import android.content.Context;
import android.os.AsyncTask;

import com.lemy.telpoo2lib.net.Dataget;
import com.lemy.telpoo2lib.utils.Mlog;

public class Task extends AsyncTask<TaskParams, Void, Dataget> {

    private static final String DEFAULT_KEY_PARRAM = "DEFAULT_KEY_PARRAM";
//    protected final Boolean TASK_FAILED = false;
//    protected final Boolean TASK_DONE = true;
    protected Model baseModel;
    protected int taskType;
    protected Context context;
//    protected HashMap<String, Object> param = new HashMap<>();

    protected String msg = null;
    private Integer queue=0;

//    protected ArrayList<?> dataFromModel = null;
//    protected ArrayList<?> dataReturn = null;
//    protected Object dataFrom = null;
//    protected Object dataTo = null;

    /**
     * neu muon clone mot asyntask, goi ham nay, sau do goi @setAllData
     * @return
     */


//    public class DataReturn{
//        boolean status=false;
//        Object data;
//        Integer queue=null;
//
//
//
//        public void setFail(){
//            status=false;
//        };
//        public void setDataSuccess(Object data){
//            status=true;
//            this.data=data;
//        }
//
//        public void setFromNetData(Dataget netData) {
//            status=netData.isSuccess();
//            data=netData.getData();
//
//        }
//
//
//    }

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


    @Override
    protected void onPreExecute() {
        switch (taskType) {
            default:
        }
    }

    @Override
    protected Dataget doInBackground(TaskParams... params) {

        return new Dataget();

    }


    @Override
    protected void onPostExecute(Dataget result) {
        boolean isSucces= result.isSuccess();
        if (isCancelled()) {
            Mlog.D("Canceled TaskType=" + taskType);
            return;
        }

        if (isSucces)baseModel.onSuccess(taskType, result.getData(), msg,queue);
        else baseModel.onFail(taskType, msg,queue);

    }


    public void setModel(Model baseModel) {

        this.baseModel = baseModel;
    }
}
