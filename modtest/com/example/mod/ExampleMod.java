package com.example.mod;

import co.uk.niadel.napi.nml.EnumLoadState;
import co.uk.niadel.napi.annotations.LoadStateMethod;
import co.uk.niadel.napi.annotations.ModRegister;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Test mod for N-API.
 *
 * @author Niadel
 */
@ModRegister(modId = "NIADEL_example_mod", version = "1.0")
public class ExampleMod
{
	public static final Logger logger = LogManager.getLogger("N-API Test mod");

	@LoadStateMethod(EnumLoadState.PREINIT)
	public void preModInit()
	{
		logger.log(Level.INFO, "PREINIT");
	}

	@LoadStateMethod(EnumLoadState.INIT)
	public void modInit()
	{
		logger.log(Level.INFO, "INIT");
	}

	@LoadStateMethod(EnumLoadState.POSTINIT)
	public void postModInit()
	{
		logger.log(Level.INFO, "POSTINIT");
	}
}
