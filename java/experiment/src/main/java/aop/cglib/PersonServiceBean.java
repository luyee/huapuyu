package aop.cglib;

public class PersonServiceBean {
	private String user = null;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public PersonServiceBean() {
	}

	public PersonServiceBean(String user) {
		this.user = user;
	}

	public String getPersonName(Integer personid) {
		System.out.println("执行getPersonName方法");
		return "xxx";
	}

	public void save(String name) {
		System.out.println("执行save方法");
	}

	public void update(String name, Integer personid) {
		System.out.println("执行update方法");
	}
}
