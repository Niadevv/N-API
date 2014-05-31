package co.uk.niadel.mpi.gen.loot;

public enum EnumLootLocation
{
	JUNGLE("Jungle"),
	DESERT("Desert"),
	DUNGEON("Dungeon");
	
	/**
	 * The ID of the location, generally in a way that makes sense.
	 */
	public String locId;
	
	EnumLootLocation(String lootId)
	{
		this.locId = lootId;
	}
}
