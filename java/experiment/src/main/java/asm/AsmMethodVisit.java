package asm;

import java.util.List;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.springframework.aop.Advisor;

public class AsmMethodVisit extends MethodAdapter {

	private String advisorClassName;
	private List<Advisor> advisors;

	public AsmMethodVisit(MethodVisitor mv) {
		super(mv);
	}

	public AsmMethodVisit(MethodVisitor mv, String advisorClassName, List<Advisor> advisors) {
		super(mv);
		this.advisorClassName = advisorClassName;
		this.advisors = advisors;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		super.visitMethodInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitCode() {
		// 此方法在访问方法的头部时被访问到，仅被访问一次
		// 此处可插入新的指令
		super.visitCode();
	}

	@Override
	public void visitInsn(int opcode) {
		// 此方法可以获取方法中每一条指令的操作类型，被访问多次
		// 如应在方法结尾处添加新指令，则应判断：
		if (opcode == Opcodes.RETURN) {
			mv.visitTypeInsn(Opcodes.NEW, "com/xyz/Check");// 新建一个Check类
			mv.visitInsn(Opcodes.DUP);// NEW指令完后，对象的引用将从栈中弹出，而INVOKESPECIAL需要该引用，用DUP指令增加对象的引用
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/xyz/Check", "<init>", "()V");// 调用构造方法，初始化Check类
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/xyz/Check", "checkSecurity", "()V");// 调用类中的方法
		}
		super.visitInsn(opcode);
	}
}
