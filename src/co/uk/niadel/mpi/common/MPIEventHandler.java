package co.uk.niadel.mpi.common;

import net.minecraft.item.Item;
import co.uk.niadel.mpi.events.IEventHandler;
import co.uk.niadel.mpi.events.items.EventItemRightClicked;

/**
 * Used for IThrowable and the more Forge-esque utilities. Is also a reference implementation
 * of an Event Handler.
 * @author Niadel
 *
 */
public class MPIEventHandler implements IEventHandler
{
	@Override
	public void handleEvent(Object event)
	{
		if (event instanceof EventItemRightClicked)
		{
			EventItemRightClicked castEvent = (EventItemRightClicked) event;
			Item thrownItem = castEvent.clickedItem.getItem();
			
			if (thrownItem instanceof IThrowable)
			{
				((IThrowable) thrownItem).throwItem(castEvent.clickedItem, castEvent.world, castEvent.player);
			}
		}
	}
}
