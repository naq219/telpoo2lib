package com.lemy.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lemy.telpoo2lib.model.BObject;
import com.lemy.telpoo2lib.model.Model;
import com.lemy.telpoo2lib.model.TaskParams;
import com.lemy.telpoo2lib.utils.Mlog;

public class MainActivity extends Activity {
    int queue=0;
    TaskExample taskExample;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainlayout);

            int a=1;
    }

    public void click(View view) {
        BObject ojj = new BObject();
        //if (taskExample!=null) taskExample.cancel(true);
         taskExample = new TaskExample(new Model(){
            @Override
            public void onSuccess(int taskType, Object data, String msg, Integer queue1) {
                super.onSuccess(taskType, data, msg, queue1);
                Mlog.D("queue="+queue1+data);
                Toast.makeText(MainActivity.this,"queue="+queue1+data,Toast.LENGTH_LONG).show();
            }
        },123,this);
         queue++;
        Mlog.D("queue vao="+queue);
        taskExample.setQueue(queue);
        taskExample.exeOj("ahehehihi");


    }
}
