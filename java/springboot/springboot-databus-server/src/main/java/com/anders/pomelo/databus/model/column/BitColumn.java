package com.anders.pomelo.databus.model.column;

import java.io.Serializable;

import com.anders.pomelo.databus.model.Column;

public class BitColumn extends Column implements Serializable {

	private static final long serialVersionUID = 2829017960208954775L;

	// TODO Anders 增加length字段

	public BitColumn(String name, String type, long pos) {
		super(name, type, pos);
	}

}
