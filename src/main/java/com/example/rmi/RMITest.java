package com.example.rmi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.Test;

/**
 * @Description:
 * <p>
 * 测试
 * </p>
 * 
 * @author 王旭
 * @time 2016年3月14日 下午5:14:36
 */
public class RMITest {

    @Test
    public void testServer() throws RemoteException, MalformedURLException, AlreadyBoundException {
        HelloServer server = new HelloServer();
        server.server();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClient() throws MalformedURLException, RemoteException, NotBoundException {
        HelloClient client = new HelloClient();
        client.client();
    }

}