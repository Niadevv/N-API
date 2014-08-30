package co.uk.niadel.mpi.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import co.uk.niadel.mpi.common.NAPIData;

/**
 * Partially borrowed and modified code from the N-API installer. I have plans to make a universal
 * library of mine (which will include DoubleMap) to make this sort of thing unecessary.
 * @author Niadel
 *
 */
public final class FileUtils
{
	/**
	 * Copies directories recursively, with the help of a StackOverflow answer.
	 * @param from
	 * @param to
	 */
	public static final void copyFilesRecursively(File from, File to)
	{
		if (from.isDirectory())
		{
			//Code borrowed and modified from StackOverFlow, from below page, 2nd response. 
			//http://stackoverflow.com/questions/5368724/how-to-copy-a-folder-and-all-its-subfolders-and-files-into-another-folder
			String[] children = from.list();
			
	        for (String currChild : children) 
	        {
	            try
				{
					copyFile(new File(from, currChild), new File(to, currChild));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
	        }
		}
	}
	
	public static final void injectFileIntoZip(File from, ZipFile to)
	{
		try
		{
			File temp = new File(new File(".").toPath().toString().replace(NAPIData.MC_VERSION + ".jar", "") + "temp.zip");
			temp.createNewFile();
			ZipFile tempZip = new ZipFile(temp);
			PrintStream outStream = new PrintStream(new ZipOutputStream(new FileOutputStream(tempZip.getName())));
			
			Scanner fromScanner = new Scanner(from);
			
			while (fromScanner.hasNext())
			{
				outStream.print(fromScanner.next());
			}
			
			fromScanner.close();
			outStream.close();
			tempZip.close();
			temp.delete();
			
			copyZipFilesRecursively(tempZip, to);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Like the above method, only it does it with zip files.
	 * @param from
	 * @param to
	 */
	public static final void copyZipFilesRecursively(ZipFile from, ZipFile to)
	{
		Enumeration<? extends ZipEntry> fileEnum = from.entries();
		
		while (fileEnum.hasMoreElements())
		{
			try
			{
				Scanner zipStream = new Scanner(from.getInputStream(from.getEntry(fileEnum.nextElement().getName())));
				ZipOutputStream outStream = new ZipOutputStream(new FileOutputStream(to.getName()));
				
				while (zipStream.hasNext())
				{
					outStream.write(zipStream.nextByte());
				}
				
				zipStream.close();
				outStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Extracts a zip file to a specified output file.
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static final void extractZip(ZipFile in, File out) throws IOException
	{
		Enumeration<? extends ZipEntry> enumZip = in.entries();
		
		while (enumZip.hasMoreElements())
		{
			ZipEntry next = enumZip.nextElement();
			ZipInputStream inStream = new ZipInputStream(in.getInputStream(next));
			FileOutputStream outStream = new FileOutputStream(out);

			//Borrowed and ever so slightly modified code.
			byte[] data = new byte[2048];
			int b = -1;
			while ((b = inStream.read(data)) != -1)
			{
				outStream.write(data, 0, b);
			}

			inStream.close();
			outStream.close();
		}
	}
	
	/**
	 * Copies an individual file to another, replacing the existing to file with the from after
	 * checking the files exist.
	 * @param from
	 * @param to
	 * @throws IOException
	 */
	public static final void copyFile(File from, File to) throws IOException
	{
		//Clears the from file.
		if (from.exists())
		{
			from.delete();
			from.createNewFile();
			copyFile(from, to);
		}
		
		if (to.exists())
		{
			//Clears the to file.
			to.delete();
			to.createNewFile();
			copyFile(from, to);
		}
		
		cloneFiles(new FileInputStream(from), new FileOutputStream(to));
	}
	
	/**
	 * Does copyFile, but only does streams and doesn't check if the Files exist.
	 * @param from
	 * @param to
	 */
	public static final void cloneFiles(InputStream from, OutputStream to)
	{
		Scanner fileScanner = new Scanner(from);
		PrintStream out = new PrintStream(to);
		
		while (fileScanner.hasNext())
		{
			out.println(fileScanner.nextLine());
		}
		
		fileScanner.close();
		out.close();
	}
	
	/**
	 * Basically the same as cloneFiles, but with files instead.
	 * @param from
	 * @param to
	 */
	public static final void cloneFiles(File from, File to)
	{
		try
		{
			cloneFiles(new FileInputStream(from), new FileOutputStream(to));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static final void addToLine(String toAdd, int lineToAddAt, File theFile)
	{
		File tempFile = new File("temp");

		try (Scanner scanner = new Scanner(theFile))
		{
			int lineCounter = 0;
			tempFile.createNewFile();
			PrintStream printStream = new PrintStream(tempFile);

			//Copy the original file's contents to a temp file.
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
			e.printStackTrace();
		}
		finally
		{
			//Delete the temprorary file as it is no longer needed.
			tempFile.delete();
		}
	}

	public static final void downloadFile(URL url, File out)
	{
		try
		{
			//May help users like me who don't have amazing luck with auto downloads - If it works for Minecraft it should work for me.
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 8080));

			//Borrowed from Minecraft's HttpUtil class. Deobfuscated so I don't get as yelled at :P.
			HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection(proxy);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("Content-Length", "" + url.openStream().available());
			httpURLConnection.setRequestProperty("Content-Language", "en-US");
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(false);
			Scanner inputStream = new Scanner(new DataInputStream(httpURLConnection.getInputStream()));
			FileOutputStream outputStream = new FileOutputStream(out);

			while (inputStream.hasNext())
			{
				outputStream.write(inputStream.nextByte());
			}

			inputStream.close();
			outputStream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
