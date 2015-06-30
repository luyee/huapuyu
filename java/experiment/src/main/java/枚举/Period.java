package 枚举;

public enum Period {

	SHORT("短期", 0), LONG("长期", 1);

	private Integer value;
	private String label;

	private Period(String label, Integer value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return this.label;
	}

	public Integer getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.label;
	}

}
