package simpleFactory.dahua;

public class SubOperation extends Operation<Integer> {
	@Override
	Integer operator() {
		return a - b;
	}
}
