package com.anders.ethan.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class Server {
	@Test
	public void test1() throws UnknownHostException, IOException {
		Socket socket = new  Socket("127.0.0.1",3306);
		 
		OutputStream out = socket.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(out);
		//建立连接报文信息 来自wireshark(捕捉终端执行mysql -u root -p -h 127.0.0.1时对应的login request信息)
		String hexs = "bb00000185a67f0000000001210000000000000000000000000000000000000000000000726f6f740014c2ee436b504f4f78089396223ad76f21fd5aee566d7973716c5f6e61746976655f70617373776f7264006a035f6f730964656269616e362e300c5f636c69656e745f6e616d65086c69626d7973716c045f7069640531363638330f5f636c69656e745f76657273696f6e06352e362e3137095f706c6174666f726d067838365f36340c70726f6772616d5f6e616d65056d7973716c";
		//byte[] bytes = convertHexStrToByteArray(hexs); //将上述的16进制信息转为byte数组 如"bb"--> -69
		byte[] bytes = null;
		int packetLen = 191;
		bos.write(bytes, 0, packetLen);
		bos.flush();
		 
		//执行查询命令 select 'hello' 来自wireshark
		hexs = "0f0000000373656c656374202768656c6c6f27";
//		bytes = convertHexStrToByteArray(hexs);
		bos.write(bytes,0,19);
		bos.flush();
		 
		//读取查询SQL的返回
		InputStream in = socket.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(in);
		byte[] buf = new byte[1024];
		int len = bis.read(buf);
		System.out.println(new String(buf, len-14, 5));
		 
		
		 
		
	}
	
	private byte[] convertHexStrToByteArray(String hexs){
	    byte[] a = new byte[hexs.length()/2];
	    int index = 0;
	    for(int i=0; i<hexs.length(); i+=2){
	        String e = hexs.substring(i, i+2);       a[index++] = convertIntToByte(Integer.valueOf(e, 16));
	    }
	    
	    return a;
	}
	
	private byte convertIntToByte(int i) {
	    if(i<= 127)
	        return (byte)i;
	    else
	        return (byte)(i-256);
	}
	
	private String convertByteArrayToHexString(byte[] result) {
	    StringBuilder sb = new StringBuilder();
	    for(byte b : result){
	        int i = b;
	        if(i<0)
	            i = 256-(i*-1);
	        String hex = Integer.toHexString(i);
	        if(hex.length()==1)
	            sb.append("0");
	        sb.append(Integer.toHexString(i));
	    }
	    return sb.toString();
	}
}
