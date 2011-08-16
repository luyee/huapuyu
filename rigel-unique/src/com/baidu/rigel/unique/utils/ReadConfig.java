package com.baidu.rigel.unique.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;

/**
 * 读取配置信息
 * 
 * @author Anders Zhu
 * 
 */
public class ReadConfig {

	private int days = 75;
	private String title;
	private String errorParamMsg;
	private Short lowestPosType;
	private Set<String> titleSet = new HashSet<String>();
	private String staticSourceServer;

	public String getStaticSourceServer() {
		return staticSourceServer;
	}

	public void setStaticSourceServer(String staticSourceServer) {
		this.staticSourceServer = staticSourceServer;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public void setTitle(String title) {
		if (StringUtils.isNotBlank(title)) {
			titleSet = new HashSet<String>(Arrays.asList(title.split(Constant.COMMA)));
			this.title = StringUtils.join(titleSet, Constant.COMMA);
		}
	}

	@PostConstruct
	public void splitTitleToSet() {
		titleSet.addAll(Arrays.asList(this.title.split(Constant.COMMA)));
	}

	public void addTitle(String title) {
		if (StringUtils.isNotBlank(title)) {
			titleSet = new HashSet<String>(Arrays.asList(title.split(Constant.COMMA)));
			if (StringUtils.isNotBlank(this.title))
				titleSet.addAll(Arrays.asList(this.title.split(Constant.COMMA)));
			this.title = StringUtils.join(titleSet, Constant.COMMA);
		}
	}

	public Set<String> getTitleSet() {
		return titleSet;
	}

	public void setTitleSet(Set<String> titleSet) {
		this.titleSet = titleSet;
	}

	public String getErrorParamMsg() {
		return errorParamMsg;
	}

	public void setErrorParamMsg(String errorParamMsg) {
		this.errorParamMsg = errorParamMsg;
	}

	public Short getLowestPosType() {
		return lowestPosType;
	}

	public void setLowestPosType(Short lowestPosType) {
		this.lowestPosType = lowestPosType;
	}
}
