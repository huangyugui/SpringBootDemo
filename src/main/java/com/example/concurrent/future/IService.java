package com.example.concurrent.future;

public interface IService<V, T> {

    public IFuture<T> submit(ICallable<V> call);
    
}
