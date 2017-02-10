package visitor;

/**
 * Visitor
 * 
 * @author Anders Zhu
 * 
 */
public abstract class Action {
	public abstract void getManConclusion(男人 man);

	public abstract void getWomanConclusion(女人 woman);
}
