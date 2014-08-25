package co.uk.niadel.mpi.asm;

import co.uk.niadel.mpi.modhandler.loadhandler.ModContainer;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.List;

/**
 * Allows the @ModRegister annotation to do the equivalent of Forge's @Mod annotation.
 */
public class NAPIASMModLocatingTransformer implements IASMTransformer, Opcodes
{
	@Override
	public byte[] manipulateBytecodes(String className)
	{
		try
		{
			ClassReader classReader = new ClassReader(className);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			String modid = null, version = null;
			boolean library = false;
			Object mod = Class.forName(className).newInstance();

			for (AnnotationNode annotationNode : classNode.visibleAnnotations)
			{
				if (annotationNode.desc.contains("co/uk/niadel/mpi/annotations/ModRegister"))
				{
					List<Object> annotationValues = annotationNode.values;

					modid = (String) annotationValues.get(0);
					version = (String) annotationValues.get(1);
					library = (boolean) annotationValues.get(2);
				}
			}

			if (modid != null && version != null)
			{
				NModLoader.loadMod(new ModContainer(mod, modid, version, library));
			}
		}
		catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
		{
			if (e instanceof InstantiationException)
			{
				NAPILogHelper.logError("Class " + className + " could not be instantiated! If your register is abstract, make it non-abstract!");
			}

			if (e instanceof IllegalAccessException)
			{
				NAPILogHelper.logError("Class " + className + " could not be instantiated! If your register does have a constructor, which you shouldn't, make it public.");
			}

			NAPILogHelper.logError(e);
		}

		return null;
	}
}
