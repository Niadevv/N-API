package co.uk.niadel.napi.asm.transformers;

import co.uk.niadel.napi.asm.IASMTransformer;
import co.uk.niadel.napi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.io.IOException;

/**
 * Adds support for @ModObjectHolder.
 *
 * @author Niadel
 */
public class NAPIASMModObjectHolderTransformer implements IASMTransformer
{
	@Override
	public byte[] manipulateBytecodes(String className, byte[] bytes)
	{
		ClassReader classReader = new ClassReader(bytes);
		ClassNode classNode = new ClassNode();
		classReader.accept(classNode, 0);

		boolean hadMethods = false;

		for (AnnotationNode annotation : classNode.visibleAnnotations)
		{
			if (annotation.desc.contains("co/uk/niadel/napi/annotations/ModObjectHolder"))
			{
				if (!classNode.methods.isEmpty())
				{
					classNode.methods.removeAll(classNode.methods);
					hadMethods = true;
				}

				//Makes all of the fields immutable by adding them to the Immutable transformer's list of fields to make immutable.
				for (FieldNode fieldNode : classNode.fields)
				{
					NAPIASMImmutableTransformer.immutableFields.put(className, fieldNode);
				}
			}
		}

		if (hadMethods)
		{
			return new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES).toByteArray();
		}
		else
		{
			return bytes;
		}
	}
}
