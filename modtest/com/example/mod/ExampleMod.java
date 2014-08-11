package com.example.mod;

import co.uk.niadel.mpi.modhandler.IModRegister;

/**
 * Test mod for N-API.
 */
public class ExampleMod implements IModRegister
{
	public String getVersion()
	{
		return "1.0";
	}

	public String getModId()
	{
		return "NIADEL_example_mod";
	}

	public void preModInit()
	{
		System.out.println("PREINIT");
	}

	public void modInit()
	{
		System.out.println("INIT");
	}

	public void postModInit()
	{
		System.out.println("POSTINIT");
	}
}
