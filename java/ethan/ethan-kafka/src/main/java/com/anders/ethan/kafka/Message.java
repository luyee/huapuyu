package com.anders.ethan.kafka;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2527215292797077908L;
	private List<EventData> datas;

	public List<EventData> getDatas() {
		return datas;
	}

	public void setDatas(List<EventData> datas) {
		this.datas = datas;
	}
	
	
}
