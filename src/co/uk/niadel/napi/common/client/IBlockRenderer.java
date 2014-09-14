package co.uk.niadel.napi.common.client;

import net.minecraft.block.Block;

/**
 * Allows for custom rendering of blocks. To make your block renderable via this method, make your render type -1.
 * @author Niadel
 */
public interface IBlockRenderer
{
	/**
	 * Does the rendering of the actual block. I don't know what par1, par2 and par3 are.
	 * @param block The block being rendered.
	 */
	public void renderBlock(Block block, int par1, int par2, int par3);
}
