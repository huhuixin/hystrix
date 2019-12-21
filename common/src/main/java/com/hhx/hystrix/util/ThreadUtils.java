package com.hhx.hystrix.util;

/**
 * @author hhx
 */
public class ThreadUtils {

    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
