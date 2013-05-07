package spring.setter循环依赖;

public class ClassB {
	private ClassA classA;

	public ClassB() {
	}

	public ClassB(ClassA classA) {
		this.classA = classA;
	}

	public void setClassA(ClassA classA) {
		this.classA = classA;
	}

	public void b() {
		classA.a();
	}
}
