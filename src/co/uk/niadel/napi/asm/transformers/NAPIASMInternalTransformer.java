package co.uk.niadel.napi.asm.transformers;

import co.uk.niadel.commons.datamanagement.ValueExpandableMap;
import co.uk.niadel.napi.annotations.Internal;
import co.uk.niadel.napi.asm.IASMTransformer;
import co.uk.niadel.napi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Gets rid of calls to methods and usage of methods outside of the package the method that the @Internal marks.
 *
 * @author Niadel
 */
public class NAPIASMInternalTransformer implements IASMTransformer, Opcodes
{
	/**
	 * Map of fields/methods/classes/etc with the @Internal annotation. Keyed by class name that the f/m/c/etc is owned by.
	 * Valued by the Field/Method/ClassNode that has the @Internal annotation.
	 */
	private static final ValueExpandableMap<String, Object> internalFields = new ValueExpandableMap<>();

	@Override
	public byte[] manipulateBytecodes(String className)
	{
		try
		{
			ClassReader classReader = new ClassReader(className);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			scanForInternals(className, classNode);
			removeInternalUsages(className, classNode);

			return new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES).toByteArray();
		}
		catch (IOException e)
		{
			NAPILogHelper.instance.logError(e);
		}

		return null;
	}

	public void scanForInternals(String className, ClassNode classNode)
	{
		try
		{
			for (AnnotationNode annotationNode : classNode.visibleAnnotations)
			{
				if (annotationNode.desc.contains("co/uk/niadel/napi/annotations/Internal"))
				{
					Internal annotation = Class.forName(className).getAnnotation(Internal.class);

					if (!annotation.documentationOnly())
					{
						internalFields.put(annotation.owningPackage(), classNode);
					}
				}
			}
		}
		catch (ClassNotFoundException impossibru)
		{
			//Should NEVER, EVER happen. EVAR!
			NAPILogHelper.instance.logError("Impossibru error! KAAAAAABOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOMMMMMMMMMMMMM!!!!!!!!!");
			NAPILogHelper.instance.logError(impossibru);
		}
	}

	public void removeInternalUsages(String className, ClassNode classNode)
	{

	}
}
