package aop.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxyFactory implements MethodInterceptor {
	private Object targerObject;

	public Object createProxyInstance(Object targetObject) {
		this.targerObject = targetObject;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.targerObject.getClass());
		enhancer.setCallback(this);
		return enhancer.create();
	}

	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		PersonServiceBean bean = (PersonServiceBean) this.targerObject;
		Object result = null;
		if (bean.getUser() != null) {
			System.out.println(proxy.getClass());
			System.out.println(method.getName() + " : user is not null");
			result = method.invoke(this.targerObject, args);
		}
		else {
			System.out.println(proxy.getClass());
			System.out.println(method.getName() + " : user is null");
		}
		return result;
	}
}
