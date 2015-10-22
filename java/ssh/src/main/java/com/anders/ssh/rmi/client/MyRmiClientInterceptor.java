package com.anders.ssh.rmi.client;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.remoting.RemoteInvocationFailureException;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.rmi.RmiClientInterceptorUtils;
import org.springframework.remoting.rmi.RmiInvocationHandler;
import org.springframework.remoting.support.RemoteInvocationBasedAccessor;
import org.springframework.remoting.support.RemoteInvocationUtils;

public class MyRmiClientInterceptor extends RemoteInvocationBasedAccessor implements MethodInterceptor {

	private boolean lookupStubOnStartup = true;

	private boolean cacheStub = true;

	private boolean refreshStubOnConnectFailure = false;

	private RMIClientSocketFactory registryClientSocketFactory;

	private Remote cachedStub;

	private final Object stubMonitor = new Object();

	/**
	 * Set whether to look up the RMI stub on startup. Default is "true".
	 * <p>
	 * Can be turned off to allow for late start of the RMI server. In this
	 * case, the RMI stub will be fetched on first access.
	 * 
	 * @see #setCacheStub
	 */
	public void setLookupStubOnStartup(boolean lookupStubOnStartup) {
		this.lookupStubOnStartup = lookupStubOnStartup;
	}

	/**
	 * Set whether to cache the RMI stub once it has been located. Default is
	 * "true".
	 * <p>
	 * Can be turned off to allow for hot restart of the RMI server. In this
	 * case, the RMI stub will be fetched for each invocation.
	 * 
	 * @see #setLookupStubOnStartup
	 */
	public void setCacheStub(boolean cacheStub) {
		this.cacheStub = cacheStub;
	}

	/**
	 * Set whether to refresh the RMI stub on connect failure. Default is
	 * "false".
	 * <p>
	 * Can be turned on to allow for hot restart of the RMI server. If a cached
	 * RMI stub throws an RMI exception that indicates a remote connect failure,
	 * a fresh proxy will be fetched and the invocation will be retried.
	 * 
	 * @see java.rmi.ConnectException
	 * @see java.rmi.ConnectIOException
	 * @see java.rmi.NoSuchObjectException
	 */
	public void setRefreshStubOnConnectFailure(boolean refreshStubOnConnectFailure) {
		this.refreshStubOnConnectFailure = refreshStubOnConnectFailure;
	}

	/**
	 * Set a custom RMI client socket factory to use for accessing the RMI
	 * registry.
	 * 
	 * @see java.rmi.server.RMIClientSocketFactory
	 * @see java.rmi.registry.LocateRegistry#getRegistry(String, int,
	 *      RMIClientSocketFactory)
	 */
	public void setRegistryClientSocketFactory(RMIClientSocketFactory registryClientSocketFactory) {
		this.registryClientSocketFactory = registryClientSocketFactory;
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
//		prepare();
	}

	/**
	 * Fetches RMI stub on startup, if necessary.
	 * 
	 * @throws RemoteLookupFailureException
	 *             if RMI stub creation failed
	 * @see #setLookupStubOnStartup
	 * @see #lookupStub
	 */
	public void prepare() throws RemoteLookupFailureException {
		// Cache RMI stub on initialization?
		if (this.lookupStubOnStartup) {
			Remote remoteObj = lookupStub();
			if (logger.isDebugEnabled()) {
				if (remoteObj instanceof RmiInvocationHandler) {
					logger.debug("RMI stub [" + getServiceUrl() + "] is an RMI invoker");
				} else if (getServiceInterface() != null) {
					boolean isImpl = getServiceInterface().isInstance(remoteObj);
					logger.debug("Using service interface [" + getServiceInterface().getName() + "] for RMI stub ["
							+ getServiceUrl() + "] - " + (!isImpl ? "not " : "") + "directly implemented");
				}
			}
			if (this.cacheStub) {
				this.cachedStub = remoteObj;
			}
		}
	}

