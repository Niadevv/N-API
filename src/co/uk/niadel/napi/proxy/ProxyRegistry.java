package co.uk.niadel.napi.proxy;

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
	public static final List<IModProxy> clientProxies = new ArrayList<>();
	public static final List<IModProxy> serverProxies = new ArrayList<>();

	public static final void registerClientProxy(IModProxy proxy)
	{
		clientProxies.add(proxy);
	}

	public static final void registerServerProxy(IModProxy proxy)
	{
		serverProxies.add(proxy);
	}

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
