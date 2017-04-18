package com.example.concurrent.future;

public class Callablement implements ICallable<String>{

    @Override
    public String call() {
        System.out.println("It need a long time!");
        try {
            Thread.sleep(1000);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "result,result,result,result";
    }

}
