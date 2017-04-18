package com.example.concurrent.future;

public class SubmitService implements IService<String, String>{

    @Override
    public IFuture<String> submit(ICallable<String> call) {
        final IFuture<String> f = new VirturalTask();
        new Thread(() -> {
           String str = call.call();
           VirturalTask vt = (VirturalTask)f;
           vt.setStr(str);
        }).start();
        return f;
    }
    
}
