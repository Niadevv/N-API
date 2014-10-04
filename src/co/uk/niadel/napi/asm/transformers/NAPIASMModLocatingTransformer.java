package co.uk.niadel.napi.asm.transformers;

import co.uk.niadel.napi.annotations.Immutable;
import co.uk.niadel.napi.annotations.ModRegister;
import co.uk.niadel.napi.annotations.UnstableMod;
import co.uk.niadel.napi.asm.ASMRegistry;
import co.uk.niadel.napi.asm.IASMTransformer;
import co.uk.niadel.napi.nml.DependenciesRegistry;
import co.uk.niadel.napi.nml.ModContainer;
import co.uk.niadel.napi.nml.NModLoader;
import co.uk.niadel.napi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Allows the @ModRegister annotation to do the equivalent of Forge's @Mod annotation.
 */
public class NAPIASMModLocatingTransformer implements IASMTransformer, Opcodes
{
	@Immutable
	public static final NAPIASMModParsingTransformer modParser = new NAPIASMModParsingTransformer();

	@Override
	public byte[] manipulateBytecodes(String className, byte[] bytes)
	{
		try
		{
			ClassReader classReader = new ClassReader(bytes);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			String modid = null, version = null, unstableWarnMessage = "";
			boolean library = false, unstableMod = false;
			Class<?> modClass = Class.forName(className);
			Object mod = modClass.newInstance();
			List<String> dependencies = new ArrayList<>();

			for (AnnotationNode annotationNode : classNode.visibleAnnotations)
			{
				if (annotationNode.desc.contains("co/uk/niadel/napi/annotations/ModRegister"))
				{
					//Apparently ASM can't get values in annotations (only the names of the values), so here I use reflection.
					ModRegister modRegisterAnnotation = modClass.getAnnotation(ModRegister.class);

					modid = modRegisterAnnotation.modId();
					version = modRegisterAnnotation.version();
					library = modRegisterAnnotation.isLibrary();
					dependencies = Arrays.asList(modRegisterAnnotation.dependencies());
					DependenciesRegistry.addDependencies(mod, modRegisterAnnotation.dependencies());
				}
				else if (annotationNode.desc.contains("co/uk/niadel/napi/annotations/UnstableMod"))
				{
					unstableMod = true;
					unstableWarnMessage = modClass.getAnnotation(UnstableMod.class).warningMessage();
				}
			}

			if (modid != null && version != null)
			{
				if (unstableMod && unstableWarnMessage != "")
				{
					NAPILogHelper.instance.logWarn("Found an unstable mod! Warning message from mod: " + unstableWarnMessage);
				}

				NModLoader.loadMod(new ModContainer(mod, modid, version, dependencies.toArray(new String[dependencies.size()]), library));
				ASMRegistry.callASMTransformerForClass(modParser, className);
			}
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
		{
			if (e instanceof InstantiationException)
			{
				NAPILogHelper.instance.logError("Class " + className + " could not be instantiated! If your register is abstract, make it so it is not!");
			}

			if (e instanceof IllegalAccessException)
			{
				NAPILogHelper.instance.logError("Class " + className + " could not be instantiated! If your register does have a constructor, which you probably shouldn't, make it public.");
			}

			NAPILogHelper.instance.logError(e);
		}

		return bytes;
	}
}
