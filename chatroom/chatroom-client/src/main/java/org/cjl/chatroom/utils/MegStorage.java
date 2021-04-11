package org.cjl.chatroom.utils;

import java.util.LinkedList;
import java.util.Queue;

public class MegStorage {

    private static Queue<String> q = new LinkedList<String>();
    private static int size_max = 10;
    private static int size_now = 0;

    public static void show(){
        WriteAndPrint.println("-----------聊天室------------");
        for(int i = 0; i<size_now; i++){
            WriteAndPrint.println(q.peek());
            q.add(q.peek());
            q.remove();
        }
        return;
    }

    public static void add(String msg){
        size_now++;
        q.add(msg);
        if(size_now>size_max)adjust();
        return;
    }

    private static void adjust() {
        while(size_now>size_max){
            q.remove();
            size_now--;
        }
        return;
    }

}
