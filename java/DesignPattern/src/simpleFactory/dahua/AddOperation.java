package simpleFactory.dahua;

public class AddOperation extends Operation<Integer> {
	@Override
	Integer operator() {
		return a + b;
	}
}
