package co.uk.niadel.mpi.asm;

import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;

import co.uk.niadel.mpi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import co.uk.niadel.mpi.common.NAPIData;
import co.uk.niadel.mpi.util.MCData;

/**
 * This class adds Event calls and other things necessary for N-API to work instead of having direct base edits. This class is special as
 * it is one of the only classes that has no restrictions on the classes it can or can't edit.
 * @author Niadel
 *
 */
public class NAPIASMNecessityTransformer implements IASMTransformer, Opcodes
{
	public static final void deleteBytecodesOfMethod(MethodNode method)
	{
		Iterator<AbstractInsnNode> insnNodeIterator = method.instructions.iterator();

		while (insnNodeIterator.hasNext())
		{
			method.instructions.remove(insnNodeIterator.next());
		}
	}

	public static final MethodNode constructMethodNode(int accessFlags, String methodName, String description, String signature, String[] exceptions, ClassNode classNode)
	{
		MethodNode theMethod = new MethodNode(accessFlags, methodName, description, signature, exceptions);
		classNode.methods.remove(theMethod);
		return theMethod;
	}

	/**
	 * Adds method to classNode.methods.
	 * @param method The methodNode to add to classNode.
	 * @param classNode The classNode to add method to.
	 */
	public static final void finishMethodNodeEdit(MethodNode method, ClassNode classNode)
	{
		classNode.methods.add(method);
	}

