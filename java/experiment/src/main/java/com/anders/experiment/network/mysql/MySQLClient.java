package com.anders.experiment.network.mysql;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.cassandra.cli.CliParser.updateColumnFamily_return;

public class MySQLClient {

    /*标识数字*/
    private static int flag = 0;
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(4096);
    private static ByteBuffer receiveBuffer = ByteBuffer.allocate(4096);
    
    public static void main(String[] args) throws IOException {
    	Selector selector = Selector.open();
    	
    	SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("localhost", 3306));
        
        Set<SelectionKey> selectionKeys;
        Iterator<SelectionKey> iterator;
        SelectionKey selectionKey;
        SocketChannel client;
        String receiveText;
        String sendText;
        int count=0;

        while (true) {
        	selector.select(1000L);
//            selector.select();
            selectionKeys = selector.selectedKeys();
            //System.out.println(selectionKeys.size());

            iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                selectionKey = iterator.next();
               
                if (selectionKey.isValid() && selectionKey.isConnectable()) {
                	System.out.println("ok");
                	
                	client = (SocketChannel) selectionKey.channel();
                	
                	if (client.isConnectionPending()) {
                		client.finishConnect();
//                		selectionKey.attach(null);
//                		selectionKey.cancel();
                		client.register(selector, SelectionKey.OP_READ);
                	}
                }
                else if (selectionKey.isReadable()) {
                	System.out.println("read");
                	
                	client = (SocketChannel) selectionKey.channel();
                	receiveBuffer.clear();

                    count=client.read(receiveBuffer);
                    if(count>0){
                        receiveText = new String( receiveBuffer.array(),0,count);
                        System.out.println("客户端接受服务器端数据--:"+receiveText);
                    }
                }
                else if (selectionKey.isWritable()) {
                	System.out.println("write");
                }
                else {
                	selectionKey.cancel();
                	System.out.println("cancel");
                }
              
                
                
                
//                if (selectionKey.isConnectable()) {
//                    System.out.println("client connect");
//                    client = (SocketChannel) selectionKey.channel();
//                    // 判断此通道上是否正在进行连接操作。
//
//                    // 完成套接字通道的连接过程。
//
//                    if (client.isConnectionPending()) {
//                        client.finishConnect();
//                        System.out.println("完成连接!");
//                        sendBuffer.clear();
//                        sendBuffer.put("Hello,Server".getBytes());
//                        sendBuffer.flip();
//                        client.write(sendBuffer);
//                    }
//                    client.register(selector, SelectionKey.OP_READ);
//                } else if (selectionKey.isReadable()) {
//                    client = (SocketChannel) selectionKey.channel();
//                    //将缓冲区清空以备下次读取
//
//                    receiveBuffer.clear();
//                    //读取服务器发送来的数据到缓冲区中
//
//                    count=client.read(receiveBuffer);
//                    if(count>0){
//                        receiveText = new String( receiveBuffer.array(),0,count);
//                        System.out.println("客户端接受服务器端数据--:"+receiveText);
//                        client.register(selector, SelectionKey.OP_WRITE);
//                    }
//
//                } else if (selectionKey.is.isWritable()) {
//                    sendBuffer.clear();
//                    client = (SocketChannel) selectionKey.channel();
//                    sendText = "message from client--" + (flag++);
//                    sendBuffer.put(sendText.getBytes());
//                     //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
//
//                    sendBuffer.flip();
//                    client.write(sendBuffer);
//                    System.out.println("客户端向服务器端发送数据--："+sendText);
//                    client.register(selector, SelectionKey.OP_READ);
//                }
            }
            selectionKeys.clear();
        }
    }
}