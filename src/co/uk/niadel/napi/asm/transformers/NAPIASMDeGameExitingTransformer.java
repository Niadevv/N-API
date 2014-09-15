package co.uk.niadel.napi.asm.transformers;

import co.uk.niadel.napi.asm.IASMTransformer;
import co.uk.niadel.napi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.ListIterator;

/**
 * Removes calls to stuff like Runtime.halt() or System.exit() so that, if there is an error, there's an actual stacktrace to report.
 *
 * @author Niadel
 */
public class NAPIASMDeGameExitingTransformer implements IASMTransformer, Opcodes
{
	@Override
	public byte[] manipulateBytecodes(String className)
	{
		try
		{
			ClassReader classReader = new ClassReader(className);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			if (!(className.startsWith("net.minecraft")) && !(className.startsWith("scala")) && !className.startsWith("cpw") && !(className.startsWith("com.jcraft.jogg") && !(className.startsWith("co.uk.niadel.napi.util.ModCrashReport"))))
			{
				for (MethodNode methodNode : classNode.methods)
				{
					String allExceptionsThrown = methodNode.exceptions == null ? "---" : "";

					if (methodNode.exceptions != null)
					{
						for (String exceptionThrown : methodNode.exceptions)
						{
							allExceptionsThrown += ("," + exceptionThrown);
						}
					}

					ListIterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();

					while (insnIterator.hasNext())
					{
						AbstractInsnNode instruction = insnIterator.next();

						if (instruction instanceof MethodInsnNode)
						{
							MethodInsnNode actInsn = (MethodInsnNode) instruction;

							if (actInsn.getType() == INVOKESTATIC)
							{
								if (actInsn.owner == "java/lang/System" && actInsn.name == "exit")
								{
									methodNode.instructions.remove(instruction);
									NAPILogHelper.instance.logWarn("Found call to System.exit()! DO NOT DO THIS! The call has been removed.");
									NAPILogHelper.instance.logWarn("Offending caller is method " + methodNode.name + " with description " + methodNode.desc + " which throws " + allExceptionsThrown + " in class " + className + "!");
									NAPILogHelper.instance.logWarn("If you need to exit the game because of an error, crash the game or make a ModCrashReport.");
								}
							}
							else if (actInsn.getType() == INVOKEVIRTUAL)
							{
								if (actInsn.owner == "java/lang/Runtime" && actInsn.name == "halt")
								{
									methodNode.instructions.remove(instruction);
									NAPILogHelper.instance.logWarn("Found call to Runtime.halt()! DO NOT DO THIS! The call has been removed.");
									NAPILogHelper.instance.logWarn("Offending caller is method " + methodNode.name + " with description " + methodNode.desc + " which throws " + allExceptionsThrown + " in class " + className + "!");
									NAPILogHelper.instance.logWarn("If you need to exit the game because of an error, crash the game or make a ModCrashReport.");
								}
								else if (actInsn.owner == "java/lang/Runtime" && actInsn.name == "exit")
								{
									methodNode.instructions.remove(instruction);
									NAPILogHelper.instance.logWarn("Found call to Runtime.exit()! DO NOT DO THIS! The call has been removed.");
									NAPILogHelper.instance.logWarn("Offending caller is method " + methodNode.name + " with description " + methodNode.desc + " which throws " + allExceptionsThrown + " in class " + className + "!");
									NAPILogHelper.instance.logWarn("If you need to exit the game because of an error, make a ModCrashReport.");
								}
							}
						}
					}
				}
			}

			return new ClassWriter(classReader, 0).toByteArray();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
