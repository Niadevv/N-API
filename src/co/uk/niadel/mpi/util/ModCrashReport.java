package co.uk.niadel.mpi.util;

import co.uk.niadel.mpi.common.NAPIData;
import co.uk.niadel.mpi.modhandler.loadhandler.IModContainer;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.reflection.ReflectionCallMethods;
import cpw.mods.fml.common.ModContainer;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.LogManager;

public class ModCrashReport
{
	public static final File crashesDir = new File(NModLoader.mcMainDir.toPath().toString() + "mod-crash-logs/");

	/**
	 * Random strings that are put at the top of the console. Some are based on things I've seen, others are just off the top of my head.
	 */
	public static final String[] SILLIES = new String[]
	{
		"YER GOIN' TO BE FINE DUNKEH!",
		"Uh, NO!",
		"IT'S WORSE THAN THE EULA ALLOWING LEGAL DONATIONS INSTEAD OF HAVING ALL DONATIONS ILLEGAL!",
		"IT'S NO USE!",
		"Vanilla Crash Report: You're not even good enough to be my fake!",
		"Ow...",
		"My rings!",
		"Thou art going to be fine, Donkey.",
		"(Sheepishly) Um... I'm sorry?",
		"Still better than Justin Beiber",
		"Still better than Twilight (The bad vampire romance, not the Nintendo game from a well known franchise)",
		"I should be making the vanilla crash report sillies.",
		"Sigh, goodbye 3 hours of my life.",
		"((CrashReport) mysteriousEntity).insertSorry(\":(\");",
		"Only at your Pizza Hut! Buy it now for only £9.99!",
		"Only at your Game Crash Hut!",
		"I'm sorry :(",
		"I would give you a hug token, but I don't really like being hugged by people that much.",
		"Aw :(",
		"Well, that was fun!",
		"So... Did Sonic '06 ever just stop working? Surely it wasn't THAT buggy...",
		"So... Want to go flame some boring, typical love songs?",
		"No, my pet cat wasn't named after a black hedgehog.",
		"I hope you have the internet to report this crash >:(",
		"There there... YER HEART WILL GO ON DUNKEH!",
		"Crash logs from mods! Now with quotes from out of nowhere characters from Sonic parodies starring zombies!",
		"You didn't hear it from me, but Dinnerbone's skin is based on Jamie Hyneman from the Mythbusters! Or at least it looks like it is...",
		"Am I the only person on Earth who doesn't like Cake unless it's not actually made out of cake?",
		"OMG U SHUD SUB 2 MAH CHANNUL I MAKE DAYLEE MINECRAFT STUFF HURRDEHURR <- People, don't do this. Especially not on a Direwolf20 or Etho video.",
		"KA-BOOOOOOOOOOOOOOOOOOOOOOOOOOOOMMMM!!!",
		"We appear to be having technical difficulties, we apologise for the inconvenience.",
		"We apologise for the inconvenience.",
		"So sad that errors will always be errors. Has the error code been spoiled over the updates? NOW CRASH!",
		"Crash reports! Now wasting multiple hours of modder's lives due to solving the bugs these have!"
	};

	/**
	 * The file where the report will be saved to.
	 */
	public File reportFile;

	/**
	 * Used to print to reportFile.
	 */
	public PrintStream filePrintStream;

	/**
	 * The log with all of the data of the crash report.
	 */
	public List<String> log = new ArrayList<>();

	/**
	 * Creates a mod crash log with special prefix information.
	 * @param file The file to represent the crash log.
	 * @param exception The error that the crash report is for.
	 */
	private ModCrashReport(File file, Throwable exception)
	{
		try
		{
			this.reportFile = file;

			if (!this.reportFile.exists())
			{
				file.createNewFile();
			}

			initCrashLog(exception);

			this.filePrintStream = new PrintStream(this.reportFile);

			for (String logSection : this.getLogText())
			{
				NAPILogHelper.logError(logSection);
				this.filePrintStream.println(logSection);
			}
		}
		catch (IOException e)
		{
			NAPILogHelper.logError(e);
		}
	}

	public static final ModCrashReport generateCrashReport(String modid, Throwable e)
	{
		return new ModCrashReport(new File(crashesDir, modid + System.nanoTime() + ".txt"), e);
	}
	
	public String[] getLogText()
	{
		return this.log.toArray(new String[this.log.size()]);
	}

	public void initCrashLog(Throwable e)
	{
		Random random = new Random();
		//Add the silly.
		this.log.add(SILLIES[random.nextInt(SILLIES.length - 1)]);
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		this.log.add("Time: " + (new SimpleDateFormat()).format(new Date()));
		this.log.add("Minecraft Version: " + NAPIData.FULL_VERSION);
		this.log.add("Running N-API Mods: ");

		for (IModContainer mod : NModLoader.mods.getMods())
		{
			log.add("	ModId: " + mod.getModId() + " Version: " + mod.getVersion());
		}

		this.log.add("");

		if (MCData.isForgeDominated())
		{
			this.log.add("Game is running Forge as well as N-API");

			for (Map.Entry<String, String> modEntry : NModLoader.forgeModids.entrySet())
			{
				this.log.add("	ModId: " + modEntry.getKey() + " Version: " + modEntry.getValue());
			}

			this.log.add("");
		}

		this.log.add("There has been an error that has caused the game to crash! The game is running under a modded environment, including N-API.");
		this.log.add("Java version: " + System.getProperty("java.version"));
		this.log.add("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
		this.log.add("VM Info: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));

		this.log.add("Stacktrace: ");

		for (StackTraceElement element : stackTraceElements)
		{
			this.log.add(element.toString());
		}
	}

	public void crash()
	{
		if (MCData.isForgeDominated())
		{
			try
			{
				//Reflectively calls FML's FMLCommonHandler.exitJava() to prevent compile-time errors (I have the forge sources set to
				//not compile in IntelliJ IDEA).
				ReflectionCallMethods.callMethod(Class.forName("cpw.mods.fml.common.FMLCommonHandler"), "exitJava", 1, false);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			Runtime.getRuntime().exit(1);
		}
	}

	static
	{
		if (!crashesDir.exists())
		{
			crashesDir.mkdir();
		}
	}
}
