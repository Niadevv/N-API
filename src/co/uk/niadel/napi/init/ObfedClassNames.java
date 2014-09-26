package co.uk.niadel.napi.init;

/**
 * Note that this only contains obfuscated class names that is needed to be transformed for N-API and exists only for the reason
 * of making updating ASM code easier.
 *
 * @author Niadel
 */
public class ObfedClassNames
{
	public static final String BOOTSTRAP = "kl";
	public static final String BOOTSTRAP_BLOCK_ITEM_INIT = "b";
	public static final String BOOTSTRAP_INIT_DISPENSER_BEHAVIOURS = "a";
	public static final String BOOTSTRAP_FIELD_INIT = "a";
	public static final String BLOCK = "aji";
	public static final String BLOCK_REGISTER_BLOCKS = "p";
	public static final String ITEM = "adb";
	public static final String ITEM_REGISTER_ITEMS = "l";
	public static final String BLOCKFIRE = "alb";
	public static final String BLOCKFIRE_REGISTER_FLAMMABLE_BLOCKS = "e";
	public static final String STATLIST = "pp";
	public static final String STATLIST_REGISTER_STATS = "a";
	public static final String BLOCKDYNAMICLIQUID = "akr";
	public static final String RENDERITEM = "bny";

	/**
	 * Not really needed as SRG params can be converted easily.
	 * p_..._1_ = par1.
	 * @param srgParam The string SRG parameter.
	 * @return The parameter name obfuscated.
	 */
	public static final String convertParamToObfed(String srgParam)
	{
		String obfedParam = "";
		char[] paramAsArray = srgParam.toCharArray();

		if (srgParam.startsWith("p") && srgParam.endsWith("_"))
		{
			obfedParam = "par" + paramAsArray[paramAsArray.length - 2];
		}
		else
		{
			throw new IllegalArgumentException("Parameter " + srgParam + "is NOT an SRG parameter name!");
		}

		return obfedParam;
	}
}
