package com.example.concurrent.masterworkers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Worker{
    
    private BlockingQueue<Task> bq = null;
    
    private ConcurrentHashMap<String, Object> rt = null;
    
    private boolean isRunning = true;
    
    public void setQueue(BlockingQueue<Task> bq){
        this.bq = bq;
    }
    
    public void setResultMap(ConcurrentHashMap<String, Object> rt){
        this.rt = rt;
        
    }

    public void run() {
        Task task = null;
        while((task = bq.poll()) != null && isRunning){
            
           handler(task);
        }
    }

    public void handler(Task task){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        rt.put(task.getId()+"", task.getAge());
        System.out.println(task.getId() + "," + task.getAge());
    }

}
