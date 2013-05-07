package javassist;

public class Tester {
	public static void main(String args[]) throws Exception {
		// Hello hello = new Hello();
		// hello.say();
		ClassPool cp = ClassPool.getDefault();
		CtClass cc = cp.get("javassist.Hello");
		CtMethod cm = cc.getDeclaredMethod("say");
		cm.setBody("{System.out.println(\"body\");}");
		cm.insertBefore("System.out.println(\"before\");");
		cm.insertAfter("System.out.println(\"after\");");
		Class<?> c = cc.toClass();
		Hello hello = (Hello) c.newInstance();
		hello.say();
	}
}
