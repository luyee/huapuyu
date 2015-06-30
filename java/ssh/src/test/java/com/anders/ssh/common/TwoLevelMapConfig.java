package com.anders.ssh.common;

import java.util.HashMap;
import java.util.Map;

public class TwoLevelMapConfig {

	private Map<String, Map<String, String>> twoLevelMap = new HashMap<String, Map<String, String>>();
	private Map<String, Map<String, User>> twoLevelUserMap = new HashMap<String, Map<String, User>>();
	private Map<String, UserMap> twoLevelUser2Map = new HashMap<String, UserMap>();

	public Map<String, UserMap> getTwoLevelUser2Map() {
		return twoLevelUser2Map;
	}

	public void setTwoLevelUser2Map(Map<String, UserMap> twoLevelUser2Map) {
		this.twoLevelUser2Map = twoLevelUser2Map;
	}

	public Map<String, Map<String, String>> getTwoLevelMap() {
		return twoLevelMap;
	}

	public void setTwoLevelMap(Map<String, Map<String, String>> twoLevelMap) {
		this.twoLevelMap = twoLevelMap;
	}

	public Map<String, Map<String, User>> getTwoLevelUserMap() {
		return twoLevelUserMap;
	}

	public void setTwoLevelUserMap(Map<String, Map<String, User>> twoLevelUserMap) {
		this.twoLevelUserMap = twoLevelUserMap;
	}

}

class User {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class UserMap {
	private Map<String, User> map = new HashMap<String, User>();
	private String name;

	public String getName() {
		return name;
	}

	public Map<String, User> getMap() {
		return map;
	}

	public void setMap(Map<String, User> map) {
		this.map = map;
	}

	public void setName(String name) {
		this.name = name;
	}
}
