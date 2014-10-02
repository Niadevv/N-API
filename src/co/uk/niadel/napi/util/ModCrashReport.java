package co.uk.niadel.napi.util;

import co.uk.niadel.commons.crash.CrashReport;
import co.uk.niadel.commons.logging.Logger;
import co.uk.niadel.napi.common.NAPIData;
import co.uk.niadel.napi.nml.IModContainer;
import co.uk.niadel.napi.nml.NModLoader;
import co.uk.niadel.commons.reflection.ReflectionCallMethods;

import java.io.File;
import java.io.PrintStream;
import java.util.*;

public class ModCrashReport extends CrashReport
{
	public static final File crashesDir = new File(NModLoader.mcMainDir.toPath().toString() + "mod-crash-logs/");

	/**
	 * Random strings that are put at the top of the console. Some are based on things I've seen, others are just off the top of my head.
	 */
	public static final String[] SILLIES = new String[]
	{
			"YER GOIN' TO BE FINE DUNKEH!", //Balenaproductions Sonic Zombie series reference.
			"Scout: Uh, NO!", //TF2 Reference. Strange, I've never actually played it.
			"IT'S WORSE THAN THE EULA ALLOWING LEGAL DONATIONS INSTEAD OF HAVING ALL DONATIONS ILLEGAL!", //Note on how people are whinging about something that is actually better than what we had before.
			"IT'S NO USE!", //Silver reference :D
			"Vanilla Crash Report: You're not even good enough to be my fake!", //Reference to Sonic Adventure 2
			"Ow...",
			"My rings!", //Sonic reference, as in Sonic games, when you take damage, you lose rings.
			"Thou art going to be fine, Donkey.", //Poshification of the first silly.
			"(Sheepishly) Um... I'm sorry?",
			"Still better than Justin Beiber", //THA TRUTH BRUDDA!
			"Still better than Twilight (The bad vampire romance, not the Nintendo game from a well known franchise)", //See last comment on a silly.
			"I should be making the vanilla crash report sillies.", //No really, I should.
			"Sigh, goodbye 3 hours of my life.",
			"((CrashReport) mysteriousEntity).insertSorry(\":(\");", //Java code where mysteriousEntity is cast to a CrashReport, and then the casted mysteriousEntity object's .insertSorry method is called, and passed the String ":(".
			"Only at your Pizza Hut! Buy it now for only £9.99!",
			"Only at your Game Crash Hut!", //Half reference to Balenaproductions Sonic Zombie series.
			"I'm sorry :(",
			"I would give you a hug token, but I don't really like being hugged by people that much.", //I don't like being hugged that much.
			"Aw :(",
			"Well, that was fun!",
			"So... Did Sonic '06 ever just stop working? Surely it wasn't THAT buggy...", //Question about how buggy Sonic '06 was.
			"So... Want to go flame some boring, typical love songs?", //Modern day love songs SUCK. Need I explain more?
			"No, my pet cat wasn't named after a black hedgehog.", //Reference to the fact that my pet cat Shadow was not named after Shadow The Hedgehog.
			"I hope you have the internet to report this crash >:(",
			"There there... YER HEART WILL GO ON DUNKEH!", //Balenaproductions Sonic Zombie series reference 2.
			"Crash logs from mods! Now with quotes from out of nowhere characters from Sonic parodies starring zombies!",
			"You didn't hear it from me, but Dinnerbone's skin is based on Jamie Hyneman from the Mythbusters! Or at least it looks like it is...", //Note on how Dinnerbone's skin is likely based on one of the Mythbusters.
			"Am I the only person on Earth who doesn't like Cake unless it's not actually made out of cake?", //Note on how I don't actually like cake and everyone else seems to.
			"OMG U SHUD SUB 2 MAH CHANNUL I MAKE DAYLEE MINECRAFT STUFF HURRDEHURR <- People, don't do this. Especially not on a Direwolf20 or Etho video. If you do that you suck.", //Mocking people who advertise their channels on other people's videos, especially famous people's videos like Direwolf20's or Etho's.
			"KA-BOOOOOOOOOOOOOOOOOOOOOOOOOOOOMMMM!!!",
			"We appear to be having technical difficulties, we apologise for the inconvenience.", //Common message when errors occur.
			"We apologise for the inconvenience.", //God's final message to his creation. Hitchiker's Guide To The Galaxy reference.
			"So sad that errors will always be errors. Has the error code been spoiled over the updates? NOW CRASH!", //Parody of Mundus's speech from the beginning of Chapter 22 of Devil May Cry 1
			"Crash reports! Now wasting multiple hours of modder's lives due to solving the bugs these have!",
			"The Error has occurred! Player, you are NOT getting away!", //Another parody of a Mundus quote from Devil May Cry 1 in the final level before the final boss fight.
			"Was it something I said?",
			"The crash amount! IT'S NOT OVAR 9000!!!!!!!", //A lie.
			"Over 9000 was a mistranslation. It was meant to say 8000. IT'S OVAR 8000!",
			"FLIBBLE FLOBBLE!",
			"Get your snacks!", //Etho reference.
			"DAG SAG NABIT GADAMMIT!",
			"Game over man! It's game over!", //Reference from the ban message when you die on a hardcore server.
			"The story of my life.",
			"Oh, my gumballs!", //Regular show reference.
			"Hahahah NO!",
			"Still nowhere near as strange as Uncle Grandpa.", //MOAR OF DA TRUTH BRUDDA!
			"1.8 is where the code is (MA MA MA MA MA MAA)!", //Reference to common YTP intro.
			"i herd u liek crashz", //Reference to "I heard u liek mudkipz"
			"Swedish Meatballs, with added crash sauce.", //Reference to AnderZEL's Twitch new Subscriber message.
			"Shrek is love, Shrek is life. Shrek is not, however, this crash.",
			"p15 r3p0r7 m3 70 7h3 m0d d3v :(", //1337 for pls report me to the mod dev :(
			"I bet vanilla never had this problem.",
			"This is bad news bears!",
			"When in doubt, 42.", //Hitchhiker's Guide To The Galaxy reference.
			"Um, er, hey! Over there! A distraction! *Runs off*",
			"There there. Thine heart shalt go forth Donkey.", //Another poshification.
			"The cake is a 37th Silly Comment",  //Solve it!
			"EventFactory.fireEvent(new EventModCrash())",
			"Antoine! >:|", //Sonic SatAM reference anyone?
			"Crash Minecraft! (Like a boss!)",
			"I love it when a crash comes together.",
			NModLoader.doesModExist("NIADEL_example_mod") ? "I see you have the N-API example mod. I hope it wasn't that doing it." : "I <3 Ternary Operators :3", //OOOH Special Ternary Silly :D
			"I hope this isn't related to me finding Sonic SatAM to be somewhat entertaining :P!",
			"Live and learn! Standing on the edge of tomorrow! Live and learn! Someone has to fix a buuu-uu-u-ug! ... Hope they find their waa-ay!",
			"HOY HOY!",
			"When in doubt, Chaos Emeralds.",
			"#@#@#@#@#\n\tSpecial Silly is special\n#@#@#@#@#",
			"CRAAASHEEEHAAAASHEEEHASH!", //Dragonball Z reference.
			"That crash is a spy!",
			"User: I wanted a crashless game! Game: Crashless game? *Pause* THIS! IS! MODS! *Gives user crash* - User = You, Game = Game",
			"FALCAWN CRASH!",
			"It's a note from a mod! Dear unfortunate user, the bugs and I have crashed your modded game! The answer is in the stacktrace - I dare ya to find it if you can!",
			"Strange unexplained crash? Must be aliens.",
			"I'm not saying it's a crash, but it's probably a crash.",
			"IMA CAUSIN' A CRASH! ModCrashReport.generateCrashReport(new RuntimeException(\"Crashing for some reason\"), true).crash(false)",
			"*Comedic remark here*"
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
	 * @see co.uk.niadel.commons.crash.CrashReport#CrashReport(Throwable, boolean)
	 */
	private ModCrashReport(Throwable e, boolean crash)
	{
		super(e, "N-API", crash);
	}

	public static final ModCrashReport generateCrashReport(Throwable e, boolean crash)
	{
		return new ModCrashReport(e, crash);
	}
	
	public String[] getLogText()
	{
		return this.log.toArray(new String[this.log.size()]);
	}

	@Override
	public void logReport(Logger logger)
	{
		this.report.add(SILLIES[new Random().nextInt(SILLIES.length)]);
		super.logReport(logger);
		this.log.add("Minecraft Version: " + NAPIData.FULL_VERSION);
		this.log.add("Running N-API Mods: ");

		//Logs the mods that are loaded in N-API.
		for (IModContainer mod : NModLoader.mods.getModContainers())
		{
			this.log.add("	ModId: " + mod.getModId() + " Version: " + mod.getVersion());
		}

		this.log.add("");

		//Logs the mods that are loaded in FML.
		if (MCData.isForgeDominated())
		{
			this.log.add("Game is running Forge as well as N-API. Forge mods:");

			for (Map.Entry<String, String> modEntry : NModLoader.forgeModids.entrySet())
			{
				this.log.add("	ModId: " + modEntry.getKey() + " Version: " + modEntry.getValue());
			}

			this.log.add("");
		}

		this.log.add("There has been an error that has caused the game to crash! The game is running under a modded environment, including N-API.");
	}

	public void crash(boolean halt)
	{
		if (MCData.isForgeDominated())
		{
			try
			{
				//Reflectively calls FML's FMLCommonHandler.exitJava() to prevent compile-time errors (I have the forge sources set to
				//not compile in IntelliJ IDEA).
				ReflectionCallMethods.callMethod(Class.forName("cpw.mods.fml.common.FMLCommonHandler"), "exitJava", 1, halt);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			if (halt)
			{
				Runtime.getRuntime().halt(1);
			}
			else
			{
				Runtime.getRuntime().exit(1);
			}
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
