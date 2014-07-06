package co.uk.niadel.mpi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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
public class FileUtils
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
}
