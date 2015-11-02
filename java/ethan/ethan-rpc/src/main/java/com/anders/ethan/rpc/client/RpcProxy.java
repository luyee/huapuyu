package com.anders.ethan.rpc.client;

import java.lang.reflect.Method;
import java.util.UUID;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

import org.springframework.util.Assert;

import com.anders.ethan.rpc.common.RpcRequest;
import com.anders.ethan.rpc.common.RpcResponse;
import com.anders.ethan.rpc.zk.ServiceDiscovery;

public class RpcProxy {
	private String serverAddress;
	private ServiceDiscovery serviceDiscovery;

	// P2P
	public RpcProxy(String serverAddress) {
		Assert.hasText(serverAddress);
		this.serverAddress = serverAddress;
	}

	// ZK
	public RpcProxy(ServiceDiscovery serviceDiscovery) {
		Assert.notNull(serviceDiscovery);
		this.serviceDiscovery = serviceDiscovery;
	}

	@SuppressWarnings("unchecked")
	public <T> T create(Class<?> interfaceClass) {
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
				new Class<?>[] { interfaceClass }, new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						RpcRequest request = new RpcRequest();
						request.setRequestId(UUID.randomUUID().toString());
						request.setClassName(method.getDeclaringClass()
								.getName());
						request.setMethodName(method.getName());
						request.setParamTypes(method.getParameterTypes());
						request.setParamValues(args);

						// 如果serviceDiscovery为空，则是P2P连接
						if (serviceDiscovery != null) {
							serverAddress = serviceDiscovery.discover(request
									.getClassName());
						}

						String[] array = serverAddress.split(":");

						String host = array[0];
						int port = Integer.parseInt(array[1]);

						RpcClient client = new RpcClient(host, port);
						RpcResponse response = client.send(request);

						if (response.isException()) {
							throw response.getException();
						} else {
							return response.getResult();
						}
					}
				});
	}
}
