package com.estu.petify.petifystorefront.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PetifyDateUtils {

    public static String getCurrentDateWithPattern(final String pattern){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = new Date();
        return formatter.format(date);
    }

}
