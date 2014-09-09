package co.uk.niadel.napi.asm;

import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;

import co.uk.niadel.napi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import co.uk.niadel.napi.util.MCData;

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
			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(className);
			ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
			FieldVisitor fieldVisitor;
			MethodVisitor methodVisitor;
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
			classReader.accept(classNode, 0);

			switch (className)
			{
				//Adding INSTANCE field so that the ItemBaseModArmour works, adding event calls.
				case "net.minecraft.item.Item":
					classNode.fields.add(new FieldNode(ACC_PUBLIC, "INSTANCE", null, null, null));

					methodNode = constructMethodNode(ACC_PUBLIC, "<init>", "()V", null, null, classNode);

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

					finishMethodNodeEdit(methodNode, classNode);

					//Add onEaten event call.
					methodNode = constructMethodNode(ACC_PUBLIC, "onEaten", "(Lnet/minecraft/item/ItemStack)V", null, null, classNode);

					deleteBytecodesOfMethod(methodNode);

					ln0 = new LabelNode();
					ln0.accept(methodNode);
					methodNode.instructions.add(ln0);
					methodNode.instructions.add(new LineNumberNode(478, ln0));
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/napi/events/EventItemEaten"));
					methodNode.instructions.add(new InsnNode(DUP));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 1));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 2));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 3));
					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/napi/events/EventItemEaten", "<init>", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false));
					ln1 = new LabelNode();
					ln1.accept(methodNode);
					methodNode.instructions.add(ln1);
					methodNode.instructions.add(new LineNumberNode(479, ln1));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 1));
					methodNode.instructions.add(new InsnNode(ARETURN));
					ln2 = new LabelNode();
					ln2.accept(methodNode);
					methodNode.instructions.add(ln2);

					finishMethodNodeEdit(methodNode, classNode);

					methodNode = constructMethodNode(ACC_PUBLIC, "onItemUse", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityPlayer;Lnet/minecraft/world/World;IIIIFFF)Z", null, null, classNode);

					deleteBytecodesOfMethod(methodNode);

					ln0 = new LabelNode();
					ln0.accept(methodNode);
					methodNode.instructions.add(new LineNumberNode(458, ln0));
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/napi/events/items/EventItemUse"));
					methodNode.instructions.add(new InsnNode(DUP));

					//Lazy loading of local variables.
					for (int i = 1; i == 10; i++)
					{
						methodNode.instructions.add(new VarInsnNode(ALOAD, i));
					}

					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/napi/events/items/EventItemUse", "<init>", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;IIIIFFF)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false));
					break;

				//Obfuscated Item patching
				case "abn":
					classWriter.newField("abn", "INSTANCE", "Labn;");
					fieldVisitor = classWriter.visitField(ACC_PUBLIC, "INSTANCE", "Labn", null, null);
					fieldVisitor.visitEnd();
					break;

				//Adding call to GUIHUDRegistry.callAllRenders in RenderItem.renderGameOverlay.
				case "net.minecraft.client.renderer.entity.RenderItem":
					methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "renderGameOverlay", "(FZII)V", null, null);
					methodVisitor.visitCode();
					l261 = new Label();
					methodVisitor.visitLabel(l261);
					methodVisitor.visitLineNumber(498, l261);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/client/GUIHUDRegistry", "callAllRenderers", "(Lnet/minecraft/client/gui/GuiIngame;)V", false);
					break;

				//Obfuscated RenderItem patching
				case "bnq":
					methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "a", "(FZII)V", null, null);
					methodVisitor.visitCode();
					l261 = new Label();
					methodVisitor.visitLabel(l261);
					methodVisitor.visitLineNumber(498, l261);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/client/GUIHUDRegistry", "callAllRenderers", "(Lbah;)V", false);
					break;

				//Add call to VillagePieceRegistry.addAllPieces in getStructureVillageWeightedPieceList
				case "net.minecraft.world.gen.structure.StructureVillagePieces":
					methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "getStructureVillageWeightedPieceList", "(Ljava/util/Random;I)Ljava/util/List;", null, null);
					methodVisitor.visitCode();
					l10 = new Label();
					methodVisitor.visitLabel(l10);
					methodVisitor.visitLineNumber(54, l10);
					methodVisitor.visitVarInsn(ALOAD, 2);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/gen/structures/VillagePieceRegistry", "addAllPieces", "(Ljava/util/ArrayList;)V", false);
					break;

				//Obfuscated StructureVillagePieces patching
				case "avp":
					methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "a", "(Ljava/util/Random;I)Ljava/util/List;", null, null);
					methodVisitor.visitCode();
					l10 = new Label();
					methodVisitor.visitLabel(l10);
					methodVisitor.visitLineNumber(54, l10);
					methodVisitor.visitVarInsn(ALOAD, 2);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/gen/structures/VillagePieceRegistry", "addAllPieces", "(Ljava/util/ArrayList;)V", false);
					break;

				//Add calls to events. Oh, and fix for Forge renaming isClient.
				case "net.minecraft.world.World":
					if (MCData.isForge)
					{
						classNode.fields.add(new FieldNode(ACC_PUBLIC, "isClient", null, null, null));

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

					methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "spawnEntityInWorld", "(Lnet/minecraft/entity/Entity;)Z", null, null);
					methodVisitor.visitCode();

					//Add the event local variable for EventEntitySpawned.
					l6 = new Label();
					methodVisitor.visitLineNumber(1409, l6);
					methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
					methodVisitor.visitTypeInsn(NEW, "co/uk/niadel/napi/events/entity/EventEntitySpawned");
					methodVisitor.visitInsn(DUP);
					methodVisitor.visitVarInsn(ALOAD, 1);
					methodVisitor.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/napi/events/entity/EventEntitySpawned", "<init>", "(Lnet/minecraft/entity/Entity;)V", false);
					methodVisitor.visitVarInsn(ASTORE, 5);

					l9 = new Label();
					methodVisitor.visitLineNumber(1420, l9);
					methodVisitor.visitFrame(F_APPEND, 1, new Object[]{"co/uk/niadel/napi/events/EventCancellable"}, 0, null);
					methodVisitor.visitVarInsn(ALOAD, 5);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false);


					l12 = new Label();
					methodVisitor.visitLabel(l12);
					methodVisitor.visitLineNumber(1415, l12);
					methodVisitor.visitTypeInsn(NEW, "co/uk/niadel/napi/events/entity/EventPlayerSpawned");
					methodVisitor.visitInsn(DUP);
					methodVisitor.visitVarInsn(ALOAD, 6);
					methodVisitor.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/napi/events/entity/EventPlayerSpawned", "<init>", "(Lnet/minecraft/entity/player/EntityPlayer;)V", false);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false);

					l15 = new Label();
					methodVisitor.visitLineNumber(1423, l15);
					methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
					methodVisitor.visitVarInsn(ALOAD, 5);
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "co/uk/niadel/napi/events/EventCancellable", "isCancelled", "()Z", false);
					break;

				//Obfuscated World patching.
				case "afn":
					methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "d", "(Lqn;)Z", null, null);
					methodVisitor.visitCode();

					//Add the event local variable for EventEntitySpawned.
					l6 = new Label();
					methodVisitor.visitLineNumber(1409, l6);
					methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
					methodVisitor.visitTypeInsn(NEW, "co/uk/niadel/napi/events/entity/EventEntitySpawned");
					methodVisitor.visitInsn(DUP);
					methodVisitor.visitVarInsn(ALOAD, 1);
					methodVisitor.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/napi/events/entity/EventEntitySpawned", "<init>", "(Lqn;)V", false);
					methodVisitor.visitVarInsn(ASTORE, 5);

					l9 = new Label();
					methodVisitor.visitLineNumber(1420, l9);
					methodVisitor.visitFrame(F_APPEND, 1, new Object[]{"co/uk/niadel/napi/events/EventCancellable"}, 0, null);
					methodVisitor.visitVarInsn(ALOAD, 5);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false);


					l12 = new Label();
					methodVisitor.visitLabel(l12);
					methodVisitor.visitLineNumber(1415, l12);
					methodVisitor.visitTypeInsn(NEW, "co/uk/niadel/napi/events/entity/EventPlayerSpawned");
					methodVisitor.visitInsn(DUP);
					methodVisitor.visitVarInsn(ALOAD, 6);
					methodVisitor.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/napi/events/entity/EventPlayerSpawned", "<init>", "(Lxl;)V", false);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false);

					l15 = new Label();
					methodVisitor.visitLineNumber(1423, l15);
					methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
					methodVisitor.visitVarInsn(ALOAD, 5);
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "co/uk/niadel/napi/events/EventCancellable", "isCancelled", "()Z", false);
					break;

				//Add EventFactory call to doExplosionA
				case "net.minecraft.util.Explosion":
					methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "doExplosionA", "()V", null, null);
					methodVisitor.visitCode();

					l79 = new Label();
					methodVisitor.visitLineNumber(163, l79);
					methodVisitor.visitTypeInsn(NEW, "co/uk/niadel/napi/events/entity/EventExplosion");
					methodVisitor.visitInsn(DUP);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/world/Explosion", "exploder", "Lnet/minecraft/entity/Entity;");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/world/Explosion", "explosionX", "D");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/world/Explosion", "explosionY", "D");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/world/Explosion", "explosionZ", "D");
					methodVisitor.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/napi/events/entity/EventExplosion", "<init>", "(Lnet/minecraft/entity/Entity;DDD)V", false);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false);
					break;

				//Obfuscated Explosion patching.
				case "agw":
					methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "a", "()V", null, null);
					methodVisitor.visitCode();

					l79 = new Label();
					methodVisitor.visitLineNumber(163, l79);
					methodVisitor.visitTypeInsn(NEW, "co/uk/niadel/napi/events/entity/EventExplosion");
					methodVisitor.visitInsn(DUP);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "agw", "exploder", "Lsa;");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "agw", "explosionX", "D");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "agw", "explosionY", "D");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "agw", "explosionZ", "D");
					methodVisitor.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/napi/events/entity/EventExplosion", "<init>", "(Lsa;DDD)V", false);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false);
					break;

				//Adding the call to BiomeRegistry.registerAllBiomes.
				case "net.minecraft.world.gen.layer.GenLayerBiome":
					methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "initializeAllBiomeGenerators", "(JLnet/minecraft/world/WorldType;)[Lnet/minecraft/world/gen/layer/GenLayer;", null, null);
					methodVisitor.visitCode();

					Label l52 = new Label();
					methodVisitor.visitLabel(l52);
					methodVisitor.visitLineNumber(107, l52);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/gen/layers/GenLayerRegistry", "iterateLayers", "()[Lco/uk/niadel/napi/gen/layers/IGenLayer;", false);
					methodVisitor.visitInsn(POP);
					break;

				case "net.minecraft.entity.EntityLiving":
					methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "(Lnet/minecraft/world/World;)V", null, null);

					l17 = new Label();
					methodVisitor.visitLabel(l17);
					methodVisitor.visitLineNumber(94, l17);
					methodVisitor.visitTypeInsn(NEW, "co/uk/niadel/napi/events/entity/EventEntityLivingInit");
					methodVisitor.visitInsn(DUP);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitVarInsn(ALOAD, 1);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "tasks", "Lnet/minecraft/entity/ai/EntityAITasks;");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "targetTasks", "Lnet/minecraft/entity/ai/EntityAITasks;");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "lookHelper", "Lnet/minecraft/entity/ai/EntityLookHelper;");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "moveHelper", "Lnet/minecraft/entity/ai/EntityMoveHelper;");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "jumpHelper", "Lnet/minecraft/entity/ai/EntityJumpHelper;");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "bodyHelper", "Lnet/minecraft/entity/EntityBodyHelper;");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "navigator", "Lnet/minecraft/pathfinding/PathNavigate;");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/entity/EntityLiving", "senses", "Lnet/minecraft/entity/ai/EntitySenses;");
					methodVisitor.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/napi/events/entity/EventEntityLivingInit", "<init>", "(Lnet/minecraft/entity/EntityLiving;Lnet/minecraft/world/World;Lnet/minecraft/entity/ai/EntityAITasks;Lnet/minecraft/entity/ai/EntityAITasks;Lnet/minecraft/entity/ai/EntityLookHelper;Lnet/minecraft/entity/ai/EntityMoveHelper;Lnet/minecraft/entity/ai/EntityJumpHelper;Lnet/minecraft/entity/EntityBodyHelper;Lnet/minecraft/pathfinding/PathNavigate;Lnet/minecraft/entity/ai/EntitySenses;)V", false);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false);
					break;

				//Add call to Player events.
				case "net.minecraft.entity.player.EntityPlayer":
					methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "stopUsingItem", "()V", null, null);
					methodVisitor.visitCode();

					l3 = new Label();
					methodVisitor.visitLabel(l3);
					methodVisitor.visitLineNumber(238, l3);
					methodVisitor.visitTypeInsn(NEW, "co/uk/niadel/napi/events/items/EventItemStoppedUse");
					methodVisitor.visitInsn(DUP);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/entity/player/EntityPlayer", "itemInUse", "Lnet/minecraft/item/ItemStack;");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/entity/player/EntityPlayer", "worldObj", "Lnet/minecraft/world/World;");
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, "net/minecraft/entity/player/EntityPlayer", "itemInUseCount", "I");
					methodVisitor.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/napi/events/items/EventItemStoppedUse", "<init>", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;I)V", false);
					methodVisitor.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false);
					break;

				case "net.minecraft.entity.EntityLivingBase":
					methodNode = new MethodNode(ACC_PUBLIC, "onItenapickup", "(Lnet/minecraft/entity/Entity;I)V", null, null);

					ln0 = new LabelNode();
					ln0.accept(methodNode);

					methodNode.instructions.add(ln0);
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/napi/events/entity/EventEntityPickupItem"));
					methodNode.instructions.add(new InsnNode(DUP));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/EntityLivingBase", "p_71001_1_", "Lnet/minecraft/entity/Entity"));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/EntityLivingBase", "p_71001_2_", "I"));
					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/napi/events/entity/EventEntityPickupItem", "<init>", "(Lnet/minecraft/entity/Entity;I)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false));
					break;

				case "net.minecraft.entity.Entity":
					methodNode = new MethodNode(ACC_PUBLIC, "updateRidden", "()V", null, null);

					ln0 = new LabelNode();
					ln0.accept(methodNode);

					methodNode.instructions.add(ln0);
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/napi/events/entity/EventUpdateRidden"));
					methodNode.instructions.add(new InsnNode(DUP));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/napi/events/entity/EventUpdateRidden", "<init>", "(Lnet/minecraft/entity/Entity;)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false));
					break;

				case "sa":
					methodNode = new MethodNode(ACC_PUBLIC, "ab", "()V", null, null);

					ln0 = new LabelNode();
					ln0.accept(methodNode);

					methodNode.instructions.add(ln0);
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/napi/events/entity/EventUpdateRidden"));
					methodNode.instructions.add(new InsnNode(DUP));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/napi/events/entity/EventUpdateRidden", "<init>", "(Lsa;)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false));
					break;

				//Ensure no-one tampers with MCData's getNAPIRegisterClass method via ASM or... (shudders) base edits.
				case "co.uk.niadel.napi.util.MCData":
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
					methodNode.instructions.add(new LdcInsnNode("co.uk.niadel.napi.modhandler.NAPIModRegister"));
					methodNode.instructions.add(new InsnNode(ARETURN));
					break;

				//Add call to fire EventCrash.
				case "net.minecraft.util.ReportedException":
					methodNode = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null);

					ln1 = new LabelNode();
					ln1.accept(methodNode);
					methodNode.instructions.add(ln1);
					methodNode.instructions.add(new LineNumberNode(14, ln1));
					methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/napi/events/client/EventCrash"));
					methodNode.instructions.add(new InsnNode(DUP));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 1));
					methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
					methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/napi/events/client/EventCrash", "<init>", "(Lnet/minecraft/crash/CrashReport;Lnet/minecraft/util/ReportedException;)V", false));
					methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/napi/events/EventFactory", "fireEvent", "(Lco/uk/niadel/napi/events/IEvent;)V", false));
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
					methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "co/uk/niadel/napi/dimensions/ProviderRegistry", "getProvider", "(I)Lnet/minecraft/world/WorldProvider;", false));
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

				case "net.minecraft.block.BlockDynamicLiquid":
					methodNode = constructMethodNode(ACC_PRIVATE, "func_149813_h", "(Lnet/minecraft/world/World;IIII)V", null, null, classNode);
					//Instruction 58 = .setBlock call.
					//Add event call after 59
					MethodInsnNode setBlockCall = (MethodInsnNode) methodNode.instructions.get(58);
					LabelNode eventLabel = new LabelNode();
					methodNode.instructions.insert(setBlockCall, eventLabel);
					InsnList eventInstructions = new InsnList();
					eventInstructions.add(new LineNumberNode(188, eventLabel));
					eventInstructions.add(new TypeInsnNode(NEW, "co/uk/niadel/events/blocks/EventBlockWashedAway"));
					eventInstructions.add(new InsnNode(DUP));
					eventInstructions.add(new VarInsnNode(ALOAD, 1));
					eventInstructions.add(new VarInsnNode(ALOAD, 2));
					eventInstructions.add(new VarInsnNode(ALOAD, 3));
					eventInstructions.add(new VarInsnNode(ALOAD, 4));
					eventInstructions.add(new VarInsnNode(ALOAD, 0));
					eventInstructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/napi/EventBlockWashedAway", "<init>", "()V", false));
					eventInstructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/napi/EventFactory", "fireEvent", "()V", false));
					methodNode.instructions.insert(eventLabel, eventInstructions);
					finishMethodNodeEdit(methodNode, classNode);
					break;

				default:
					//Not any of the correct classes, return the passed bytes.
					return classWriter.toByteArray();
			}

			NAPILogHelper.log("Transformed class " + className + "!");

			return classWriter.toByteArray();
		}
		catch (IOException e)
		{
			NAPILogHelper.instance.logError(e);
			e.printStackTrace();
		}

		return new byte[0];
	}
}
