package co.uk.niadel.napi.asm.transformers;

import co.uk.niadel.commons.datamanagement.ValueExpandableMap;
import co.uk.niadel.napi.annotations.Immutable;
import co.uk.niadel.napi.asm.IASMTransformer;
import co.uk.niadel.napi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.IOException;

/**
 * Allows for the @Immutable annotation to work.
 *
 * @author Niadel
 */
public class NAPIASMImmutableTransformer implements IASMTransformer, Opcodes
{
	/**
	 * Keyed by the name of the class, entried by a field node that represents an immutable field. Ironically enough, is an immutable
	 * field in itself.
	 */
	@Immutable
	static final ValueExpandableMap<String, FieldNode> immutableFields = new ValueExpandableMap<>();

	@Immutable
	private static final NAPIASMModObjectHolderTransformer modObjectHolderTransformer = new NAPIASMModObjectHolderTransformer();

	@Override
	public byte[] manipulateBytecodes(String className, byte[] bytes)
	{
		modObjectHolderTransformer.manipulateBytecodes(className, bytes);

		ClassReader classReader = new ClassReader(bytes);
		ClassNode classNode = new ClassNode();
		classReader.accept(classNode, 0);

		this.findImmutables(classNode);
		return this.removeImmutableFieldPuts(classNode, classReader);
	}

	private void findImmutables(ClassNode classNode)
	{
		for (FieldNode fieldNode : classNode.fields)
		{
			for (AnnotationNode annotationNode : fieldNode.visibleAnnotations)
			{
				if (annotationNode.desc.contains("co/uk/niadel/napi/annotations/Immutable"))
				{
					immutableFields.put(classNode.name, fieldNode);
				}
			}
		}
	}

	/**
	 * Removes calls to reflection and setting directly etc.
	 */
	private byte[] removeImmutableFieldPuts(ClassNode classNode, ClassReader classReader)
	{
		for (MethodNode methodNode : classNode.methods)
		{
			for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
			{
				if (insnNode instanceof FieldInsnNode && (insnNode.getOpcode() == PUTFIELD || insnNode.getOpcode() == PUTSTATIC))
				{
					FieldInsnNode setFieldInsn = (FieldInsnNode) insnNode;

					if (immutableFields.containsKey(setFieldInsn.owner))
					{
						methodNode.instructions.remove(insnNode);
						//Remove load from before the method call to prevent issues.
						methodNode.instructions.remove(methodNode.instructions.get(methodNode.instructions.indexOf(insnNode) - 1));
						NAPILogHelper.instance.logWarn("Removed the set of field " + setFieldInsn.name + " in class " + setFieldInsn.owner + " as it was marked with the @Immutable annotation!");
					}
				}
				else if (insnNode instanceof MethodInsnNode)
				{
					MethodInsnNode methodInsnNode = (MethodInsnNode) insnNode;

					if (methodInsnNode.getOpcode() == INVOKESTATIC)
					{
						if (methodInsnNode.owner.contains("Reflection") && (methodInsnNode.owner.contains("co/uk/niadel") || methodInsnNode.owner.contains("cpw/mods/fml")) && methodInsnNode.name.startsWith("set"))
						{
							AbstractInsnNode prevInsn = null;
							//Get rid of the aloads.
							for (int i = 0; i == 4; i++)
							{
								if (prevInsn == null)
								{
									prevInsn = methodInsnNode;
								}

								prevInsn = prevInsn.getPrevious();

								if (prevInsn.getOpcode() == ALOAD || prevInsn.getOpcode() == ILOAD)
								{
									methodNode.instructions.remove(prevInsn);
								}
								else
								{
									break;
								}
							}

							methodNode.instructions.remove(insnNode);

							NAPILogHelper.instance.logWarn("Removed call to a potential setter reflection method from either an N-API or FML reflection helper class!");
						}
					}
					else if (methodInsnNode.getOpcode() == INVOKEVIRTUAL)
					{
						if (methodInsnNode.owner.contains("Method"))
						{
							if (methodInsnNode.name == "set")
							{
								AbstractInsnNode prevInsn1, prevInsn2;

								prevInsn1 = methodInsnNode.getPrevious();
								prevInsn2 = prevInsn1.getPrevious();

								methodNode.instructions.remove(prevInsn2);
								methodNode.instructions.remove(prevInsn1);
								methodNode.instructions.remove(insnNode);
							}
						}
					}
				}
			}
		}

		return new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES).toByteArray();
	}
}
