package co.uk.niadel.napi.nml;

import co.uk.niadel.napi.nml.IModContainer;
import net.minecraft.client.resources.FileResourcePack;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Used to allow modders the assets system. Code based on that of BlazeLoader.
 *
 * @author Niadel
 */
public class NMLResourcePack extends FileResourcePack
{
	public IModContainer mod;

	public NMLResourcePack(IModContainer container)
	{
		super(container.getLocationInFilesystem());
		this.mod = container;
	}

	@Override
	public String getPackName()
	{
		return "NMLInternalModResourcePack:" + mod.getModId();
	}

	@Override
	public InputStream getInputStreamByName(String name) throws IOException
	{
		try
		{
			return super.getInputStreamByName(name);
		}
		catch (IOException e)
		{
			if (name == "pack.mcmeta")
			{
				return new ByteArrayInputStream(
						("{" +
								"%*pack%*:" +
								"{" +
								"	%*description%*: %*A resource pack auto generated by N-API. If mod " + this.mod.getModId() + " has any textures or sounds, you can thank N-API :)!%*," +
								"   %*pack_format%*: 1" +
								"}" +
						"}").replace("%*", "\"").getBytes() //To make the pack format easier to read.
				);
			}
			else
			{
				throw e;
			}
		}
	}
}
