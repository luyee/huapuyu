package com.anders.pomelo.databus.model.column;

import java.io.Serializable;

import com.anders.pomelo.databus.model.Column;

public class DateColumn extends Column implements Serializable {

	private static final long serialVersionUID = 2829017960208954775L;

	public DateColumn(String name, String type, long pos) {
		super(name, type, pos);
	}

}
