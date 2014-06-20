package co.uk.niadel.mpi.common.block;


/**
 * Contains a list of all types of possible block materials for mods. You still have
 * to have a vanilla one though, this is an old thing from before release to public, back
 * when all blocks extended a mod blocks class. I decided it was too much work and so the current
 * Block registering system we have nowadays came into existence.
 * @author Niadel
 *
 */
public enum EnumBlockTypes 
{
	WOOD,
	ORE,
	STONE,
	DECORATION,
	PORTAL,
	UNDEFINED,
	MINERALBLOCK,
	LEAF;
}
