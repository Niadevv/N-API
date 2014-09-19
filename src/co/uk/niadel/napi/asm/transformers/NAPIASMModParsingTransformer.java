package co.uk.niadel.napi.asm.transformers;

import co.uk.niadel.napi.annotations.EnumLoadState;
import co.uk.niadel.napi.annotations.LoadStateMethod;
import co.uk.niadel.napi.asm.ASMRegistry;
import co.uk.niadel.napi.asm.IASMTransformer;
import co.uk.niadel.napi.events.EventFactory;
import co.uk.niadel.napi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.napi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;

/**
 * Parses mod registers for info and stuff. Mainly used for the @LoadStateMethod annotation.
 */
public class NAPIASMModParsingTransformer implements IASMTransformer, Opcodes
{
	@Override
	public byte[] manipulateBytecodes(String className)
	{
		try
		{
			Object mod = Class.forName(className).newInstance();
			ClassReader classReader = new ClassReader(className);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			for (MethodNode methodNode : classNode.methods)
			{
				parseMethod(methodNode, className, mod);
			}

			for (FieldNode fieldNode : classNode.fields)
			{
				parseField(fieldNode, className, mod);
			}
		}
		catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
		{
			NAPILogHelper.instance.logError(e);
		}

		return null;
	}

	/**
	 * Parses a method for annotations and does stuff.
	 * @param methodNode The method node that is being parsed.
	 * @param className The name of the class that the method belongs to.
	 */
	public void parseMethod(MethodNode methodNode, String className, Object mod)
	{
		if (methodNode.visibleAnnotations != null)
		{
			for (AnnotationNode annotationNode : methodNode.visibleAnnotations)
			{
				if (annotationNode.desc == "co/uk/niadel/napi/annotations/LoadStateMethod")
				{
					try
					{
						Class<?> clazz = Class.forName(className);

						EnumLoadState loadState = clazz.getMethod(methodNode.name, new Class[] {}).getAnnotation(LoadStateMethod.class).value();

						if (loadState == EnumLoadState.PREINIT)
						{
							NModLoader.preInitMethods.put(className, clazz.getDeclaredMethod(methodNode.name, clazz));
						}
						else if (loadState == EnumLoadState.INIT)
						{
							NModLoader.initMethods.put(className, clazz.getDeclaredMethod(methodNode.name, clazz));
						}
						else if (loadState == EnumLoadState.POSTINIT)
						{
							NModLoader.postInitMethods.put(className, clazz.getDeclaredMethod(methodNode.name, clazz));
						}
					}
					catch (ClassNotFoundException | NoSuchMethodException e)
					{
						NAPILogHelper.instance.logError(e);
					}
				}
			}
		}
	}

	/**
	 * Parses a field for annotations.
	 * @param fieldNode The field node.
	 * @param className The name of the class that the field belongs to.
	 * @param mod The mod object that is being parsed by this transformer.
	 */
	public void parseField(FieldNode fieldNode, String className, Object mod) throws IOException
	{
		if (fieldNode.visibleAnnotations != null)
		{
			for (AnnotationNode annotationNode : fieldNode.visibleAnnotations)
			{
				if (annotationNode.desc == "co/uk/niadel/napi/annotations/ASMTransformer")
				{
					String typeClassName = fieldNode.desc.replace("/", ".");

					ClassReader typeReader = new ClassReader(typeClassName);
					ClassNode classNode = new ClassNode();
					typeReader.accept(classNode, 0);

					if (classNode.interfaces.contains("co/uk/niadel/napi/asm/IASMTransformer") && fieldNode.value != null)
					{
						ASMRegistry.registerTransformer((IASMTransformer) fieldNode.value);
					}
				}
				else if (annotationNode.desc == "co/uk/niadel/napi/annotations/EventHandler")
				{
					EventFactory.registerEventHandler(fieldNode.value);
				}
			}
		}
	}
}
