package co.uk.niadel.mpi.events;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import co.uk.niadel.mpi.annotations.MPIAnnotations.EventHandlerMethod;
import co.uk.niadel.mpi.util.ArrayUtils;
import co.uk.niadel.mpi.util.NAPILogHelper;

/**
 * Where events are fired/generated, and event handlers are registered. Replaces EventsList and allows for event handlers
 * with annotations.
 *
 * @author Niadel
 */
public final class EventFactory
{
	private static Map<Object, Method[]> handlers = new HashMap<>();

	public static final void registerEventHandler(Object handler)
	{
		handlers.put(handler, getHandlerMethods(handler));
	}

	public static final Method[] getHandlerMethods(Object handler)
	{
		return handler.getClass().getDeclaredMethods();
	}

	public static final void fireEvent(IEvent event)
	{
		Iterator<Entry<Object, Method[]>> handlersIter = handlers.entrySet().iterator();

		while (handlersIter.hasNext())
		{
			Entry<Object, Method[]> currEntry = handlersIter.next();
			Method[] handlerMethods = currEntry.getValue();
			Object currHandler = currEntry.getKey();

			for (Method method : handlerMethods)
			{
				if (isMethodForEvent(method, event))
				{
					try
					{
						method.invoke(currHandler, event);
					}
					catch (IllegalAccessException | InvocationTargetException e)
					{
						NAPILogHelper.logError("Error invoking method " + method.getName() + " of event handler " + currHandler.getClass().getName() + " for event " + event.getClass().getName() + "!");
						NAPILogHelper.logError("Stacktrace as follows for exception " + e.getClass().getName());
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static final boolean isEventMethod(Method method)
	{
		boolean hasTheAnnotation = false;
		boolean hasEventParam = false;

		//Checks for the @EventHandlerMethod annotation.
		for (Annotation annotation : getMethodAnnotations(method))
		{
			if (annotation.getClass() == EventHandlerMethod.class)
			{
				hasTheAnnotation = true;
				break;
			}
		}

		//Checks for a parameter that is an event and that it only has one parameter.
		if (method.getParameterTypes().length == 1)
		{
			Class<?>[] theParamTypes = method.getParameterTypes();

			if (ArrayUtils.doesArrayContainValue(theParamTypes[0].getInterfaces(), IEvent.class))
			{
				hasEventParam = true;
			}
		}
		else
		{
			throw new RuntimeException("The method " + method.getName() + " must take ONLY ONE event as an argument!");
		}

		if (hasTheAnnotation && hasEventParam)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static final boolean isMethodForEvent(Method method, IEvent event)
	{
		if (isEventMethod(method))
		{
			if (ArrayUtils.doesArrayContainValue(method.getParameterTypes(), event.getClass()))
			{
				return true;
			}
		}

		return false;
	}

	public static final Annotation[] getMethodAnnotations(Method method)
	{
		return method.getDeclaredAnnotations();
	}

	public static final Map<Object, Method[]> getHandlers()
	{
		return handlers;
	}
}
