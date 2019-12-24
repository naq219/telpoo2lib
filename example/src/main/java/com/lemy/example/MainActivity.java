package com.lemy.example;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lemy.telpoo2lib.model.BaseObject;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        BaseObject ojj = new BaseObject();

    }
}
