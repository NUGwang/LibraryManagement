package com.example.way;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToString {
    public static String dateToString(){
        Date myDate = new Date();
        SimpleDateFormat sim2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sim2.format(myDate);
        return s;
    }
}
