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
		cr.accept(cn, 0);
		
		byte[] patchedBytes = null;
		
		switch (currClassName)
		{
			//Adding instance field so that the ItemBaseModArmour works
			case "net.minecraft.item.Item":
				cw.newField("net/minecraft/item/Item", "instance", "public Lnet/minecraft/item/Item;");
				fv = cw.visitField(ACC_PUBLIC, "instance", "public Lnet/minecraft/item/Item", null, null);
				fv.visitEnd();
				break;
				
			//Obfuscated Item patching
			case "abn":
				cw.newField("abn", "instance", "public Labn;");
				fv = cw.visitField(ACC_PUBLIC, "instance", "public Labn", null, null);
				fv.visitEnd();
				break;
			
			case "net.minecraft.client.renderer.entity.RenderItem":
				MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "renderGameOverlay", "(FZII)V", null, null);
				mv.visitCode();
				Label l261 = new Label();
				mv.visitLabel(l261);
				mv.visitLineNumber(498, l261);
				mv.visitVarInsn(ALOAD, 0);
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/api/client/GUIHUDRegistry", "callAllRenderers", "(Lnet/minecraft/client/gui/GuiIngame;)V");
				break;
		}
		
		return bytes;
	}
}
