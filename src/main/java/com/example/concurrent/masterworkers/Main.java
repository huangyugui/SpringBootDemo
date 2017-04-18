package com.example.concurrent.masterworkers;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println("可用核心数：" + Runtime.getRuntime().availableProcessors());
        Master master = new Master(new Worker(), Runtime.getRuntime().availableProcessors());
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            master.put(new Task(i, "aa"+i, random.nextInt(1000)));
        }
        master.commit();
        long start = System.currentTimeMillis();
        while(!master.isComplete()){
        }
        System.out.println("------------执行完毕--------------");
        long end = System.currentTimeMillis() - start;
        int result = master.getResult();
        System.out.println("总共耗时：" + end + ", 结果是：" + result);
//        try {
//            Thread.sleep(10000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    
}
