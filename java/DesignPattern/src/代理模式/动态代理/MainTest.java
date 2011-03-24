package 代理模式.动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MainTest
{
	public static void main(String[] args) throws IllegalArgumentException, InstantiationException, IllegalAccessException
	{
		Subject subject = (Subject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(), RealSubject.class.getInterfaces(), new ProxySubject());
		subject.request();

		MyInterface1 proxy1 = (MyInterface1) MyHandle.getObject(MyClass1.class);
		System.out.println(proxy1.getClass().getName());
		proxy1.sayHello();
		MyInterface2 proxy2 = (MyInterface2) MyHandle.getObject(MyClass2.class);
		System.out.println(proxy2.getClass().getName());
		proxy2.sayHello();
	}
}

interface MyInterface1
{
	public void sayHello();
}

class MyClass1 implements MyInterface1
{
	@Override
	public void sayHello()
	{
		System.out.println("fuck xwc");
	}
}

interface MyInterface2
{
	public void sayHello();
}

class MyClass2 implements MyInterface2
{
	@Override
	public void sayHello()
	{
		System.out.println("fuck wl");
	}
}

class MyProxy implements InvocationHandler
{
	private Object target;

	public MyProxy(Object target)
	{
		this.target = target;
	}

	public void before()
	{
		System.out.println("before");
	}

	public void after()
	{
		System.out.println("after");
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		this.before();
		method.invoke(this.target, args);
		this.after();
		return null;
	}
}

// 动态代理其实就是java.lang.reflect.Proxy类动态的根据您指定的所有接口生成一个class byte，
// 该class会继承Proxy类，并实现所有你指定的接口（您在参数中传入的接口数组）；
// 然后再利用您指定的classloader将class byte加载进系统，
// 最后生成这样一个类的对象，并初始化该对象的一些值，如invocationHandler，
// 以即所有的接口对应的Method成员。 初始化之后将对象返回给调用的客户端。这样客户端拿到的就是一个实现你所有的接口的Proxy对象。
class MyHandle
{
	@SuppressWarnings("unchecked")
	public static Object getObject(Class c) throws IllegalArgumentException, InstantiationException, IllegalAccessException
	{
		return Proxy.newProxyInstance(c.getClassLoader(), c.getInterfaces(), new MyProxy(c.newInstance()));
	}
}