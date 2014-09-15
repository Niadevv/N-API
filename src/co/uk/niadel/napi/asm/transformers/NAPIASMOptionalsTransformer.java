package co.uk.niadel.napi.asm.transformers;

import co.uk.niadel.napi.annotations.ReplacementFor;
import co.uk.niadel.napi.asm.IASMTransformer;
import co.uk.niadel.napi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.napi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.io.IOException;

/**
 * Gives functionality for annotations like ReplacementFor and other annotations that only do something when certain mods exist/don't exist.
 *
 * @author Niadel
 */
public class NAPIASMOptionalsTransformer implements IASMTransformer, Opcodes
{
	@Override
	public byte[] manipulateBytecodes(String className)
	{
		try
		{
			ClassReader classReader = new ClassReader(className);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			for (FieldNode fieldNode : classNode.fields)
			{
				handleField(fieldNode, classNode, className);
			}

			return new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES).toByteArray();
		}
		catch (IOException | ClassNotFoundException e)
		{
			NAPILogHelper.instance.logError(e);
		}

		return null;
	}

	public void handleField(FieldNode fieldNode, ClassNode classNode, String className) throws ClassNotFoundException
	{
		for (AnnotationNode annotationNode : fieldNode.visibleAnnotations)
		{
			if (annotationNode.desc.contains("co/uk/niadel/napi/annotations/ReplacementFor"))
			{
				ReplacementFor annotation = Class.forName(className).getAnnotation(ReplacementFor.class);

				if (NModLoader.doesModExist(annotation.itemInModWithModId()))
				{
					//The mod that the field is a replacement for an item in exists, remove the field.
					classNode.fields.remove(fieldNode);
				}
			}
		}
	}
}
