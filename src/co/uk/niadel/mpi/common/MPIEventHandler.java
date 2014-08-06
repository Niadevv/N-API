package co.uk.niadel.mpi.common;

import co.uk.niadel.mpi.annotations.MPIAnnotations.EventHandlerMethod;
import co.uk.niadel.mpi.common.items.ICustomSpawnEgg;
import co.uk.niadel.mpi.events.items.EventItemUse;
import co.uk.niadel.mpi.util.MCData;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import co.uk.niadel.mpi.common.block.IMeasureTransferer;
import co.uk.niadel.mpi.common.gui.GUIRegistry;
import co.uk.niadel.mpi.events.client.EventDisplayModGUI;
import co.uk.niadel.mpi.events.items.EventItemRightClicked;
import co.uk.niadel.mpi.events.world.EventBlockSet;
import co.uk.niadel.mpi.util.MCUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Used for IThrowable and the more Forge-esque utilities. Is also a reference implementation
 * of an Event Handler.
 * @author Niadel
 *
 */
public class MPIEventHandler
{
	@EventHandlerMethod
	public void handleModDisplayEvent(EventDisplayModGUI event)
	{
		if (MCData.isClientSide())
		{
			GUIRegistry.renderers.get(event.guiId).render();
		}
	}

	@EventHandlerMethod
	public void handleBlockSet(EventBlockSet event)
	{
		if (event.blockSet instanceof IMeasureTransferer && !event.world.isClient)
		{
			IMeasureTransferer blockTransferer = (IMeasureTransferer) event.blockSet;
			World world = event.world;
			int x = event.x, y = event.y, z = event.z;

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

	@EventHandlerMethod
	public void handleItemRightClicked(EventItemRightClicked event)
	{
		if (!event.world.isClient)
		{
			//For throwable items.
			Item thrownItem = event.clickedItem.getItem();

			if (thrownItem instanceof IThrowable)
			{
				((IThrowable) thrownItem).throwItem(event.clickedItem, event.world, event.player);
			}
		}
	}

	@EventHandlerMethod
	public void handleItemUse(EventItemUse event)
	{
		if (event.itemStack.getItem() instanceof ICustomSpawnEgg && !event.world.isClient)
		{
			try
			{
				ICustomSpawnEgg item = (ICustomSpawnEgg) event.itemStack.getItem();
				Entity entity = item.getSpawnedEntity().getConstructor(World.class).newInstance(event.world);
				entity.setLocationAndAngles(event.x + 0.5, event.y + 1, event.z + 0.5, 0, 0);
			}
			catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
			{
				e.printStackTrace();
			}

		}
	}
}
