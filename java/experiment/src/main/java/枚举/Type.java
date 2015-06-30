package 枚举;

public enum Type {
	可用((byte) 1), 不可用((byte) 0);

	private final Byte value;

	private Type(Byte value) {
		this.value = value;
	}

	public Byte getValue() {
		return value;
	}
}
