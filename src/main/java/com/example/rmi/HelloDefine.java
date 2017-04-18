package com.example.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *@Description:<p>远程接口定义</p>
 *@author 王旭
 *@time 2016年3月14日 下午4:53:48
 */
public interface HelloDefine extends Remote {
    
    public String helloWorld() throws RemoteException;
    
    public String sayHello(String name) throws RemoteException;
    
}