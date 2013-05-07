package spring.构造器循环依赖;

public class ClassA {
	private ClassB classB;

	public ClassA() {
	}

	public ClassA(ClassB classB) {
		this.classB = classB;
	}

	public void setClassB(ClassB classB) {
		this.classB = classB;
	}

	public void a() {
		classB.b();
	}
}
