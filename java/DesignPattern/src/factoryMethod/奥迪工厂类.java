package factoryMethod;

public class 奥迪工厂类 implements IFactory {
	public ICar factory() {
		return new 奥迪();
	}
}
