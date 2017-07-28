package com.anders.pomelo.databus.model.column;

import java.io.Serializable;

import com.anders.pomelo.databus.model.Column;

public class BigIntColumn extends Column implements Serializable {

	private static final long serialVersionUID = 6617775472727821679L;

	private boolean signed;

	public BigIntColumn(String name, String type, long pos, boolean signed) {
		super(name, type, pos);
		this.signed = signed;
	}

	public boolean isSigned() {
		return signed;
	}

	public void setSigned(boolean signed) {
		this.signed = signed;
	}

}
