package visitor;

/**
 * Element
 * 
 * @author Anders Zhu
 * 
 */
public abstract class Person {
	public abstract void accept(Action visitor);
}
