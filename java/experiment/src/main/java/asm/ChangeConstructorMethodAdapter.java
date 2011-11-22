package asm;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ChangeConstructorMethodAdapter extends MethodAdapter {

	private String superClassName;// 用于接收父类名

	public ChangeConstructorMethodAdapter(MethodVisitor arg0, String superClassName) {
		super(arg0);
		this.superClassName = superClassName;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		// 如果当前指令是调用父类的构造方法
		if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
			owner = superClassName;
		}
		super.visitMethodInsn(opcode, owner, name, desc);// 改写父类为superClassName
	}
}
