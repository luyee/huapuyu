package visitor;

/**
 * ConcreteElement
 * 
 * @author Anders Zhu
 * 
 */
public class 男人 extends Person {
	@Override
	public void accept(Action visitor) {
		visitor.getManConclusion(this);
	}
}
