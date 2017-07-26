package com.anders.pomelo.databus.model;

public abstract class Column {

	protected String name;
	protected String type;
	protected long pos;

	public Column(String name, String type, long pos) {
		this.name = name;
		this.type = type;
		this.pos = pos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getPos() {
		return pos;
	}

	public void setPos(long pos) {
		this.pos = pos;
	}

}
