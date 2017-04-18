package com.example.concurrent.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {
    
    public static void test1(){
        ExecutorService es = Executors.newFixedThreadPool(5);
        Future<String> f = es.submit(() -> {
            Thread.sleep(1000);
            return "result";
        });
        
        new Thread(() -> {
           try {
            System.out.println(f.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        }).start();
    }
    
    public static void test2(){
        IService<String, String> service = new SubmitService();
        IFuture<String> submit = service.submit(new Callablement());
        new Thread(() -> {
           System.out.println(submit.get()); 
        }).start();
    }

    public static void main(String[] args) {
        test2();
    }
    
}
