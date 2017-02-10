package asm;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class AsmClassVisit extends ClassAdapter {

	private String className; // 用于保存原始类名

	public AsmClassVisit(ClassVisitor cv) {
		super(cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		className = name;
		superName = name;
		// name = name + AOPEngine.CHILD_POSTFIX; // 定义子类的名字
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);// 先得到原始的方法
		MethodVisitor newMethod = null;

		if ("add".equals(name)) // 此处的add即为需要修改的方法
		{
			newMethod = new AsmMethodVisit(mv); // 访问需要修改的方法
			return newMethod;
		}
		if ("<init>".equals(name)) // 还需要改造子类的构造方法
		{
			newMethod = new ChangeConstructorMethodAdapter(mv, className);
			return newMethod;
		}
		return mv;
	}

}
