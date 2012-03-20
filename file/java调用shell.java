jShell.java 


/* 
 *  jShell.java 
 *  class jShell is used for executing shell command 
 *  USAGE: 
 *      jShell obj=new jShell(shellCommand); 
 *      obj.startErr(); 
 *      obj.startOut(); 
 *      obj.startIn(); 
 *  You can Interupt I/O thread when nessasary: 
 *      obj.interruptErr(); 
 *      obj.interruptOut(); 
 *      obj.interruptIn(); 
 *   
 *  BY Ahui Wang    Nankai U.    2007-05-12    
 */ 

import java.io.*; 

public class jShell { 
    Thread tIn;    //handle input of child process 
    Thread tOut;//handle output of child process 
    Thread tErr;//handle error output of child process 
     
    public jShell(String shellCommand){ 
         
        Process child=null;    //child process 
        try{ 
            child=Runtime.getRuntime().exec(shellCommand); 
        } 
        catch(IOException e){ 
            e.printStackTrace(); 
        } 
        if(child==null){ 
            return; 
        } 
         
        final InputStream inputStream=child.getInputStream(); 
        final BufferedReader brOut= 
            new BufferedReader(new InputStreamReader(inputStream)); 
             
        tOut=new Thread(){    //initialize thread tOut 
            String line; 
            int lineNumber=0; 
            public void run(){ 
                try{ 
                    while((line=brOut.readLine())!=null){     
                        System.out.println(lineNumber+". "+line); 
                        lineNumber++; 
                        } 
                    } 
                catch(IOException e){ 
                    e.printStackTrace(); 
                } 
            } 
        }; 
             
        final InputStream errorStream=child.getErrorStream(); 
        final BufferedReader brErr= 
            new BufferedReader(new InputStreamReader(errorStream)); 
         
        tErr=new Thread(){    //initialize thread tErr 
            String line; 
            int lineNumber=0; 
            public void run(){ 
                try{ 
                    while((line=brErr.readLine())!=null){ 
                        System.out.println(lineNumber+". "+line); 
                        lineNumber++; 
                        } 
                    } 
                catch(IOException e){ 
                    e.printStackTrace(); 
                } 
            } 
        }; 
         
        // read buffer of parent process' input stream 
        final BufferedReader reader = 
            new BufferedReader(new InputStreamReader(System.in)); 
        final OutputStream outputStream = child.getOutputStream(); 
        tIn=new Thread(){ 
            String line; 
            public void run() { 
                try { 
                    while (true) { 
                        outputStream.write( (reader.readLine()+"\n").getBytes()); 
                        outputStream.flush(); 
                    } 
                } 
                catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            }  
        }; 
             
    } 
    public void startIn(){ //start thread tIn 
        if(tIn!=null){ 
            tIn.start(); 
        } 
    } 
    public void startErr(){ //start thread tErr 
        if(tErr!=null){ 
            tErr.start(); 
        } 
    } 
    public void startOut(){ //start thread tOut 
        if(tOut!=null){ 
            tOut.start(); 
        } 
    } 
    public void interruptIn(){ //interrupt thread tIn 
        if(tIn!=null){ 
            tIn.interrupt(); 
        } 
    } 
    public void interruptErr(){ //interrupt thread tErr 
        if(tErr!=null){ 
            tErr.interrupt(); 
        } 
    } 
    public void interruptOut(){ //interrupt thread tOut 
        if(tOut!=null){ 
            tOut.interrupt(); 
        } 
    } 
     
}  

CODE: mainC.java 
---------------------------------------------------------------------------------- 
public final class mainC { 
    public static void main(String[] args) { 
        jShell shell=new jShell("ls -l"); 
        shell.startErr(); 
        shell.startIn(); 
        shell.startOut(); 
    } 
} 

RESULT: 
--------------------------------------------------------------------------------- 
0. 总用量 44 
1. -rwxrwxrwx    1 root     root          219  5月 12 10:41 ex.pl 
2. -rwxrwxrwx    1 root     root          211  5月 12 10:39 ex.pl~ 
3. -rwxrwxrwx    1 root     root          150  5月 12 10:41 ex.sh 
4. -rwxrwxrwx    1 root     root          124  5月 12 10:20 ex.sh~ 
5. -rwxrwxrwx    1 root     root         1198  5月 12 10:43 jShell$1.class 
6. -rwxrwxrwx    1 root     root         1198  5月 12 10:43 jShell$2.class 
7. -rwxrwxrwx    1 root     root         1222  5月 12 10:43 jShell$3.class 
8. -rwxrwxrwx    1 root     root         2241  5月 12 10:43 jShell.class 
9. -rwxrwxrwx    1 root     root         2720  5月 12 10:43 jShell.java 
10. -rwxrwxrwx    1 root     root          544  5月 12 11:43 mainC.class 
11. -rwxrwxrwx    1 root     root          170  5月 12 11:43 mainC.java  

PS:

Process  process=Runtime.getRuntime().exec("");中产生停滞（阻塞，blocking），怎么解决？   
---------------------------------------------------------------   

这个是因为Runtime.getRuntime().exec()要自己去处理stdout和stderr的。   
所以如果你想让程序正常运行的话，请务必将上述用别的线程流取走。   


【JAVA中执行bat】 

>test.bat   
haha   
exit  99   

>RuntimeTest.java   
public  class  RuntimeTest  {   

           public  static  void  main(String[]  args)  {   
                       try  {   
                                   Process  process=Runtime.getRuntime().exec("test.bat");   
                                   StreamGobbler  errorGobbler  =  new  StreamGobbler(process.getErrorStream(),  "ERROR");                           
                  
                           //  kick  off  stderr   
                           errorGobbler.start();   
                              
                           StreamGobbler  outGobbler  =  new  StreamGobbler(process.getInputStream(),  "STDOUT");   
                           //  kick  off  stdout   
                           outGobbler.start();   
                              
                                   process.waitFor();   
                                   System.out.println(process.exitValue());   
                       }  catch(Exception  e)  {}               
           }   
}   


>StreamGobbler.java   
import  java.io.BufferedReader;   
import  java.io.IOException;   
import  java.io.InputStream;   
import  java.io.InputStreamReader;   
import  java.io.OutputStream;   
import  java.io.PrintWriter;   

public  class  StreamGobbler  extends  Thread  {   
           InputStream  is;   
           String  type;   
           OutputStream  os;   
          
           StreamGobbler(InputStream  is,  String  type)  {   
                       this(is,  type,  null);   
           }   

       StreamGobbler(InputStream  is,  String  type,  OutputStream  redirect)  {   
               this.is  =  is;   
               this.type  =  type;   
               this.os  =  redirect;   
       }   
          
       public  void  run()  {   
               try  {   
                       PrintWriter  pw  =  null;   
                       if  (os  !=  null)   
                               pw  =  new  PrintWriter(os);   
                                  
                       InputStreamReader  isr  =  new  InputStreamReader(is);   
                       BufferedReader  br  =  new  BufferedReader(isr);   
                       String  line=null;   
                       while  (  (line  =  br.readLine())  !=  null)  {   
                               if  (pw  !=  null)   
                                       pw.println(line);   
                               System.out.println(type  +  ">"  +  line);           
                       }   
                       if  (pw  !=  null)   
                               pw.flush();   
               }  catch  (IOException  ioe)  {   
                       ioe.printStackTrace();       
               }   
       }   
} 



本文来自CSDN博客，转载请标明出处：http://blog.csdn.net/hanqunfeng/archive/2009/07/21/4368517.aspx