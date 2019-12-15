package com.lemy.telpoo2lib.utils;

public class StringUtils {
    public static String moneyDot(String ss) {

        StringBuilder b= new StringBuilder(ss);
        boolean ok=true;
        do {
            int position=b.indexOf("."); if (position==-1)position=b.length();
            if (position-3<=0) break;
            b.insert(position-3,".");
        }
        while (ok);


        return b.toString();
    }
}
