package com.android.c196.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();

        //determine desired pattern
        simpleDateFormat.applyPattern("MMM dd/yyyy");
        return simpleDateFormat.format(date);
    }

    public static String formatButtonDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();

        //determine desired pattern
        simpleDateFormat.applyPattern("MMM dd yyyy");
        return simpleDateFormat.format(date);
    }
}
