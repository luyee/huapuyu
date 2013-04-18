package simpleFactory.dahua;

public class OperationFactory {
	public enum Operator {
		ADD, SUB
	}

	public static Operation<Integer> getOperation(Operator operator) {
		switch (operator) {
		case ADD:
			return new AddOperation();
		case SUB:
			return new SubOperation();
		default:
			throw new RuntimeException("没有默认的操作");
		}
	}
}
