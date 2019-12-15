package com.lemy.telpoo2lib.db;

import android.content.Context;

import com.lemy.telpoo2lib.utils.SprUtils;

public class DbLibCache extends DbLib{
    protected DbLibCache(Context context) {
        super(context);

    }

    private static long getTime(Context context){
        return SprUtils.getLong("DbLibCache_time",context,60*1000l);
    }

    public static void saveTimeMilis(Context context,Long timeMilis){
        String[] table={"cache"};
        String[][] key={};
        key[0][0]="url";
        init(table,key,context,"cacheurl",1);
        SprUtils.saveLong("DbLibCache_time",timeMilis,context);
    }




}
