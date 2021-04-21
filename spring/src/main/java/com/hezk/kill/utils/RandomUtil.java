package com.hezk.kill.utils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    private static final SimpleDateFormat dateFormatOne = new SimpleDateFormat("yyyyMMddHHmmssSS");

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();


    public static String generateOrderCode() {
        return dateFormatOne.format(DateTime.now().toDate()) + generateNumber(4);
    }

    public static String generateNumber(final int num) {
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= num; i++) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }


}