package co.uk.niadel.mpi.common;

import co.uk.niadel.mpi.common.block.IWrenchable;
import co.uk.niadel.mpi.common.items.ICustomSpawnEgg;
import co.uk.niadel.mpi.events.EventHandlerMethod;
import co.uk.niadel.mpi.events.items.EventItemUse;
import co.uk.niadel.mpi.util.MCData;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import co.uk.niadel.mpi.common.block.IMeasureTransferer;
import co.uk.niadel.mpi.common.gui.GUIRegistry;
import co.uk.niadel.mpi.events.client.EventDisplayModGUI;
import co.uk.niadel.mpi.events.items.EventItemRightClicked;
import co.uk.niadel.mpi.events.blocks.EventBlockSet;
import co.uk.niadel.mpi.util.MCUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Used for IThrowableItem and the more Forge-esque utilities. Is also a reference implementation
 * of an Event Handler. This makes modder's lives much easier and is a core part of the common package.
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
			GUIRegistry.renderGUIRenderer(event.guiId);
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
		Item item = event.clickedItem.getItem();

		if (!event.world.isClient)
		{
			//For throwable items.
			if (item instanceof IThrowableItem)
			{
				IThrowableItem throwable = (IThrowableItem) item;

				if (throwable.canThrowItem(event.clickedItem, event.world, event.player))
				{
					//Borrowed, modified (mainly for optimisation purposes) and partially deobfuscated code from EntityThrowable
					final float PI = (float) Math.PI;
					final Entity thrownEntity = throwable.getThrownEntity(event.world, event.player);
					thrownEntity.setLocationAndAngles(event.player.posX, event.player.posY + (double) event.player.getEyeHeight(), event.player.posZ, event.player.rotationYaw, event.player.rotationPitch);
					thrownEntity.posX -= (double)(MathHelper.cos(thrownEntity.rotationYaw / 180.0F * PI) * 0.16F);
					thrownEntity.posY -= 0.10000000149011612D;
					thrownEntity.posZ -= (double)(MathHelper.sin(thrownEntity.rotationYaw / 180.0F * PI) * 0.16F);
					thrownEntity.setPosition(thrownEntity.posX, thrownEntity.posY, thrownEntity.posZ);
					thrownEntity.yOffset = 0.0F;
					final float CONSTANT = 0.4F;
					final float COSVALUE = MathHelper.cos(thrownEntity.rotationPitch / 180.0F * PI);
					thrownEntity.motionX = (double)(-MathHelper.sin(thrownEntity.rotationYaw / 180.0F * PI) * COSVALUE * CONSTANT);
					thrownEntity.motionZ = (double)(MathHelper.cos(thrownEntity.rotationYaw / 180.0F * PI) * COSVALUE * CONSTANT);
					thrownEntity.motionY = (double)(-MathHelper.sin((thrownEntity.rotationPitch) / 180.0F * PI) * CONSTANT);

					event.world.spawnEntityInWorld(thrownEntity);
				}
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
		else if (event.itemStack.getItem() instanceof IWrenchable && !event.world.isClient)
		{
			((IWrenchable) event.itemStack.getItem()).onWrench(event.itemStack, event.clicker, event.world, event.world.getBlock(event.x, event.y, event.z), event.x, event.y, event.z, event.side);
		}
	}
}
