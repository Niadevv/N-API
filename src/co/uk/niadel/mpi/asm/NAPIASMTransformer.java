package co.uk.niadel.mpi.asm;

import java.util.ListIterator;
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
 * This class adds Event calls instead of having direct base edits.
 * @author Niadel
 *
 */
public class NAPIASMTransformer implements IASMTransformer, Opcodes
{
	@Override
	public String[] requestTransformedClasses()
	{
		if (MCData.isGameObfed())
		{
			return NAPIData.ASMD_CLASSES_OBFD;
		}
		else
		{
			return NAPIData.ASMD_CLASSES;
		}
	}

	@Override
	public byte[] manipulateBytecodes(String className, byte[] bytes)
	{
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		ClassWriter cw = new ClassWriter(cr, ASM4);
		FieldVisitor fv;
		MethodVisitor mv;
		MethodNode methodNode;
		
		//Labels, because switch control structures are awkward with local variables.
		LabelNode l0;
		LabelNode l1;
		Label l261;
		Label l10;
		Label l12;
		Label l6;
		Label l9;
		Label l15;
		Label l79;
		Label l17;
		LabelNode l2;
		ListIterator insnIterator;
		cr.accept(cn, 0);
		
		byte[] patchedBytes = null;
		
		switch (className)
		{
			//Adding instance field so that the ItemBaseModArmour works, adding event calls.
			case "net.minecraft.item.Item":
				cn.fields.add(new FieldNode(ACC_PUBLIC, "instance", null, null, null));
				
				methodNode = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null);
				
				methodNode.instructions.add(new LineNumberNode(55, (LabelNode) methodNode.instructions.get(5)));
				methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
				methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
				methodNode.instructions.add(new FieldInsnNode(PUTFIELD, "net/minecraft/item/Item", "instance", "Lnet/minecraft/item/Item;"));
				
				//Add onEaten event call.
				methodNode = new MethodNode(ACC_PUBLIC, "onEaten", "(Lnet/minecraft/item/ItemStack)V", null, null);
				insnIterator = methodNode.instructions.iterator();
				
				while (insnIterator.hasNext())
				{
					methodNode.instructions.remove((AbstractInsnNode) insnIterator.next());
				}
				
				l0 = new LabelNode();
				l0.accept(methodNode);
				methodNode.instructions.add(l0);
				methodNode.instructions.add(new LineNumberNode(478, l0));
				methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/mpi/events/EventItemEaten"));
				methodNode.instructions.add(new InsnNode(DUP));
				methodNode.instructions.add(new VarInsnNode(ALOAD, 1));
				methodNode.instructions.add(new VarInsnNode(ALOAD, 2));
				methodNode.instructions.add(new VarInsnNode(ALOAD, 3));
				methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/mpi/events/EventItemEaten", "<init>", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V"));
				methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/events/EventsList", "fireEvent", "(Ljava/lang/Object;)V"));
				l1 = new LabelNode();
				l1.accept(methodNode);
				methodNode.instructions.add(l1);
				methodNode.instructions.add(new LineNumberNode(479, l1));
				methodNode.instructions.add(new VarInsnNode(ALOAD, 1));
				methodNode.instructions.add(new InsnNode(ARETURN));
				l2 = new LabelNode();
				l2.accept(methodNode);
				methodNode.instructions.add(l2);
				break;
				
			//Obfuscated Item patching
			case "abn":
				cw.newField("abn", "instance", "Labn;");
				fv = cw.visitField(ACC_PUBLIC, "instance", "Labn", null, null);
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
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/client/GUIHUDRegistry", "callAllRenderers", "(Lnet/minecraft/client/gui/GuiIngame;)V");
				break;
			
			//Obfuscated RenderItem patching
			case "bnq":
				mv = cw.visitMethod(ACC_PUBLIC, "a", "(FZII)V", null, null);
				mv.visitCode();
				l261 = new Label();
				mv.visitLabel(l261);
				mv.visitLineNumber(498, l261);
				mv.visitVarInsn(ALOAD, 0);
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/client/GUIHUDRegistry", "callAllRenderers", "(Lbah;)V");
				break;
			
			//Add call to VillagePieceRegistry.addAllPieces in getStructureVillageWeightedPieceList
			case "net.minecraft.world.gen.structure.StructureVillagePieces":
				mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getStructureVillageWeightedPieceList", "(Ljava/util/Random;I)Ljava/util/List;", null, null);
				mv.visitCode();
				l10 = new Label();
				mv.visitLabel(l10);
				mv.visitLineNumber(54, l10);
				mv.visitVarInsn(ALOAD, 2);
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/gen/structures/VillagePieceRegistry", "addAllPieces", "(Ljava/util/ArrayList;)V");
			
			//Obfuscated StructureVillagePieces patching
			case "aua":
				mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "a", "(Ljava/util/Random;I)Ljava/util/List;", null, null);
				mv.visitCode();
				l10 = new Label();
				mv.visitLabel(l10);
				mv.visitLineNumber(54, l10);
				mv.visitVarInsn(ALOAD, 2);
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/gen/structures/VillagePieceRegistry", "addAllPieces", "(Ljava/util/ArrayList;)V");
			
			//Add calls to events. Oh, and fix for Forge renaming isClient.
			case "net.minecraft.world.World":
				cw.newField("net/minecraft/world/World", "isClient", "Z");
				fv = cw.visitField(ACC_PUBLIC, "isClient", "Z", null, null);
				fv.visitEnd();
				
				mv = cw.visitMethod(ACC_PUBLIC, "spawnEntityInWorld", "(Lnet/minecraft/entity/Entity;)Z", null, null);
				mv.visitCode();
				
				//Add the event local variable for EventEntitySpawned.
				l6 = new Label();
				mv.visitLineNumber(1409, l6);
				mv.visitFrame(F_SAME, 0, null, 0, null);
				mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventEntitySpawned");
				mv.visitInsn(DUP);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventEntitySpawned", "<init>", "(Lnet/minecraft/entity/Entity;)V");
				mv.visitVarInsn(ASTORE, 5);
				
				l9 = new Label();
				mv.visitLineNumber(1420, l9);
				mv.visitFrame(F_APPEND,1, new Object[] {"co/uk/niadel/mpi/events/EventCancellable"}, 0, null);
				mv.visitVarInsn(ALOAD, 5);
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventsList", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V");
				
				
				l12 = new Label();
				mv.visitLabel(l12);
				mv.visitLineNumber(1415, l12);
				mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventPlayerSpawned");
				mv.visitInsn(DUP);
				mv.visitVarInsn(ALOAD, 6);
				mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventPlayerSpawned", "<init>", "(Lnet/minecraft/entity/player/EntityPlayer;)V");
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventsList", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V");
				
				l15 = new Label();
				mv.visitLineNumber(1423, l15);
				mv.visitFrame(F_SAME, 0, null, 0, null);
				mv.visitVarInsn(ALOAD, 5);
				mv.visitMethodInsn(INVOKEVIRTUAL, "co/uk/niadel/mpi/events/EventCancellable", "isCancelled", "()Z");

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
				mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventEntitySpawned", "<init>", "(Lqn;)V");
				mv.visitVarInsn(ASTORE, 5);
				
				l9 = new Label();
				mv.visitLineNumber(1420, l9);
				mv.visitFrame(F_APPEND,1, new Object[] {"co/uk/niadel/mpi/events/EventCancellable"}, 0, null);
				mv.visitVarInsn(ALOAD, 5);
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventsList", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V");
				
				
				l12 = new Label();
				mv.visitLabel(l12);
				mv.visitLineNumber(1415, l12);
				mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventPlayerSpawned");
				mv.visitInsn(DUP);
				mv.visitVarInsn(ALOAD, 6);
				mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventPlayerSpawned", "<init>", "(Lxl;)V");
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventsList", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V");
				
				l15 = new Label();
				mv.visitLineNumber(1423, l15);
				mv.visitFrame(F_SAME, 0, null, 0, null);
				mv.visitVarInsn(ALOAD, 5);
				mv.visitMethodInsn(INVOKEVIRTUAL, "co/uk/niadel/mpi/events/EventCancellable", "isCancelled", "()Z");
			
			//Add EventsList call to doExplosionA
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
				mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventExplosion", "<init>", "(Lnet/minecraft/entity/Entity;DDD)V");
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventsList", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V");
				
			//Obfuscated Explosion patching.
			case "afi":
				mv = cw.visitMethod(ACC_PUBLIC, "a", "()V", null, null);
				mv.visitCode();
				
				l79 = new Label();
				mv.visitLineNumber(163, l79);
				mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventExplosion");
				mv.visitInsn(DUP);
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, "afi", "exploder", "Lqn;");
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, "afi", "explosionX", "D");
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, "afi", "explosionY", "D");
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, "afi", "explosionZ", "D");
				mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventExplosion", "<init>", "(Lqn;DDD)V");
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventsList", "fireEvent", "(Lco/uk/niadel/mpi/events/IEvent;)V");
			
			//Adding the call to BiomeRegistry.registerAllBiomes.
			case "net.minecraft.world.gen.layer.GenLayerBiome":
				mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "initializeAllBiomeGenerators", "(JLnet/minecraft/world/WorldType;)[Lnet/minecraft/world/gen/layer/GenLayer;", null, null);
				mv.visitCode();
				
				Label l52 = new Label();
				mv.visitLabel(l52);
				mv.visitLineNumber(107, l52);
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/gen/layers/GenLayerRegistry", "iterateLayers", "()[Lco/uk/niadel/mpi/gen/layers/IGenLayer;");
				mv.visitInsn(POP);
			
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
				mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventEntityLivingInit", "<init>", "(Lnet/minecraft/entity/EntityLiving;Lnet/minecraft/world/World;Lnet/minecraft/entity/ai/EntityAITasks;Lnet/minecraft/entity/ai/EntityAITasks;Lnet/minecraft/entity/ai/EntityLookHelper;Lnet/minecraft/entity/ai/EntityMoveHelper;Lnet/minecraft/entity/ai/EntityJumpHelper;Lnet/minecraft/entity/EntityBodyHelper;Lnet/minecraft/pathfinding/PathNavigate;Lnet/minecraft/entity/ai/EntitySenses;)V");
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventsList", "fireEvent", "(Ljava/lang/Object;)V");

			//Add call to Player events.
			case "net.minecraft.entity.player.EntityPlayer":
				mv = cw.visitMethod(ACC_PUBLIC, "stopUsingItem", "()V", null, null);
				mv.visitCode();
				
				Label l3 = new Label();
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
				mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/items/EventItemStoppedUse", "<init>", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;I)V");
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/mpi/events/EventsList", "fireEvent", "(Ljava/lang/Object;)V");
			
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
				l0 = new LabelNode();
				l0.accept(methodNode);
				methodNode.instructions.add(l0);
				methodNode.instructions.add(new LineNumberNode(108, l0));
				methodNode.instructions.add(new LdcInsnNode("co.uk.niadel.mpi.modhandler.ModRegister"));
				methodNode.instructions.add(new InsnNode(ARETURN));
			
			//Add call to fire EventCrash.
			case "net.minecraft.util.ReportedException":
				methodNode = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null);
				
				l1 = new LabelNode();
				l1.accept(methodNode);
				methodNode.instructions.add(l1);
				methodNode.instructions.add(new LineNumberNode(14, l1));
				methodNode.instructions.add(new TypeInsnNode(NEW, "co/uk/niadel/mpi/events/client/EventCrash"));
				methodNode.instructions.add(new InsnNode(DUP));
				methodNode.instructions.add(new VarInsnNode(ALOAD, 1));
				methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
				methodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "co/uk/niadel/mpi/events/client/EventCrash", "<init>", "(Lnet/minecraft/crash/CrashReport;Lnet/minecraft/util/ReportedException;)V"));
				methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, "co/uk/niadel/mpi/events/EventsList", "fireEvent", "(Ljava/lang/Object;)V"));
				methodNode.instructions.add(new InsnNode(RETURN));
				
			default:
				//Not any of the correct classes, return null.
				return null;
		}
		
		return bytes;
	}
}
