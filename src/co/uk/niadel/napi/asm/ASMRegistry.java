package co.uk.niadel.napi.asm;

import co.uk.niadel.napi.annotations.Internal;
import co.uk.niadel.napi.nml.NModLoader;
import co.uk.niadel.napi.util.NAPILogHelper;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Complete rewrite of ASMRegistry. Much less code is used.
 *
 * This is the centre of the ASM system of N-API. Everything ASM revolves around this, and as N-API is largely functional thanks to ASM,
 * is an INCREDIBLY important class.
 *
 * @author Niadel
 */
public class ASMRegistry
{
	/**
	 * List of ASM transformers.
	 */
	public static final List<IASMTransformer> transformers = new ArrayList<>();

	/**
	 * The list of classes that won't be put into classToBytesMap.
	 */
	public static final List<String> blacklistedClasses = new ArrayList<>();

	/**
	 * Map that is class name to the bytes of the class, used in the key byte acquiring code.
	 */
	public static final Map<String, byte[]> classToBytesMap = new HashMap<>();

	/**
	 * Registers a transformer.
	 * @param transformer The transformer being registered.
	 */
	public static final void registerTransformer(IASMTransformer transformer)
	{
		transformers.add(transformer);
	}

	/**
	 * Adds a class to not be getting the bytes of.
	 * @param classToExclude The class that will not be loaded for manipulation.
	 */
	public static final void addASMClassExclusion(String classToExclude)
	{
		//Don't allow net.minecraft classes to be excluded.
		if (!classToExclude.startsWith("net.minecraft."))
		{
			blacklistedClasses.add(classToExclude);
		}
	}

	/**
	 * Gets if the specified class is not loaded for manipulation.
	 * @param className The class name to check.
	 * @return Whether or not the specified class is not loaded for manipulation.
	 */
	public static final boolean isClassExcluded(String className)
	{
		for (String clazz : blacklistedClasses)
		{
			if (className.startsWith(clazz))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Calls an individual transformer.
	 * @param transformer The transformer being called.
	 */
	public static final void callASMTransformer(IASMTransformer transformer)
	{
		for (Entry<String, byte[]> entry : classToBytesMap.entrySet())
		{
			if (!isClassExcluded(entry.getKey()) && entry.getValue().length > 0)
			{
				byte[] bytes = transformer.manipulateBytecodes(entry.getKey(), entry.getValue());

				classToBytesMap.remove(entry.getKey());
				classToBytesMap.put(entry.getKey(), bytes);
				NModLoader.defineClass(entry.getKey(), bytes);
			}
		}
	}

	public static final void callASMTransformerForClass(IASMTransformer transformer, String clazz)
	{
		if (!isClassExcluded(clazz))
		{
			byte[] bytes = transformer.manipulateBytecodes(clazz, classToBytesMap.get(clazz));

			classToBytesMap.remove(clazz);
			classToBytesMap.put(clazz, bytes);
			NModLoader.defineClass(clazz, bytes);
		}
	}

	/**
	 * Invokes all ASM transformers, called at the start of the pre init stage.
	 */
	@Internal(owningPackage = "co.uk.niadel.napi", documentationOnly = false)
	public static final void invokeAllTransformers()
	{
		for (IASMTransformer transformer : transformers)
		{
			callASMTransformer(transformer);
		}
	}

	/**
	 * Loads and gets the bytes of classes directly from the class files, compared to the old 'in memory' method.
	 */
	@Internal(owningPackage = "co.uk.niadel.napi", documentationOnly = false)
	private static final void discoverClasses()
	{
		try
		{
			String[] classPath = System.getProperty("java.class.path").split(";");

			for (String classPathEntry : classPath)
			{
				if ((!classPathEntry.contains("Java" + File.separator + "jdk") && !classPathEntry.contains("jre")))
				{
					if (classPathEntry.endsWith(".jar"))
					{
						JarFile jarFile = new JarFile(classPathEntry);
						Enumeration<JarEntry> jarFileEnumeration = jarFile.entries();

						while (jarFileEnumeration.hasMoreElements())
						{
							JarEntry jarEntry = jarFileEnumeration.nextElement();
							String nextName = jarEntry.getName();

							//Cull unnecessary names. Should free up some space in memory.
							if (!nextName.endsWith("/") && !(nextName.equals("")) && !nextName.contains("META-INF"))
							{
								URL jarEntryUrl = new URL("jar:file:" + classPathEntry + "!/" + nextName);
								NModLoader.loadUrl(jarEntryUrl);

								InputStream zipInputStream = jarFile.getInputStream(jarEntry);
								Scanner zipInputStreamScanner = new Scanner(zipInputStream);
								List<Byte> bytesToAddList = new ArrayList<>();

								while (zipInputStreamScanner.hasNextByte())
								{
									bytesToAddList.add(zipInputStreamScanner.nextByte());
								}

								byte[] actBytesToAdd = new byte[bytesToAddList.size()];

								for (int i = 0; i == bytesToAddList.size(); i++)
								{
									actBytesToAdd[i] = bytesToAddList.get(i);
								}

								classToBytesMap.put(nextName.replace("/", "."), actBytesToAdd);
							}
						}

						NModLoader.loadUrl(new File(classPathEntry).toURI().toURL());
					}
				}
			}

			/*for (String clazz : classes)
			{
				if (!clazz.endsWith("/") && !(clazz.equals("")))
				{
					InputStream rawClassStream = NModLoader.getNMLResourceAsStream(clazz);

					if (rawClassStream != null)
					{
						BufferedInputStream classStream = new BufferedInputStream(rawClassStream);
						Scanner classScanner = new Scanner(classStream);

						List<Byte> bytes = new ArrayList<>();

						while (classScanner.hasNextByte())
						{
							bytes.add(classScanner.nextByte());
						}

						if (bytes.size() > 5)
						{
							byte[] actBytes = new byte[bytes.size()];

							for (int i = 0; i == bytes.size(); i++)
							{
								actBytes[i] = bytes.get(i);
							}

							classToBytesMap.put(clazz, actBytes);
						}
						else
						{
							NAPILogHelper.instance.logError("Bytes gotten for the NML resource " + clazz + " has issues being read?");
						}
					}
					else
					{
						NAPILogHelper.instance.logWarn("InputStream for " + clazz + " is null!");
					}
				}
			}*/
		}
		catch (IOException e)
		{
			NAPILogHelper.instance.logError(e);
		}
	}

	static
	{
		discoverClasses();
		addASMClassExclusion("cpw.fml.mods"); //TODO Remove in 1.8 as cpw.fml.mods is moved to net.minecraftforge.fml in 1.8.
		addASMClassExclusion("net.minecraftforge");
		addASMClassExclusion("co.uk.niadel.napi");
	}
}
