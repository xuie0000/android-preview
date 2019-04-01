package com.xuie.javalib;

import java.util.Calendar;

/**
 * @author xujie
 */
public class MyClass {

    public static void main(String[] args) {
        long time = 1554058632432L;
        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(time)
        cal.setTimeInMillis(System.currentTimeMillis());
        System.out.println("test time - " + cal.getTime().toString());
    }
}
