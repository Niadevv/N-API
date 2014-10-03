package co.uk.niadel.napi.common.client;

import co.uk.niadel.napi.annotations.DocumentationAnnotations.Temprorary;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Where you register block renderers.
 *
 * @author Niadel
 */
@Temprorary(versionToBeRemoved = "1.8, when you can use Block Model JSON formatting instead")
public class BlockRenderingRegistry
{
	public static final List<IBlockRenderer> blockRenderers = new ArrayList<>();

	/**
	 * Registers a block renderer.
	 * @param renderer
	 */
	public static final void registerBlockRenderer(IBlockRenderer renderer)
	{
		blockRenderers.add(renderer);
	}

	public static final void callRenderHandlers(Block renderedBlock, int par1, int par2, int par3)
	{
		for (IBlockRenderer blockRenderer : blockRenderers)
		{
			blockRenderer.renderBlock(renderedBlock, par1, par2, par3);
		}
	}
}
