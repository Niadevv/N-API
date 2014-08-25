package co.uk.niadel.mpi.asm;

import co.uk.niadel.mpi.annotations.IAnnotationHandler;
import co.uk.niadel.mpi.annotations.MPIAnnotations;
import co.uk.niadel.mpi.events.EventFactory;
import co.uk.niadel.mpi.events.IEvent;
import co.uk.niadel.mpi.modhandler.ModRegister;
import co.uk.niadel.mpi.util.NAPILogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Optimises Event Handler data getting. Special as it is not actually registered and is called manually.
 */
public final class NAPIASMEventHandlerTransformer implements IASMTransformer, Opcodes
{
	/**
	 * The current event.
	 */
	public static IEvent currentEvent;

	public static final void setCurrentEvent(IEvent newEvent)
	{
		onEventChanged(newEvent);
		currentEvent = newEvent;
	}

	@Override
	public byte[] manipulateBytecodes(String className)
	{
		try
		{
			ClassReader classReader = new ClassReader(className);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);
			MethodNode eventMethodNode = null;
			int currentIteration = 0;

			//If the class is an event handler.
			if (classNode.interfaces.contains("co/uk/niadel/mpi/events/IEventHandler"))
			{
				for (MethodNode methodNode : classNode.methods)
				{
					for (AnnotationNode annotationNode : methodNode.visibleAnnotations)
					{
						if (annotationNode.desc == "Lco/uk/niadel/events/EventHandlerMethod;")
						{
							currentIteration++;
							eventMethodNode = addEventHandlerCall(methodNode, currentIteration);
						}
					}
				}

				if (eventMethodNode != null)
				{
					//Add the return instruction.
					LabelNode labelNode = new LabelNode();
					labelNode.accept(eventMethodNode);
					eventMethodNode.instructions.add(new LineNumberNode(108 + currentIteration, labelNode));
					eventMethodNode.instructions.add(new InsnNode(RETURN));
				}
			}

			return new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES).toByteArray();
		}
		catch (IOException e)
		{
			NAPILogHelper.logError(e);
			NAPILogHelper.logError("Unable to transform event handler class " + className + "!");
		}

		return null;
	}

	private MethodNode addEventHandlerCall(MethodNode eventMethodNode, int currentIteration) throws IOException
	{
		ClassReader evFacClassReader = new ClassReader("co.uk.niadel.mpi.events.EventFactory");
		ClassNode evFacClassNode = new ClassNode();
		evFacClassReader.accept(evFacClassNode, 0);
		MethodNode evFacMethodNode = null;

		//TEMPNOTE .desc for parameters
		String eventMethodParam = eventMethodNode.desc.substring(1, eventMethodNode.desc.length() - 2);
		boolean isValidMethod = true;

		if (eventMethodParam.startsWith("L"))
		{
			if (!(eventMethodParam.replace("/", ".").replace("L", "").replace(";", "") == currentEvent.getClass().getName()))
			{
				isValidMethod = false;
			}
		}
		else
		{
			isValidMethod = false;
			NAPILogHelper.logError("Method " + eventMethodNode.name + " in an event handler does not take an event as a parameter!");
		}

		if (isValidMethod)
		{
			/*
		 	* Add:
		 	* 	public static final void callEventHandlers()
		 	* 	{
		 	* 	    new EventHandler.someMethod(event);
		 	* 	}
		 	*/

			String paramAsType = eventMethodParam.replace("L", "").replace(";", "");

			//Get the method node if it already exists.
			boolean methodFound = false;

			for (MethodNode currMethod : evFacClassNode.methods)
			{
				//Check it's the exact method.
				if (currMethod.name.contains("callAllEventHandlers") && currMethod.access == ACC_PUBLIC + ACC_STATIC + ACC_FINAL && currMethod.desc == "()V" && currMethod.signature == null && currMethod.exceptions == null)
				{
					evFacMethodNode = currMethod;
					methodFound = true;
				}
			}

			//Otherwise, create it.
			if (!methodFound)
			{
				evFacMethodNode = new MethodNode(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "callAllEventHandlers", "()V", null, null);
			}

			//Add the instructions themselves.
			LabelNode labelNode = new LabelNode();
			labelNode.accept(evFacMethodNode);
			evFacMethodNode.instructions.add(new LineNumberNode(107 + currentIteration, labelNode));
			evFacMethodNode.instructions.add(new TypeInsnNode(NEW, paramAsType));
			evFacMethodNode.instructions.add(new InsnNode(DUP));
			evFacMethodNode.instructions.add(new FieldInsnNode(GETSTATIC, "co/uk/niadel/mpi/asm/NAPIASMEventHandlerTransformer", "currentEvent", "Lco/uk/niadel/mpi/events/IEvent"));
			evFacMethodNode.instructions.add(new TypeInsnNode(CHECKCAST, paramAsType));
			evFacMethodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, paramAsType, eventMethodNode.name, "(" + eventMethodParam + ")V", false));
			return evFacMethodNode;
		}

		return null;
	}

	public static void onEventChanged(IEvent newEvent)
	{
		if (newEvent.getClass() != currentEvent.getClass())
		{
			//Recall this transformer.
			ASMRegistry.callASMTransformer(ModRegister.eventHandler);
		}
	}
}
