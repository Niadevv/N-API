package co.uk.niadel.mpi.forgewrapper;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public class ASMPatcher implements IClassTransformer, Opcodes
{	
	public byte[] transform(String currClassName, String newClassName, byte[] bytes)
	{
		byte[] patchedBytes = null;
		
		patchedBytes = patchClass(currClassName, newClassName, bytes);
		
		if (patchedBytes != null)
		{
			return patchedBytes;
		}
		else
		{
			return bytes;
		}
	}
	
	/**
	 * Does the actual class patching. GOD BLESS YOU BYTECODE OUTLINE PLUGIN!
	 * 
	 * This only patches things that can't be Access Transformered or isn't already doable with Forge.
	 * @param currClassName
	 * @param newClassName
	 * @param bytes
	 * @return
	 */
	public byte[] patchClass(String currClassName, String newClassName, byte[] bytes)
	{
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		ClassWriter cw = new ClassWriter(cr, ASM4);
		FieldVisitor fv;
		MethodVisitor mv;
		
		//Labels, because switch control structures are awkward with local variables.
		Label l261;
		Label l10;
		Label l12;
		Label l6;
		Label l9;
		Label l15;
		Label l79;
		cr.accept(cn, 0);
		
		byte[] patchedBytes = null;
		
		switch (currClassName)
		{
			//Adding instance field so that the ItemBaseModArmour works
			case "net.minecraft.item.Item":
				cw.newField("net/minecraft/item/Item", "instance", "Lnet/minecraft/item/Item;");
				fv = cw.visitField(ACC_PUBLIC, "instance", "Lnet/minecraft/item/Item", null, null);
				fv.visitEnd();
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
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/api/client/GUIHUDRegistry", "callAllRenderers", "(Lnet/minecraft/client/gui/GuiIngame;)V");
				break;
			
			//Obfuscated RenderItem patching
			case "bnq":
				mv = cw.visitMethod(ACC_PUBLIC, "a", "(FZII)V", null, null);
				mv.visitCode();
				l261 = new Label();
				mv.visitLabel(l261);
				mv.visitLineNumber(498, l261);
				mv.visitVarInsn(ALOAD, 0);
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/api/client/GUIHUDRegistry", "callAllRenderers", "(Lbah;)V");
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
			
			//Add calls to events.
			case "net.minecraft.world.World":
				mv = cw.visitMethod(ACC_PUBLIC, "spawnEntityInWorld", "(Lnet/minecraft/entity/Entity;)Z", null, null);
				mv.visitCode();
				
				//Add the event local variable for EventEntitySpawned.
				l6 = new Label();
				mv.visitLineNumber(1409, l6);
				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventEntitySpawned");
				mv.visitInsn(DUP);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventEntitySpawned", "<init>", "(Lnet/minecraft/entity/Entity;)V");
				mv.visitVarInsn(ASTORE, 5);
				
				l9 = new Label();
				mv.visitLineNumber(1420, l9);
				mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"co/uk/niadel/mpi/events/EventCancellable"}, 0, null);
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
				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				mv.visitVarInsn(ALOAD, 5);
				mv.visitMethodInsn(INVOKEVIRTUAL, "co/uk/niadel/mpi/events/EventCancellable", "isCancelled", "()Z");

			case "afn":
				mv = cw.visitMethod(ACC_PUBLIC, "d", "(Lqn;)Z", null, null);
				mv.visitCode();
				
				//Add the event local variable for EventEntitySpawned.
				l6 = new Label();
				mv.visitLineNumber(1409, l6);
				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				mv.visitTypeInsn(NEW, "co/uk/niadel/mpi/events/entity/EventEntitySpawned");
				mv.visitInsn(DUP);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitMethodInsn(INVOKESPECIAL, "co/uk/niadel/mpi/events/entity/EventEntitySpawned", "<init>", "(Lqn;)V");
				mv.visitVarInsn(ASTORE, 5);
				
				l9 = new Label();
				mv.visitLineNumber(1420, l9);
				mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"co/uk/niadel/mpi/events/EventCancellable"}, 0, null);
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
				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
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
		}
		
		
		return bytes;
	}
}