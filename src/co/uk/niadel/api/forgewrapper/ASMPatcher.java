package co.uk.niadel.api.forgewrapper;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public class ASMPatcher implements IClassTransformer, Opcodes
{	
	public byte[] transform(String currClassName, String newClassName, byte[] bytes)
	{
		switch (currClassName)
		{
			//Classes are deobfuscated by Forge at runtime. This should (if CPW got it right) work.
			case "net.minecraft.item.Item":
				patchClass(currClassName, newClassName, bytes);
		}
	}
	
	public byte[] patchClass(String currClassName, String newClassName, byte[] bytes)
	{
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		ClassWriter cw = new ClassWriter(cr, ASM4);
		cr.accept(cn, 0);
		
		switch (currClassName)
		{
			case "net.minecraft.item.Item":
				cw.newField("abn", "instance", "public Labn;");
				FieldVisitor fv = cw.visitField(ACC_PUBLIC, "instance", "public Labn", null, null);
				fv.visitEnd();
				
		}
	}
}
