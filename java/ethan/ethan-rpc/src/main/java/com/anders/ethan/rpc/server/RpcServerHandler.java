package com.anders.ethan.rpc.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.ethan.rpc.common.RpcRequest;
import com.anders.ethan.rpc.common.RpcResponse;

public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RpcServerHandler.class);

	private final Map<String, Object> providersMap;

	public RpcServerHandler(Map<String, Object> providersMap) {
		this.providersMap = providersMap;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request)
			throws Exception {

		System.out.println(this + " " + request);

		RpcResponse response = new RpcResponse();
		response.setRequestId(request.getRequestId());
		try {
			Object result = invoke(request);
			response.setResult(result);
		} catch (Throwable t) {
			response.setException(t);
		}
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

		System.out.println(this + " " + response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		LOGGER.error(cause.getMessage());
		ctx.close();
	}

	private Object invoke(RpcRequest request) throws Throwable {
		String className = request.getClassName();
		Object serviceBean = providersMap.get(className);

		Class<?> serviceClass = serviceBean.getClass();
		String methodName = request.getMethodName();
		Class<?>[] parameterTypes = request.getParamTypes();
		Object[] parameters = request.getParamValues();

		FastClass serviceFastClass = FastClass.create(serviceClass);
		FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName,
				parameterTypes);
		return serviceFastMethod.invoke(serviceBean, parameters);
	}
}
