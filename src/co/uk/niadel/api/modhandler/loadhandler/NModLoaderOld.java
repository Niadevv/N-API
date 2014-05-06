package co.uk.niadel.api.modhandler.loadhandler;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.Main;
import net.minecraft.profiler.Profiler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import co.uk.niadel.api.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.api.modhandler.IModRegister;
import co.uk.niadel.api.modhandler.ModRegisteringBase;


@Deprecated
/**
 * The original Mod Loader code, removed from functionality as it did not work, and needed a drastic
 * code cleanup.
 * 
 * Kept for future reference.
 * @author Niadel
 * @deprecated New, cleaner code is available. This likely doesn't even work.
 */
public class NModLoaderOld
{
	static ClassLoader modLoader;
	public static Minecraft theMinecraft = Minecraft.getMinecraft();
	public static Profiler mcProfiler = Minecraft.mcProfiler;
	
	/**
	 * Changes the default loader to that of your own. Maybe a Forge compat
	 * thing where it uses the Forge loader instead? Not likely though.
	 * I'll probably make an @Mod class for Forge mode, and perhaps
	 * a coremod as well to change what is needed.
	 * @param newLoader
	 */
	public final void changeLoader(ClassLoader newLoader)
	{
		modLoader = newLoader;
	}
	
	public final static Class loadRegister(String register) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		// A bit of Reflection magic :3 Thanks StackOverflow!
		Class registerClass = modLoader.loadClass(register);
		Method preModInit = registerClass.getDeclaredMethod("preModInit", new Class[] {});
		Method modInit = registerClass.getDeclaredMethod("modInit", new Class[] {});
		Method postModInit = registerClass.getDeclaredMethod("postModInit", new Class[] {});
		
		mcProfiler.startSection("Calling " + register.toString() + "preModInit method!");
		preModInit.invoke(registerClass, (Object) null);
		
		modInit.invoke(registerClass, (Object) null);
		mcProfiler.startSection("[N-API] Loaded register " + register.toString() + ".class and called modInit!");
		
		mcProfiler.startSection("Beginning " + register.toString() + "'s post initialisation method!");
		postModInit.invoke(registerClass, (Object) null);
		
		return registerClass;
	}

	/**
	 * Loads a mod.
	 */
	public final void loadMod(Class[] mod) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		for (int i = 0; i == mod.length; i++)
		{
			try 
			{
				if (mod[i].getName() != "ModRegister.class")
				{
					modLoader.loadClass(mod[i].getName());
					mcProfiler.startSection("[N-API] Loaded mod class " + mod[i].getName());
				}
				else if (mod[i].getName() == "ModRegister.class")
				{
					loadRegister(mod[i].getName());
				}
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
	}

	/*
	@TestFeature(stable=false,firstAppearance="1.0")
	public static final void loadModFromZip(File zip) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		String zipFileName = (theMinecraft.mcDataDir + File.separator + "mods" + File.separator + zip.getName());
		
		if (shouldAcceptFile(zipFileName))
		{
			ModZipFile modZip = new ModZipFile(zipFileName);
			
			Iterator modZipIter = modZip.iterator();
			
			File[] files = modZip.listFiles();
			
			while (modZipIter.hasNext())
			{
				File file = (File) modZipIter.next();
				// Apparently, getName() gives the path D:<. Hence the excessive amount of code.
				String binaryName = getModID(theMinecraft.mcDataDir + File.separator + "binary_names" + File.separator + modZip.getName().replace(".zip", "").replace(".jar", "").substring((modZip.getName().length() - 12)) + File.separator);
				File fileEntry = file;
				String entry = fileEntry.getName();
				
				if (entry.contains("ModRegister.class"))
				{	
					NModLoader.loadRegister(binaryName + entry);
				}
			}
			
			mcProfiler.startSection("[N-API] Loaded mod: " + zipFileName);
			modZip.close();
		}
		
	}
	*/
	
	
	/**
	 * The revised version of loadModFromZip. It uses the more flexible loading system.
	 * @param modDir
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws ClassNotFoundException 
	 */
	public static final void loadModFromFile(File modDir) throws FileNotFoundException, IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		String binaryName = getModID(modDir.getPath());
		
		loadRegister(binaryName + "ModRegister");
	}
	
	
	public static final String getModID(String dir) throws IOException, FileNotFoundException
	{
		if (!dir.endsWith(File.separator))
		{
			dir += File.separator;
		}
		
		File idFile = new File(dir + "ModID.modid");
		
		Scanner scanner;
		scanner = new Scanner(idFile);
		
		String id = scanner.nextLine();
		
		scanner.close();
		return id;
	}
	
	/**
	 * Decides whether or not to accept the file.
	 */
	private static final boolean shouldAcceptFile(String fileName)
	{
		return fileName.toLowerCase().endsWith(".zip") | fileName.toLowerCase().endsWith(".jar");
	}
	
	@TestFeature(stable=false,firstAppearance="1.0")
	/**
	 * Loads mods from the mods directory.
	 */
	public final static void loadModsFromDir() throws ClassNotFoundException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		File modsDir = new File(theMinecraft.mcDataDir + File.separator + "mods");
		File actualModsDir = new File(modsDir.getPath() + File.separator + "act_mods" + File.separator);
		
		if (modsDir.listFiles() != null)
		{
			
			// One of the lesser known methods of iteration in Java.
			// Similar to Python's for i in variable loop.
			for (File modZip : modsDir.listFiles())
			{	
				//loadModFromZip(modZip);
				
				File extractedFile = extractZip(modZip, actualModsDir + File.separator + modZip.getName().replace(".zip", "").replace(".jar", ""));
				String binaryName = getModID(modsDir + modZip.getName() + File.separator);
				
				loadRegister(binaryName);
			}
		}
	}
	
	/**
	 * Extracts the zip to the act_mods directory where it is actually loaded.
	 * While this isn't the fastest way to load the mod as it has to extract, once
	 * it is extracted once, it'll be as fast as ever.
	 * 
	 * @param zip
	 * @param toDir
	 * @return
	 * @throws IOException
	 */
	public final static File extractZip(File zip, String toDir) throws IOException
	{	
		ZipFile zipFile = new ZipFile(zip);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		    
		while (entries.hasMoreElements()) 
		{
			ZipEntry entry = entries.nextElement();
		    File entryDestination = new File(toDir + File.separator, entry.getName());
		    entryDestination.getParentFile().mkdirs();
		    InputStream in = zipFile.getInputStream(entry);
		    OutputStream out = new FileOutputStream(entryDestination)
		    {
		      	public void finalize() throws IOException
		        {
		       		this.close();
		       	}
		    };
		    
		    IOUtils.copy(in, out);
	        IOUtils.closeQuietly(in);
	        IOUtils.closeQuietly(out);
		}
		
		zipFile.close();
		return new File(toDir);
	}
}
