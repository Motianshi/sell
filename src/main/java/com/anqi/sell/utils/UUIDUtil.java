package com.anqi.sell.utils;

import java.util.Random;

public class UUIDUtil {

    public static String getUUID() {
        Random random = new Random();

        Integer num = random.nextInt(9999) + 1111;

        return System.currentTimeMillis() + num + "";
    }
}