	@Override
	public byte[] manipulateBytecodes(String className)
	{
		try
		{
			ClassNode cn = new ClassNode();
			ClassReader cr = new ClassReader(className);
			ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
			FieldVisitor fv;
			MethodVisitor mv;
			MethodNode methodNode;

			//Labels, because switch control structures are awkward with local variables.
			LabelNode ln0;
			LabelNode ln1;
			LabelNode ln3;
			LabelNode ln4;
			Label l3;
			Label l261;
			Label l10;
			Label l12;
			Label l6;
			Label l9;
			Label l15;
			Label l79;
			Label l17;
			LabelNode ln2;
			ListIterator insnIterator;
			cr.accept(cn, 0);

			switch (className)
			{
				//Adding INSTANCE field so that the ItemBaseModArmour works, adding event calls.
				case "net.minecraft.item.Item":
					cn.fields.add(new FieldNode(ACC_PUBLIC, "INSTANCE", null, null, null));

					methodNode = constructMethodNode(ACC_PUBLIC, "<init>", "()V", null, null, cn);

					deleteBytecodesOfMethod(methodNode);

					ln0 = new LabelNode();
					ln0.accept(methodNode);
					methodNode.instructions.add(new LineNumberNode(45, ln0));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false));
					ln1 = new LabelNode();
					ln1.accept(methodNode);
					methodNode.instructions.add(new LineNumberNode(55, ln1));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new VarInsnNode(BIPUSH, 64));
					methodNode.instructions.add(new FieldInsnNode(PUTFIELD, "net/minecraft/item/Item", "maxStackSize", "I"));
					ln2 = new LabelNode();
					ln2.accept(methodNode);
					methodNode.instructions.add(new LineNumberNode(80, ln2));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new FieldInsnNode(PUTFIELD, "net/minecraft/item/Item", "INSTANCE", "Lnet/minecraft/item/Item"));
					ln3 = new LabelNode();
					ln3.accept(methodNode);
					methodNode.instructions.add(new LineNumberNode(858, ln3));
					methodNode.instructions.add(new InsnNode(RETURN));
					ln4 = new LabelNode();
					ln4.accept(methodNode);

					finishMethodNodeEdit(methodNode, cn);

					//Add onEaten event call.
					methodNode = constructMethodNode(ACC_PUBLIC, "onEaten", "(Lnet/minecraft/item/ItemStack)V", null, null, cn);

					deleteBytecodesOfMethod(methodNode);

					ln0 = new LabelNode();
					ln0.accept(methodNode);
					methodNode.instructions.add(ln0);
					methodNode.instructions.add(new LineNumberNode(478, ln0));
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/mpi/events/EventItemEaten"));
					methodNode.instructions.add(new InsnNode(DUP));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 1));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 2));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 3));
					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/mpi/events/EventItemEaten", "<init>", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false));
					ln1 = new LabelNode();
					ln1.accept(methodNode);
					methodNode.instructions.add(ln1);
					methodNode.instructions.add(new LineNumberNode(479, ln1));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 1));
					methodNode.instructions.add(new InsnNode(ARETURN));
					ln2 = new LabelNode();
					ln2.accept(methodNode);
					methodNode.instructions.add(ln2);

					finishMethodNodeEdit(methodNode, cn);

					methodNode = constructMethodNode(ACC_PUBLIC, "onItemUse", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityPlayer;Lnet/minecraft/world/World;IIIIFFF)Z", null, null, cn);

					deleteBytecodesOfMethod(methodNode);

					ln0 = new LabelNode();
					ln0.accept(methodNode);
					methodNode.instructions.add(new LineNumberNode(458, ln0));
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/mpi/events/items/EventItemUse"));
					methodNode.instructions.add(new InsnNode(DUP));

					//Lazy loading of local variables.
					for (int i = 1; i == 10; i++)
					{
						methodNode.instructions.add(new VarInsnNode(ALOAD, i));
					}

					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/mpi/events/items/EventItemUse", "<init>", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;IIIIFFF)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false));
					break;

				//Obfuscated Item patching
				case "abn":
					cw.newField("abn", "INSTANCE", "Labn;");
					fv = cw.visitField(ACC_PUBLIC, "INSTANCE", "Labn", null, null);
					fv.visitEnd();
					break;

				//Adding call to GUIHUDRegistry.callAllRenders in RenderItem.renderGameOverlay.
				case "net.minecraft.client.renderer.entity.RenderItem":
					mv = cw.visitMethod(ACC_PUBLIC, "renderGameOverlay", "(FZII)V", null, null);
					mv.visitCode();
					l261 = new Label();
					mv.visitLabel(l261);
					mv.visitLineNumber(498, l261);
					mv.visitVarInsn(ALOAD, 0);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/client/GUIHUDRegistry", "callAllRenderers", "(Lnet/minecraft/client/gui/GuiIngame;)V", false);
					break;

				//Obfuscated RenderItem patching
				case "bnq":
					mv = cw.visitMethod(ACC_PUBLIC, "a", "(FZII)V", null, null);
					mv.visitCode();
					l261 = new Label();
					mv.visitLabel(l261);
					mv.visitLineNumber(498, l261);
					mv.visitVarInsn(ALOAD, 0);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/client/GUIHUDRegistry", "callAllRenderers", "(Lbah;)V", false);
					break;

				//Add call to VillagePieceRegistry.addAllPieces in getStructureVillageWeightedPieceList
				case "net.minecraft.world.gen.structure.StructureVillagePieces":
					mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getStructureVillageWeightedPieceList", "(Ljava/util/Random;I)Ljava/util/List;", null, null);
					mv.visitCode();
					l10 = new Label();
					mv.visitLabel(l10);
					mv.visitLineNumber(54, l10);
					mv.visitVarInsn(ALOAD, 2);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/gen/structures/VillagePieceRegistry", "addAllPieces", "(Ljava/util/ArrayList;)V", false);
					break;

				//Obfuscated StructureVillagePieces patching
				case "avp":
					mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "a", "(Ljava/util/Random;I)Ljava/util/List;", null, null);
					mv.visitCode();
					l10 = new Label();
					mv.visitLabel(l10);
					mv.visitLineNumber(54, l10);
					mv.visitVarInsn(ALOAD, 2);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/gen/structures/VillagePieceRegistry", "addAllPieces", "(Ljava/util/ArrayList;)V", false);
					break;

				//Add calls to events. Oh, and fix for Forge renaming isClient.
				case "net.minecraft.world.World":
					if (MCData.isForge)
					{
						cn.fields.add(new FieldNode(ACC_PUBLIC, "isClient", null, null, null));

						methodNode = new MethodNode(ACC_PUBLIC, "<init>", "(Lnet/minecraft/world/storage/ISaveHandler;Ljava/lang/String;Lnet/minecraft/world/WorldProvider;Lnet/minecraft/world/WorldSettings;Lnet/minecraft/profiler/Profiler;)V", null, null);

						ln0 = new LabelNode();
						ln0.accept(methodNode);
						methodNode.instructions.add(ln0);
						methodNode.instructions.add(new LineNumberNode(199, ln0));
						methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
						methodNode.instructions.add(new FieldInsnNode(GETFIELD, "Lnet/minecraft/world/World", "isRemote", "Z"));
						methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
						methodNode.instructions.add(new FieldInsnNode(PUTFIELD, "Lnet/minecraft/world/World", "isClient", "Z"));

						methodNode = new MethodNode(ACC_PUBLIC, "<init>", "(Lnet/minecraft/world/storage/ISaveHandler;Ljava/lang/String;Lnet/minecraft/world/WorldSettings;Lnet/minecraft/world/WorldProvider;Lnet/minecraft/profiler/Profiler;)V", null, null);

						ln0 = new LabelNode();
						ln0.accept(methodNode);
						methodNode.instructions.add(ln0);
						methodNode.instructions.add(new LineNumberNode(199, ln0));
						methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
						methodNode.instructions.add(new FieldInsnNode(GETFIELD, "Lnet/minecraft/world/World", "isRemote", "Z"));
						methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
						methodNode.instructions.add(new FieldInsnNode(PUTFIELD, "Lnet/minecraft/world/World", "isClient", "Z"));
					}

					mv = cw.visitMethod(ACC_PUBLIC, "spawnEntityInWorld", "(Lnet/minecraft/entity/Entity;)Z", null, null);
					mv.visitCode();

					//Add the event local variable for EventEntitySpawned.
					l6 = new Label();
					mv.visitLineNumber(1409, l6);
					mv.visitFrame(F_SAME, 0, null, 0, null);
					mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventEntitySpawned");
					mv.visitInsn(DUP);
					mv.visitVarInsn(ALOAD, 1);
					mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventEntitySpawned", "<init>", "(Lnet/minecraft/entity/Entity;)V", false);
					mv.visitVarInsn(ASTORE, 5);

					l9 = new Label();
					mv.visitLineNumber(1420, l9);
					mv.visitFrame(F_APPEND, 1, new Object[]{"co/uk/niadel/mpi/events/EventCancellable"}, 0, null);
					mv.visitVarInsn(ALOAD, 5);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false);


					l12 = new Label();
					mv.visitLabel(l12);
					mv.visitLineNumber(1415, l12);
					mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventPlayerSpawned");
					mv.visitInsn(DUP);
					mv.visitVarInsn(ALOAD, 6);
					mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventPlayerSpawned", "<init>", "(Lnet/minecraft/entity/player/EntityPlayer;)V", false);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false);

					l15 = new Label();
					mv.visitLineNumber(1423, l15);
					mv.visitFrame(F_SAME, 0, null, 0, null);
					mv.visitVarInsn(ALOAD, 5);
					mv.visitMethodInsn(INVOKEVIRTUAL, "co/uk/niadel/mpi/events/EventCancellable", "isCancelled", "()Z", false);
					break;

				//Obfuscated World patching.
				case "afn":
					mv = cw.visitMethod(ACC_PUBLIC, "d", "(Lqn;)Z", null, null);
					mv.visitCode();

					//Add the event local variable for EventEntitySpawned.
					l6 = new Label();
					mv.visitLineNumber(1409, l6);
					mv.visitFrame(F_SAME, 0, null, 0, null);
					mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventEntitySpawned");
					mv.visitInsn(DUP);
					mv.visitVarInsn(ALOAD, 1);
					mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventEntitySpawned", "<init>", "(Lqn;)V", false);
					mv.visitVarInsn(ASTORE, 5);

					l9 = new Label();
					mv.visitLineNumber(1420, l9);
					mv.visitFrame(F_APPEND, 1, new Object[]{"co/uk/niadel/mpi/events/EventCancellable"}, 0, null);
					mv.visitVarInsn(ALOAD, 5);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false);


					l12 = new Label();
					mv.visitLabel(l12);
					mv.visitLineNumber(1415, l12);
					mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventPlayerSpawned");
					mv.visitInsn(DUP);
					mv.visitVarInsn(ALOAD, 6);
					mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventPlayerSpawned", "<init>", "(Lxl;)V", false);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false);

					l15 = new Label();
					mv.visitLineNumber(1423, l15);
					mv.visitFrame(F_SAME, 0, null, 0, null);
					mv.visitVarInsn(ALOAD, 5);
					mv.visitMethodInsn(INVOKEVIRTUAL, "co/uk/niadel/mpi/events/EventCancellable", "isCancelled", "()Z", false);
					break;

				//Add EventFactory call to doExplosionA
				case "net.minecraft.util.Explosion":
					mv = cw.visitMethod(ACC_PUBLIC, "doExplosionA", "()V", null, null);
					mv.visitCode();

					l79 = new Label();
					mv.visitLineNumber(163, l79);
					mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventExplosion");
					mv.visitInsn(DUP);
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/world/Explosion", "exploder", "Lnet/minecraft/entity/Entity;");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/world/Explosion", "explosionX", "D");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/world/Explosion", "explosionY", "D");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/world/Explosion", "explosionZ", "D");
					mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventExplosion", "<init>", "(Lnet/minecraft/entity/Entity;DDD)V", false);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false);
					break;

				//Obfuscated Explosion patching.
				case "agw":
					mv = cw.visitMethod(ACC_PUBLIC, "a", "()V", null, null);
					mv.visitCode();

					l79 = new Label();
					mv.visitLineNumber(163, l79);
					mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventExplosion");
					mv.visitInsn(DUP);
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "agw", "exploder", "Lsa;");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "agw", "explosionX", "D");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "agw", "explosionY", "D");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "agw", "explosionZ", "D");
					mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventExplosion", "<init>", "(Lsa;DDD)V", false);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false);
					break;

				//Adding the call to BiomeRegistry.registerAllBiomes.
				case "net.minecraft.world.gen.layer.GenLayerBiome":
					mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "initializeAllBiomeGenerators", "(JLnet/minecraft/world/WorldType;)[Lnet/minecraft/world/gen/layer/GenLayer;", null, null);
					mv.visitCode();

					Label l52 = new Label();
					mv.visitLabel(l52);
					mv.visitLineNumber(107, l52);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/gen/layers/GenLayerRegistry", "iterateLayers", "()[Lco/uk/niadel/mpi/gen/layers/IGenLayer;", false);
					mv.visitInsn(POP);
					break;

				case "net.minecraft.entity.EntityLiving":
					mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Lnet/minecraft/world/World;)V", null, null);

					l17 = new Label();
					mv.visitLabel(l17);
					mv.visitLineNumber(94, l17);
					mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventEntityLivingInit");
					mv.visitInsn(DUP);
					mv.visitVarInsn(ALOAD, 0);
					mv.visitVarInsn(ALOAD, 1);
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "tasks", "Lnet/minecraft/entity/ai/EntityAITasks;");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "targetTasks", "Lnet/minecraft/entity/ai/EntityAITasks;");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "lookHelper", "Lnet/minecraft/entity/ai/EntityLookHelper;");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "moveHelper", "Lnet/minecraft/entity/ai/EntityMoveHelper;");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "jumpHelper", "Lnet/minecraft/entity/ai/EntityJumpHelper;");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "bodyHelper", "Lnet/minecraft/entity/EntityBodyHelper;");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "navigator", "Lnet/minecraft/pathfinding/PathNavigate;");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "senses", "Lnet/minecraft/entity/ai/EntitySenses;");
					mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventEntityLivingInit", "<init>", "(Lnet/minecraft/entity/EntityLiving;Lnet/minecraft/world/World;Lnet/minecraft/entity/ai/EntityAITasks;Lnet/minecraft/entity/ai/EntityAITasks;Lnet/minecraft/entity/ai/EntityLookHelper;Lnet/minecraft/entity/ai/EntityMoveHelper;Lnet/minecraft/entity/ai/EntityJumpHelper;Lnet/minecraft/entity/EntityBodyHelper;Lnet/minecraft/pathfinding/PathNavigate;Lnet/minecraft/entity/ai/EntitySenses;)V", false);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false);
					break;

				//Add call to Player events.
				case "net.minecraft.entity.player.EntityPlayer":
					mv = cw.visitMethod(ACC_PUBLIC, "stopUsingItem", "()V", null, null);
					mv.visitCode();

					l3 = new Label();
					mv.visitLabel(l3);
					mv.visitLineNumber(238, l3);
					mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/items/EventItemStoppedUse");
					mv.visitInsn(DUP);
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/player/EntityPlayer", "itemInUse", "Lnet/minecraft/item/ItemStack;");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/player/EntityPlayer", "worldObj", "Lnet/minecraft/world/World;");
					mv.visitVarInsn(ALOAD, 0);
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/player/EntityPlayer", "itemInUseCount", "I");
					mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/items/EventItemStoppedUse", "<init>", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;I)V", false);
					mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false);
					break;

				case "net.minecraft.entity.EntityLivingBase":
					methodNode = new MethodNode(ACC_PUBLIC, "onItemPickup", "(Lnet/minecraft/entity/Entity;I)V", null, null);

					ln0 = new LabelNode();
					ln0.accept(methodNode);

					methodNode.instructions.add(ln0);
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/mpi/events/entity/EventEntityPickupItem"));
					methodNode.instructions.add(new InsnNode(DUP));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/EntityLivingBase", "p_71001_1_", "Lnet/minecraft/entity/Entity"));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/EntityLivingBase", "p_71001_2_", "I"));
					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventEntityPickupItem", "<init>", "(Lnet/minecraft/entity/Entity;I)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false));
					break;

				case "net.minecraft.entity.Entity":
					methodNode = new MethodNode(ACC_PUBLIC, "updateRidden", "()V", null, null);

					ln0 = new LabelNode();
					ln0.accept(methodNode);

					methodNode.instructions.add(ln0);
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/mpi/events/entity/EventUpdateRidden"));
					methodNode.instructions.add(new InsnNode(DUP));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventUpdateRidden", "<init>", "(Lnet/minecraft/entity/Entity;)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false));
					break;

				case "sa":
					methodNode = new MethodNode(ACC_PUBLIC, "ab", "()V", null, null);

					ln0 = new LabelNode();
					ln0.accept(methodNode);

					methodNode.instructions.add(ln0);
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/mpi/events/entity/EventUpdateRidden"));
					methodNode.instructions.add(new InsnNode(DUP));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventUpdateRidden", "<init>", "(Lsa;)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false));
					break;

				//Ensure no-one tampers with MCData's getNAPIRegisterClass method via ASM or... (shudders) base edits.
				case "co.uk.niadel.mpi.util.MCData":
					methodNode = new MethodNode(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "getNAPIRegisterClass", "()Z", null, null);

					//Clear the instructions in the method, to get rid of dodgy tampering.
					insnIterator = methodNode.instructions.iterator();

					while (insnIterator.hasNext())
					{
						methodNode.instructions.remove((AbstractInsnNode) insnIterator.next());
					}

					//Add the method instructions back in the way they should be.
					ln0 = new LabelNode();
					ln0.accept(methodNode);
					methodNode.instructions.add(ln0);
					methodNode.instructions.add(new LineNumberNode(108, ln0));
					methodNode.instructions.add(new LdcInsnNode("co.uk.niadel.mpi.modhandler.ModRegister"));
					methodNode.instructions.add(new InsnNode(ARETURN));
					break;

				//Add call to fire EventCrash.
				case "net.minecraft.util.ReportedException":
					methodNode = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null);

					ln1 = new LabelNode();
					ln1.accept(methodNode);
					methodNode.instructions.add(ln1);
					methodNode.instructions.add(new LineNumberNode(14, ln1));
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/mpi/events/client/EventCrash"));
					methodNode.instructions.add(new InsnNode(DUP));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 1));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/mpi/events/client/EventCrash", "<init>", "(Lnet/minecraft/crash/CrashReport;Lnet/minecraft/util/ReportedException;)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V", false));
					methodNode.instructions.add(new InsnNode(RETURN));
					break;

				case "net.minecraft.world.WorldProvider":
					methodNode = new MethodNode(ACC_PUBLIC + ACC_STATIC, "getProviderForDimension", "(I)Lnet/minecraft/world/WorldProvider", null, null);

					Iterator<AbstractInsnNode> insnNodeIterator = methodNode.instructions.iterator();
					//Clear the old instructions.
					while (insnNodeIterator.hasNext())
					{
						methodNode.instructions.remove(insnNodeIterator.next());
					}

					//Now add the new instructions.
					//Adding:
				/*
				return WorldProviderRegistry.getProvider(p_76570_0_);
				 */
					ln0 = new LabelNode();
					ln0.accept(methodNode);
					methodNode.instructions.add(new LineNumberNode(204, ln0));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "co/uk/niadel/mpi/dimensions/ProviderRegistry", "getProvider", "(I)Lnet/minecraft/world/WorldProvider;", false));
					ln1 = new LabelNode();
					ln1.accept(methodNode);
					methodNode.instructions.add(new InsnNode(ARETURN));

					break;

				//Add patch to allow for more blocks to be added. LOTS more blocks to be added.
				case "net.minecraft.util.ObjectIntIdentityMap":
					//If the game is FML, FML changes the block registry to be that of it's own internal type, which most likely has
					//This sort of thing already added. In that case, this ASM is useless as ObjectIntIdentityMap is not used,
					//or if it is, FML more than likely has that covered.
					if (!MCData.isForge)
					{
						methodNode = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null);

						Iterator<AbstractInsnNode> insnNodeIterator1 = methodNode.instructions.iterator();

						while (insnNodeIterator1.hasNext())
						{
							methodNode.instructions.remove(insnNodeIterator1.next());
						}

						ln0 = new LabelNode();
						ln0.accept(methodNode);
						methodNode.instructions.add(new LineNumberNode(10, ln0));
						methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
						methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false));
						ln1 = new LabelNode();
						ln1.accept(methodNode);
						methodNode.instructions.add(new LineNumberNode(12, ln1));
						methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
						methodNode.instructions.add(new TypeInsnNode(NEW, "java/util/IdentityHashMap"));
						methodNode.instructions.add(new InsnNode(DUP));
						methodNode.instructions.add(new VarInsnNode(SIPUSH, Integer.MAX_VALUE));
						methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "java/util/IdentityHashMap", "<init>", "(I)V", false));
						methodNode.instructions.add(new FieldInsnNode(PUTFIELD, "net/minecraft/util/ObjectIntIdentityMap.field_148749_a", "Ljava/util/IdentityHashMap;", null));
						ln2 = new LabelNode();
						ln2.accept(methodNode);
						methodNode.instructions.add(new LineNumberNode(13, ln2));
						methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
						methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "com/google/common/collect/Lists", "newArrayList", "()Ljava/util/ArrayList;", false));
						methodNode.instructions.add(new InsnNode(RETURN));
					}
					break;

				default:
					//Not any of the correct classes, return the passed bytes.
					return cw.toByteArray();
			}

			NAPILogHelper.log("Transformed class " + className + "!");

			return cw.toByteArray();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return new byte[0];
	}
}
