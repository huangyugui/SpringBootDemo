package com.example.concurrent.future;

public class VirturalTask implements IFuture<String>{
    
//    private RealTask rt = null;
    
    private String str = null;
    
    public synchronized void setStr(String str){
        this.str = str;
        this.notifyAll();
    }

    @Override
    public synchronized String get() {
        if(str == null){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
    

}