	/**
	 * Create the RMI stub, typically by looking it up.
	 * <p>
	 * Called on interceptor initialization if "cacheStub" is "true"; else
	 * called for each invocation by {@link #getStub()}.
	 * <p>
	 * The default implementation looks up the service URL via
	 * <code>java.rmi.Naming</code>. This can be overridden in subclasses.
	 * 
	 * @return the RMI stub to store in this interceptor
	 * @throws RemoteLookupFailureException
	 *             if RMI stub creation failed
	 * @see #setCacheStub
	 * @see java.rmi.Naming#lookup
	 */
	protected Remote lookupStub() throws RemoteLookupFailureException {
		try {
			Remote stub = null;
			if (this.registryClientSocketFactory != null) {
				// RMIClientSocketFactory specified for registry access.
				// Unfortunately, due to RMI API limitations, this means
				// that we need to parse the RMI URL ourselves and perform
				// straight LocateRegistry.getRegistry/Registry.lookup calls.
				URL url = new URL(null, getServiceUrl(), new DummyURLStreamHandler());
				String protocol = url.getProtocol();
				if (protocol != null && !"rmi".equals(protocol)) {
					throw new MalformedURLException("Invalid URL scheme '" + protocol + "'");
				}
				String host = url.getHost();
				int port = url.getPort();
				String name = url.getPath();
				if (name != null && name.startsWith("/")) {
					name = name.substring(1);
				}
				Registry registry = LocateRegistry.getRegistry(host, port, this.registryClientSocketFactory);
				stub = registry.lookup(name);
			} else {
				// Can proceed with standard RMI lookup API...
				stub = Naming.lookup(getServiceUrl());
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Located RMI stub with URL [" + getServiceUrl() + "]");
			}
			return stub;
		} catch (MalformedURLException ex) {
			throw new RemoteLookupFailureException("Service URL [" + getServiceUrl() + "] is invalid", ex);
		} catch (NotBoundException ex) {
			throw new RemoteLookupFailureException(
					"Could not find RMI service [" + getServiceUrl() + "] in RMI registry", ex);
		} catch (RemoteException ex) {
			throw new RemoteLookupFailureException("Lookup of RMI stub failed", ex);
		}
	}

	/**
	 * Return the RMI stub to use. Called for each invocation.
	 * <p>
	 * The default implementation returns the stub created on initialization, if
	 * any. Else, it invokes {@link #lookupStub} to get a new stub for each
	 * invocation. This can be overridden in subclasses, for example in order to
	 * cache a stub for a given amount of time before recreating it, or to test
	 * the stub whether it is still alive.
	 * 
	 * @return the RMI stub to use for an invocation
	 * @throws RemoteLookupFailureException
	 *             if RMI stub creation failed
	 * @see #lookupStub
	 */
	protected Remote getStub() throws RemoteLookupFailureException {
		if (!this.cacheStub || (this.lookupStubOnStartup && !this.refreshStubOnConnectFailure)) {
			return (this.cachedStub != null ? this.cachedStub : lookupStub());
		} else {
			synchronized (this.stubMonitor) {
				if (this.cachedStub == null) {
					this.cachedStub = lookupStub();
				}
				return this.cachedStub;
			}
		}
	}

	/**
	 * Fetches an RMI stub and delegates to <code>doInvoke</code>. If configured
	 * to refresh on connect failure, it will call {@link #refreshAndRetry} on
	 * corresponding RMI exceptions.
	 * 
	 * @see #getStub
	 * @see #doInvoke(MethodInvocation, Remote)
	 * @see #refreshAndRetry
	 * @see java.rmi.ConnectException
	 * @see java.rmi.ConnectIOException
	 * @see java.rmi.NoSuchObjectException
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String sgtr = getServiceUrl();
		sgtr.replace("1199", "1200");
		setServiceUrl("rmi://127.0.0.1:1200/AndersRmi");
		
		prepare();
		
		Remote stub = getStub();
		try {
			return doInvoke(invocation, stub);
		} catch (RemoteConnectFailureException ex) {
			return handleRemoteConnectFailure(invocation, ex);
		} catch (RemoteException ex) {
			if (isConnectFailure(ex)) {
				return handleRemoteConnectFailure(invocation, ex);
			} else {
				throw ex;
			}
		}
	}

	/**
	 * Determine whether the given RMI exception indicates a connect failure.
	 * <p>
	 * The default implementation delegates to
	 * {@link RmiClientInterceptorUtils#isConnectFailure}.
	 * 
	 * @param ex
	 *            the RMI exception to check
	 * @return whether the exception should be treated as connect failure
	 */
	protected boolean isConnectFailure(RemoteException ex) {
		return RmiClientInterceptorUtils.isConnectFailure(ex);
	}

