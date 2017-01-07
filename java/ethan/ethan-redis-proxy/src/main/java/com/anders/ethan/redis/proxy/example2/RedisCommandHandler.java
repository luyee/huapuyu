package com.anders.ethan.redis.proxy.example2;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RedisCommandHandler extends SimpleChannelInboundHandler<Command> {

	private Map<String, Wrapper> methods = new HashMap<String, Wrapper>();

	interface Wrapper {
		Reply<?> execute(Command command) throws RedisException;
	}

	public RedisCommandHandler(final RedisServer rs) {
		Class<? extends RedisServer> aClass = rs.getClass();
		for (final Method method : aClass.getMethods()) {
			final Class<?>[] types = method.getParameterTypes();
			methods.put(method.getName(), new Wrapper() {
				@Override
				public Reply<?> execute(Command command) throws RedisException {
					Object[] objects = new Object[types.length];
					try {
						command.toArguments(objects, types);
						return (Reply<?>) method.invoke(rs, objects);
					} catch (Exception e) {
						return new ErrorReply("ERR " + e.getMessage());
					}
				}
			});
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Command msg)
			throws Exception {
		String name = new String(msg.getName());
		Wrapper wrapper = methods.get(name);
		Reply<?> reply;
		if (wrapper == null) {
			reply = new ErrorReply("unknown command '" + name + "'");
		} else {
			reply = wrapper.execute(msg);
		}
		if (reply == StatusReply.QUIT) {
			ctx.close();
		} else {
			if (msg.isInline()) {
				if (reply == null) {
					reply = new InlineReply(null);
				} else {
					reply = new InlineReply(reply.data());
				}
			}
			if (reply == null) {
				reply = ErrorReply.NYI_REPLY;
			}
			ctx.write(reply);
		}
	}
}
