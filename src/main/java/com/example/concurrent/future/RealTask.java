package com.example.concurrent.future;

public class RealTask implements IFuture<String>{
    
    private ICallable<String> call = null;
    
    public void setCallable(ICallable<String> call){
        this.call = call;
    }
    
    @Override
    public String get() {
        return call.call();
    }
    

}
