package com.anders.experiment.cglib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Tester {
	public final static String ENTER = "\r\n";
	public final static String LEFT_BRACKET = "{";
	public final static String RIGHT_BRACKET = "}";
	public final static String TAP = "\t";
	public final static String SPACE = " ";

	public static void main(String[] args) throws IOException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, InterruptedException {
		// 生成java代码
		StringBuilder sb = new StringBuilder();
		// sb.append("package com.anders.experiment.cglib;" + ENTER);
		// sb.append(ENTER);
		sb.append("public class Temp" + ENTER);
		sb.append(LEFT_BRACKET + ENTER);
		sb.append(TAP + "public Temp(Integer i)" + ENTER);
		sb.append(TAP + LEFT_BRACKET + ENTER);
		sb.append(TAP + TAP + "System.out.println(\"cglib test\");" + ENTER);
		sb.append(TAP + RIGHT_BRACKET + ENTER);
		sb.append(RIGHT_BRACKET + ENTER);

		System.out.println("Temp.java内容为：\n" + sb.toString());

		Thread.sleep(1000);

		// String fileName = System.getProperty("user.dir") +
		// "/src/main/java/com/anders/experiment/cglib/Temp.java";
		String fileName = "D:\\Temp.java";
		File file = new File(fileName);
		System.out.println("Temp.java路径为：" + fileName);
		FileWriter fw = new FileWriter(file);
		fw.write(sb.toString());
		fw.flush();
		fw.close();

		Thread.sleep(1000);

		// 编译成class
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> iterable = fileManager.getJavaFileObjects(fileName);
		CompilationTask compilationTask = compiler.getTask(null, fileManager, null, null, null, iterable);
		compilationTask.call();

		Thread.sleep(1000);

		try {
			Class.forName("Temp");
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Thread.sleep(1000);

		// 将class文件拉入classPath中
		// URL[] urls = new URL[] { new URL("file:/" + System.getProperty("user.dir") +
		// "/src/main/java") };
		// System.out.println(file.toURI().toURL()); // 输出为：file:/D:/Temp.java
		URL[] urls = new URL[] { new URL("file:/D:/") };
		URLClassLoader urlClassLoader = new URLClassLoader(urls);
		// 注意：url只能为路径（例如：file:/D:/），不能为文件（例如：file:/D:/Temp.java），因为loadClass中会将url（file:/D:/）+name（例如Temp）+.class作为class文件路径，比如这里的file:/D:/Temp.class
		// 原来我将Temp设置了包（com.anders.experiment.cglib.Temp），但是loadClass中会将其改为com/anders/experiment/cglib/Temp，处理后为file:/D:/com/anders/experiment/cglib/Temp.class，该路径下并没有class文件，所以系统抛错ClassNotFoundException
		// Class<?> c = urlClassLoader.loadClass("com.anders.experiment.cglib.Temp");
		Class<?> c = urlClassLoader.loadClass("Temp");
		System.out.println(c);

		// 有参构造方法的实例化
		Constructor<?> constructor = c.getConstructor(Integer.class);
		constructor.newInstance(1);
	}
}