	/**
	 * Refresh the stub and retry the remote invocation if necessary.
	 * <p>
	 * If not configured to refresh on connect failure, this method simply
	 * rethrows the original exception.
	 * 
	 * @param invocation
	 *            the invocation that failed
	 * @param ex
	 *            the exception raised on remote invocation
	 * @return the result value of the new invocation, if succeeded
	 * @throws Throwable
	 *             an exception raised by the new invocation, if it failed as
	 *             well
	 * @see #setRefreshStubOnConnectFailure
	 * @see #doInvoke
	 */
	private Object handleRemoteConnectFailure(MethodInvocation invocation, Exception ex) throws Throwable {
		if (this.refreshStubOnConnectFailure) {
			String msg = "Could not connect to RMI service [" + getServiceUrl() + "] - retrying";
			if (logger.isDebugEnabled()) {
				logger.warn(msg, ex);
			} else if (logger.isWarnEnabled()) {
				logger.warn(msg);
			}
			return refreshAndRetry(invocation);
		} else {
			throw ex;
		}
	}

	/**
	 * Refresh the RMI stub and retry the given invocation. Called by invoke on
	 * connect failure.
	 * 
	 * @param invocation
	 *            the AOP method invocation
	 * @return the invocation result, if any
	 * @throws Throwable
	 *             in case of invocation failure
	 * @see #invoke
	 */
	protected Object refreshAndRetry(MethodInvocation invocation) throws Throwable {
		Remote freshStub = null;
		synchronized (this.stubMonitor) {
			this.cachedStub = null;
			freshStub = lookupStub();
			if (this.cacheStub) {
				this.cachedStub = freshStub;
			}
		}
		return doInvoke(invocation, freshStub);
	}

	/**
	 * Perform the given invocation on the given RMI stub.
	 * 
	 * @param invocation
	 *            the AOP method invocation
	 * @param stub
	 *            the RMI stub to invoke
	 * @return the invocation result, if any
	 * @throws Throwable
	 *             in case of invocation failure
	 */
	protected Object doInvoke(MethodInvocation invocation, Remote stub) throws Throwable {
		if (stub instanceof RmiInvocationHandler) {
			// RMI invoker
			try {
				return doInvoke(invocation, (RmiInvocationHandler) stub);
			} catch (RemoteException ex) {
				throw RmiClientInterceptorUtils.convertRmiAccessException(invocation.getMethod(), ex,
						isConnectFailure(ex), getServiceUrl());
			} catch (InvocationTargetException ex) {
				Throwable exToThrow = ex.getTargetException();
				RemoteInvocationUtils.fillInClientStackTraceIfPossible(exToThrow);
				throw exToThrow;
			} catch (Throwable ex) {
				throw new RemoteInvocationFailureException("Invocation of method [" + invocation.getMethod()
						+ "] failed in RMI service [" + getServiceUrl() + "]", ex);
			}
		} else {
			// traditional RMI stub
			try {
				return RmiClientInterceptorUtils.invokeRemoteMethod(invocation, stub);
			} catch (InvocationTargetException ex) {
				Throwable targetEx = ex.getTargetException();
				if (targetEx instanceof RemoteException) {
					RemoteException rex = (RemoteException) targetEx;
					throw RmiClientInterceptorUtils.convertRmiAccessException(invocation.getMethod(), rex,
							isConnectFailure(rex), getServiceUrl());
				} else {
					throw targetEx;
				}
			}
		}
	}

	/**
	 * Apply the given AOP method invocation to the given
	 * {@link RmiInvocationHandler}.
	 * <p>
	 * The default implementation delegates to {@link #createRemoteInvocation}.
	 * 
	 * @param methodInvocation
	 *            the current AOP method invocation
	 * @param invocationHandler
	 *            the RmiInvocationHandler to apply the invocation to
	 * @return the invocation result
	 * @throws RemoteException
	 *             in case of communication errors
	 * @throws NoSuchMethodException
	 *             if the method name could not be resolved
	 * @throws IllegalAccessException
	 *             if the method could not be accessed
	 * @throws InvocationTargetException
	 *             if the method invocation resulted in an exception
	 * @see org.springframework.remoting.support.RemoteInvocation
	 */
	protected Object doInvoke(MethodInvocation methodInvocation, RmiInvocationHandler invocationHandler)
			throws RemoteException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		if (AopUtils.isToStringMethod(methodInvocation.getMethod())) {
			return "RMI invoker proxy for service URL [" + getServiceUrl() + "]";
		}

		return invocationHandler.invoke(createRemoteInvocation(methodInvocation));
	}

	/**
	 * Dummy URLStreamHandler that's just specified to suppress the standard
	 * <code>java.net.URL</code> URLStreamHandler lookup, to be able to use the
	 * standard URL class for parsing "rmi:..." URLs.
	 */
	private static class DummyURLStreamHandler extends URLStreamHandler {

		@Override
		protected URLConnection openConnection(URL url) throws IOException {
			throw new UnsupportedOperationException();
		}
	}

}