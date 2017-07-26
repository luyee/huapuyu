package com.anders.pomelo.databus.model.column;

import java.io.Serializable;

import com.anders.pomelo.databus.model.Column;

public class FloatColumn extends Column implements Serializable {

	private static final long serialVersionUID = 2829017960208954775L;

	private boolean signed;

	public FloatColumn(String name, String type, long pos, boolean signed) {
		super(name, type, pos);
		this.signed = signed;
		// TODO Anders maxwell没有考虑无符号问题
	}

	public boolean isSigned() {
		return signed;
	}

	public void setSigned(boolean signed) {
		this.signed = signed;
	}

}
