package org.cjl.chatroom.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WriteAndPrint {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void println(String meg) {
        System.out.println(meg);
        return;
    }

    public static void print(String meg) {
        System.out.print(meg);
        return;
    }

    public static boolean isNull(String meg) {
        return meg.isEmpty();
    }

    public static String write() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
