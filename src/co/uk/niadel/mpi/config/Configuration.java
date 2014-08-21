package co.uk.niadel.mpi.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.FileUtils;
import co.uk.niadel.mpi.util.NAPILogHelper;

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
	 * @param configName The name of the config to generate.
	 * @param data Extra data to add.
	 */
	public Configuration(String configName, String[] data)
	{
		this(configName);
		this.addLines(data);
	}

	/**
	 * Creates a new config.
	 * @param configName The name of the config to generate.
	 */
	public Configuration(String configName)
	{
		this.theConfig = generateNewConfig(configName);
	}
	
	/**
	 * Generates a new config file.
	 * @param configName The name of the config to generate.
	 * @return The generated config file.
	 */
	public File generateNewConfig(String configName)
	{
		File configFile = new File(modsConfigs, configName);

		if (!modsConfigs.exists())
		{
			modsConfigs.mkdir();

			if (modsConfigs.isDirectory())
			{
				NAPILogHelper.log("Created config directory " + modsConfigs.toPath().toString());
			}
			else
			{
				NAPILogHelper.logError("Created config, but for some reason it's a file >:O");
			}
		}
		else
		{
			NAPILogHelper.log("Config directory already exists!");
		}

		if (!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
				NAPILogHelper.log("Created config file " + configFile.toPath().toString());
			}
			catch (IOException e)
			{
				NAPILogHelper.logError("Could not create config file " + configFile.toPath().toString() + "!");
				e.printStackTrace();
			}
		}

		return configFile;
	}
	
	/**
	 * Adds lines to a the config file.
	 *
	 * @param data The lines of data to add.
	 */
	public final void addLines(String[] data)
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
	 */
	public final void updateConfig()
	{
		try
		{
			if (this.theConfig.exists())
			{
				Scanner configScanner;

				configScanner = new Scanner(this.theConfig);
				PrintStream configWriter;

				int lineCount = 0;

				while (configScanner.hasNext())
				{
					lineCount++;
					String currLine = configScanner.nextLine();

					if (!currLine.startsWith("#") && !currLine.startsWith("//"))
					{
						if (currLine.substring(currLine.indexOf("=") + 1) != this.data.get(currLine.substring(0, currLine.indexOf("="))))
						{
							FileUtils.addToLine(currLine.substring(0, currLine.indexOf("=")) + this.data.get(currLine.substring(currLine.indexOf("=") + 1)), lineCount, this.theConfig);
						}
					}
				}

				this.data.clear();

				configScanner = new Scanner(this.theConfig);

				while (configScanner.hasNext())
				{
					String currLine = configScanner.nextLine();

					//Check is to allow for comments in the config. Allows both Python and Java one line comments.
					if (!currLine.startsWith("#") && !currLine.startsWith("//"))
					{
						this.data.put(currLine.substring(0, currLine.indexOf("=")), currLine.substring(currLine.indexOf("=") + 1));
					}
				}

				configScanner.close();
			}
			else
			{
				throw new FileNotFoundException("[CONFIGERROR] Config File not found!");
			}
		}
		catch (IOException e)
		{
			NAPILogHelper.logError("Error while updating config " + this.theConfig.getName() + "! Read stack trace for error!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets a specific string of data to parse. You will need to parse this yourself, because
	 * I'm too lazy at this point in time.
	 * @param configValue The value to get.
	 * @return The value.
	 */
	public final String getConfigValue(String configValue)
	{
		updateConfig();
		return this.data.get(configValue);
	}

	/**
	 * Gets a config value, but if the config key does not exist, the value is created and the default value is returned.
	 * @param configValue The key in the config.
	 * @param defaultValue The default value to return if configValue does not exist.
	 * @return The value that is of configValue, or defaultValue if the value does not exist.
	 */
	public final String getConfigValue(String configValue, String defaultValue)
	{
		if (!this.doesConfigValueExist(configValue))
		{
			this.addConfigValue(configValue, defaultValue);
			return defaultValue;
		}
		else
		{
			if (this.getConfigValue(configValue) != defaultValue)
			{
				return this.getConfigValue(configValue);
			}
			else
			{
				return defaultValue;
			}
		}
	}
	
	/**
	 * Adds an option to the config file.
	 * @param valueName
	 * @param defaultValue
	 */
	public final void addConfigValue(String valueName, String defaultValue)
	{
		try
		{
			if (!this.doesConfigValueExist(valueName))
			{
				PrintStream writer = new PrintStream(this.theConfig);
				writer.println(valueName + " = " + defaultValue);

				this.data.put(valueName, defaultValue);

				updateConfig();
				writer.close();
			}
			else
			{
				NAPILogHelper.logWarn("Config value " + valueName + " already exists in config file " + this.theConfig.toPath().toString() + "! Call to addConfigValue ignored!");
			}
		}
		catch (FileNotFoundException e)
		{
			NAPILogHelper.logError("[CONFIGERROR] The config MUST be created BEFORE adding any data.");
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
	public final void addConfigValues(String[] valueNames, String[] defaultValues)
	{
		if (valueNames.length == defaultValues.length)
		{
			for (int i = 0; i == valueNames.length; i++)
			{
				addConfigValue(valueNames[i], defaultValues[i]);
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
	public final boolean doesConfigValueExist(String value)
	{
		return data.get(value) != null;
	}

	public final void setConfigValue(String configValue, String newValue)
	{
		data.remove(configValue);
		data.put(configValue, newValue);
		updateConfig();
	}
}
