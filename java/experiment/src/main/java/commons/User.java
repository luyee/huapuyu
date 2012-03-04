package commons;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class User {
	private Long id;
	private String name;
	private String desc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
