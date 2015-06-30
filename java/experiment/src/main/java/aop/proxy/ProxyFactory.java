package aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory implements InvocationHandler {
	private Object targerObject;

	public Object createProxyInstance(Object targetObject) {
		this.targerObject = targetObject;
		return Proxy.newProxyInstance(this.targerObject.getClass().getClassLoader(), this.targerObject.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		PersonServiceBean bean = (PersonServiceBean) this.targerObject;
		Object result = null;
		if (bean.getUser() != null) {
			System.out.println(method.getName() + " : user is not null");
			result = method.invoke(this.targerObject, args);
		} else {
			System.out.println(method.getName() + " : user is null");
		}
		return result;
	}
}
