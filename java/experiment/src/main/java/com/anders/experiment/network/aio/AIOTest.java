package com.anders.experiment.network.aio;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

// 先启动server，后启动client
public class AIOTest {  
    
    @Test  
    public void testServer() throws IOException, InterruptedException {  
        /*SimpleServer server = */new SimpleServer(1234);  
        System.out.println("server is running...");
          
        Thread.sleep(60000);  
    }  
      
    @Test  
    public void testClient() throws IOException, InterruptedException, ExecutionException {  
        SimpleClient client = new SimpleClient("localhost", 1234);  
        client.write((byte) 11); 
        System.out.println("send : " + 11);
    }  
  
}  
