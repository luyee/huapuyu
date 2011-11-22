package javassist;

public class Tester {
	public static void main(String args[]) throws Exception {
		// Hello hello = new Hello();
		// hello.say();
		ClassPool cp = ClassPool.getDefault();
		CtClass cc = cp.get("javassist.Hello");
		CtMethod m = cc.getDeclaredMethod("say");
		m.setBody("{System.out.println(\"shit\");}");
		m.insertBefore("System.out.println(\"fuck\");");
		m.insertAfter("System.out.println(\"method over\");");
		Class c = cc.toClass();
		Hello h = (Hello) c.newInstance();
		h.say();
	}
}
