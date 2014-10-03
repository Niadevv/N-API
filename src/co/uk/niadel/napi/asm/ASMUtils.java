package co.uk.niadel.napi.asm;

import co.uk.niadel.napi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

	public static final String getPackageOfClass(File clazz)
	{
		try
		{
			ClassReader classReader = new ClassReader(new FileInputStream(clazz));
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			return classNode.name;
		}
		catch (IOException e)
		{
			NAPILogHelper.instance.logError(e);
		}

		return "";
	}
}
