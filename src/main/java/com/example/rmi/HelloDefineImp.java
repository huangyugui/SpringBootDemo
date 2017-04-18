package com.example.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @Description:
 * <p>
 * 远程接口实现
 * </p>
 * 
 * @author 王旭
 * @time 2016年3月14日 下午4:57:50
 */
public class HelloDefineImp extends UnicastRemoteObject implements HelloDefine {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public HelloDefineImp() throws RemoteException {
        super();
    }

    public String helloWorld() throws RemoteException {
        return "Hello AlphaGo!";
    }

    public String sayHello(String name) throws RemoteException {
        return "Hello" + name + "!";
    }

}