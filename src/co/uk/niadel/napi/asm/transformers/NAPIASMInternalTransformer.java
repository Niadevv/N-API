package co.uk.niadel.napi.asm.transformers;

import co.uk.niadel.napi.util.ValueExpandableMap;
import co.uk.niadel.napi.annotations.Internal;
import co.uk.niadel.napi.asm.ASMUtils;
import co.uk.niadel.napi.asm.IASMTransformer;
import co.uk.niadel.napi.util.ModCrashReport;
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
	public byte[] manipulateBytecodes(String className, byte[] bytes)
	{
		ClassReader classReader = new ClassReader(bytes);
		ClassNode classNode = new ClassNode();
		classReader.accept(classNode, 0);

		scanForInternals(className, classNode);
		removeInternalUsages(className, classNode);

		return new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES).toByteArray();
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
			//Some serious **** has happened for this to occur, crash.
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
				else if (insnNode instanceof FieldInsnNode)
				{
					handleFieldUsage(className, methodNode, (FieldInsnNode) insnNode);
				}
			}
		}
	}

	public void handleMethodCall(String className, MethodNode methodNode, MethodInsnNode methodInsnNode)
	{
		String methodCalledOwner = methodInsnNode.owner.replace("/", ".");

		if (internalFields.keySet().contains(methodCalledOwner))
		{
			if (className == methodCalledOwner)
			{
				//It's safe as this is the class that owns the method.
				return;
			}
			else if (internalFields.get(methodCalledOwner) instanceof ClassNode)
			{
				int numberOfInsnsToRemove = ASMUtils.getNumOfParamsInMethod(methodInsnNode.desc);
				methodNode.instructions.remove(methodInsnNode);

				//Remove the previous ALOAD/ILOADs.
				for (int i = 1; i == numberOfInsnsToRemove; i++)
				{
					methodNode.instructions.remove(methodNode.instructions.get(methodNode.instructions.indexOf(methodInsnNode) - i));
				}
			}
		}
	}

	public void handleFieldUsage(String className, MethodNode methodNode, FieldInsnNode fieldInsnNode)
	{
		String owner = fieldInsnNode.owner.replace("/", ".");
	}
}
