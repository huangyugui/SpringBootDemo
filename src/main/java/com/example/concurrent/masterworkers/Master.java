package com.example.concurrent.masterworkers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Master {

    private BlockingQueue<Task> bq = new LinkedBlockingQueue<>();
    
    private List<Thread> workers = new ArrayList<Thread>();
    
    private ConcurrentHashMap<String, Object> result = new ConcurrentHashMap<>();
    
    //1、初始化master
    public Master(Worker worker, int workerCount){
        for(int i = 0; i < workerCount; i++){
            worker.setQueue(bq);
            worker.setResultMap(result);
            Thread thread = new Thread(() -> {
                worker.run();
            });
            workers.add(thread);
        }
    }
    
    //2、往bq里面放task对象
    public void put(Task task){
        bq.add(task);
    }
    
    //3、启动worker开始执行
    public void commit(){
        for(int i = 0; i < workers.size(); i++){
            workers.get(i).start();
        }
    }
    
    //4、是否已经完成
    public boolean isComplete(){
        boolean flag = true;
        for(int i = 0; i < workers.size(); i++){
            if(workers.get(i).getState() != Thread.State.TERMINATED){
                return false;
            }
        }
        return flag;
    }
    
    //5、获取结果
    public Integer getResult(){
        int rt = 0;
        for(Map.Entry<String, Object> entry : result.entrySet()){
            rt += (Integer)entry.getValue();
        }
        return rt;
    }
    
    
}
