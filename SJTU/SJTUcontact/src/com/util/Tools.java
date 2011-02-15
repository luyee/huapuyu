package com.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.os.Environment;
import cn.com.sjtu.ContactColumn;
import cn.com.sjtu.ExportUtils;

public class Tools {
	private static CodeUtils codeUtils;
	private static String sdcardPath;
	
	/**
	 * 
	 * @return 2001-11-11 11:11:11
	 */
	public static String getTime(){
		Date date = new Date();
		return String.format("%tF", date) + " " + String.format("%tT", date);
	}
	
	public static List<User> cursor2User(Cursor cursor,Map<Integer, String> groupMap,boolean codeFlag){
		int count = cursor.getCount();
		List<User> userList = new ArrayList<User>();
		cursor.moveToFirst();
		for (int i = 0; i < count; i++) {
			User user = new User();
			
			user.setId(cursor.getInt(cursor.getColumnIndex(ContactColumn._ID)));
			user.setMobileNumber(cursor.getString(cursor.getColumnIndex(ContactColumn.MOBILE)));
			user.setName(cursor.getString(cursor.getColumnIndex(ContactColumn.NAME)));
			user.setCreatedDate(cursor.getString(cursor.getColumnIndex(ContactColumn.CREATED)));
			user.setModifiedDate(cursor.getString(cursor.getColumnIndex(ContactColumn.MODIFIED)));
			user.setGroupnum(cursor.getInt(cursor.getColumnIndex(ContactColumn.GROUPNUM)));
			user.setGroupName(groupMap.get(user.getGroupnum()));
			user.setIsCode(codeFlag);
			user.setEmail(cursor.getString(cursor.getColumnIndex(ContactColumn.EMAIL)));
			user.setAddress(cursor.getString(cursor.getColumnIndex(ContactColumn.ADDRESS)));
			user.setHomenum(cursor.getString(cursor.getColumnIndex(ContactColumn.HOMENUM)));
			
			userList.add(user);
			cursor.moveToNext();
		}
		return userList;
	}
	
	public static Map<Integer, String> getIdColumnMap(Cursor cursor){
		Map<Integer, String> map = new HashMap<Integer, String>();
		String[] strs = cursor.getColumnNames();
		String id = "_id";
		String others = "";
		for (int i = 0; i < strs.length; i++) {
			if(!strs[i].equals("_id")) others = strs[i];
		}
		int count = cursor.getCount();
		cursor.moveToFirst();
		for (int i = 0; i < count; i++) {
			map.put(cursor.getInt(cursor.getColumnIndex(id)), cursor.getString(cursor.getColumnIndex(others)));
			cursor.moveToNext();
		}
		return map;
	}
	
	public static String enCode (String message){
		if(codeUtils == null ){
			try {
				codeUtils = new CodeUtils();
			} catch (Exception e) {
				e.printStackTrace();
				return "加密出错"+e.getMessage();
			}
		}
		try {
			return codeUtils.encrypt(message);
		} catch (Exception e) {
			e.printStackTrace();
			return "加密出错"+e.getMessage();
		}
		
	}
	
	public static String deCode (String message){
		if(codeUtils == null ){
			try {
				codeUtils = new CodeUtils();
			} catch (Exception e) {
				e.printStackTrace();
				return "解密出错"+e.getMessage();
			}
		}
		try {
			return codeUtils.decrypt(message);
		} catch (Exception e) {
			e.printStackTrace();
			return "解密出错"+e.getMessage();
		}
	}
	
	public static String getContactSavePath(){
		String dir = getSdcardPath() + "contact/";
		File dirFile = new File(dir);
		if(!dirFile.isDirectory()){
			dirFile.mkdir();
		}
		return dir;
	}
	
	public static String getSdcardPath() {
		if(null == sdcardPath){
			sdcardPath = Environment.getExternalStorageDirectory() + "/";
		}
		return sdcardPath;
	}
}
