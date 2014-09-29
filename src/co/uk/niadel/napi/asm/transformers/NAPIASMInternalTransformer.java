package co.uk.niadel.napi.asm.transformers;

import co.uk.niadel.commons.datamanagement.ValueExpandableMap;
import co.uk.niadel.napi.annotations.Internal;
import co.uk.niadel.napi.asm.IASMTransformer;
import co.uk.niadel.napi.util.ModCrashReport;
import co.uk.niadel.napi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.IOException;

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

					break;
				}
			}

			for (MethodNode methodNode : classNode.methods)
			{
				for (AnnotationNode annotationNode : classNode.visibleAnnotations)
				{
					if (annotationNode.desc.contains("co/uk/niadel/napi/annotations/Internal"))
					{
						Internal annotation = Class.forName(className).getAnnotation(Internal.class);

						if (!annotation.documentationOnly())
						{
							internalFields.put(annotation.owningPackage(), methodNode);
						}

						break;
					}
				}
			}

			for (FieldNode fieldNode : classNode.fields)
			{
				for (AnnotationNode annotationNode : classNode.visibleAnnotations)
				{
					if (annotationNode.desc.contains("co/uk/niadel/napi/annotations/Internal"))
					{
						Internal annotation = Class.forName(className).getAnnotation(Internal.class);

						if (!annotation.documentationOnly())
						{
							internalFields.put(annotation.owningPackage(), fieldNode);
						}

						break;
					}
				}
			}
		}
		catch (ClassNotFoundException impossibru)
		{
			//Some serious **** has happened to let this happen, crash.
			ModCrashReport.generateCrashReport(impossibru, true);
		}
	}

	public void removeInternalUsages(String className, ClassNode classNode)
	{
		for (MethodNode methodNode : classNode.methods)
		{
			for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
			{
				if (insnNode instanceof MethodInsnNode)
				{
					handleMethodCall(className, methodNode, (MethodInsnNode) insnNode);
				}
			}
		}
	}

	public void handleMethodCall(String className, MethodNode methodNode, MethodInsnNode methodInsnNode)
	{
		if (internalFields.keySet().contains(methodInsnNode.owner.replace("/", ".")))
		{

		}
	}
}
