package co.uk.niadel.napi.proxy;

import co.uk.niadel.napi.annotations.Internal;
import co.uk.niadel.napi.util.MCData;

import java.util.ArrayList;
import java.util.List;

/**
 * Primitive system used to register proxies.
 *
 * @author Niadel
 */
public class ProxyRegistry
{
	/**
	 * List of proxies exclusively for the client.
	 */
	public static final List<IModProxy> clientProxies = new ArrayList<>();

	/**
	 * List of proxies exclusively for the server.
	 */
	public static final List<IModProxy> serverProxies = new ArrayList<>();

	/**
	 * Registers a proxy. If serverProxy is true, it's registered as a server proxy, otherwise it's registered as a client proxy.
	 * @param proxy The proxy to register.
	 * @param serverProxy Whether or not the proxy is for the server.
	 */
	public static final void registerProxy(IModProxy proxy, boolean serverProxy)
	{
		if (serverProxy)
		{
			registerServerProxy(proxy);
		}
		else
		{
			registerClientProxy(proxy);
		}
	}

	/**
	 * Registers a proxy for the server.
	 * @param proxy The proxy to register.
	 */
	public static final void registerServerProxy(IModProxy proxy)
	{
		serverProxies.add(proxy);
	}

	/**
	 * Registers a proxy for the client.
	 * @param proxy The proxy to register.
	 */
	public static final void registerClientProxy(IModProxy proxy)
	{
		clientProxies.add(proxy);
	}

	/**
	 * Runs all registered proxies, depending on the side.
	 */
	@Internal(owningPackage = "co.uk.niadel.napi", documentationOnly = false)
	public static final void executeProxies()
	{
		if (MCData.isClientSide())
		{
			for (IModProxy proxy : clientProxies)
			{
				proxy.init();
			}
		}
		else if (MCData.isServerSide())
		{
			for (IModProxy proxy : serverProxies)
			{
				proxy.init();
			}
		}
	}
}
