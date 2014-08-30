package co.uk.niadel.mpi.asm;

import co.uk.niadel.mpi.util.MCData;
import co.uk.niadel.mpi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;

/**
 * Fixes classes otherwise unable to be passed as Strings without edits to the base code. Only operates in a Non-FML environment as,
 * helpfully, FML had this same issue and fixed it.
 */
public class NAPIASMClassFixingTransformer implements IASMTransformer, Opcodes
{
	@Override
	public byte[] manipulateBytecodes(String className)
	{
		try
		{
			ClassReader classReader = new ClassReader(className);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);
			ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
			MethodNode methodNode = null;

			switch (className)
			{
				case "net.minecraft.util.StringTranslate":
					//FML already patches StringTranslate appropriately to fix the NPE that makes this class a bad class.
					if (!MCData.isForgeDominated())
					{
						/* TEMP Bytecode instructions to remind me of how to check if something's not null.
						StringTranslate.<init>
						ALOAD 4
   						IFNULL L10
    					ALOAD 4
    					ARRAYLENGTH
    					ICONST_2
    					IF_ICMPNE L10
						 */

						//TODO Finish fixing.
						methodNode = NAPIASMNecessityTransformer.constructMethodNode(ACC_PUBLIC, "<init>", "()V", null, null, classNode);
						LabelNode l18 = new LabelNode();

					}

					break;

				default:
					return classWriter.toByteArray();
			}
		}
		catch (IOException e)
		{
			NAPILogHelper.logError(e);
		}

		return null;
	}
}
