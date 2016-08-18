package com.anders.ssh.common;

import java.io.IOException;

public class Native2Ascii {  
    private static final String PREFIX = "\\u";   
    /** 
     * @param args 
     */  
    public static void main(String[] args)throws IOException {        
        String str = "address=淞虹路（(长宁区)）";  
        String ascii = native2ascii(str);  
        System.out.println("ascii->\n" + ascii);  
        //   
        String data = ascii2native(ascii);  
        System.out.println("native->\n" + data);  
    }  
    /** 
     * native2ascii 
     * @param text 
     * @return 
     */  
    public static String native2ascii(String text){  
        StringBuilder sb = new StringBuilder();  
        for (int i=0; i<text.length(); i++){  
            char ch = text.charAt(i);  
            String ascii = char2ascii(ch);  
            sb.append(ascii);  
        }  
        return sb.toString();  
    }  
    /** 
     * char -> ascii 
     * @param ch 
     * @return 
     */  
    private static String char2ascii(char ch) {  
        if (ch < 256){  
            return Character.toString(ch);  
        }  
        String hex = Integer.toHexString(ch);  
        if (hex.length() < 4){  
            hex = "0" + hex;  
        }  
        return PREFIX + hex;  
    }  
    /** 
     * native2ascii 
     * @param ascii 
     * @return 
     */  
    public static String ascii2native(String text){  
        StringBuilder sb = new StringBuilder();  
        int start = 0;  
        int idx = text.indexOf(PREFIX);  
        while (idx != -1){  
            // 上一个 Unicode 码与当前 Unicode 码之间的有效字符  
            // eg: \u0101ABC\u0102 之间的ABC  
            sb.append(text.substring(start, idx));  
            // 转换当前 Unicode 码  
            String ascii = text.substring(idx + 2,idx + 6);  
            char ch = (char)Integer.parseInt(ascii,16);  
            sb.append(ch);  
            // 查找下一个 Unicode  
            start = idx + 6;  
            idx = text.indexOf(PREFIX,start);  
        }  
        // 结尾的有效字符  
        sb.append(text.substring(start));  
        return sb.toString();  
    }   
}  
