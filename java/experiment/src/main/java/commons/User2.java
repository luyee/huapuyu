package commons;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class User2 {
	private Long id;
	private String name;
	private Date time;
	private List<User3> list;

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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<User3> getList() {
		return list;
	}

	public void setList(List<User3> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
