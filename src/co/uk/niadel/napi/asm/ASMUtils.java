package co.uk.niadel.napi.asm;

import java.util.*;

/**
 * Utilities for ASM.
 *
 * @author Niadel
 */
public class ASMUtils
{
	public static final Set<String> paramTypes = new HashSet<>(Arrays.asList("I", "L", "S", "B", "C", "F", "D", "Z"));

	public static final int getNumOfParamsInMethod(String methodDesc)
	{
		int paramsInMethod = 0;
		String methodDescription = methodDesc.substring(methodDesc.indexOf("("), methodDesc.indexOf(")")).replace("(", "").replace(")", "");
		String[] descComponents = methodDescription.split(";");

		if (descComponents.length > 1)
		{
			for (String descComponent : descComponents)
			{
				if (descComponent.contains("/"))
				{
					paramsInMethod++;
				}
				else if (paramTypes.contains(String.valueOf(descComponent.charAt(1))))
				{
					paramsInMethod = descComponent.length();
				}
			}
		}
		else
		{
			paramsInMethod = descComponents.length == 0 ? 0 : 1;
		}

		return paramsInMethod;
	}
}
