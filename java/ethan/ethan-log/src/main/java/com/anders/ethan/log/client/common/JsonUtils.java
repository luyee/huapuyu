package com.anders.ethan.log.client.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anders.ethan.log.common.Annotation;
import com.anders.ethan.log.common.Endpoint;
import com.anders.ethan.log.common.Span;

public class JsonUtils implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4067763405310397425L;



	public static <T> String toJson(T o) {
		String s = "";
	    s = JSON.toJSONString(o,SerializerFeature.DisableCircularReferenceDetect);
		return s;
	}
	
	public static <T> List<T> fromJsonArray(String json, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		list = JSON.parseArray(json, clazz);
		return list;
	}

	public static <T>  T fromJson(String json, Class<T> clazz) {
		
		return JSON.parseObject(json, clazz);
	}
	
	
	
	public static void main(String[] args) {
		Span span  = new Span();
//		span.setApp("aaa");
		Annotation annotation1 = new Annotation();
		Endpoint endpoint =  new Endpoint();
		endpoint.setHost("1");
		annotation1.setHost(endpoint);
		List<Annotation> annotations = new ArrayList<Annotation>();
		
		annotations.add(annotation1);
		
		Annotation annotation2 = new Annotation();
		Endpoint endpoint1 =  new Endpoint();
		endpoint1.setHost("2");
		annotation2.setHost(endpoint1);
		annotations.add(annotation2);
		span.setAnnotations(annotations);
		System.out.println(JsonUtils.toJson(span));
		System.out.println(System.getProperty("user.home") );
	}
}
