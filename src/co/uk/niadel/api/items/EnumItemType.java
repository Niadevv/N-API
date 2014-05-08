package co.uk.niadel.api.items;

/**
 * The enum that is used for interacting with other mod items.
 * One of the more MPI parts. Not used in current versions of N-API but can still be used
 * by mods. This was added back when ItemRegistry (then called ItemModded) was a base 
 * item for all mod items, but was changed as this caused some issues (IE. It would 
 * mean having to redo the abstract items in mod base forms.
 * @author Niadel
 */
public enum EnumItemType 
{
	MATERIAL,
	ORE,
	TOOL,
	IRON,
	EMERALD,
	STONE,
	BUILDING,
	DECORATION,
	UNOBTAINABLE,
	INDESTRUCTABLE;
}
