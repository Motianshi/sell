package com.anqi.model;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println(Long.MAX_VALUE);
        System.out.println("1570859568392183");

        String[] hosts = new String[]{"1212","1212"};

        int s = 0;
        for (int i = 0; i <5 ; i++) {
            s = new Random().nextInt(2);
            System.out.println(s);
            System.out.println(hosts.length);
        }
    }
}
