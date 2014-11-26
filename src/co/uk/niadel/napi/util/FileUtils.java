package co.uk.niadel.napi.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

/**
 * File related utillities.
 * @author Niadel
 */
public class FileUtils
{
	/**
	 * Adds a string to a specific line.
	 * @param toAdd The string to add to a line.
	 * @param lineToAddAt The line to add toAdd at.
	 * @param theFile The file to add the line to.
	 */
	public static final void addToLine(String toAdd, int lineToAddAt, File theFile)
	{
		File tempFile = new File("temp");

		try (Scanner scanner = new Scanner(theFile))
		{
			int lineCounter = 0;
			tempFile.createNewFile();
			PrintStream printStream = new PrintStream(tempFile);

			//Copy the original file's contents to a temporary file.
			while (scanner.hasNext())
			{
				String nextLine = scanner.nextLine();
				lineCounter++;

				if (lineCounter == lineToAddAt)
				{
					printStream.println(toAdd);
				}
				else
				{
					printStream.println(nextLine);
				}
			}

			//Clear the original file.
			theFile.delete();
			theFile.createNewFile();

			Scanner scanner2 = new Scanner(tempFile);

			PrintStream printStream2 = new PrintStream(theFile);

			//Copy the temprorary file's contents to the original file.
			while (scanner2.hasNext())
			{
				printStream2.println(scanner2.nextLine());
			}
		}
		catch (IOException e)
		{
			NAPILogHelper.logError(e);
		}
		finally
		{
			//Delete the temporary file as it is no longer needed.
			tempFile.delete();
		}
	}

	public static final void downloadFile(URL fileUrl, File out)
	{
		try
		{
			ReadableByteChannel channel = Channels.newChannel(fileUrl.openStream());
			Scanner connectionScanner = new Scanner(fileUrl.openStream());
			FileOutputStream outStream = new FileOutputStream(out);
			outStream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
