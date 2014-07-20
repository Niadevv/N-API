package co.uk.niadel.mpi.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class ModCrashLog
{
	public File theLog;
	public PrintStream filePrintStream;
	
	/**
	 * Creates a simple mod crash log.
	 * @param file
	 * @param e
	 */
	public ModCrashLog(File file, Throwable e)
	{
		try
		{
			this.filePrintStream = new PrintStream(file);
			e.printStackTrace(this.filePrintStream);
		}
		catch (FileNotFoundException e1)
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e2)
			{
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * Creates a mod crash log with special prefix information.
	 * @param file
	 * @param e
	 * @param preReportStuff Used to add things like random comments like "I blame Niadel" or "Here, have a hug token I took from
	 * Dinnerbone! [~~HUG~~] NOTE: CANNOT BE REDEEMED AT NEAREST MOJANGSTA :("
	 */
	public ModCrashLog(File file, Throwable e, String[] preReportStuff)
	{
		try
		{
			this.filePrintStream = new PrintStream(file);
			
			for (String currLine : preReportStuff)
			{
				//Fun fact, System.out is actually a PrintStream. Methods that work with System.out should work with this.
				this.filePrintStream.println(currLine);
			}
			
			e.printStackTrace(this.filePrintStream);
		}
		catch (FileNotFoundException e1)
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e2)
			{
				e2.printStackTrace();
			}
		}
	}
	
	public String[] getLogText()
	{
		String[] stringToReturn = new String[] {};
		Scanner logScanner = null;
		
		try
		{
			logScanner = new Scanner(this.theLog);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

        int i = 0;

		while (logScanner.hasNext())
		{
			stringToReturn[i] = logScanner.next();
			i++;
		}
		
		logScanner.close();
		return stringToReturn;
	}
}
