package com.anders.ethan.databus.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import com.google.code.or.OpenParser;
import com.google.code.or.OpenParserTest;
import com.google.code.or.binlog.BinlogEventListener;
import com.google.code.or.binlog.BinlogEventV4;
import com.google.code.or.binlog.impl.event.XidEvent;

public class ParserTest {
	
	
	public static void main(String[] args)
		    throws Exception
		  {
		    OpenParser op = new OpenParser();
		    op.setStartPosition(4);
		    op.setBinlogFileName("xp-bin.000001");
		    op.setBinlogFilePath("D:/soft/mysql-5.6.19-master/data");
		    
		    op.setBinlogEventListener(new BinlogEventListener()
		    {
		      public void onEvents(BinlogEventV4 event)
		      {
		    	  System.out.println(event);
//		        if ((event instanceof XidEvent)) {
//		          OpenParserTest.LOGGER.info("{}", event);
//		          System.out.println(event);
//		        }
		      }
		    });
		    op.start();
		    
//		    LOGGER.info("press 'q' to stop");
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    for (String line = br.readLine(); line != null; line = br.readLine()) {
		      if (line.equals("q"))
		      {
		        op.stop(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		        break;
		      }
		    }
		  }
}
