package co.uk.niadel.napi.init;

import co.uk.niadel.napi.asm.IASMTransformer;
import co.uk.niadel.napi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.IOException;

/**
 * Patches the game when launched from the Minecraft Launcher. Adds necessary calls to Bootstrap.
 *
 * @author Niadel
 */
public class MCGamePatcher implements IASMTransformer, Opcodes
{
	@Override
	public byte[] manipulateBytecodes(String className)
	{
		try
		{
			ClassReader classReader = new ClassReader(className);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			LabelNode l0, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11;

			switch (className)
			{
				case ObfedClassNames.BOOTSTRAP:
					MethodNode methodNode = new MethodNode(ACC_PUBLIC, ObfedClassNames.BOOTSTRAP_BLOCK_ITEM_INIT, "()V", null, null);

					methodNode.instructions.clear();
					l0 = new LabelNode();
					l0.accept(methodNode);
					methodNode.instructions.add(l0);
					methodNode.instructions.add(new LineNumberNode(396, l0));
					methodNode.instructions.add(new FieldInsnNode(GETSTATIC, ObfedClassNames.BOOTSTRAP, ObfedClassNames.BOOTSTRAP_FIELD_INIT, "Z"));
					l1 = new LabelNode();
					l1.accept(methodNode);
					methodNode.instructions.add(new JumpInsnNode(IFNE, l1));
					l2 = new LabelNode();
					l2.accept(methodNode);
					methodNode.instructions.add(l2);
					methodNode.instructions.add(new LineNumberNode(398, l2));
					methodNode.instructions.add(new InsnNode(ICONST_1));
					methodNode.instructions.add(new FieldInsnNode(PUTSTATIC, ObfedClassNames.BOOTSTRAP, ObfedClassNames.BOOTSTRAP_FIELD_INIT, "Z"));
					//NModLoader.loadModsFromDir();
					l3 = new LabelNode();
					l3.accept(methodNode);
					methodNode.instructions.add(new LineNumberNode(399, l3));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/nml/NModLoader", "loadModsFromDir", "()V", false));
					//NModLoader.callAllPreInits();
					l4 = new LabelNode();
					l4.accept(methodNode);
					methodNode.instructions.add(l4);
					methodNode.instructions.add(new LineNumberNode(400, l4));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/nml/NModLoader", "callAllPreInits", "()V", false));
					//Block.registerBlocks();
					l5 = new LabelNode();
					l5.accept(methodNode);
					methodNode.instructions.add(l5);
					methodNode.instructions.add(new LineNumberNode(401, l5));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, ObfedClassNames.BLOCK, ObfedClassNames.BLOCK_REGISTER_BLOCKS, "()V", false));
					l6 = new LabelNode();
					l6.accept(methodNode);
					methodNode.instructions.add(l6);
					methodNode.instructions.add(new LineNumberNode(402, l6));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, ObfedClassNames.ITEM, ObfedClassNames.ITEM_REGISTER_ITEMS, "()V", false));
					l7 = new LabelNode();
					l7.accept(methodNode);
					methodNode.instructions.add(l7);
					methodNode.instructions.add(new LineNumberNode(403, l7));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, ObfedClassNames.BLOCKFIRE, ObfedClassNames.BLOCKFIRE_REGISTER_FLAMMABLE_BLOCKS, "()V", false));
					l8 = new LabelNode();
					l8.accept(methodNode);
					methodNode.instructions.add(l8);
					methodNode.instructions.add(new LineNumberNode(405, l8));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, ObfedClassNames.STATLIST, ObfedClassNames.STATLIST_REGISTER_STATS, "()V", false));
					l9 = new LabelNode();
					l9.accept(methodNode);
					methodNode.instructions.add(l9);
					methodNode.instructions.add(new LineNumberNode(406, l9));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/modhandler/nml/NModLoader", "callAllInits", "()V", false));
					l10 = new LabelNode();
					l10.accept(methodNode);
					methodNode.instructions.add(l10);
					methodNode.instructions.add(new LineNumberNode(407, l10));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, ObfedClassNames.BOOTSTRAP, ObfedClassNames.BOOTSTRAP_INIT_DISPENSER_BEHAVIOURS, "()V", false));
					l11 = new LabelNode();
					l11.accept(methodNode);
					methodNode.instructions.add(l11);
					methodNode.instructions.add(new LineNumberNode(408, l11));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/modhandler/nml/NModloader", "callAllPostInits", "()V", false));
					methodNode.instructions.add(l1);
					methodNode.instructions.add(new LineNumberNode(409, l1));
//					methodNode.instructions.add(new FrameNode(F_SAME, 0, null, 0, null));
					methodNode.instructions.add(new InsnNode(RETURN));
			}

			return new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS).toByteArray();
		}
		catch (IOException e)
		{
			NAPILogHelper.instance.logError(e);
		}

		return null;
	}
}
