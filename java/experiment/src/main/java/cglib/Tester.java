package cglib;

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

public class Tester
{
	public final static String ENTER = "\r\n";
	public final static String LEFT_BRACKET = "{";
	public final static String RIGHT_BRACKET = "}";
	public final static String TAP = "\t";
	public final static String SPACE = " ";

	public static void main(String[] args) throws IOException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		// 生成java代码
		StringBuilder sb = new StringBuilder();
		// String src = "package cglib;" + enter + "public class Temp {" + enter + "	" + "public Temp(Integer i){}" + enter + "public static void main(String[] args) {" + enter + "		System.out.println(\"This is a Test\");" + enter + "	}}";

		sb.append("package cglib;" + ENTER);
		sb.append(ENTER);
		sb.append("public class Temp" + ENTER);
		sb.append(LEFT_BRACKET + ENTER);
		sb.append(TAP + "public Temp(Integer i)" + ENTER);
		sb.append(TAP + LEFT_BRACKET + ENTER);
		sb.append(TAP + TAP + "System.out.println(\"cglib test\");" + ENTER);
		sb.append(TAP + RIGHT_BRACKET + ENTER);
		sb.append(RIGHT_BRACKET + ENTER);

		System.out.println(sb.toString());

		String fileName = System.getProperty("user.dir") + "/src/main/java/cglib/Temp.java";
		System.out.println(fileName);
		FileWriter fw = new FileWriter(new File(fileName));
		fw.write(sb.toString());
		fw.flush();
		fw.close();

		// 编译成class
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> iterable = fileManager.getJavaFileObjects(fileName);
		CompilationTask compilationTask = compiler.getTask(null, fileManager, null, null, null, iterable);
		compilationTask.call();

		// 将class文件拉入classPath中
		URL[] urls = new URL[] { new URL("file:/" + System.getProperty("user.dir") + "/src/main/java") };
		URLClassLoader urlClassLoader = new URLClassLoader(urls);
		Class<?> c = urlClassLoader.loadClass("cglib.Temp");
		System.out.println(c);

		// 有参构造方法的实例化
		Constructor<?> constructor = c.getConstructor(Integer.class);
		constructor.newInstance(1);
	}
}
