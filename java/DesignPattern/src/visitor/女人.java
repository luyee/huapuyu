package visitor;

/**
 * ConcreteElement
 * 
 * @author Anders Zhu
 * 
 */
public class 女人 extends Person {
	@Override
	public void accept(Action visitor) {
		visitor.getWomanConclusion(this);
	}
}
