package co.uk.niadel.napi.forgewrapper;

import co.uk.niadel.napi.common.NAPIData;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;
import java.util.Collections;

/**
 * FML container so I don't have to keep an mcmod.info, because, let's face it, they're annoying.
 *
 * @author Niadel
 */
public class NAPIModContainer extends DummyModContainer
{
	public NAPIModContainer()
	{
		super(new ModMetadata());
		ModMetadata metadata = getMetadata();
		metadata.description = "A Forge compatible alternative to Forge, it allows for most of the same stuff as both MinecraftForge and FML both" +
				" by itself and with the two.";
		metadata.credits = "Whatever difference there was between this and my other projects that allowed me to continue with this," +
				" up until this point at the least. Oh, and Gradle, for not downloading all of ForgeGradle's dependencies, prompting me" +
				" to make this.";
		metadata.authorList = Arrays.asList("Niadel");
		metadata.modId = NAPIData.FORGE_MODID;
		metadata.name = NAPIData.NAME;
		metadata.dependants = Collections.emptyList();
		metadata.dependencies = Collections.emptyList();
		metadata.childMods = Collections.emptyList();
		metadata.requiredMods = Collections.emptySet();
		metadata.version = NAPIData.FULL_VERSION;
		metadata.updateUrl = "";
		metadata.url = "https://github.com/Niadel/N-API";
		metadata.parent = "";
		metadata.useDependencyInformation = false;
	}
}
