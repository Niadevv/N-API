package co.uk.niadel.mpi.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.NAPILogHelper;
import co.uk.niadel.mpi.util.UtilityMethods;

@TestFeature(stable = false, firstAppearance = "1.0")
/**
 * Base for config files, these are relatively Forge-esque, down to the fact they're
 * in the same directory to avoid problems for people who have ID Mismatches and are 
 * used to Forge config methods.
 * 
 * @author Niadel
 */
public class Configuration 
{
	/**
	 * The mods config directory.
	 */
	public static File modsConfigs = new File(NModLoader.mcMainDir, "config/").getAbsoluteFile();
	
	/**
	 * This file, the config.
	 */
	public File theConfig;
	
	/**
	 * The data in the file. Indexed by the value name and keyed by the string value.
	 */
	public Map<String, String> data = new HashMap<>();
	
	/**
	 * Creates the new config with the pre-added data.
	 * @param configName
	 * @param data
	 */
	public Configuration(String configName, String[] data)
	{
		this(configName);
		addData(data);
	}
	
	public Configuration(String configName)
	{
		this.theConfig = generateNewConfig(configName);
	}
	
	/**
	 * Generates a new config file.
	 * @param configName
	 * @return The generated config file.
	 */
	public File generateNewConfig(String configName)
	{
		File configFile = new File(modsConfigs, configName);

		if (!modsConfigs.exists())
		{
			try
			{
				configFile.createNewFile();
				NAPILogHelper.log("Created config directory " + configFile.getName());
			}
			catch (IOException e)
			{
				NAPILogHelper.log(configFile.toPath().toString());

				if (!modsConfigs.isDirectory())
				{
					NAPILogHelper.logError("WHY THE F*** IS modsConfigs NOT A DIRECTORY!?");
					NAPILogHelper.log("modsConfigs path: " + modsConfigs.toPath().toString());
				}

				e.printStackTrace();
			}
		}

		if (!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
				NAPILogHelper.log("Created config file " + configFile.getName());
			}
			catch (IOException e)
			{
				NAPILogHelper.logError("Could not create " + configFile.getName() + "! Path is " + configFile.getPath().toString());
				e.printStackTrace();
			}
		}

		return configFile;
	}
	
	/**
	 * Adds data to a the specified config file.
	 *
	 * @param data
	 */
	public final void addData(String[] data)
	{
		try
		{
			PrintStream configWriter = new PrintStream(this.theConfig);

			for (String currData : data)
			{
				configWriter.println(currData);
			}

			configWriter.close();
		}
		catch (FileNotFoundException e)
		{
			NAPILogHelper.logError("The file " + this.theConfig.getPath() + " does NOT exist!");
		}
	}
	
	/**
	 * Updates data in the config.
	 * @param config
	 * @throws FileNotFoundException
	 */
	public final void updateOptions(File config) throws FileNotFoundException
	{
		if (config.exists())
		{
			Scanner configScanner = new Scanner(config.toString());
			
			while (configScanner.hasNext())
			{
				String currLine = configScanner.next();
				this.data.put(currLine.replace("=", "").substring(0, currLine.indexOf("=")), currLine.replace("=", "").substring(currLine.indexOf("=") + 1));
			}
			
			configScanner.close();
		}
		else
		{
			throw new FileNotFoundException("[CONFIGERROR] Config File not found!");
		}
	}
	
	/**
	 * Gets a specific string of data to parse. You will need to parse this yourself, because
	 * I'm too lazy at this point in time.
	 * @param configValue
	 * @return
	 */
	public final String getOptionValue(String configValue)
	{
		try
		{
			updateOptions(theConfig);
			return this.data.get(configValue);
		}
		catch (FileNotFoundException e)
		{
			NAPILogHelper.logError("Someone forgot to create a config .-.");
			NAPILogHelper.logError(e);
			return null;
		}
	}
	
	/**
	 * Adds an option to the config file.
	 * @param valueName
	 */
	public final void addOption(String valueName, String defaultValue)
	{
		PrintStream writer;
		
		try
		{
			writer = new PrintStream(this.theConfig);
			
			if (!valueName.trim().endsWith("="))
			{
				writer.println(valueName);
			}
			else
			{
				writer.println(valueName + " = " + defaultValue);
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println("[CONFIGERROR] The config MUST be created BEFORE adding any data.");
			NAPILogHelper.logError("Someone forgot to create a config .-.");
			NAPILogHelper.logError(e);
		}
	}
	
	/**
	 * Adds multiple lines of data to the config. Each value in valueNames will be put in with
	 * the same value at the value's index in defaultValues. In other words:
	 * 
	 * <p>[1,2,3,4] valueNames
	 * <p> &nbsp|&nbsp |&nbsp |&nbsp |
	 * <p> \/\/\/\/
	 * <p>[1,2,3,4] defaultValues</p>
	 * 
	 * <!---It looks better in the Javadoc, don't worry. Huh, I remember much more HTML than I thought I did.-->
	 * @param valueNames
	 * @param defaultValues
	 */
	public final void addOptions(String[] valueNames, String[] defaultValues)
	{
		if (valueNames.length == defaultValues.length)
		{
			for (int i = 0; i == valueNames.length; i++)
			{
				addOption(valueNames[i], defaultValues[i]);
			}
		}
		else
		{
			throw new RuntimeException("[CONFIGERROR] The arrays valueNames and defaultValues passed to addDataLines MUST be the same length!");
		}
	}
	
	/**
	 * Gets whether the config option specified exists.
	 * @param value
	 * @return
	 */
	public final boolean doesOptionExist(String value)
	{
		if (data.get(value) != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
