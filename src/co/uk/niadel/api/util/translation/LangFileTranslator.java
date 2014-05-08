package co.uk.niadel.api.util.translation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LangFileTranslator 
{
	public String[] fileContents;
	public String fileName;
	
	/**
	 * Gets the file contents for parsing later on.
	 * @param langFile The lang file.
	 */
	public LangFileTranslator(File langFile)
	{
		try 
		{
			Scanner fileScanner = new Scanner(langFile);
			String[] arrayOfLangContents = new String[] {};
			int i = 0;
			
			while (fileScanner.hasNext())
			{
				arrayOfLangContents[i] = fileScanner.next();
				i++;
			}
			
			if (arrayOfLangContents[0] != null)
			{
				fileContents = arrayOfLangContents;
			}
			
			fileScanner.close();
			fileName = langFile.toString();
		}
		catch (FileNotFoundException e) 
		{
			//Let the user know something was awkward with file reading
			System.err.println("An error occured attempting to read a nonexistant lang file! Please check to see if all mods have their lang files.");
			e.printStackTrace();
		}
	}
	
	public String getTranslation(String unlocalisedName)
	{
		String translationToReturn = "";
		
		for (int i = 0; i == fileContents.length; i++)
		{
			if (fileContents[i].startsWith(unlocalisedName))
			{
				translationToReturn = fileContents[i].substring(unlocalisedName.length() + 1);
			}
			else
			{
				System.err.println("Translation " + unlocalisedName + " does not exist in file " + fileName);
			}
		}
		
		if (translationToReturn != "")
		{
			return translationToReturn;
		}
		//The method should have returned by this point, but without this it decides
		//it's not good enough for some bizarre reason
		throw new RuntimeException("Could not get a translation from " + fileName + "!");
	}
}
