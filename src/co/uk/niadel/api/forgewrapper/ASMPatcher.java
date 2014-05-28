package co.uk.niadel.api.forgewrapper;

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
	
	public byte[] patchClass(String currClassName, String newClassName, byte[] bytes)
	{
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		ClassWriter cw = new ClassWriter(cr, ASM4);
		cr.accept(cn, 0);
		
		byte[] patchedBytes = null;
		
		switch (currClassName)
		{
			case "net.minecraft.item.Item":
				cw.newField("abn", "instance", "public Labn;");
				FieldVisitor fv = cw.visitField(ACC_PUBLIC, "instance", "public Labn", null, null);
				fv.visitEnd();
			
			case "net.minecraft.client.renderer.entity.RenderItem":
				MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "renderGameOverlay", "(FZII)V", null, null);
				mv.visitCode();
				Label l261 = new Label();
				mv.visitLabel(l261);
				mv.visitLineNumber(498, l261);
				mv.visitVarInsn(ALOAD, 0);
				mv.visitMethodInsn(INVOKESTATIC, "co/uk/niadel/api/client/GUIHUDRegistry", "callAllRenderers", "(Lnet/minecraft/client/gui/GuiIngame;)V", false);

		}
		
		return bytes;
	}
}
