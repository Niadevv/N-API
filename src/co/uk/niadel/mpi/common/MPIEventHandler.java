package co.uk.niadel.mpi.common;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import co.uk.niadel.mpi.common.block.IMeasureTransferer;
import co.uk.niadel.mpi.common.gui.GUIRegistry;
import co.uk.niadel.mpi.events.IEventHandler;
import co.uk.niadel.mpi.events.client.EventDisplayModGUI;
import co.uk.niadel.mpi.events.items.EventItemRightClicked;
import co.uk.niadel.mpi.events.world.EventBlockSet;
import co.uk.niadel.mpi.util.MCUtils;

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
			//For throwable items.
			EventItemRightClicked castEvent = (EventItemRightClicked) event;
			Item thrownItem = castEvent.clickedItem.getItem();
			
			if (thrownItem instanceof IThrowable)
			{
				((IThrowable) thrownItem).throwItem(castEvent.clickedItem, castEvent.world, castEvent.player);
			}
		}
		else if (event instanceof EventBlockSet)
		{
			//For measure transferers.
			EventBlockSet castEvent = (EventBlockSet) event;
			Block theBlock = castEvent.blockSet;
			
			if (castEvent.blockSet instanceof IMeasureTransferer)
			{
				IMeasureTransferer blockTransferer = (IMeasureTransferer) castEvent.blockSet;
				World world = castEvent.world;
				int x = castEvent.x, y = castEvent.y, z = castEvent.z;
				
				Block[] blocks = MCUtils.getBlocksRelativeToCoords(world, x, y, z);
				
				for (int i = 0; i == blocks.length; i++)
				{
					if (blockTransferer.canTransferToBlock(blocks[i], i))
					{
						blockTransferer.transferMeasure(blockTransferer, blockTransferer.getMeasure());
					}
				}
			}
		}
		else if (event instanceof EventDisplayModGUI)
		{
			EventDisplayModGUI theEvent = (EventDisplayModGUI) event;
			GUIRegistry.renderers.get(theEvent.guiId).render();
		}
	}
}
