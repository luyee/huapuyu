package com.anders.pomelo.databus.model.column;

import java.io.Serializable;

import com.anders.pomelo.databus.model.Column;

public class StringColumn extends Column implements Serializable {

	private static final long serialVersionUID = 6409161406148643513L;

	private String charset;

	public StringColumn(String name, String type, long pos, String charset) {
		super(name, type, pos);
		this.charset = charset;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}
