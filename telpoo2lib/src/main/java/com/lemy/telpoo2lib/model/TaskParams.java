package com.lemy.telpoo2lib.model;

import java.util.HashMap;

public class TaskParams  {
    private static final String DEFAULT_KEY_PARRAM = "DEFAULT_KEY_PARRAM";
    HashMap<String,Object> param;

    public TaskParams () {
        param =new HashMap<>();
    }

    public TaskParams (String key,Object object) {
        param =new HashMap<>();
        param.put(key,object);
    }

    public TaskParams (Object object) {
        param =new HashMap<>();
        param.put(DEFAULT_KEY_PARRAM,object);
    }



    public static TaskParams getInstance(){
        TaskParams taskParams = new TaskParams();
        return taskParams;
    }

    public void put(String key,Object value){
        param.put(key,value);
    }
    public Object get(String key){
        if(param.containsKey(key))
            return  param.get(key);
        return null;
    }

    public void setTaskParramDeFault(Object value) {
        param.put(DEFAULT_KEY_PARRAM, value);
    }

    public String getTaskParamDefaultString(){
        return  param.get(DEFAULT_KEY_PARRAM).toString();
    }

    public String getTaskParramString(String key) {

        if (param.containsKey(key))
            return param.get(key).toString();
        return null;

    }

    public Integer getTaskParramInt(String key) {

        if (param.containsKey(key))
            return (Integer) param.get(key);
        return null;

    }

    public BaseObject getTaskParramBaseObject(String key) {

        if (param.containsKey(key))
            return (BaseObject) param.get(key);
        return null;

    }



}
