package builder;

public abstract class Builder {
	public Builder() {
	}

	public abstract void 主题();

	public abstract void 内容();

	public void 发件人(String person) {
		System.out.println(this.toString() + " : " + person);
	}

	public void 收件人(String person) {
		System.out.println(this.toString() + " : " + person);
	}
}
